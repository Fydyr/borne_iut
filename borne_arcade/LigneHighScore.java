/**
 * Représente une entrée dans le tableau des meilleurs scores.
 * Chaque ligne contient un nom de joueur (3 caractères max) et un score.
 * Le format de sérialisation est {@code NOM-SCORE} (ex: "ABC-1500").
 */
class LigneHighScore{
    private String nom;
    private int score;

    /** Constructeur par défaut. Nom initialisé à "AAA", score à 0. */
    public LigneHighScore(){
	nom="AAA";
	score=0;
    }

    /**
     * Construit une ligne de highscore avec un nom et un score.
     * @param nnom nom du joueur (tronqué à 3 caractères, "AAA" si trop long)
     * @param sscore score du joueur (0 si négatif)
     */
    public LigneHighScore(String nnom, int sscore){
	if(nnom.length()>3)
	    nnom="AAA";
	else
	    nom=new String(nnom);
	if(sscore<0)
	    score=0;
	else
	    score=sscore;
    }

    /**
     * Constructeur par copie.
     * @param l la ligne de highscore à copier
     */
    public LigneHighScore(LigneHighScore l){
	nom=new String(l.nom);
	score=l.score;
    }

    /**
     * Construit une ligne de highscore à partir d'une chaîne sérialisée.
     * Le format attendu est {@code NOM-SCORE} (ex: "ABC-1500").
     * @param str la chaîne à parser
     */
    public LigneHighScore(String str){
	String[] tab = str.split("-");
	if(tab.length!=2){
	    nom = "AAA";
	    score=0;
	}else{
	    nom=new String(tab[0]);
	    score = Integer.parseInt(tab[1]);
	}
	    
    }

    public int getScore(){
	return score;
    }

    public String getNom(){
	return nom;
    }

    public String toString(){
	return nom+"-"+score;
    }
}
