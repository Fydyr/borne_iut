import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texture;

/**
 * Represents an empty tile in the Minesweeper game.
 */
public class Empty implements Tile {
    /* Attributes */
    private boolean masked;
    private boolean flag;
    private int x;
    private int y;
    private int nbNeighbours;

    /* Builders */
    public Empty() {
        this.masked = true;
        this.flag = false;
        this.x = 0;
        this.y = 0;
    }

    public Empty(int x, int y) {
        this.masked = true;
        this.flag = false;
        this.x = x;
        this.y = y;
    }

    public Empty(int x, int y, boolean masked, boolean flag) {
        this.x = x;
        this.y = y;
        this.masked = masked;
        this.flag = flag;
    }

    /*
     * Getters
     */
/**
 * @return True if the tile is masked, false otherwise.
 */
    public boolean getMasked() {
        return this.masked;
    }

/**
 * @return True if the tile is flagged, false otherwise.
 */
    public boolean getFlag() {
        return this.flag;
    }

/**
 * @return The x coordinate of the tile.
 */
    public int getX() {
        return this.x;
    }

/**
 * @return The y coordinate of the tile.
 */
    public int getY() {
        return this.y;
    }

/**
 * @return The number of neighboring mines.
 */
    public int getNbNeighbours() {
        return this.nbNeighbours;
    }

    /* Setters */
/**
 * @param masked The new masked state of the tile.
 */
    public void setMasked(boolean masked) {
        this.masked = masked;
    }

/**
 * @param flag The new flag state of the tile.
 */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

/**
 * @param x The new x coordinate of the tile.
 */
    public void setX(int x) {
        this.x = x;
    }

/**
 * @param y The new y coordinate of the tile.
 */
    public void setY(int y) {
        this.y = y;
    }

/**
 * Sets the x and y coordinates of the tile.
 * @param x The new x coordinate.
 * @param y The new y coordinate.
 */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* Methods */
/**
 * Discovers the tile and recursively discovers neighboring tiles if this tile has no neighboring mines.
 * @param board The game board.
 */
    public void discover(Board board) {
        this.masked = false;
        board.addDiscoveredTile(this);
        if (this.nbNeighbours == 0) {
            int x = this.x;
            int y = this.y;
            // up right
            if ((-1 < x + 1) && (x + 1 < board.getWidth()) && (-1 < y + 1) && (y + 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x + 1, y + 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // right
            if ((-1 < x + 1) && (x + 1 < board.getWidth()) && (-1 < y) && (y < board.getHeight())) {
                Tile neighbour = board.getCase(x + 1, y);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // down right
            if ((-1 < x + 1) && (x + 1 < board.getWidth()) && (-1 < y - 1) && (y - 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x + 1, y - 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // down
            if ((-1 < x) && (x < board.getWidth()) && (-1 < y - 1) && (y - 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x, y - 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // down left
            if ((-1 < x - 1) && (x - 1 < board.getWidth()) && (-1 < y - 1) && (y - 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x - 1, y - 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // left
            if ((-1 < x - 1) && (x - 1 < board.getWidth()) && (-1 < y) && (y < board.getHeight())) {
                Tile neighbour = board.getCase(x - 1, y);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // up left
            if ((-1 < x - 1) && (x - 1 < board.getWidth()) && (-1 < y + 1) && (y + 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x - 1, y + 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }

            // up
            if ((-1 < x) && (x < board.getWidth()) && (-1 < y + 1) && (y + 1 < board.getHeight())) {
                Tile neighbour = board.getCase(x, y + 1);
                if (neighbour.getMasked() && !neighbour.getFlag()) {
                    neighbour.discover(board);
                }
            }
        }
    }

/**
 * Toggles the flag state of the tile.
 * @param board The game board.
 */
    public void switchFlag(Board board) {
        this.flag = !this.flag;
        board.addDiscoveredTile(this);
    }

    @Override
/**
 * @param sizeTile The size of each tile in pixels.
 * @return A rectangle representing the tile's graphic.
 */
    public Rectangle displayGraphic(int sizeTile) {
        Point p = new Point(this.x * sizeTile, this.y * sizeTile);
        if (this.masked) {
            if (this.flag) {
                return new Texture("./img/Minesweeper_flag.png", p, sizeTile, sizeTile);
            } else {
                return new Texture("./img/Minesweeper_unopened_square.png", p, sizeTile, sizeTile);
            }
        } else {
            String path = "./img/Minesweeper_";
            path += this.nbNeighbours;
            path += ".png";
            return new Texture(path, p, sizeTile, sizeTile);
        }
    }

    @Override
/**
 * Displays the tile's state to the console.
 */
    public void display() {
        if (this.masked) {
            if (this.flag) {
                System.out.print("P ");
            } else {
                System.out.print("X ");
            }
        } else {
            System.out.print("  ");
        }
    }

    @Override
/**
 * Counts the number of neighboring mines.
 * @param plato The game board.
 * @return The number of neighboring mines.
 */
    public int neighbour(Board plato) {
        int nbN = 0;
        int x = this.x;
        int y = this.y;
        // up right
        if ((-1 < x + 1) && (x + 1 < plato.getWidth()) && (-1 < y + 1) && (y + 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x + 1, y + 1);
            nbN += neighbour.addNeighbour();
        }

        // right
        if ((-1 < x + 1) && (x + 1 < plato.getWidth()) && (-1 < y) && (y < plato.getHeight())) {
            Tile neighbour = plato.getCase(x + 1, y);
            nbN += neighbour.addNeighbour();
        }

        // down right
        if ((-1 < x + 1) && (x + 1 < plato.getWidth()) && (-1 < y - 1) && (y - 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x + 1, y - 1);
            nbN += neighbour.addNeighbour();
        }

        // down
        if ((-1 < x) && (x < plato.getWidth()) && (-1 < y - 1) && (y - 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x, y - 1);
            nbN += neighbour.addNeighbour();
        }

        // down left
        if ((-1 < x - 1) && (x - 1 < plato.getWidth()) && (-1 < y - 1) && (y - 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x - 1, y - 1);
            nbN += neighbour.addNeighbour();
        }

        // left
        if ((-1 < x - 1) && (x - 1 < plato.getWidth()) && (-1 < y) && (y < plato.getHeight())) {
            Tile neighbour = plato.getCase(x - 1, y);
            nbN += neighbour.addNeighbour();
        }

        // up left
        if ((-1 < x - 1) && (x - 1 < plato.getWidth()) && (-1 < y + 1) && (y + 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x - 1, y + 1);
            nbN += neighbour.addNeighbour();
        }

        // up
        if ((-1 < x) && (x < plato.getWidth()) && (-1 < y + 1) && (y + 1 < plato.getHeight())) {
            Tile neighbour = plato.getCase(x, y + 1);
            nbN += neighbour.addNeighbour();
        }
        this.nbNeighbours = nbN;
        return nbN;
    }

    @Override
/**
 * Adds 1 to the number of neighboring mines.
 * @return 1
 */
    public int addNeighbour() {
        return 0;
    }

    @Override
/**
 * Ends the game because a mine has been discovered.
 */
    public boolean endGameMine() {
        return false;
    }

    @Override
/**
 * Ends the game because all non-mine tiles have been discovered.
 */
    public boolean endGameWin() {
        if (this.masked) {
            return false;
        } else {
            return true;
        }
    }
}
