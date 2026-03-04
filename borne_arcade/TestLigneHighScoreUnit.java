import java.util.ArrayList;

/**
 * Tests unitaires pour la classe LigneHighScore.
 * Aucune dépendance MG2D — exécutable en mode headless.
 *
 * Usage : java TestLigneHighScoreUnit
 */
class TestLigneHighScoreUnit {

    private static int passed = 0;
    private static int failed = 0;

    static void assertTrue(boolean condition, String message) {
        if (condition) {
            System.out.println("  [OK] " + message);
            passed++;
        } else {
            System.err.println("  [ECHEC] " + message);
            failed++;
        }
    }

    static void assertEquals(Object expected, Object actual, String message) {
        boolean ok = expected == null ? actual == null : expected.equals(actual);
        if (ok) {
            System.out.println("  [OK] " + message);
            passed++;
        } else {
            System.err.println("  [ECHEC] " + message + " (attendu: <" + expected + ">, obtenu: <" + actual + ">)");
            failed++;
        }
    }

    // ------------------------------------------------------------------ //
    // Constructeur par défaut                                              //
    // ------------------------------------------------------------------ //
    static void testConstructeurParDefaut() {
        System.out.println("--- Constructeur par défaut ---");
        LigneHighScore l = new LigneHighScore();
        assertEquals("AAA", l.getNom(),  "nom par défaut = AAA");
        assertEquals(0,     l.getScore(), "score par défaut = 0");
    }

    // ------------------------------------------------------------------ //
    // Constructeur (nom, score)                                            //
    // ------------------------------------------------------------------ //
    static void testConstructeurNomScore() {
        System.out.println("--- Constructeur (nom, score) ---");
        LigneHighScore l = new LigneHighScore("ABC", 1500);
        assertEquals("ABC",  l.getNom(),   "nom correct");
        assertEquals(1500,   l.getScore(), "score correct");

        LigneHighScore l2 = new LigneHighScore("ZZZ", 0);
        assertEquals("ZZZ", l2.getNom(),   "nom 3 chars valide");
        assertEquals(0,     l2.getScore(), "score = 0 accepté");
    }

    static void testConstructeurNomTropLong() {
        System.out.println("--- Constructeur : nom trop long ---");
        LigneHighScore l = new LigneHighScore("ABCDE", 500);
        assertEquals("AAA", l.getNom(), "nom > 3 chars → AAA");
        assertEquals(500,   l.getScore(), "score inchangé malgré nom invalide");
    }

    static void testConstructeurScoreNegatif() {
        System.out.println("--- Constructeur : score négatif ---");
        LigneHighScore l = new LigneHighScore("XYZ", -10);
        assertEquals("XYZ", l.getNom(),   "nom inchangé avec score négatif");
        assertEquals(0,     l.getScore(), "score négatif → 0");
    }

    static void testConstructeurNomVideEtScoreNegatif() {
        System.out.println("--- Constructeur : nom vide + score négatif ---");
        LigneHighScore l = new LigneHighScore("", -999);
        assertEquals(0, l.getScore(), "score négatif → 0 (nom vide)");
    }

    // ------------------------------------------------------------------ //
    // Constructeur de copie                                                //
    // ------------------------------------------------------------------ //
    static void testConstructeurCopie() {
        System.out.println("--- Constructeur de copie ---");
        LigneHighScore original = new LigneHighScore("DEF", 750);
        LigneHighScore copie    = new LigneHighScore(original);
        assertEquals("DEF", copie.getNom(),   "copie : nom identique");
        assertEquals(750,   copie.getScore(), "copie : score identique");

        // Indépendance (pas de mutation croisée au niveau du score — int est primitif)
        assertTrue(original != copie, "copie : objet distinct");
    }

    // ------------------------------------------------------------------ //
    // Constructeur depuis chaîne "NOM-SCORE"                              //
    // ------------------------------------------------------------------ //
    static void testConstructeurParseValide() {
        System.out.println("--- Constructeur parse (valide) ---");
        LigneHighScore l = new LigneHighScore("GHI-2000");
        assertEquals("GHI",  l.getNom(),   "parse : nom correct");
        assertEquals(2000,   l.getScore(), "parse : score correct");
    }

