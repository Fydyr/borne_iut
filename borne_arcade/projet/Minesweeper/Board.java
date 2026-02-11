import java.util.ArrayList;

/**
 * Represente le plateau de jeu du Demineur.
 * Contient les cases (mines et cases vides), gere les actions du joueur
 * et verifie les conditions de fin de partie.
 */
public class Board {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int width;
    private int height;
    private int nbBombs;
    private ArrayList<Tile> discoveredTiles = new ArrayList<Tile>();

    /**
     * Construit un plateau avec les dimensions et le nombre de bombes donnes.
     * @param width largeur du plateau en nombre de cases
     * @param height hauteur du plateau en nombre de cases
     * @param nbBombs nombre de bombes a placer aleatoirement
     */
    public Board(int width, int height, int nbBombs) {
        // if (width >= 6 && height >= 6 && nbBombs >= 1 && nbBombs < width * height) {
            this.width = width;
            this.height = height;
            this.nbBombs = nbBombs;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    this.tiles.add(new Empty(i, j));
                }
            }
            for (int i = 0; i < nbBombs; i++) {
                int x = (int) (Math.random() * width);
                int y = (int) (Math.random() * height);
                Tile c = this.getCase(x, y);
                this.tiles.remove(c);
                this.tiles.add(new Bomb(x, y));
            }
            this.neighbourhood();
        // } else {
            // throw new IllegalArgumentException("Invalid input values for the plateau.");
        // }
    }

    /* Getters */
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getNbBombs() {
        return this.nbBombs;
    }

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public ArrayList<Tile> getDiscoveredTiles() {
        return this.discoveredTiles;
    }

    /**
     * Ajoute une case a la liste des cases decouvertes si elle n'y est pas deja.
     * @param c la case a ajouter
     */
    public void addDiscoveredTile(Tile c) {
        if (!this.discoveredTiles.contains(c)) {
            this.discoveredTiles.add(c);
        }
    }

    public void clearDiscoveredTiles() {
        this.discoveredTiles.clear();
    }

    /**
     * Recupere la case situee aux coordonnees donnees.
     * @param x coordonnee horizontale
     * @param y coordonnee verticale
     * @return la case correspondante, ou null si aucune case n'est trouvee
     */
    public Tile getCase(int x, int y) {
        for (Tile c : this.tiles) {
            if (c.getX() == x && c.getY() == y) {
                return c;
            }
        }
        return null;
    }

    public void display() {
        for (int j = this.height; j > -1; j--) {
            for (int i = 0; i < this.width; i++) {
                for (Tile c : this.tiles) {
                    if (c.getX() == i && c.getY() == j) {
                        c.display();
                    }
                }
            }
            // System.out.println();
        }
    }

    /**
     * Execute une action sur la case correspondant aux coordonnees pixel.
     * @param x coordonnee x en pixels
     * @param y coordonnee y en pixels
     * @param b le bouton d'action (creuser ou drapeau)
     * @param sizeTile taille d'une case en pixels
     */
    public void action(int x, int y, Button b, int sizeTile) {
        int i = x / sizeTile;
        int j = y / sizeTile;
        System.out.println("Action on tile: " + i + ", " + j);
        System.out.println("Action on tile: " + x + ", " + y);
        for (Tile c : this.tiles) {
            if (c.getX() == i && c.getY() == j) {
                b.actionButton(c, this);
                this.addDiscoveredTile(c);
            }
        }
    }

    public void neighbourhood() {
        for (Tile c : this.tiles) {
            c.neighbour(this);
        }
    }

    /**
     * Verifie si le joueur a perdu (une mine a ete decouverte).
     * @return true si une mine est devoilee, false sinon
     */
    public boolean endGameMine() {
        boolean end = false;
        int i = 0;
        while (end == false && i < this.tiles.size()) {
            Tile c = this.tiles.get(i);
            end = c.endGameMine();
            i++;
        }
        return end;
    }

    /**
     * Verifie si le joueur a gagne (toutes les cases non-mines sont decouvertes).
     * @return true si le joueur a gagne, false sinon
     */
    public boolean endGameWin() {
        boolean end = true;
        int i = 0;
        while (end == true && i < this.tiles.size()) {
            Tile c = this.tiles.get(i);
            end = c.endGameWin();
            i++;
        }
        return end;
    }
}
