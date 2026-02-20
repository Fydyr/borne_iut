#!/bin/bash

if [ -d "$HOME/git/MG2D" ]; then
   echo "Fichier git MG2D present dans le dossier home de l'utilisateur"
   DEPENDENCIES="$HOME/git/MG2D/"
else
   if [ -f "../MG2D.jar" ]; then
      echo "MG2D.jar existe."
      DEPENDENCIES="$(cd .. && pwd)/MG2D.jar"
   else
      echo "No MG2D dependency found"
      DEPENDENCIES=null
   fi
fi


if [ -n "$DEPENDENCIES" ]; then
   echo "Compilation du menu de la borne d'arcade"
   echo "Veuillez patienter"
   javac -cp .:"$DEPENDENCIES" *.java

   cd projet

   for i in *
   do
       cd $i
       # Vérifier s'il y a des fichiers .java dans ce dossier
       if ls *.java 1> /dev/null 2>&1; then
           echo "Compilation du jeu "$i
           echo "Veuillez patienter"
           javac -cp .:../..:"$DEPENDENCIES" *.java
       else
           if find . -maxdepth 2 -type f -name "*.py" | grep -q .; then
               echo "Fichiers Python trouvés dans "$i
               if [ -f "./requirements.txt" ]; then
                   echo "Création d'un venv"

                   # Direct execution without needing to 'source'
                   python3 -m venv ./venv
                   ./venv/bin/pip install -r requirements.txt
               fi
           else
               echo "Pas de fichiers Java ou python dans "$i", compilation ignorée"
           fi
       fi
       cd ..
   done
fi

cd ..