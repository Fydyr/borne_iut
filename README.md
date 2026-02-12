# Borne Arcade IUT

Borne d'arcade developpee a l'IUT du Littoral Cote d'Opale (IUTLCO).
Menu de selection en Java (MG2D) et collection de 14 jeux en Java, Python et Lua.

## Materiel cible

- Raspberry Pi 3
- Ecran 4:3, resolution 1280x1024
- 2 joueurs : joystick + 6 boutons par joueur

## Jeux disponibles

| Jeu | Langage | Description |
|-----|---------|-------------|
| ball-blast | Python / Pygame | Tir de balles |
| Columns | Java / MG2D | Puzzle de colonnes |
| CursedWare | Lua / LOVE 2D | Mini-jeux |
| DinoRail | Java / MG2D | Jeu de dinosaure |
| InitialDrift | Java / MG2D | Course automobile |
| JavaSpace | Java / MG2D | Jeu spatial |
| Kowasu_Renga | Java / MG2D | Casse-briques |
| Minesweeper | Java / MG2D | Demineur |
| OsuTile | Python / Pygame | Jeu de rythme |
| PianoTile | Java / MG2D | Piano tiles |
| Pong | Java / MG2D | Pong classique |
| Puissance_X | Java / MG2D | Puissance 4 |
| Snake_Eater | Java / MG2D | Snake |
| TronGame | Python / Pygame | Tron |

## Installation rapide

```bash
cd ~/git
git clone <URL_DU_DEPOT>/borne_arcade.git
cd borne_arcade
chmod +x lancerBorne.sh
./lancerBorne.sh
```

Le script `lancerBorne.sh` s'occupe de tout : il telecharge MG2D automatiquement si absent, compile tous les jeux, puis lance le menu.

Voir la [documentation complete](docs/pages/installation.md) pour les details.

## Documentation

### Generer la documentation

```bash
# Linux
./generate_docs.sh

# Windows
generate_docs.bat
```

La documentation HTML est generee dans `docs/generated/html/`.

### Pages de documentation

- [Page d'accueil](docs/pages/mainpage.md) - Presentation du projet
- [Installation](docs/pages/installation.md) - Guide d'installation
- [Guide utilisateur](docs/pages/utilisateur.md) - Controles et navigation
- [Ajouter un jeu](docs/pages/ajout_jeu.md) - Tutoriel pour ajouter un nouveau jeu

## Outils de documentation automatique

Le projet inclut des outils de documentation automatique dans `tools/` :

- **ai_doc_patch.py** - Analyse les diffs Git et propose des mises a jour de doc via Ollama
- **doc_coverage.py** - Detecte les fonctions sans documentation (Java, Python, Lua)
- **release_notes.py** - Genere des notes de release depuis les commits Git

```bash
# Verifier la couverture de documentation
python tools/doc_coverage.py borne_arcade/

# Generer des notes de release
python tools/release_notes.py --output CHANGELOG.md
```

## CI/CD

Les GitHub Actions automatisent :
- Build de la documentation Doxygen sur chaque push/PR
- Suggestions de doc par IA sur chaque PR
- Rapport de couverture documentation
- Publication sur GitHub Pages (branche main)
- Notes de release automatiques (sur creation de tag)
