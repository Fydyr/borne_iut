import java.io.IOException;
import java.io.File;
import java.util.Map;

import MG2D.geometrie.Texture;
import MG2D.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Triangle;
import MG2D.Clavier;


public class Pointeur {
    private int value;
    private Texture triangleGauche;
    private Texture triangleDroite;
    private Texture rectangleCentre;

    public Pointeur(){
	this.triangleGauche = new Texture("img/star.png", new Point(30, 492), 40,40);
	// this.triangleDroite = new Triangle(Couleur .ROUGE, new Point(550, 560), new Point(520, 510), new Point(550, 460), true);
	this.triangleDroite = new Texture("img/star.png", new Point(530, 492), 40,40);
	this.rectangleCentre = new Texture("img/select2.png", new Point(80, 460), 440, 100);
	this.value = Graphique.tableau.length-1;
    }

    public void lancerScript(File script){
	try {
	    Graphique.stopMusiqueFond();
	    Graphique.setVisible(false);
	    File wd = script.getParentFile();
	    ProcessBuilder pb = new ProcessBuilder("/bin/bash", script.getName());
	    pb.directory(wd);
	    pb.inheritIO();
	    System.gc();
	    Process process = pb.start();
	    process.waitFor();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    Graphique.setVisible(true);
	    Graphique.lectureMusiqueFond();
	}
    }

    public void lancerJeu(ClavierBorneArcade clavier){
	if(clavier.getBoutonJ1ATape()){

	    //System.out.println(Graphique.tableau[getValue()].getChemin());
	    try {
		Graphique.stopMusiqueFond();
		Graphique.setVisible(false);
		Bouton b = Graphique.tableau[getValue()];
		String chemin = b.getChemin();
		String nom = b.getNom();
		String[] scripts = {nom+".sh", "start.sh", "launch.sh", "launcher.sh"};

		for(String script : scripts){
		    File f = new File(chemin+"/"+script);
		    if(f.exists()){
			File venv = new File(chemin+"/venv");
			Process process;
			File wd = new File(chemin);

			ProcessBuilder pb = new ProcessBuilder("/bin/bash", script);
			pb.directory(wd);
			pb.inheritIO();

			if(venv.exists()){
				Map<String, String> env = pb.environment();
				String venvPath = venv.getAbsolutePath();
				String path = env.get("PATH");
				env.put("PATH", venvPath + "/bin" + File.pathSeparator + (path == null ? "" : path));
				env.put("VIRTUAL_ENV", venvPath);
				env.remove("PYTHONHOME");
				env.put("SDL_AUDIODRIVER", "pulseaudio");
				env.put("PYTHONDONTWRITEBYTECODE", "1");
			}
			System.gc();
			process = pb.start();
			process.waitFor();		//ajouté afin d'attendre la fin de l'exécution du jeu pour reprendre le contrôle sur le menu
			break;
		    }
		}
		Graphique.setVisible(true);
		Graphique.lectureMusiqueFond();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Graphique.setVisible(true);
	    } catch(Exception e){	//on catche toutes les exceptions, nécessaire pour le waitFor()
			e.printStackTrace();
			Graphique.setVisible(true);
		}

	    //System.out.println("le process sur "+Graphique.tableau[getValue()].getChemin()+" est bien lancé");
	}
    }

    public int getValue() {
	return value;
    }

    public void setValue(int value) {
	this.value = value;
    }

    public Texture getTriangleGauche() {
	return triangleGauche;
    }

    public void setTriangleGauche(Texture triangleGauche) {
	this.triangleGauche = triangleGauche;
    }

    public Texture getTriangleDroite() {
	return triangleDroite;
    }

    public void setTriangleDroite(Texture triangleDroite) {
	this.triangleDroite = triangleDroite;
    }

    public Texture getRectangleCentre() {
	return rectangleCentre;
    }

    public void setRectangleCentre(Texture rectangleCentre) {
	this.rectangleCentre = rectangleCentre;
    }

}
