import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texture;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Boîte affichant l'image de présentation du jeu sélectionné.
 * L'image est chargée depuis le fichier {@code photo_small.png} situé dans
 * le répertoire du jeu. Si l'image est absente, une image par défaut est utilisée.
 */
public class BoiteImage extends Boite{

    Texture image;

    /**
     * Construit la boîte image pour un jeu donné.
     * @param rectangle zone d'affichage dans l'interface
     * @param image chemin vers le répertoire du jeu (ex: "projet/Pong")
     */
    BoiteImage(Rectangle rectangle, String image) {
	super(rectangle);
        Path filePath = Paths.get(image+"/photo_small.png");
	if (Files.exists(filePath)) {
	    this.image = new Texture(image+"/photo_small.png", new Point(760, 648));
	    //this.image.setTaille(400, 320);
	} else {
	    this.image.setImg("./img/synave.png");
	}
	this.image = new Texture(image+"/photo_small.png", new Point(760, 648));
    }

    /**
     * Retourne la texture de l'image affichée.
     * @return la texture MG2D de l'image du jeu
     */
    public Texture getImage() {
	return this.image;
    }

    /**
     * Change l'image affichée pour celle du jeu au chemin donné.
     * Charge {@code photo_small.png} depuis le répertoire du jeu,
     * ou l'image par défaut si le fichier n'existe pas.
     * @param chemin chemin vers le répertoire du jeu
     */
    public void setImage(String chemin) {
        Path filePath = Paths.get(chemin+"/photo_small.png");
	if (Files.exists(filePath)) {
	    this.image.setImg(chemin+"/photo_small.png");
	    //this.image.setTaille(400, 320);
	} else {
	    this.image.setImg("./img/synave.png");
	}
    }
}