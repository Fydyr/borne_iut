# Borne Arcade IUT

Plateforme logicielle pour borne d'arcade développée à l'IUT du Littoral Côte d'Opale.
Menu de sélection de jeux open source, conçu pour fonctionner sur Raspberry Pi 3 avec écran 4:3 (1280x1024).

## Jeux disponibles

| Jeu | Langage | Framework |
|-----|---------|-----------|
| Minesweeper | Java | MG2D |
| Puissance X | Java | MG2D |
| Columns | Java | MG2D |
| JavaSpace | Java | MG2D |
| Snake Eater | Java | MG2D |
| Pong | Java | MG2D |
| InitialDrift | Java | MG2D |
| DinoRail | Java | MG2D |
| Kowasu Renga | Java | MG2D |
| TronGame | Python | Pygame |
| PianoTile | Python | Pygame |
| OsuTile | Python | Pygame |
| Ball Blast | Python | Pygame |
| CursedWare | Lua | LÖVE 2D |

## Installation rapide

### Prérequis

- Raspberry Pi 3 (ou supérieur)
- Raspberry Pi OS

### Dépendances

```bash
sudo apt-get update
sudo apt-get install openjdk-25-jdk git python3 python3-pip love
pip3 install pygame
```

### Clonage

```bash
git clone https://github.com/Fydyr/borne_iut.git
```

### Lancement

```bash
cd ~/git/borne_iut/borne_arcade
sh ./lancement_borne.sh
```

## Contrôles

| Joueur 1 | Touche | Joueur 2 | Touche |
|----------|--------|----------|--------|
| Joystick haut | Flèche haut | Joystick haut | O |
| Joystick bas | Flèche bas | Joystick bas | L |
| Joystick gauche | Flèche gauche | Joystick gauche | K |
| Joystick droite | Flèche droite | Joystick droite | M |
| Boutons haut | R / T / Y | Boutons haut | A / Z / E |
| Boutons bas | F / G / H | Boutons bas | Q / S / D |

**Navigation dans le menu** : Joystick haut/bas (J1) pour parcourir, bouton A (F) pour lancer, bouton Z (Y) pour quitter.

## Documentation

La documentation complète est générée automatiquement avec Doxygen et disponible sur :
**https://fydyr.github.io/borne_iut/**

Elle comprend :
- Documentation technique (API des classes Java, Python, Lua)
- Guide d'installation
- Guide utilisateur

### Générer la documentation localement

```bash
# Installer Doxygen
sudo apt-get install doxygen    # Debian/Raspbian
winget install doxygen          # Windows

# Générer
./generate_docs.sh              # Linux
./generate_docs.bat             # Windows
```

La documentation HTML est générée dans `docs/generated/html/`.

## Structure du projet

```
borne_iut/
├── borne_arcade/
│   ├── Main.java              # Point d'entrée
│   ├── Graphique.java         # Interface graphique du menu
│   ├── Boite*.java            # Composants d'affichage
│   ├── Bouton.java            # Bouton de sélection de jeu
│   ├── Pointeur.java          # Curseur de navigation
│   ├── HighScore.java         # Gestion des scores
│   ├── ClavierBorneArcade.java # Mapping clavier/joystick
│   └── projet/                # Répertoire des jeux
│       ├── ball-blast/        # Python (Pygame)
│       ├── Columns/           # Java (MG2D)
│       ├── CursedWare/        # Lua (LÖVE 2D)
│       ├── DinoRail/          # Java (MG2D)
│       ├── InitialDrift/      # Java (MG2D)
│       ├── JavaSpace/         # Java (MG2D)
│       ├── Kowasu_Renga/      # Java (MG2D)
│       ├── Minesweeper/       # Java (MG2D)
│       ├── OsuTile/           # Python (Pygame)
│       ├── PianoTile/         # Python (Pygame)
│       ├── Pong/              # Java (MG2D)
│       ├── Puissance_X/       # Java (MG2D)
│       ├── Snake_Eater/       # Java (MG2D)
│       └── TronGame/          # Python (Pygame)
├── docs/
│   └── pages/                 # Pages de documentation
├── Doxyfile                   # Configuration Doxygen
└── .github/workflows/         # CI/CD GitHub Actions
```
