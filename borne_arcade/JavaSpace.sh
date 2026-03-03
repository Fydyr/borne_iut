#!/bin/bash
xdotool mousemove 1280 1024
cd projet/JavaSpace
touch highscore
java -cp .:../..:/home/$USER/git/MG2D Main
