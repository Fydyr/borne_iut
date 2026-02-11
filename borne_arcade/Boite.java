import MG2D.geometrie.Rectangle;

/**
 * Classe abstraite représentant une zone rectangulaire dans l'interface du menu.
 * Chaque boîte encapsule un Rectangle MG2D et sert de base aux composants
 * d'affichage : image du jeu ({@link BoiteImage}), liste de sélection
 * ({@link BoiteSelection}) et description ({@link BoiteDescription}).
 */
public abstract class Boite {
    private Rectangle rectangle;

    /**
     * Construit une boîte à partir d'un rectangle MG2D.
     * @param rectangle le rectangle définissant la zone d'affichage
     */
    Boite(Rectangle rectangle){
	this.rectangle = rectangle;
    }

    /**
     * Retourne le rectangle MG2D de cette boîte.
     * @return le rectangle d'affichage
     */
    public Rectangle getRectangle() {
	return rectangle;
    }

    /**
     * Modifie le rectangle MG2D de cette boîte.
     * @param rectangle le nouveau rectangle d'affichage
     */
    public void setRectangle(Rectangle rectangle) {
	this.rectangle = rectangle;
    }
}
