```diff
--- a/docs/pages/ajout_jeu.md
+++ b/docs/pages/ajout_jeu.md
@@ -51,6 +51,13 @@
### Script de lancement

Creez un fichier `borne_arcade/MonJeu.sh` :

### Jeu Java (avec MG2D)
@@ -58,6 +65,13 @@
java -cp .:../..:/home/pi/git/MG2D Main

### Jeu Python (Pygame)

@@ -65,6 +79,10 @@
python3 ./src

### Jeu Lua (LOVE 2D)

@@ -72,6 +90,11 @@
love .

> **Note** : `xdotool mousemove 1280 1024` deplace le curseur hors ecran pour eviter l'economiseur d'ecran.

+**Lancer un script externe :**
+Si votre jeu nécessite l'exécution d'un script externe (par exemple, pour initialiser des paramètres), vous pouvez l'ajouter avant la commande de lancement du jeu.  Assurez-vous que le script est exécutable (`chmod +x script.sh`).
+
+Exemple:
+```bash
+#!/bin/bash
+./mon_script_initialisation.sh
+python3 ./src
+```

N'oubliez pas de rendre le script executable :

```bash
chmod +x borne_arcade/MonJeu.sh
```
```