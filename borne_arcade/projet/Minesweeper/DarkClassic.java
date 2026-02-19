/**
 * Classe representant le theme sombre classique pour le jeu Minesweeper.
 */
public class DarkClassic implements Theme {

    public DarkClassic() {
    }

    @Override
/**
 * @return Le chemin vers l'image de la bombe pour ce theme.
 */
    public String getBomb() {
        return this.toString() + "/Minesweeper_bomb.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du drapeau pour ce theme.
 */
    public String getFlag() {
        return this.toString() + "/Minesweeper_flag.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du drapeau (verite) pour ce theme.
 */
    public String getFlagTrue() {
        return this.toString() + "/Minesweeper_flag.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du point d'interrogation pour ce theme.
 */
    public String getDig() {
        return this.toString() + "/Minesweeper_questionmark.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du point d'interrogation (verite) pour ce theme.
 */
    public String getDigTrue() {
        return this.toString() + "/Minesweeper_questionmark.png";
    }
    
    @Override
/**
 * @return Le chemin vers l'image de la case masquee pour ce theme.
 */
    public String getTileMasked() {
        return this.toString() + "/Minesweeper_unopened_square.png";
    }

    @Override
/**
 * @param nbVoisins Le nombre de voisins mines de la case.
 * @return Le chemin vers l'image de la case decouverte avec le nombre de voisins.
 */
    public String getTileDiscovered(int nbVoisins) {
        return this.toString() + "/Minesweeper_" + nbVoisins + ".png";
    }

    @Override
/**
 * @return Le chemin vers l'image du bouton quitter pour ce theme.
 */
    public String getQuit() {
        return this.toString() + "/Minesweeper_cross.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du bouton redemarrer pour ce theme.
 */
    public String getRestart() {
        return this.toString() + "/Minesweeper_arrow.png";
    }

    @Override
/**
 * @return Le chemin vers l'image de l'arriere-plan pour ce theme.
 */
    public String getBackground() {
        return this.toString() + "/Minesweeper_background.png";
    }

    @Override
/**
 * @return Le chemin vers l'image de la victoire pour ce theme.
 */
    public String getWin() {
        return this.toString() + "/Minesweeper_win.png";
    }

    @Override
/**
 * @return Le chemin vers l'image de la defaite pour ce theme.
 */
    public String getLose() {
        return this.toString() + "/Minesweeper_lose.png";
    }

    @Override
/**
 * @return Le nom du theme.
 */
    public String toString() {
        return "dark_classic";
    }

    @Override
/**
 * Affiche un message indiquant que le theme DarkClassic a ete selectionne.
 */
    public void display() {
        System.out.println("DarkClassic theme selected.");
    }
    
    @Override
/**
 * @return Le chemin vers l'image du niveau facile pour ce theme.
 */
    public String getLevelEasy() {
        return this.toString() + "/Level_easy.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du niveau moyen pour ce theme.
 */
    public String getLevelMedium() {
        return this.toString() + "/Level_medium.png";
    }

    @Override
/**
 * @return Le chemin vers l'image du niveau difficile pour ce theme.
 */
    public String getLevelHard() {
        return this.toString() + "/Level_hard.png";
    }
}
