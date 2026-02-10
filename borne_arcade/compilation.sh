#!/bin/bash

if [ -f "../MG2D.jar" ]; then
   echo "MG2D.jar exists."
   DEPENDENCIES="../MG2D.jar"
else
   if [ -d "~/git/MG2D" ]; then
      echo "MG2D folder exists in user’s home."
      DEPENDENCIES="~/git/MG2D/"
   else
      echo "No MG2D dependency found"
	DEPENDENCIES=null
   fi
fi


if [ -n "$DEPENDENCIES" ]; then
   echo "Compilation du menu de la borne d'arcade"
   echo "Veuillez patienter"
   javac -cp .:$FOLDER *.java

   cd projet

   #PENSER A REMETTRE COMPILATION JEUX!!!
   for i in *
   do
       cd $i
       echo "Compilation du jeu "$i
       echo "Veuillez patienter"
       javac -cp .:../..:$FOLDER *.java
       cd ..
   done
fi

cd ..
