# PianoTile

Jeu de Piano Tiles developpe en Python avec Pygame par **Emma GUILBERT** (2025).

## Description

Jeu de rythme inspire du celebre Piano Tiles : des notes defilent a l'ecran et le joueur doit appuyer sur les bonnes touches au bon moment pour marquer des points. Le jeu propose plusieurs musiques avec des niveaux de difficulte differents.

## Musiques disponibles

- Believer
- Blinding Lights
- Sunflower
- Sweater Weather

## Fonctionnalites

- **Systeme de comptes** : inscription et connexion avec nom d'utilisateur et mot de passe
- **Profil joueur** : page de profil avec statistiques personnelles
- **Selection de musique** : choix parmi plusieurs morceaux avec apercu
- **Filtrage** : filtrer les musiques par difficulte ou annee
- **Statistiques** : suivi des scores et performances
- **Multijoueur** : mode multijoueur
- **Compte a rebours** : timer avant le debut de la partie
- **Fin de partie** : ecrans differents selon victoire ou defaite

## Architecture

```
PianoTile/
├── app/
│   └── project.py          # Classe principale du projet
├── core/
│   ├── button.py            # Gestion des boutons arcade
│   ├── logic.py             # Logique de jeu et navigation entre pages
│   ├── pageState.py         # Enum des etats de page (ACCUEIL, PLAY, PROFIL, etc.)
│   └── player.py            # Classe joueur
├── data/
│   ├── database.py          # Gestion base de donnees SQLite
│   └── database.db          # Base de donnees des joueurs et scores
├── ui/
│   ├── interface.py         # Interface principale
│   ├── layout/              # Vues d'affichage
│   │   ├── backgroundView.py
│   │   ├── gameView.py      # Vue du jeu en cours
│   │   ├── menuView.py      # Vue du menu
│   │   ├── musicView.py     # Vue de selection musicale
│   │   ├── selectionView.py # Vue de selection
│   │   ├── sortedView.py    # Vue triee/filtree
│   │   └── timerView.py     # Vue du compte a rebours
│   ├── manager/
│   │   └── windowManager.py # Gestionnaire de fenetre
│   ├── page/                # Pages de l'application
│   │   ├── basePage.py
│   │   ├── connexionPage.py
│   │   ├── detailPage.py
│   │   ├── filtrerPage.py
│   │   ├── gamePage.py
│   │   ├── helpPage.py
│   │   ├── inscriptionPage.py
│   │   ├── profilPage.py
│   │   ├── quitterPage.py
│   │   └── statistiquePage.py
│   └── utils/               # Utilitaires graphiques
│       ├── color.py
│       ├── image.py
│       ├── inputBox.py
│       ├── note.py
│       └── piano.py
└── assets/
    ├── font/                # Polices
    ├── img/                 # Images (musiques, pages)
    └── music/               # Fichiers audio MP3
```

## Pages du jeu

| Page | Description |
|------|-------------|
| Accueil | Menu principal avec liste des musiques |
| Connexion | Formulaire de connexion |
| Inscription | Formulaire d'inscription |
| Profil | Informations du joueur connecte |
| Detail | Details d'une musique selectionnee |
| Play | Partie en cours |
| Filtrer | Filtrage des musiques |
| Aide | Page d'aide |
| Statistique | Statistiques du joueur |
| Multijoueur | Mode multijoueur |
| Quitter | Confirmation de sortie |

## Technologie

- **Langage** : Python 3
- **Bibliotheque graphique** : Pygame
- **Base de donnees** : SQLite
