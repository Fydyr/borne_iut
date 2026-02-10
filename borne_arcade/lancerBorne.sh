#!/bin/bash

setxkbmap borne

if [ -d "~/git/MG2D" ]; then
   echo "MG2D folder exists in user’s home."
   DEPENDENCIES="~/git/MG2D/"
else
   if [ -f "../MG2D.jar" ]; then
      echo "MG2D.jar exists."
      DEPENDENCIES="../MG2D.jar"
   else
      echo "No MG2D dependency found"
	DEPENDENCIES=null
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
   java -cp .:$DEPENDENCIES Main
fi

./clean.sh

for i in {30..1}
do
    echo "Extinction de la borne dans $i secondes"
    sleep 1
done

sudo halt