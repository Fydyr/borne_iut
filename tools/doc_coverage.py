#!/usr/bin/env python3
"""
doc_coverage.py - Detecte les fonctions et classes publiques sans documentation.

Supporte Java (Javadoc), Python (docstrings) et Lua (--- comments).

Usage:
    python doc_coverage.py <source_dir> [--threshold 50] [--json] [--output report.txt]

Exemple:
    python doc_coverage.py borne_arcade/
    python doc_coverage.py borne_arcade/ --threshold 60 --json --output coverage.json
"""

import sys
import os
import re
import ast
import json
import argparse


def find_files(source_dir, extensions):
    """Trouve tous les fichiers avec les extensions donnees."""
    files = []
    for root, _, filenames in os.walk(source_dir):
        # Ignorer les repertoires generes
        if any(skip in root for skip in ["generated", "__pycache__", ".git", "node_modules"]):
            continue
        for filename in filenames:
            if any(filename.endswith(ext) for ext in extensions):
                files.append(os.path.join(root, filename))
    return files


# ============================================================
# Analyse Java
# ============================================================

def analyze_java_file(filepath):
    """Analyse un fichier Java pour detecter les declarations publiques sans Javadoc."""
    try:
        with open(filepath, "r", encoding="utf-8", errors="replace") as f:
            content = f.read()
    except (OSError, PermissionError):
        return [], []

    lines = content.split("\n")
    documented = []
    undocumented = []

    # Pattern pour les declarations publiques
    public_pattern = re.compile(
        r"^\s*public\s+(?:static\s+)?(?:final\s+)?(?:abstract\s+)?"
        r"(?:class|interface|enum|[\w<>\[\]]+\s+\w+\s*\()"
    )

    for i, line in enumerate(lines):
        match = public_pattern.search(line)
        if not match:
            continue

        # Extraire le nom de la declaration
        name = extract_java_name(line)
        if not name:
            continue

        # Verifier si un commentaire Javadoc precede
        has_javadoc = check_javadoc_before(lines, i)
        entry = {
            "file": os.path.relpath(filepath),
            "line": i + 1,
            "name": name,
            "type": "java",
        }

        if has_javadoc:
            documented.append(entry)
        else:
            undocumented.append(entry)

    return documented, undocumented


def extract_java_name(line):
    """Extrait le nom d'une declaration Java."""
    line = line.strip()
    # Classe/interface/enum
    match = re.search(r"(class|interface|enum)\s+(\w+)", line)
    if match:
        return f"{match.group(1)} {match.group(2)}"
    # Methode
    match = re.search(r"(\w+)\s*\(", line)
    if match:
        return f"method {match.group(1)}"
    return None


def check_javadoc_before(lines, index):
    """Verifie s'il y a un commentaire Javadoc avant la ligne donnee."""
    # Remonter les lignes precedentes pour trouver */
    i = index - 1
    while i >= 0:
        stripped = lines[i].strip()
        if stripped == "":
            i -= 1
            continue
        if stripped == "*/" or stripped.endswith("*/"):
            # Remonter pour trouver /**
            j = i - 1
            while j >= 0:
                if "/**" in lines[j]:
                    return True
                j -= 1
            return False
        if stripped.startswith("@") or stripped.startswith("*"):
            i -= 1
            continue
        return False
    return False


# ============================================================
# Analyse Python
# ============================================================

def analyze_python_file(filepath):
    """Analyse un fichier Python pour detecter les fonctions/classes sans docstring."""
    try:
        with open(filepath, "r", encoding="utf-8", errors="replace") as f:
            source = f.read()
    except (OSError, PermissionError):
        return [], []

    try:
        tree = ast.parse(source, filename=filepath)
    except SyntaxError:
        return [], []

    documented = []
    undocumented = []

    for node in ast.walk(tree):
        if not isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
            continue

        # Ignorer les methodes privees (commencant par _)
        if node.name.startswith("_") and node.name != "__init__":
            continue

        has_docstring = (
            node.body
            and isinstance(node.body[0], ast.Expr)
            and isinstance(node.body[0].value, (ast.Constant, ast.Str))
        )

        entry = {
            "file": os.path.relpath(filepath),
            "line": node.lineno,
            "name": f"{'class' if isinstance(node, ast.ClassDef) else 'function'} {node.name}",
            "type": "python",
        }

        if has_docstring:
            documented.append(entry)
        else:
            undocumented.append(entry)

    return documented, undocumented


# ============================================================
# Analyse Lua
# ============================================================

