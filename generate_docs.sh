#!/bin/bash
echo "============================================"
echo "  Generation de la documentation"
echo "  Borne Arcade IUT"
echo "============================================"
echo

if ! command -v doxygen &> /dev/null; then
    echo "ERREUR : Doxygen n'est pas installe."
    echo "Installez-le avec : sudo apt-get install doxygen"
    exit 1
fi

echo "Lancement de Doxygen..."
doxygen Doxyfile

if [ $? -eq 0 ]; then
    echo
    echo "Documentation generee avec succes !"
    echo "Ouvrir : docs/generated/html/index.html"
else
    echo
    echo "ERREUR lors de la generation."
    exit 1
fi
