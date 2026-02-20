/**
 * Interface definissant un theme visuel pour le jeu Minesweeper.
 */
public interface Theme {

/**
 * Retourne le caractere representant une bombe.
 *
 * @return Le caractere de la bombe.
 */
    public String getBomb();
/**
 * Retourne le caractere representant un drapeau.
 *
 * @return Le caractere du drapeau.
 */
    public String getFlag();
/**
 * Retourne le caractere representant un drapeau correctement place.
 *
 * @return Le caractere du drapeau correct.
 */
    public String getFlagTrue();
/**
 * Retourne le caractere representant une case a creuser.
 *
 * @return Le caractere de la case a creuser.
 */
    public String getDig();
/**
 * Retourne le caractere representant une case creusee correctement.
 *
 * @return Le caractere de la case creusee.
 */
    public String getDigTrue();
/**
 * Retourne le caractere representant une case masquee.
 *
 * @return Le caractere de la case masquee.
 */
    public String getTileMasked();
/**
 * Retourne le caractere representant une case decouverte avec un certain nombre de voisins.
 *
 * @param nbNeighbours Le nombre de bombes voisines.
 * @return Le caractere de la case decouverte.
 */
    public String getTileDiscovered(int nbNeighbours);
/**
 * Retourne le caractere representant le bouton 'Quitter'.
 *
 * @return Le caractere du bouton 'Quitter'.
 */
    public String getQuit();
/**
 * Retourne le caractere representant le bouton 'Recommencer'.
 *
 * @return Le caractere du bouton 'Recommencer'.
 */
    public String getRestart();
/**
 * Retourne le caractere representant l'arriere-plan.
 *
 * @return Le caractere de l'arriere-plan.
 */
    public String getBackground();
/**
 * Retourne le caractere representant la victoire.
 *
 * @return Le caractere de la victoire.
 */
    public String getWin();
/**
 * Retourne le caractere representant la defaite.
 *
 * @return Le caractere de la defaite.
 */
    public String getLose();
/**
 * Retourne une representation textuelle du theme.
 *
 * @return Une chaine de caracteres representant le theme.
 */
    public String toString();
/**
 * Retourne le caractere representant le niveau facile.
 *
 * @return Le caractere du niveau facile.
 */
    public String getLevelEasy();
/**
 * Retourne le caractere representant le niveau moyen.
 *
 * @return Le caractere du niveau moyen.
 */
    public String getLevelMedium();
/**
 * Retourne le caractere representant le niveau difficile.
 *
 * @return Le caractere du niveau difficile.
 */
    public String getLevelHard();
/**
 * Affiche le theme.
 */
    public void display();
}
