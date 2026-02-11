import java.awt.Font;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;


import MG2D.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import MG2D.geometrie.Texte;

/**
 * Représente un bouton dans la liste de sélection du menu.
 * Chaque bouton correspond à un jeu disponible et contient son nom,
 * son chemin sur le disque, sa texture d'arrière-plan et son texte affiché.
 * La méthode statique {@link #remplirBouton()} scanne le répertoire {@code projet/}
 * pour créer automatiquement un bouton par jeu détecté.
 */
public class Bouton {
    private Texte texte;
    private String chemin;
    private String nom;
    private Texture texture;
    private int numeroDeJeu;


    /** Constructeur par défaut. Tous les attributs sont initialisés à null. */
    public Bouton(){
	this.texte = null;
	this.texture = null;
	this.chemin = null;
	this.nom = null;
    }

    /**
     * Construit un bouton avec tous ses attributs.
     * @param texte le texte MG2D affiché sur le bouton
     * @param texture la texture d'arrière-plan du bouton
     * @param chemin le chemin vers le répertoire du jeu
     * @param nom le nom du jeu
     */
    public Bouton(Texte texte, Texture texture, String chemin, String nom){
	this.texte = texte;
	this.texture = texture;
	this.chemin = chemin;
	this.nom = nom;
    }

    /**
     * Scanne le répertoire {@code projet/} et crée un bouton pour chaque
     * sous-répertoire trouvé (chaque jeu). Les boutons sont stockés
     * dans {@link Graphique#tableau}.
     */
    public static void remplirBouton(){
	for(int i = 0 ; i < Graphique.tableau.length ; i++){
	    Graphique.tableau[i] = new Bouton();
	}

	Path yourPath = FileSystems.getDefault().getPath("projet/");

	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(yourPath)) {
	    int i = Graphique.tableau.length - 1;
	    for (Path path : directoryStream) {
		Graphique.tableau[i].setTexte(new Texte(Couleur .NOIR, path.getFileName().toString(), new Font("Calibri", Font.TYPE1_FONT, 30), new Point(310, 510)));
		Graphique.tableau[i].setTexture(new Texture("img/bouton2.png", new Point(100, 478), 400, 65));
		for(int j=0;j<Graphique.tableau.length-(i+1);j++){
		    Graphique.tableau[i].getTexte().translater(0,-110);
		    Graphique.tableau[i].getTexture().translater(0,-110);
		}
		Graphique.tableau[i].setChemin("projet/"+path.getFileName().toString());
		Graphique.tableau[i].setNom(path.getFileName().toString());
		Graphique.tableau[i].setNumeroDeJeu(i);
		i--;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public String getChemin() {
	return chemin;
    }

    public void setChemin(String chemin) {
	this.chemin = chemin;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Texte getTexte() {
	return texte;
    }

    public void setTexte(Texte texte) {
	this.texte = texte;
    }

    public Texture getTexture() {
	return texture;
    }

    public void setTexture(Texture texture) {
	this.texture = texture;
    }

    public int getNumeroDeJeu() {
	return numeroDeJeu;
    }

    public void setNumeroDeJeu(int numeroDeJeu) {
	this.numeroDeJeu = numeroDeJeu;
    }
}
