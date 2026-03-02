#!bin/bash

sudo apt install openjdk-25-jdk python3.13 love git pip python3-pygame -y

git submodule init --recursive
git submodule update --recursive
sudo apt install openjdk-25-jdk python3.13 python3.13-venv love git pip python3-pygame -y

git submodule update --init --recursive