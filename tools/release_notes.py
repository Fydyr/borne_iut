#!/usr/bin/env python3
"""
release_notes.py - Genere des notes de release a partir des commits Git via Ollama.

Usage:
    python release_notes.py [--from TAG] [--to TAG] [--output CHANGELOG.md]

Exemples:
    python release_notes.py --from v1.0 --to v1.1
    python release_notes.py --from HEAD~20 --to HEAD --output CHANGELOG.md
"""

import sys
import os
import subprocess
import json
import argparse
import requests

OLLAMA_URL = os.environ.get("OLLAMA_URL", "https://ollama.kinator.fr")
OLLAMA_MODEL = os.environ.get("OLLAMA_MODEL", "gemma3:27b")
OLLAMA_FALLBACK_MODEL = os.environ.get("OLLAMA_FALLBACK_MODEL", "qwen2.5:latest")
TIMEOUT = 120


SYSTEM_PROMPT = """Tu es un assistant technique qui genere des notes de release en francais.

A partir d'une liste de commits Git, tu dois :
1. Regrouper les changements par categorie (Nouveautes, Corrections, Ameliorations, Autres)
2. Reformuler chaque changement de maniere claire et orientee utilisateur
3. Ignorer les commits de merge et les commits techniques (gitignore, CI, etc.)
4. Utiliser le format Markdown

Format attendu :
## [Version] - Date

### Nouveautes
- Description des nouvelles fonctionnalites

### Corrections
- Description des bugs corriges

### Ameliorations
- Description des ameliorations

Si aucun changement significatif, ecris simplement :
## [Version] - Date

Mise a jour mineure, corrections diverses.
"""


def get_git_log(from_ref, to_ref):
    """Recupere le log Git entre deux references."""
    try:
        result = subprocess.run(
            ["git", "log", f"{from_ref}...{to_ref}", "--pretty=format:%h %s", "--no-merges"],
            capture_output=True,
            text=True,
            timeout=30,
        )
        if result.returncode != 0:
            # Fallback sans range
            result = subprocess.run(
                ["git", "log", to_ref, "-20", "--pretty=format:%h %s", "--no-merges"],
                capture_output=True,
                text=True,
                timeout=30,
            )
        return result.stdout
    except (subprocess.TimeoutExpired, FileNotFoundError) as e:
        print(f"[WARN] Erreur git log: {e}")
        return ""


def get_latest_tag():
    """Recupere le dernier tag Git."""
    try:
        result = subprocess.run(
            ["git", "describe", "--tags", "--abbrev=0"],
            capture_output=True,
            text=True,
            timeout=10,
        )
        return result.stdout.strip() if result.returncode == 0 else None
    except (subprocess.TimeoutExpired, FileNotFoundError):
        return None


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
            "temperature": 0.4,
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


def generate_fallback_notes(git_log, version):
    """Genere des notes basiques sans IA."""
    from datetime import date

    lines = git_log.strip().split("\n")
    notes = [f"## {version} - {date.today().isoformat()}\n"]

    if lines and lines[0]:
        notes.append("### Changements\n")
        for line in lines:
            if line.strip():
                # Enlever le hash du commit
                parts = line.split(" ", 1)
                msg = parts[1] if len(parts) > 1 else line
                notes.append(f"- {msg}")
    else:
        notes.append("Aucun changement significatif.\n")

    return "\n".join(notes)


def main():
    parser = argparse.ArgumentParser(description="Generation de notes de release")
    parser.add_argument("--from", dest="from_ref", help="Reference de depart (tag ou commit)")
    parser.add_argument("--to", dest="to_ref", default="HEAD", help="Reference de fin (defaut: HEAD)")
    parser.add_argument("--output", help="Fichier de sortie (defaut: stdout)")
    parser.add_argument("--version", help="Nom de la version (defaut: detecte depuis le tag)")
    args = parser.parse_args()

    # Determiner les references
    from_ref = args.from_ref
    if not from_ref:
        from_ref = get_latest_tag()
        if not from_ref:
            from_ref = "HEAD~20"
            print(f"[INFO] Aucun tag trouve, utilisation de {from_ref}")

    version = args.version or args.to_ref
    if version == "HEAD":
        tag = get_latest_tag()
        version = tag if tag else "dev"

    # Recuperer les commits
    git_log = get_git_log(from_ref, args.to_ref)
    if not git_log:
        print("[INFO] Aucun commit trouve entre les references.")
        return

    print(f"[INFO] {len(git_log.splitlines())} commits trouves entre {from_ref} et {args.to_ref}")

    # Generer les notes avec l'IA
    prompt = (
        f"Voici les commits Git pour la version {version} :\n\n"
        f"{git_log}\n\n"
        f"Genere des notes de release en francais pour la version {version}."
    )

    print(f"[INFO] Appel Ollama ({OLLAMA_MODEL})...")
    notes = ollama_chat(prompt, OLLAMA_MODEL)

    if notes is None:
        print(f"[INFO] Fallback sur {OLLAMA_FALLBACK_MODEL}...")
        notes = ollama_chat(prompt, OLLAMA_FALLBACK_MODEL)

    if notes is None:
        print("[WARN] Ollama injoignable. Generation de notes basiques.")
        notes = generate_fallback_notes(git_log, version)

    # Sortie
    if args.output:
        # Si le fichier existe, prependre les nouvelles notes
        existing = ""
        if os.path.isfile(args.output):
            with open(args.output, "r", encoding="utf-8") as f:
                existing = f.read()

        with open(args.output, "w", encoding="utf-8") as f:
            f.write(notes)
            if existing:
                f.write("\n\n" + existing)

        print(f"[INFO] Notes sauvegardees dans {args.output}")
    else:
        print("\n" + notes)


if __name__ == "__main__":
    main()
