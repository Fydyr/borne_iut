#!/bin/bash

if [ -d "$HOME/git/MG2D" ]; then
   echo "MG2D folder exists in user's home."
   DEPENDENCIES="$HOME/git/MG2D/"
else
   if [ -f "../MG2D.jar" ]; then
      echo "MG2D.jar exists."
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

   #PENSER A REMETTRE COMPILATION JEUX!!!
   for i in *
   do
       cd $i
       # Vérifier s'il y a des fichiers .java dans ce dossier
       if ls *.java 1> /dev/null 2>&1; then
           echo "Compilation du jeu "$i
           echo "Veuillez patienter"
           javac -cp .:../..:"$DEPENDENCIES" *.java
       else
           echo "Pas de fichiers Java dans "$i", compilation ignorée"
       fi
       cd ..
   done
fi

cd ..