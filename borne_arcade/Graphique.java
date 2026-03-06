import java.awt.Font;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import MG2D.geometrie.*;
import MG2D.geometrie.Point;
import MG2D.audio.*;
import MG2D.*;
import MG2D.FenetrePleinEcran;

public class Graphique {

    //private final Fenetre f;
    private static final FenetrePleinEcran f = new FenetrePleinEcran("_Menu Borne D'arcade_");
    private int TAILLEX;
    private int TAILLEY;
    private ClavierBorneArcade clavier;
    private BoiteSelection bs;
    private BoiteImage bi;
    private BoiteDescription bd;
    public static Bouton[] tableau;
    private Pointeur pointeur;
    Font font;
    Font fontSelect;
	public static boolean[] textesAffiches;
	public static Bruitage musiqueFond;
	private static String[] tableauMusiques;
	private static int cptMus;


    public Graphique(){
    	

	TAILLEX = 1280;
	TAILLEY = 1024;

	font = null;
	try{
	    File in = new File("fonts/PrStart.ttf");
	    font = font.createFont(Font.TRUETYPE_FONT, in);
	    font = font.deriveFont(32.0f);
	}catch (Exception e) {
	    System.err.println(e.getMessage());
	}

	//f = new Fenetre("_Menu Borne D'arcade_",TAILLEX,TAILLEY);
	f.setVisible(true);
	clavier = new ClavierBorneArcade();
	f.addKeyListener(clavier);
	f.getP().addKeyListener(clavier);

	/*Retrouver le nombre de jeux dispo*/
	Path yourPath = FileSystems.getDefault().getPath("projet/");
	int cpt=0;
	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(yourPath)) {
	    for (Path path : directoryStream) {
		cpt++;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

	tableau = new Bouton[cpt];
	textesAffiches = new boolean[cpt];
	for(int i=0;i<cpt;i++){
		textesAffiches[i]=true;
	}
	
	Bouton.remplirBouton();
	pointeur = new Pointeur();
	bs = new BoiteSelection(new Rectangle(Couleur .GRIS_CLAIR, new Point(0, 0), new Point(640, TAILLEY), true), pointeur);
	//f.ajouter(bs.getRectangle());
	//System.out.println(tableau[pointeur.getValue()].getChemin());
	bi = new BoiteImage(new Rectangle(Couleur .GRIS_FONCE, new Point(640, 512), new Point(TAILLEX, TAILLEY), true), new String(tableau[pointeur.getValue()].getChemin()));
	//f.ajouter(bi.getRectangle());
	bd = new BoiteDescription(new Rectangle(Couleur .GRIS, new Point(640, 0), new Point(TAILLEX, 512), true));
	bd.lireFichier(tableau[pointeur.getValue()].getChemin());
	bd.lireHighScore(tableau[pointeur.getValue()].getChemin());
	//f.ajouter(bd.getRectangle());

	Texture fond = new Texture("img/fondretro3.png", new Point(0, 0), TAILLEX, TAILLEY);
	f.ajouter(fond);
	//ajout apres fond car bug graphique sinon
	f.ajouter(bi.getImage());
	for(int i = 0 ; i < bd.getMessage().length ; i++){
	    f.ajouter(bd.getMessage()[i]);
	}
	//f.ajouter(bd.getMessage());
	f.ajouter(pointeur.getTriangleGauche());
	f.ajouter(pointeur.getTriangleDroite());
	for(int i = 0 ; i < tableau.length ; i++){
	    f.ajouter(tableau[i].getTexture());
	}
	f.ajouter(pointeur.getRectangleCentre());
	for(int i = 0 ; i < tableau.length ; i++){
	    f.ajouter(tableau[i].getTexte());
	    tableau[i].getTexte().setPolice(font);
	    tableau[i].getTexte().setCouleur(Couleur.BLANC);
	}
	//add texture
	for(int i = 0 ; i < bd.getBouton().length ; i++){
	    f.ajouter(bd.getBouton()[i]);
	}
	f.ajouter(bd.getJoystick());
	//add texte
	for(int i = 0 ; i < bd.gettBouton().length ; i++){
	    f.ajouter(bd.gettBouton()[i]);
	}
	f.ajouter(bd.gettJoystick());
	f.ajouter(new Ligne(Couleur.NOIR,new Point(670,360), new Point(1250,360)));
	f.ajouter(new Ligne(Couleur.NOIR,new Point(670,190), new Point(1250,190)));
	f.ajouter(new Ligne(Couleur.NOIR,new Point(960,210), new Point(960,310)));
	f.ajouter(bd.getHighscore());
	for(int i = 0 ; i < bd.getListeHighScore().length ; i++){
	    f.ajouter(bd.getListeHighScore()[i]);
	}
	
	/*Musique de fond*/
	//Comptage du nombre de musiques disponibles
	Path cheminMusiques = FileSystems.getDefault().getPath("sound/bg/");
	cptMus=0;
	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(cheminMusiques)) {
	    for (Path path : directoryStream) {
		cptMus++;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	//Creation d'un tableau de musiques
	tableauMusiques = new String[cptMus];
	try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(cheminMusiques)) {
	    int i = cptMus-1;
	    for (Path path : directoryStream) {
		tableauMusiques[i]=path.getFileName().toString();
		i--;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	//Choix d'une musique aleatoire et lecture de celle-ci
	this.lectureMusiqueFond();
    }

    public void selectionJeu(){	
		Texture fondBlancTransparent = new Texture("./img/blancTransparent.png", new Point(0,0));
		Rectangle boutonNon = new Rectangle(Couleur.ROUGE, new Point(340, 600), 200, 100, true);
		Rectangle boutonOui = new Rectangle(Couleur.VERT, new Point(740, 600), 200, 100, true);
		Texte message = new Texte(Couleur.NOIR, "Voulez vous vraiment quitter ?", font, new Point(640, 800));
		Texte non = new Texte(Couleur.NOIR, "NON", font, new Point(440, 650));
		Texte oui = new Texte(Couleur.NOIR, "OUI", font, new Point(840, 650));
		Rectangle rectSelection = new Rectangle(Couleur.BLEU, new Point(330,590),220,120, true);
		int frame=0;
		boolean fermetureMenu=false;
		int selectionSur = 0;
		Texte textePrec=tableau[pointeur.getValue()].getTexte();
		while(true){
			try {
				if(frame==0){
					if(textesAffiches[pointeur.getValue()]==true){
						f.supprimer(tableau[pointeur.getValue()].getTexte());
						textesAffiches[pointeur.getValue()]=false;
					}
				}
				if(frame==3){
					if(textesAffiches[pointeur.getValue()]==false){
						f.ajouter(tableau[pointeur.getValue()].getTexte());
						textesAffiches[pointeur.getValue()]=true;
					}
				}
				if(frame==6){
					frame=-1;
				}
				frame++;
				// System.out.println("frame n°"+frame);
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
			try{
				Thread.sleep(50);
			}catch(Exception e){}
			
			if(!fermetureMenu){
				if(bs.selection(clavier)){
				bi.setImage(tableau[pointeur.getValue()].getChemin());

				if(clavier.getBoutonJ1BTape()){
				    menuScripts(tableau[pointeur.getValue()]);
				}

				fontSelect = null;
				try{
				File in = new File("fonts/PrStart.ttf");
				fontSelect = fontSelect.createFont(Font.TRUETYPE_FONT, in);
				fontSelect = fontSelect.deriveFont(48.0f);
				}catch (Exception e) {
				System.err.println(e.getMessage());
				}

				// if(!tableau[pointeur.getValue()].getTexte().getPolice().equals(fontSelect)){
				// tableau[pointeur.getValue()].getTexte().setPolice(fontSelect);
				// }
				
				
				
				

				tableau[pointeur.getValue()].getTexte().setPolice(font);

				bd.lireFichier(tableau[pointeur.getValue()].getChemin());
				bd.lireHighScore(tableau[pointeur.getValue()].getChemin());
				bd.lireBouton(tableau[pointeur.getValue()].getChemin());
				/*
				// System.out.println(tableau[pointeur.getValue()].getChemin());
				// bd.setMessage(tableau[pointeur.getValue()].getNom());
				*/
				pointeur.lancerJeu(clavier);
				
				
				}else{
					f.ajouter(fondBlancTransparent);
					f.ajouter(message);
					f.ajouter(rectSelection);
					f.ajouter(boutonNon);
					f.ajouter(boutonOui);
					f.ajouter(non);
					f.ajouter(oui);
					fermetureMenu=true;
					
				}
			}else{
					if(clavier.getJoyJ1DroiteEnfoncee()){
						selectionSur=1;
					}
						
					if(clavier.getJoyJ1GaucheEnfoncee()){
						selectionSur=0;
					}
					   
					
					if(selectionSur==0){
						rectSelection.setA(new Point(330,590));
						rectSelection.setB(new Point(550,710));
					}
					else{
						rectSelection.setB(new Point(950,710));
						rectSelection.setA(new Point(730,590));
						
					}
					if(clavier.getBoutonJ1ATape()){
						if(selectionSur==0){
							f.supprimer(fondBlancTransparent);
							f.supprimer(message);
							f.supprimer(rectSelection);
							f.supprimer(boutonNon);
							f.supprimer(boutonOui);
							f.supprimer(non);
							f.supprimer(oui);
							fermetureMenu=false;
						}
						else{
							System.exit(0);
						}
					}

			}
			f.rafraichir();
		}//fin while true
    }

    public void menuScripts(Bouton jeu){
	String chemin = jeu.getChemin();
	File dir = new File(chemin);
	File[] files = dir.listFiles(new java.io.FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.endsWith(".sh");
	    }
	});

	if(files == null || files.length == 0) return;

	Texture fondBlancTransparent = new Texture("./img/blancTransparent.png", new Point(0,0));
	Rectangle fondMenu = new Rectangle(Couleur.BLANC, new Point(340, 300), new Point(940, 800), true);
	Texte titre = new Texte(Couleur.NOIR, "SCRIPTS", font, new Point(640, 750));
	
	f.ajouter(fondBlancTransparent);
	f.ajouter(fondMenu);
	f.ajouter(titre);

	ArrayList<Texte> listeTextes = new ArrayList<Texte>();
	for(int i=0; i<files.length; i++){
	    Texte t = new Texte(Couleur.NOIR, files[i].getName(), font.deriveFont(20.0f), new Point(640, 700 - i*40));
	    listeTextes.add(t);
	    f.ajouter(t);
	}

	Texture selecteur = new Texture("img/star.png", new Point(360, 690), 30, 30);
	f.ajouter(selecteur);

	int index = 0;
	boolean exit = false;

	while(!exit){
	    try{ Thread.sleep(50); }catch(Exception e){}

	    selecteur.setA(new Point(360, 690 - index*40));

	    if(clavier.getJoyJ1BasTape()){
		if(index < files.length - 1) index++;
	    }
	    if(clavier.getJoyJ1HautTape()){
		if(index > 0) index--;
	    }
	    if(clavier.getBoutonJ1ATape()){
		pointeur.lancerScript(files[index]);
		exit = true;
	    }
	    if(clavier.getBoutonJ1ZTape() || clavier.getBoutonJ1YTape()){
		exit = true;
	    }
	    
	    f.rafraichir();
	}

	f.supprimer(fondBlancTransparent);
	f.supprimer(fondMenu);
	f.supprimer(titre);
	f.supprimer(selecteur);
	for(Texte t : listeTextes){
	    f.supprimer(t);
	}
    }
    
    public static void lectureMusiqueFond() {
    	musiqueFond = new Bruitage ("sound/bg/"+tableauMusiques[(int)(Math.random()*cptMus)]);
    	musiqueFond.lecture();
    }
	
	public static void stopMusiqueFond(){
		musiqueFond.arret();
	}
	
	public static void afficherTexte(int valeur){
		f.ajouter(tableau[valeur].getTexte());
	}

	public static void setVisible(boolean visible){
		f.setVisible(visible);
	}
}