    static void testConstructeurParseScoreZero() {
        System.out.println("--- Constructeur parse (score = 0) ---");
        LigneHighScore l = new LigneHighScore("AAA-0");
        assertEquals("AAA", l.getNom(),   "parse : nom AAA");
        assertEquals(0,     l.getScore(), "parse : score 0");
    }

    static void testConstructeurParseFormatInvalide() {
        System.out.println("--- Constructeur parse (format invalide) ---");
        LigneHighScore l1 = new LigneHighScore("PAS_DE_TIRET");
        assertEquals("AAA", l1.getNom(),   "sans tiret → nom AAA");
        assertEquals(0,     l1.getScore(), "sans tiret → score 0");

        LigneHighScore l2 = new LigneHighScore("");
        assertEquals("AAA", l2.getNom(),   "vide → nom AAA");
        assertEquals(0,     l2.getScore(), "vide → score 0");
    }

    static void testConstructeurParseTropDeTirets() {
        System.out.println("--- Constructeur parse (trop de tirets) ---");
        // "A-B-C" → split donne 3 parts → tab.length != 2 → défaut
        LigneHighScore l = new LigneHighScore("A-B-C");
        assertEquals("AAA", l.getNom(),   "trop de tirets → nom AAA");
        assertEquals(0,     l.getScore(), "trop de tirets → score 0");
    }

    // ------------------------------------------------------------------ //
    // toString                                                             //
    // ------------------------------------------------------------------ //
    static void testToString() {
        System.out.println("--- toString ---");
        LigneHighScore l = new LigneHighScore("JKL", 3000);
        assertEquals("JKL-3000", l.toString(), "toString format NOM-SCORE");

        LigneHighScore l2 = new LigneHighScore();
        assertEquals("AAA-0", l2.toString(), "toString défaut");
    }

    static void testToStringRoundtrip() {
        System.out.println("--- toString round-trip ---");
        LigneHighScore original = new LigneHighScore("MNO", 9999);
        LigneHighScore parsed   = new LigneHighScore(original.toString());
        assertEquals(original.getNom(),   parsed.getNom(),   "round-trip : nom");
        assertEquals(original.getScore(), parsed.getScore(), "round-trip : score");
    }

    // ------------------------------------------------------------------ //
    // Intégration : tri et liste de scores                                 //
    // ------------------------------------------------------------------ //
    static void testTriListeScores() {
        System.out.println("--- Tri d'une liste de scores ---");
        ArrayList<LigneHighScore> list = new ArrayList<>();
        list.add(new LigneHighScore("AAA", 500));
        list.add(new LigneHighScore("BBB", 300));
        list.add(new LigneHighScore("CCC", 100));

        // Vérifier que la liste est bien ordonnée (décroissant)
        assertTrue(list.get(0).getScore() >= list.get(1).getScore(), "liste décroissante pos 0≥1");
        assertTrue(list.get(1).getScore() >= list.get(2).getScore(), "liste décroissante pos 1≥2");
    }

    // ------------------------------------------------------------------ //
    // Valeurs limites                                                      //
    // ------------------------------------------------------------------ //
    static void testValeurLimites() {
        System.out.println("--- Valeurs limites ---");
        LigneHighScore max = new LigneHighScore("ZZZ", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, max.getScore(), "score Integer.MAX_VALUE");

        LigneHighScore min = new LigneHighScore("ZZZ", 1);
        assertEquals(1, min.getScore(), "score minimal positif = 1");
    }

    // ------------------------------------------------------------------ //
    // Point d'entrée                                                       //
    // ------------------------------------------------------------------ //
    public static void main(String[] args) {
        System.out.println("=== Tests LigneHighScore ===");

        testConstructeurParDefaut();
        testConstructeurNomScore();
        testConstructeurNomTropLong();
        testConstructeurScoreNegatif();
        testConstructeurNomVideEtScoreNegatif();
        testConstructeurCopie();
        testConstructeurParseValide();
        testConstructeurParseScoreZero();
        testConstructeurParseFormatInvalide();
        testConstructeurParseTropDeTirets();
        testToString();
        testToStringRoundtrip();
        testTriListeScores();
        testValeurLimites();

        System.out.println();
        System.out.println("Résultat : " + passed + " réussis, " + failed + " échoués sur " + (passed + failed) + " tests.");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
