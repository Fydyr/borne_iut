# Ajouter un nouveau jeu {#ajout_jeu}

## Principe

Chaque jeu est un **repertoire autonome** dans `borne_arcade/projet/`. Le menu detecte automatiquement les jeux au demarrage en scannant ce repertoire.

## Fichiers obligatoires

Votre jeu doit contenir ces fichiers dans `projet/MonJeu/` :

| Fichier | Description |
|---------|-------------|
| `description.txt` | Description du jeu (max 10 lignes) |
| `bouton.txt` | Labels des controles utilises |
| `photo_small.png` | Miniature pour le menu |
| `highscore` | Fichier des scores (peut etre vide) |

Et un **script de lancement** dans `borne_arcade/MonJeu.sh`.

## Format de description.txt

```
Nom du Jeu par Auteur - Annee

Description du jeu sur une ou plusieurs lignes.
Instructions de jeu.
Maximum 10 lignes au total.
```

Exemple :
```
Pong - Remi Synave - 2013

Jeu de Pong classique.
Deplacez la barre avec le joystick.
Lancez la balle avec le bouton A.
```

## Format de bouton.txt

Une seule ligne avec 7 champs separes par `:` :

```
Joystick:Bouton1:Bouton2:Bouton3:Bouton4:Bouton5:Bouton6
```

Utilisez `aucun` ou `rien` pour les boutons non utilises.

Exemple :
```
Deplacement barre:rien:rien:Quitter:Lancer la balle:rien:rien
```

## Script de lancement

Creez un fichier `borne_arcade/MonJeu.sh` :

### Jeu Java (avec MG2D)

```bash
#!/bin/bash
xdotool mousemove 1280 1024
cd projet/MonJeu
touch highscore
java -cp .:../..:/home/pi/git/MG2D Main
```

### Jeu Python (Pygame)

```bash
#!/bin/bash
xdotool mousemove 1280 1024
cd projet/MonJeu
python3 ./src
```

### Jeu Lua (LOVE 2D)

```bash
#!/bin/bash
xdotool mousemove 1280 1024
cd projet/MonJeu
love .
```

> **Note** : `xdotool mousemove 1280 1024` deplace le curseur hors ecran pour eviter l'economiseur d'ecran.

N'oubliez pas de rendre le script executable :

```bash
chmod +x borne_arcade/MonJeu.sh
```

## Structure type par langage

### Java

```
projet/MonJeu/
├── Main.java
├── MonJeu.java
├── ClavierBorneArcade.java
├── img/
├── sounds/
├── description.txt
├── bouton.txt
├── photo_small.png
└── highscore
```

### Python

```
projet/MonJeu/
├── src/
│   ├── __main__.py
│   └── game.py
├── assets/
├── requirements.txt
├── description.txt
├── bouton.txt
├── photo_small.png
└── highscore
```

### Lua

```
projet/MonJeu/
├── main.lua
├── conf.lua
├── assets/
├── description.txt
├── bouton.txt
├── photo_small.png
└── highscore
```

## Verification

Apres avoir ajoute votre jeu :

1. Verifiez que le repertoire `projet/MonJeu/` contient tous les fichiers obligatoires
2. Lancez le menu : votre jeu doit apparaitre dans la liste
3. Testez le lancement via le bouton A
4. Verifiez que les high scores s'affichent correctement
