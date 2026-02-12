# Borne Arcade IUT {#mainpage}

## Presentation

Projet de borne d'arcade developpe a l'IUT du Littoral Cote d'Opale (IUTLCO).
Le systeme comprend un **menu de selection** en Java (bibliotheque MG2D) et une **collection de 14 jeux** en Java, Python (Pygame) et Lua (LOVE 2D).

## Architecture

```
borne_arcade/
├── *.java              # Menu principal (MG2D)
├── projet/             # Collection de jeux
│   ├── ball-blast/     # Python / Pygame
│   ├── Columns/        # Java / MG2D
│   ├── CursedWare/     # Lua / LOVE 2D
│   ├── Pong/           # Java / MG2D
│   └── ...             # 14 jeux au total
├── *.sh                # Scripts de lancement par jeu
└── Documents/          # Photos, rapports
```

## Contraintes materielles

- **Raspberry Pi 3** (modele de preference)
- **Ecran 4:3** - resolution 1280x1024
- **2 joueurs** : joystick + 6 boutons par joueur
- **Encodeur clavier** USB pour les boutons arcade

## Pages de documentation

- @subpage installation — Installation du systeme et des dependances
- @subpage utilisateur — Guide d'utilisation de la borne
- @subpage ajout_jeu — Comment ajouter un nouveau jeu

## Technologies

| Composant | Technologie |
|-----------|-------------|
| Menu principal | Java + MG2D |
| Jeux Java | Java + MG2D |
| Jeux Python | Python 3.7 + Pygame |
| Jeux Lua | Lua + LOVE 2D |
