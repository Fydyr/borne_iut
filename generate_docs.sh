#!/bin/bash
# Generation de la documentation Doxygen
# Usage: ./generate_docs.sh

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if ! command -v doxygen &> /dev/null; then
    echo "Erreur : Doxygen n'est pas installe."
    echo "  Ubuntu/Debian : sudo apt-get install doxygen"
    echo "  macOS         : brew install doxygen"
    exit 1
fi

echo "Generation de la documentation..."
doxygen Doxyfile

echo ""
echo "Documentation generee dans docs/generated/html/"
echo "Ouvrez docs/generated/html/index.html dans votre navigateur."

# Ouvrir automatiquement si possible
if command -v xdg-open &> /dev/null; then
    xdg-open docs/generated/html/index.html 2>/dev/null &
elif command -v open &> /dev/null; then
    open docs/generated/html/index.html
fi
