#!/bin/bash
xdotool mousemove 1280 1024
cd projet/Pong
java -cp .:../..:$HOME/git/MG2D Main
