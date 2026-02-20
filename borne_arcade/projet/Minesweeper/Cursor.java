import java.awt.Rectangle;

import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;

/**
 * Classe representant le curseur du jeu Démineur.
 */
public class Cursor {
    private int x;
    private int y;

    private int sizeTile;

    public Cursor(int sizeTile) {
        this.x = 0;
        this.y = 0;
        this.sizeTile = sizeTile;
    }

    public Cursor(int x, int y, int sizeTile) {
        this.x = x;
        this.y = y;
        this.sizeTile = sizeTile;
    }

    /**
     * Deplace le curseur vers le haut.
     */
    public void moveUp() {
        if (y < Constants.sizeTile * (Constants.height - 1)) {
            y += sizeTile;
        }
    }

    /**
     * Deplace le curseur vers le bas.
     */
    public void moveDown() {
        if (y > 0) {
            y -= sizeTile;
        }
    }

    /**
     * Deplace le curseur vers la gauche.
     */
    public void moveLeft() {
        if (x > 0) {
            x -= sizeTile;
        }
    }

    /**
     * Deplace le curseur vers la droite.
     */
    public void moveRight() {
        if (x < Constants.sizeTile * (Constants.width - 1)) {
            x += sizeTile;
        }
    }

    /**
     * Retourne la coordonnee x du curseur.
     * @return La coordonnee x du curseur.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnee y du curseur.
     * @return La coordonnee y du curseur.
     */
    public int getY() {
        return y;
    }

    /**
     * Definit la coordonnee x du curseur.
     * @param x La nouvelle coordonnee x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Definit la coordonnee y du curseur.
     * @param y La nouvelle coordonnee y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Definit la taille de la case du curseur.
     * @param sizeTile La nouvelle taille de la case.
     */
    public void setSizeTile(int sizeTile) {
        this.sizeTile = sizeTile;
    }

    /**
     * Retourne la taille de la case du curseur.
     * @return La taille de la case.
     */
    public int getSizeTile() {
        return sizeTile;
    }

    /**
     * Definit la position du curseur.
     * @param x La nouvelle coordonnee x.
     * @param y La nouvelle coordonnee y.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Definit la position du curseur a partir d'un point.
     * @param p Le point contenant les nouvelles coordonnees.
     */
    public void setPosition(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * Retourne la position du curseur sous forme de point.
     * @return Un point representant la position du curseur.
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * Reinitialise la position du curseur a (0, 0).
     */
    public void resetPosition() {
        x = 0;
        y = 0;
    }

    /**
     * Reinitialise la position du curseur a partir d'un point.
     * @param p Le point contenant la nouvelle position.
     */
    public void resetPosition(Point p) {
        x = p.getX();
        y = p.getY();
    }

    /**
     * Retourne un rectangle representant la position du curseur.
     * @return Un rectangle definissant la zone du curseur.
     */
    public Rectangle cursorPlace() {
        return new Rectangle(x, y, sizeTile, sizeTile);
    }

    /**
     * Retourne une texture pour le curseur.
     * @return Une texture representant le curseur.
     */
    public static Texture cursorTexture() {
        return new Texture("./img/Minesweeper_cursor.png",
                new Point(0, 0), Constants.sizeTile, Constants.sizeTile);
    }

    /**
     * Retourne une texture pour le curseur avec un decalage.
     * @param x Le decalage en x.
     * @param y Le decalage en y.
     * @return Une texture representant le curseur avec le decalage applique.
     */
    public static Texture cursorTexture(int x, int y) {
        return new Texture("./img/Minesweeper_cursor.png",
                new Point(x, y), Constants.sizeTile, Constants.sizeTile);
    }

}
