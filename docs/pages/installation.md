# Guide d'Installation {#installation}

Ce guide couvre l'installation complète de la borne d'arcade, du système d'exploitation
aux jeux.

## Prérequis matériel

- **Raspberry Pi 3** (ou supérieur)
- **Écran 4:3** de résolution 1280x1024
- **Borne 2 joueurs** : joystick et 6 boutons par joueur
- Encodeur clavier USB reliant les boutons/joysticks au Pi

## Installation du système d'exploitation

Installez **Raspberry Pi OS** sur la carte SD du Raspberry Pi.

Référez-vous à la documentation officielle :
[https://www.raspberrypi.com/software/](https://www.raspberrypi.com/software/)

## Installation des dépendances

### Java (menu principal + jeux Java)

```bash
sudo apt-get update
sudo apt-get install openjdk-25-jdk
```

### Git

```bash
sudo apt-get install git
```

### Python et Pygame (jeux Python)

```bash
sudo apt-get install python3 python3-pip
pip3 install pygame
```

Les jeux Python (TronGame, PianoTile, OsuTile, Ball Blast) nécessitent
Python 3.6+ et Pygame 2.0+.

### LÖVE 2D (jeux Lua)

```bash
sudo apt-get install love
```

Le jeu CursedWare nécessite le framework LÖVE 2D.

## Clonage du projet

Créez un répertoire de travail et clonez le dépôt nécessaire :

```bash
cd ~
mkdir git
cd git
git clone <url-du-depot>/borne_arcade.git
```

L'arborescence attendue est :

```
~/git/
  ├── MG2D/
  └── borne_arcade/
```

> **Important** : MG2D doit être dans le même répertoire parent que `borne_arcade`
> pour que la compilation Java fonctionne.

## Compilation du menu

Depuis le répertoire `borne_arcade/` :

```bash
cd ~/git/borne_arcade
.lancement_borne.sh
```

Ou manuellement :

```bash
javac -cp .:../MG2D/ *.java
```

## Lancement automatique au démarrage

Pour que le menu se lance automatiquement au démarrage du Raspberry Pi :

```bash
mv borne.desktop ~/.config/autostart/
```

Après redémarrage, un terminal s'ouvrira et l'interface de la borne se lancera
après 10 à 15 secondes de chargement.

## Lancement manuel

```bash
cd ~/git/borne_arcade
./run.sh
```

## Vérification de l'installation

1. Le menu principal s'affiche en plein écran
2. La navigation fonctionne avec le joystick du joueur 1 (haut/bas)
3. La sélection d'un jeu fonctionne avec le bouton A du joueur 1
4. Les jeux se lancent et se ferment correctement
5. Le retour au menu fonctionne après la fermeture d'un jeu

## Résolution de problèmes

### Le menu ne se lance pas
- Vérifiez que Java est installé : `java -version`
- Vérifiez que MG2D est au bon emplacement : `ls ../MG2D/`
- Recompilez : `./compile.sh`

### Un jeu Python ne se lance pas
- Vérifiez Python : `python3 --version` (3.6+ requis)
- Vérifiez Pygame : `python3 -c "import pygame; print(pygame.ver)"`

### Un jeu Lua ne se lance pas
- Vérifiez LÖVE : `love --version`

### Problèmes d'affichage
- La résolution doit être 1280x1024
- Le mode plein écran est activé par défaut

## Génération de la documentation (optionnel)

Pour régénérer cette documentation localement :

```bash
sudo apt-get install doxygen
cd ~/git/borne_arcade
doxygen Doxyfile
```

La documentation HTML est générée dans `docs/generated/html/`.
