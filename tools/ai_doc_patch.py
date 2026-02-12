#!/usr/bin/env python3
"""
ai_doc_patch.py - Analyse un diff Git et propose des mises a jour de documentation via Ollama.

Usage:
    python ai_doc_patch.py <diff_file> <docs_dir> <readme_path>

Exemple:
    python ai_doc_patch.py changes.diff docs/ README.md
"""

import sys
import os
import json
import requests

OLLAMA_URL = os.environ.get("OLLAMA_URL", "https://ollama.kinator.fr")
OLLAMA_MODEL = os.environ.get("OLLAMA_MODEL", "gemma3:27b")
OLLAMA_FALLBACK_MODEL = os.environ.get("OLLAMA_FALLBACK_MODEL", "qwen2.5:latest")
TIMEOUT = 120  # secondes

STYLE_GUIDE = """
Regles de style pour la documentation :
- Langue : francais
- Java : commentaires Javadoc (/** ... */)
- Python : docstrings triple quotes
- Lua : commentaires --- (triple tiret)
- Markdown : titres avec #, listes avec -, code avec ```
- Etre concis et precis
- Ne pas ajouter de documentation inutile
- Se concentrer sur les changements du diff
"""

SYSTEM_PROMPT = f"""Tu es un assistant de documentation technique pour un projet de borne d'arcade.
Tu analyses les changements de code (diff Git) et tu proposes des mises a jour de documentation.

{STYLE_GUIDE}

Tu dois :
1. Identifier les fonctions/classes modifiees ou ajoutees dans le diff
2. Verifier si la documentation existante couvre ces changements
3. Proposer des modifications UNIQUEMENT pour les fichiers de doc existants
4. Generer un resume des changements pour le commentaire de PR

Format de sortie attendu (JSON) :
{{
    "patches": [
        {{
            "file": "chemin/du/fichier",
            "description": "description de la modification",
            "content": "nouveau contenu ou patch"
        }}
    ],
    "summary": "Resume des changements de documentation proposes"
}}

Si aucune mise a jour de doc n'est necessaire, retourne :
{{
    "patches": [],
    "summary": "Aucune mise a jour de documentation necessaire."
}}
"""


