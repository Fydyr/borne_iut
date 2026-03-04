--- a/docs/pages/ajout_jeu.md
+++ b/docs/pages/ajout_jeu.md
@@ -18,7 +18,7 @@ ## Script de lancement

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