#!/usr/bin/env python3
"""
ai_doc_fill.py - Genere et insere la documentation manquante dans le code source existant.

Scanne un repertoire source, identifie les declarations publiques sans documentation
(via doc_coverage), et utilise Ollama pour generer et inserer inline la documentation
manquante : Javadoc pour Java, docstrings pour Python, commentaires --- pour Lua.

Usage:
    python ai_doc_fill.py <source_dir> [--max-files N] [--dry-run]

Exemples:
    python ai_doc_fill.py borne_arcade/
    python ai_doc_fill.py borne_arcade/ --max-files 5
    python ai_doc_fill.py borne_arcade/ --dry-run
"""

import sys
import os
import json
import argparse
import requests

# Importer les analyseurs depuis doc_coverage (meme repertoire)
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from doc_coverage import find_files, analyze_java_file, analyze_python_file, analyze_lua_file

OLLAMA_URL = os.environ.get("OLLAMA_URL", "https://ollama.kinator.fr")
OLLAMA_MODEL = os.environ.get("OLLAMA_MODEL", "gemma3:27b")
OLLAMA_FALLBACK_MODEL = os.environ.get("OLLAMA_FALLBACK_MODEL", "qwen2.5:latest")
TIMEOUT = 180

SYSTEM_PROMPT = """Tu es un assistant de documentation technique pour un projet de borne d'arcade universitaire.
Tu generes la documentation manquante pour des fonctions et classes dans du code source.

Regles de style :
- Langue : francais
- Java : Javadoc (/** ... */) avec @param et @return si pertinents, meme indentation que la declaration
- Python : docstring triple guillemets inseree comme PREMIERE LIGNE du corps de la fonction/classe, indentee d'un niveau supplementaire par rapport au def/class
- Lua : commentaire --- (triple tiret) avant la fonction, meme indentation que la fonction
- Etre concis et precis, ne pas sur-documenter
- Ne modifier aucune ligne de code existante

Format de reponse (JSON strict, rien d'autre) :
{
    "insertions": [
        {
            "line": <numero de ligne de la declaration (entier)>,
            "doc": "<commentaire complet a inserer, avec indentation correcte>"
        }
    ],
    "summary": "Resume de ce qui a ete documente"
}

Pour Java et Lua : "doc" est insere AVANT la ligne de declaration.
Pour Python : "doc" est insere APRES la ligne def/class (docstring dans le corps).

Si aucune documentation n'est necessaire : {"insertions": [], "summary": "Aucun ajout necessaire"}
Retourner UNIQUEMENT le JSON, sans texte supplementaire."""


