# Guide utilisateur {#utilisateur}

## Controles de la borne

### Correspondance clavier / boutons arcade

#### Joueur 1

| Controle | Touche clavier |
|----------|---------------|
| Joystick haut | Fleche haut |
| Joystick bas | Fleche bas |
| Joystick gauche | Fleche gauche |
| Joystick droite | Fleche droite |
| Bouton A (rangee 1) | R |
| Bouton B (rangee 1) | T |
| Bouton C (rangee 1) | Y |
| Bouton D (rangee 2) | F |
| Bouton E (rangee 2) | G |
| Bouton F (rangee 2) | H |

#### Joueur 2

| Controle | Touche clavier |
|----------|---------------|
| Joystick haut | O |
| Joystick bas | K |
| Joystick gauche | L |
| Joystick droite | M |
| Bouton A (rangee 1) | A |
| Bouton B (rangee 1) | Z |
| Bouton C (rangee 1) | E |
| Bouton D (rangee 2) | Q |
| Bouton E (rangee 2) | S |
| Bouton F (rangee 2) | D |

> **Attention** : L'encodeur clavier de la borne IUT a ete mal relie aux boutons d'origine. Le fichier `borne` contient la configuration corrigee via `setxkbmap`.

## Navigation dans le menu

1. **Selectionner un jeu** : Joystick haut/bas du Joueur 1
2. **Lancer un jeu** : Bouton A du Joueur 1
3. **Quitter le menu** : Bouton Z du Joueur 1 (confirmation demandee)

## Affichage du menu

Le menu affiche pour chaque jeu :
- **A gauche** : Liste des jeux disponibles (boutons de selection)
- **Au centre** : Capture d'ecran du jeu selectionne
- **A droite** : Description du jeu, controles utilises et tableau des high scores

## Gestion des high scores

Chaque jeu possede un fichier `highscore` dans son repertoire. Les scores sont affiches dans le menu sous forme de tableau.

## Extinction

Apres avoir quitte le menu, attendez **30 secondes** pour une extinction totale de la machine.
