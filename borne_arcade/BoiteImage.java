import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texture;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BoiteImage extends Boite{

    Texture image;

    BoiteImage(Rectangle rectangle, String image) {
	super(rectangle);
        Path filePath = Paths.get(image+"/photo_small.png");
	if (Files.exists(filePath)) {
	    this.image = new Texture(image+"/photo_small.png", new Point(760, 648));
	    //this.image.setTaille(400, 320);
	} else {
	    this.image.setImg("img/synave.png");
	}
	this.image = new Texture(image+"/photo_small.png", new Point(760, 648));
    }

    public Texture getImage() {
	return this.image;
    }

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