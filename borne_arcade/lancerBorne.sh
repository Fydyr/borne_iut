#!/bin/bash

setxkbmap borne


if [ -f "../MG2D.jar" ]; then
   echo "MG2D.jar exists."
   DEPENDENCIES="../"
else
   if [ -d "~/git/MG2D" ]; then
      echo "MG2D folder exists in user’s home."
      DEPENDENCIES="~/git/MG2D/"
   else
      echo "No MG2D dependency found"
	DEPENDENCIES=null
   fi
fi

FOLDER="~/git/borne_arcade"

if [-d "$FOLDER"]; then
   cd $FOLDER
   echo "nettoyage des répertoires"
   echo "Veuillez patienter"
   ./clean.sh
   ./compilation.sh
else
   echo "Aucun dossier $FOLDER trouvé, création du dossier"
   mkdir -p $FOLDER
   cd $FOLDER
   ./compilation.sh
fi

echo "Lancement du  Menu"
echo "Veuillez patienter"

if [ -n "$DEPENDENCIES" ]; then
   java -cp .:$DEPENDENCIES Main
fi

./clean.sh

for i in {30..1}
do
    echo "Extinction de la borne dans $i secondes"
    sleep 1
done

sudo halt