def ollama_chat(prompt, model=OLLAMA_MODEL):
    """Envoie un message au modele Ollama et retourne la reponse texte."""
    url = f"{OLLAMA_URL}/api/chat"
    payload = {
        "model": model,
        "messages": [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
        "stream": False,
        "options": {"temperature": 0.2},
    }
    try:
        response = requests.post(url, json=payload, timeout=TIMEOUT)
        response.raise_for_status()
        return response.json().get("message", {}).get("content", "")
    except requests.exceptions.RequestException as e:
        print(f"[WARN] Erreur Ollama ({model}): {e}")
        return None


def detect_language(filepath):
    """Detecte le langage selon l'extension du fichier."""
    return {".java": "java", ".py": "python", ".lua": "lua"}.get(
        os.path.splitext(filepath)[1].lower(), "text"
    )


def read_file_safe(path):
    """Lit un fichier en gerant les erreurs d'encodage."""
    try:
        with open(path, "r", encoding="utf-8", errors="replace") as f:
            return f.read()
    except (OSError, PermissionError):
        return ""


def parse_ai_response(response_text):
    """Extrait et parse le JSON contenu dans la reponse de l'IA."""
    start = response_text.find("{")
    end = response_text.rfind("}") + 1
    if start == -1 or end == 0:
        return None
    try:
        return json.loads(response_text[start:end])
    except json.JSONDecodeError:
        return None


def apply_insertions(file_content, insertions, is_python):
    """
    Insere les blocs de documentation dans le fichier.

    Traitement de bas en haut pour conserver les numeros de lignes corrects.
    - Java / Lua : insertion AVANT la ligne de declaration.
    - Python     : insertion APRES la ligne def/class (docstring dans le corps).
    """
    lines = file_content.split("\n")

    for ins in sorted(insertions, key=lambda x: x.get("line", 0), reverse=True):
        line_num = ins.get("line", 0)
        doc = ins.get("doc", "")

        if not doc or line_num <= 0 or line_num > len(lines):
            continue

        doc_lines = doc.split("\n")

        if is_python:
            # Inserer apres la ligne def/class
            lines = lines[:line_num] + doc_lines + lines[line_num:]
        else:
            # Inserer avant la declaration
            lines = lines[:line_num - 1] + doc_lines + lines[line_num - 1:]

    return "\n".join(lines)


def process_file(filepath, undocumented_items, dry_run=False):
    """
    Traite un fichier source pour y inserer la documentation manquante.

    Retourne (success: bool, message: str).
    """
    content = read_file_safe(filepath)
    if not content:
        return False, "Fichier illisible"

    lang = detect_language(filepath)
    is_python = (lang == "python")

    # Limiter la taille envoyee au modele
    max_chars = 6000
    displayed = content[:max_chars] + (
        "\n\n... (fichier tronque) ..." if len(content) > max_chars else ""
    )

    items_desc = "\n".join(
        f"- Ligne {item['line']}: {item['name']}"
        for item in undocumented_items
    )

    prompt = (
        f"Fichier : {os.path.relpath(filepath)}\n"
        f"Langage : {lang}\n\n"
        f"```{lang}\n{displayed}\n```\n\n"
        f"Declarations sans documentation :\n{items_desc}\n\n"
        f"Genere la documentation manquante pour ces declarations uniquement."
    )

    print(f"  [INFO] Appel Ollama ({len(undocumented_items)} declaration(s))...")
    response = ollama_chat(prompt, OLLAMA_MODEL)

    if response is None:
        print(f"  [INFO] Fallback sur {OLLAMA_FALLBACK_MODEL}...")
        response = ollama_chat(prompt, OLLAMA_FALLBACK_MODEL)

    if response is None:
        return False, "Ollama injoignable"

    result = parse_ai_response(response)
    if result is None:
        return False, "Reponse IA non parsable"

    insertions = result.get("insertions", [])
    summary = result.get("summary", "Documente")

    if not insertions:
        return True, "Aucune insertion generee"

    new_content = apply_insertions(content, insertions, is_python)

    if not dry_run:
        try:
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(new_content)
            print(f"  [OK] {len(insertions)} documentation(s) inseree(s)")
        except (OSError, PermissionError) as e:
            return False, f"Erreur ecriture: {e}"
    else:
        print(f"  [DRY-RUN] {len(insertions)} documentation(s) seraient inserees")

    return True, summary


def main():
    parser = argparse.ArgumentParser(
        description="Genere et insere la documentation manquante dans le code source"
    )
    parser.add_argument("source_dir", help="Repertoire source a analyser")
    parser.add_argument(
        "--max-files", type=int, default=10,
        help="Nombre maximum de fichiers a traiter par execution (defaut: 10)",
    )
    parser.add_argument(
        "--dry-run", action="store_true",
        help="Analyser sans modifier les fichiers",
    )
    args = parser.parse_args()

    if not os.path.isdir(args.source_dir):
        print(f"Erreur : '{args.source_dir}' n'est pas un repertoire valide.")
        sys.exit(1)

    print(f"[INFO] Analyse de '{args.source_dir}'...")

    # Collecter les elements non documentes par fichier
    undoc_by_file = {}

    for filepath in find_files(args.source_dir, [".java"]):
        _, undoc = analyze_java_file(filepath)
        if undoc:
            undoc_by_file[filepath] = undoc

    for filepath in find_files(args.source_dir, [".py"]):
        _, undoc = analyze_python_file(filepath)
        if undoc:
            undoc_by_file[filepath] = undoc

    for filepath in find_files(args.source_dir, [".lua"]):
        _, undoc = analyze_lua_file(filepath)
        if undoc:
            undoc_by_file[filepath] = undoc

    if not undoc_by_file:
        print("[INFO] Tout le code est deja documente.")
        with open("doc_fill_summary.txt", "w", encoding="utf-8") as f:
            f.write("Tout le code est deja documente. Aucune modification necessaire.")
        return

    total_undoc = sum(len(v) for v in undoc_by_file.values())
    print(
        f"[INFO] {len(undoc_by_file)} fichier(s) avec "
        f"{total_undoc} declaration(s) non documentee(s)."
    )

    # Prioriser les fichiers avec le plus de declarations manquantes
    sorted_files = sorted(undoc_by_file.items(), key=lambda x: len(x[1]), reverse=True)
    to_process = sorted_files[:args.max_files]
    skipped_count = len(sorted_files) - len(to_process)

    if skipped_count > 0:
        print(
            f"[INFO] Limite a {args.max_files} fichiers "
            f"({skipped_count} autre(s) a traiter ulterieurement)."
        )

    # Traiter chaque fichier
    summaries = []
    for i, (filepath, items) in enumerate(to_process, 1):
        rel = os.path.relpath(filepath)
        print(f"\n[{i}/{len(to_process)}] {rel} ({len(items)} manquante(s))")
        success, msg = process_file(filepath, items, dry_run=args.dry_run)
        status = "OK" if success else "ECHEC"
        summaries.append(f"- [{status}] **{rel}** : {msg}")

    # Construire et ecrire le resume
    result_lines = [f"Documentation generee pour {len(to_process)} fichier(s).\n"]
    result_lines += summaries
    if skipped_count > 0:
        result_lines.append(
            f"\n{skipped_count} fichier(s) restant(s) non traite(s) "
            f"(relancer pour continuer)."
        )

    full_summary = "\n".join(result_lines)
    with open("doc_fill_summary.txt", "w", encoding="utf-8") as f:
        f.write(full_summary)

    print(f"\n[INFO] Termine. Resume dans doc_fill_summary.txt")
    if args.dry_run:
        print("[DRY-RUN] Aucun fichier modifie.")


if __name__ == "__main__":
    main()