def ollama_chat(prompt, model=OLLAMA_MODEL):
    """Envoie un message au modele Ollama et retourne la reponse."""
    url = f"{OLLAMA_URL}/api/chat"
    payload = {
        "model": model,
        "messages": [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
        "stream": False,
        "options": {
            "temperature": 0.3,
        },
    }

    try:
        response = requests.post(url, json=payload, timeout=TIMEOUT)
        response.raise_for_status()
        data = response.json()
        return data.get("message", {}).get("content", "")
    except requests.exceptions.RequestException as e:
        print(f"[WARN] Erreur avec le modele {model}: {e}")
        return None


def read_file_safe(path):
    """Lit un fichier en gerant les erreurs."""
    try:
        with open(path, "r", encoding="utf-8") as f:
            return f.read()
    except (FileNotFoundError, PermissionError):
        return ""


def collect_doc_files(docs_dir):
    """Collecte le contenu des fichiers de documentation."""
    doc_contents = {}
    if not os.path.isdir(docs_dir):
        return doc_contents

    for root, _, files in os.walk(docs_dir):
        for filename in files:
            if filename.endswith((".md", ".txt", ".html")):
                filepath = os.path.join(root, filename)
                rel_path = os.path.relpath(filepath)
                doc_contents[rel_path] = read_file_safe(filepath)

    return doc_contents


def parse_ai_response(response_text):
    """Parse la reponse JSON de l'IA."""
    # Chercher le bloc JSON dans la reponse
    start = response_text.find("{")
    end = response_text.rfind("}") + 1

    if start == -1 or end == 0:
        return None

    try:
        return json.loads(response_text[start:end])
    except json.JSONDecodeError:
        return None


def apply_patches(patches):
    """Applique les patches proposes par l'IA."""
    applied = []
    for patch in patches:
        filepath = patch.get("file", "")
        content = patch.get("content", "")
        description = patch.get("description", "")

        if not filepath or not content:
            continue

        try:
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(content)
            applied.append(f"- {filepath}: {description}")
            print(f"[OK] Patch applique : {filepath}")
        except (OSError, PermissionError) as e:
            print(f"[WARN] Impossible d'ecrire {filepath}: {e}")

    return applied


def main():
    if len(sys.argv) < 4:
        print(f"Usage: {sys.argv[0]} <diff_file> <docs_dir> <readme_path>")
        sys.exit(1)

    diff_file = sys.argv[1]
    docs_dir = sys.argv[2]
    readme_path = sys.argv[3]

    # Lire le diff
    diff_content = read_file_safe(diff_file)
    if not diff_content:
        print("[INFO] Diff vide, rien a faire.")
        return

    # Limiter la taille du diff pour eviter de depasser les limites du modele
    max_diff_chars = 8000
    if len(diff_content) > max_diff_chars:
        diff_content = diff_content[:max_diff_chars] + "\n\n... (diff tronque) ..."

    # Lire les fichiers de doc existants
    doc_files = collect_doc_files(docs_dir)
    readme_content = read_file_safe(readme_path)

    # Construire le prompt
    prompt_parts = [
        "Voici le diff Git des changements de code :\n",
        "```diff\n" + diff_content + "\n```\n",
        "\nVoici le README actuel :\n",
        "```markdown\n" + readme_content + "\n```\n",
    ]

    if doc_files:
        prompt_parts.append("\nFichiers de documentation existants :\n")
        for path, content in doc_files.items():
            # Limiter chaque fichier
            truncated = content[:2000] if len(content) > 2000 else content
            prompt_parts.append(f"\n--- {path} ---\n{truncated}\n")

    prompt_parts.append(
        "\nAnalyse ces changements et propose des mises a jour de documentation si necessaire."
    )
    prompt = "".join(prompt_parts)

    # Appel IA - modele principal puis fallback
    print(f"[INFO] Appel Ollama ({OLLAMA_MODEL})...")
    response = ollama_chat(prompt, OLLAMA_MODEL)

    if response is None:
        print(f"[INFO] Fallback sur {OLLAMA_FALLBACK_MODEL}...")
        response = ollama_chat(prompt, OLLAMA_FALLBACK_MODEL)

    if response is None:
        print("[WARN] Ollama injoignable. Aucune suggestion de documentation generee.")
        # Ecrire un resume vide pour ne pas bloquer le workflow
        with open("doc_patch_summary.txt", "w", encoding="utf-8") as f:
            f.write("Ollama injoignable - aucune suggestion generee automatiquement.")
        return

    # Parser la reponse
    result = parse_ai_response(response)

    if result is None:
        print("[WARN] Reponse IA non parsable. Sauvegarde de la reponse brute.")
        with open("doc_patch_summary.txt", "w", encoding="utf-8") as f:
            f.write("Reponse IA (format libre) :\n\n" + response)
        return

    # Appliquer les patches
    patches = result.get("patches", [])
    summary = result.get("summary", "Aucun resume disponible.")

    if patches:
        applied = apply_patches(patches)
        print(f"\n[INFO] {len(applied)} patch(es) applique(s).")
    else:
        print("[INFO] Aucune mise a jour de documentation necessaire.")

    # Sauvegarder le resume
    with open("doc_patch_summary.txt", "w", encoding="utf-8") as f:
        f.write(summary)
        if patches:
            f.write("\n\n### Modifications appliquees\n")
            for patch in patches:
                f.write(f"- **{patch.get('file', '?')}** : {patch.get('description', '')}\n")

    print(f"\n[INFO] Resume sauvegarde dans doc_patch_summary.txt")
    print(f"Resume : {summary}")


if __name__ == "__main__":
    main()
