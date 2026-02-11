/**
 * Point d'entrée de l'application Borne Arcade.
 * Instancie l'interface graphique et lance la boucle principale
 * de sélection des jeux.
 */
public class Main {
    /**
     * Méthode principale. Crée l'interface graphique puis entre
     * dans la boucle infinie de sélection de jeu.
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args){
	Graphique g = new Graphique();
	while(true){
	    try{
		// Thread.sleep(250);
	    }catch(Exception e){};
	    g.selectionJeu();
	}
    }
}
