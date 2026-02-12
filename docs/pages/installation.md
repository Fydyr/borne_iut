# Installation {#installation}

## Systeme d'exploitation

Installez **Raspbian** (Raspberry Pi OS) sur votre Raspberry Pi 3.

## Outils necessaires

### Java JDK

```bash
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

### Git

```bash
sudo apt-get install git
```

### Python 3 et Pygame (pour les jeux Python)

```bash
sudo apt-get install python3 python3-pip
pip3 install pygame==2.6.1
```

### LOVE 2D (pour les jeux Lua)

```bash
sudo apt-get install love
```

### xdotool (deplacement du curseur)

```bash
sudo apt-get install xdotool
```

## Telechargement du projet

Creez un repertoire de travail et clonez le depot :

```bash
cd ~
mkdir -p git && cd git
git clone <URL_DU_DEPOT>/borne_arcade.git
```

> **Note** : La bibliotheque MG2D est telechargee automatiquement par le script de lancement si elle n'est pas deja presente.

## Lancement

Le script `lancerBorne.sh` gere tout automatiquement :

```bash
cd ~/git/borne_arcade
chmod +x lancerBorne.sh
./lancerBorne.sh
```

Ce script :
1. Telecharge MG2D automatiquement si absent (dans `~/git/MG2D/`)
2. Nettoie les fichiers compiles precedents
3. Compile les fichiers Java du menu et de chaque jeu
4. Lance le menu de la borne
5. A la fermeture, nettoie et eteint la borne apres 30 secondes

## Lancement automatique au demarrage

Pour lancer la borne automatiquement au demarrage du Raspberry Pi :

```bash
mv borne.desktop ~/.config/autostart/
```

Au redemarrage, le menu s'ouvrira automatiquement apres 10-15 secondes.

## Generation de la documentation

### Prerequis

Installez Doxygen :
```bash
sudo apt-get install doxygen
```

### Generer la doc

```bash
# Linux
./generate_docs.sh

# Windows
generate_docs.bat
```

La documentation HTML est generee dans `docs/generated/html/`.
