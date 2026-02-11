import MG2D.geometrie.Texture;

/**
 * Interface representant un bouton d'action dans le Demineur.
 * Permet de definir differentes actions (creuser, poser un drapeau).
 */
public interface Button {
    /**
     * Affiche l'etat du bouton en mode console.
     */
    public void display();

    /**
     * Execute l'action du bouton sur une case du plateau.
     * @param c la case ciblee
     * @param board le plateau de jeu
     */
    public void actionButton(Tile c, Board board);

    /**
     * Retourne la texture de selection du bouton pour l'affichage graphique.
     * @param sizeTile taille d'une case en pixels
     * @param width largeur de la fenetre
     * @param height hauteur de la fenetre
     * @return la texture representant le bouton
     */
    public Texture selection(int sizeTile, int width, int height);
}