def analyze_lua_file(filepath):
    """Analyse un fichier Lua pour detecter les fonctions sans commentaire ---."""
    try:
        with open(filepath, "r", encoding="utf-8", errors="replace") as f:
            content = f.read()
    except (OSError, PermissionError):
        return [], []

    lines = content.split("\n")
    documented = []
    undocumented = []

    func_pattern = re.compile(r"^\s*(?:local\s+)?function\s+(\w[\w.:]*)\s*\(")

    for i, line in enumerate(lines):
        match = func_pattern.search(line)
        if not match:
            continue

        name = match.group(1)
        # Verifier si un commentaire --- precede
        has_doc = False
        j = i - 1
        while j >= 0:
            stripped = lines[j].strip()
            if stripped == "":
                j -= 1
                continue
            if stripped.startswith("---"):
                has_doc = True
            break

        entry = {
            "file": os.path.relpath(filepath),
            "line": i + 1,
            "name": f"function {name}",
            "type": "lua",
        }

        if has_doc:
            documented.append(entry)
        else:
            undocumented.append(entry)

    return documented, undocumented


# ============================================================
# Rapport
# ============================================================

def generate_report(documented, undocumented, threshold):
    """Genere le rapport de couverture."""
    total = len(documented) + len(undocumented)
    if total == 0:
        return {"coverage": 100.0, "total": 0, "documented": 0, "undocumented": 0, "details": []}

    coverage = (len(documented) / total) * 100

    report = {
        "coverage": round(coverage, 1),
        "total": total,
        "documented": len(documented),
        "undocumented": len(undocumented),
        "threshold": threshold,
        "pass": coverage >= threshold,
        "details": undocumented,
    }

    return report


def print_report(report):
    """Affiche le rapport en format texte."""
    print("=" * 60)
    print("  RAPPORT DE COUVERTURE DOCUMENTATION")
    print("=" * 60)
    print()
    print(f"  Couverture : {report['coverage']}%")
    print(f"  Seuil      : {report['threshold']}%")
    print(f"  Statut     : {'PASSE' if report['pass'] else 'ECHEC'}")
    print()
    print(f"  Total declarations   : {report['total']}")
    print(f"  Documentees          : {report['documented']}")
    print(f"  Non documentees      : {report['undocumented']}")
    print()

    if report["details"]:
        print("  Declarations sans documentation :")
        print("  " + "-" * 56)

        # Grouper par fichier
        by_file = {}
        for item in report["details"]:
            f = item["file"]
            if f not in by_file:
                by_file[f] = []
            by_file[f].append(item)

        for filepath, items in sorted(by_file.items()):
            print(f"\n  {filepath}")
            for item in items:
                print(f"    L{item['line']:4d} : {item['name']}")

    print()
    print("=" * 60)


def main():
    parser = argparse.ArgumentParser(description="Analyse de couverture de documentation")
    parser.add_argument("source_dir", help="Repertoire source a analyser")
    parser.add_argument("--threshold", type=float, default=50.0, help="Seuil minimum de couverture (defaut: 50%%)")
    parser.add_argument("--json", action="store_true", help="Sortie en format JSON")
    parser.add_argument("--output", help="Fichier de sortie (defaut: stdout)")
    args = parser.parse_args()

    if not os.path.isdir(args.source_dir):
        print(f"Erreur : {args.source_dir} n'est pas un repertoire valide.")
        sys.exit(1)

    all_documented = []
    all_undocumented = []

    # Analyser Java
    for f in find_files(args.source_dir, [".java"]):
        doc, undoc = analyze_java_file(f)
        all_documented.extend(doc)
        all_undocumented.extend(undoc)

    # Analyser Python
    for f in find_files(args.source_dir, [".py"]):
        doc, undoc = analyze_python_file(f)
        all_documented.extend(doc)
        all_undocumented.extend(undoc)

    # Analyser Lua
    for f in find_files(args.source_dir, [".lua"]):
        doc, undoc = analyze_lua_file(f)
        all_documented.extend(doc)
        all_undocumented.extend(undoc)

    # Generer le rapport
    report = generate_report(all_documented, all_undocumented, args.threshold)

    # Sortie
    if args.json:
        output = json.dumps(report, indent=2, ensure_ascii=False)
    else:
        # Capturer la sortie texte
        import io
        old_stdout = sys.stdout
        sys.stdout = io.StringIO()
        print_report(report)
        output = sys.stdout.getvalue()
        sys.stdout = old_stdout
        print(output)

    if args.output:
        with open(args.output, "w", encoding="utf-8") as f:
            f.write(output)
        print(f"Rapport sauvegarde dans {args.output}")

    # Code retour
    if not report["pass"]:
        sys.exit(1)


if __name__ == "__main__":
    main()
