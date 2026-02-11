# Borne Arcade IUT {#mainpage}

Plateforme logicielle pour borne d'arcade développée à l'IUT du Littoral Côte d'Opale.
Le système propose un menu de sélection de jeux open source, conçu pour fonctionner sur Raspberry Pi 3.

## Documentation

- @subpage installation — Mise en place complète de la borne (matériel, OS, dépendances)
- @subpage utilisateur — Contrôles, navigation dans le menu, liste des jeux
- @subpage ajout_jeu — Procédure pour intégrer un nouveau jeu (à venir)

## Architecture du projet

Le projet est organisé en trois couches :

### Menu principal (Java)
Le menu de sélection est écrit en Java avec la bibliothèque graphique
[MG2D](https://github.com/synave/MG2D). Il gère l'affichage des jeux disponibles,
la navigation au joystick, le lancement et l'arrêt des jeux.

Fichiers clés :
- `Main.java` — Point d'entrée
- `Graphique.java` — Interface graphique et boucle du menu
- `Boite*.java` — Composants d'affichage (image, sélection, description)
- `ClavierBorneArcade.java` — Mapping des entrées joystick/boutons

### Jeux (Java, Python, Lua)

Chaque jeu est un projet autonome dans `borne_arcade/projet/` :

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

### Bibliothèque audio (Java)
Le package `javazoom` fournit le décodage MP3 pour la musique de fond du menu.
Cette bibliothèque tierce est exclue de la documentation technique.

## Générer cette documentation

Prérequis : [Doxygen](https://www.doxygen.nl/) 1.9.2 ou supérieur.

```bash
# Depuis la racine du projet
doxygen Doxyfile
```

La documentation est générée dans `docs/generated/html/`. Ouvrir `index.html` pour la consulter.

## Contribuer à la documentation

Le code source contient peu de commentaires de documentation pour le moment.
Pour améliorer la doc technique générée, ajoutez des commentaires dans le format
adapté à chaque langage :

### Java (Javadoc)
```java
/**
 * Gère l'affichage d'une boîte dans le menu.
 * @param x Position horizontale
 * @param y Position verticale
 */
public void afficher(int x, int y) { ... }
```

### Python (Docstrings)
```python
def afficher(self, x, y):
    """Gère l'affichage d'une boîte dans le menu.

    Args:
        x: Position horizontale
        y: Position verticale
    """
```

### Lua (commentaires ---)
```lua
--- Gère l'affichage d'une boîte dans le menu.
-- @param x Position horizontale
-- @param y Position verticale
function Boite:afficher(x, y)
```
