#!/bin/bash

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
if setxkbmap -I"$SCRIPT_DIR" borne -print | xkbcomp -I"$SCRIPT_DIR" - "$DISPLAY" 2>/dev/null; then
    echo "Mapping des touches appliqué avec succès"
else
    echo "Erreur lors du mapping des touches"
fi

if [ -d "$HOME/git/MG2D" ]; then
   echo "Fichier git MG2D present dans le dossier home de l'utilisateur"
   DEPENDENCIES="$HOME/git/MG2D/"
else
   if [ -f "../MG2D.jar" ]; then
      echo "MG2D.jar existe."
      DEPENDENCIES="$(cd .. && pwd)/MG2D.jar"
   else
      echo "No MG2D dependency found"
      if ! command -v git >/dev/null 2>&1; then
         DEPENDENCIES=null
      else
         echo "Installation de MG2D par git dans le dossier ~/git/MG2D"
         mkdir -p $HOME/git/MG2D
         git clone https://github.com/synave/MG2D $HOME/git/MG2D
         DEPENDENCIES="$HOME/git/MG2D/"
      fi
   fi
fi

FOLDER=$(dirname "$0")


cd $FOLDER
echo "nettoyage des répertoires"
echo "Veuillez patienter"
./clean.sh
./compilation.sh

echo "Lancement du  Menu"
echo "Veuillez patienter"

if [ -n "$DEPENDENCIES" ]; then
   java -cp .:"$DEPENDENCIES" Main
fi

./clean.sh

for i in {30..1}
do
    echo "Extinction de la borne dans $i secondes"
    sleep 1
done

# sudo halt