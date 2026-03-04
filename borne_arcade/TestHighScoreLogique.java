import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Tests unitaires pour la logique pure de HighScore :
 *   - HighScore.suivant(char)
 *   - HighScore.precedent(char)
 *   - HighScore.lireFichier(String)
 *   - HighScore.enregistrerFichier(String, ArrayList, String, int)
 *
 * Aucune interaction graphique (Fenetre, etc.) — exécutable en mode headless.
 *
 * Usage : java -cp .:$HOME/git/MG2D/ TestHighScoreLogique
 */
class TestHighScoreLogique {

    private static int passed = 0;
    private static int failed = 0;

    static void assertEquals(Object expected, Object actual, String message) {
        boolean ok = expected == null ? actual == null : expected.equals(actual);
        if (ok) {
            System.out.println("  [OK] " + message);
            passed++;
        } else {
            System.err.println("  [ECHEC] " + message
                    + " (attendu: <" + expected + ">, obtenu: <" + actual + ">)");
            failed++;
        }
    }

    static void assertTrue(boolean condition, String message) {
        if (condition) {
            System.out.println("  [OK] " + message);
            passed++;
        } else {
            System.err.println("  [ECHEC] " + message);
            failed++;
        }
    }

    // ------------------------------------------------------------------ //
    // HighScore.suivant(char)                                              //
    // ------------------------------------------------------------------ //
    static void testSuivant() {
        System.out.println("--- HighScore.suivant ---");
        assertEquals('B', HighScore.suivant('A'), "suivant(A) = B");
        assertEquals('C', HighScore.suivant('B'), "suivant(B) = C");
        assertEquals('Z', HighScore.suivant('Y'), "suivant(Y) = Z");
        assertEquals('.', HighScore.suivant('Z'), "suivant(Z) = '.'");
        assertEquals(' ', HighScore.suivant('.'), "suivant('.') = ' '");
        assertEquals('A', HighScore.suivant(' '), "suivant(' ') = A");

        // Vérification que toute la chaîne A→Z→.→ →A forme un cycle cohérent
        char c = 'A';
        for (int i = 0; i < 26; i++) c = HighScore.suivant(c); // A → Z → '.'
        assertEquals('.', c, "26 appels depuis A aboutissent à '.'");
    }

    // ------------------------------------------------------------------ //
    // HighScore.precedent(char)                                            //
    // ------------------------------------------------------------------ //
    static void testPrecedent() {
        System.out.println("--- HighScore.precedent ---");
        assertEquals('Y', HighScore.precedent('Z'), "precedent(Z) = Y");
        assertEquals('A', HighScore.precedent('B'), "precedent(B) = A");
        assertEquals(' ', HighScore.precedent('A'), "precedent(A) = ' '");
        assertEquals('.', HighScore.precedent(' '), "precedent(' ') = '.'");
        assertEquals('Z', HighScore.precedent('.'), "precedent('.') = Z");
    }

    // ------------------------------------------------------------------ //
    // Propriété inverse : suivant(precedent(c)) == c et vice-versa        //
    // ------------------------------------------------------------------ //
    static void testSuivantPrecedentInverses() {
        System.out.println("--- Symétrie suivant / precedent ---");
        char[] chars = {'A','B','M','Z','.',' '};
        for (char c : chars) {
            assertEquals((Character) c, HighScore.suivant(HighScore.precedent(c)),
                    "suivant(precedent(" + c + ")) = " + c);
            assertEquals((Character) c, HighScore.precedent(HighScore.suivant(c)),
                    "precedent(suivant(" + c + ")) = " + c);
        }
    }

    // ------------------------------------------------------------------ //
    // HighScore.lireFichier : fichier inexistant                           //
    // ------------------------------------------------------------------ //
    static void testLireFichierInexistant() {
        System.out.println("--- lireFichier : fichier inexistant ---");
        ArrayList<LigneHighScore> list = HighScore.lireFichier("/tmp/fichier_inexistant_xyz.hig");
        assertTrue(list != null,        "résultat non null");
        assertEquals(0, list.size(),    "liste vide si fichier absent");
    }

    // ------------------------------------------------------------------ //
    // HighScore.enregistrerFichier + lireFichier : cycle complet          //
    // ------------------------------------------------------------------ //
    static void testEcrireEtLireFichier() throws Exception {
        System.out.println("--- enregistrerFichier + lireFichier ---");
        File tmp = File.createTempFile("highscore_test_", ".hig");
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();

        ArrayList<LigneHighScore> liste = new ArrayList<>();
        HighScore.enregistrerFichier(path, liste, "AAA", 1000);

        ArrayList<LigneHighScore> lu = HighScore.lireFichier(path);
        assertEquals(1,      lu.size(),          "1 entrée après premier enregistrement");
        assertEquals("AAA",  lu.get(0).getNom(), "nom correct après lecture");
        assertEquals(1000,   lu.get(0).getScore(),"score correct après lecture");
    }

    static void testOrdreDecroissant() throws Exception {
        System.out.println("--- Ordre décroissant des scores ---");
        File tmp = File.createTempFile("highscore_ordre_", ".hig");
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();

        ArrayList<LigneHighScore> liste = new ArrayList<>();
        HighScore.enregistrerFichier(path, liste, "MMM", 500);
        liste = HighScore.lireFichier(path);
        HighScore.enregistrerFichier(path, liste, "ZZZ", 900);
        liste = HighScore.lireFichier(path);
        HighScore.enregistrerFichier(path, liste, "AAA", 200);
        liste = HighScore.lireFichier(path);

        assertEquals(3, liste.size(), "3 entrées dans la liste");
        // Scores doivent être ordonnés du plus grand au plus petit
        assertTrue(liste.get(0).getScore() >= liste.get(1).getScore(), "pos 0 >= pos 1");
        assertTrue(liste.get(1).getScore() >= liste.get(2).getScore(), "pos 1 >= pos 2");
        assertEquals(900, liste.get(0).getScore(), "score le plus haut en premier");
        assertEquals(200, liste.get(2).getScore(), "score le plus bas en dernier");
    }

    static void testScoreEgauxOrdre() throws Exception {
        System.out.println("--- Scores égaux : ordre stable ---");
        File tmp = File.createTempFile("highscore_egal_", ".hig");
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();

        ArrayList<LigneHighScore> liste = new ArrayList<>();
        HighScore.enregistrerFichier(path, liste, "AAA", 500);
        liste = HighScore.lireFichier(path);
        HighScore.enregistrerFichier(path, liste, "BBB", 500);
        liste = HighScore.lireFichier(path);

        assertEquals(2, liste.size(), "2 entrées avec scores égaux");
        // les deux entrées existent, peu importe l'ordre exact
        boolean bothPresent =
            (liste.get(0).getScore() == 500 && liste.get(1).getScore() == 500);
        assertTrue(bothPresent, "les deux scores 500 sont présents");
    }

    static void testLimite10Entrees() throws Exception {
        System.out.println("--- Limite de 10 entrées ---");
        File tmp = File.createTempFile("highscore_limite_", ".hig");
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();

        ArrayList<LigneHighScore> liste = new ArrayList<>();
        // Insérer 11 scores différents (décroissants)
        for (int i = 11; i >= 1; i--) {
            HighScore.enregistrerFichier(path, liste, "P" + (char)('A' + (i % 3)) + "X", i * 100);
            liste = HighScore.lireFichier(path);
        }

        assertTrue(liste.size() <= 10, "au maximum 10 entrées conservées (obtenu: " + liste.size() + ")");
        // Le score le plus faible (100) doit avoir été éliminé si besoin
        boolean found100 = false;
        for (LigneHighScore l : liste) found100 |= (l.getScore() == 100);
        assertTrue(!found100, "le score le plus faible est éliminé au-delà de 10");
    }

    static void testFichierExistantAvecContenu() throws Exception {
        System.out.println("--- lireFichier depuis un fichier existant ---");
        File tmp = File.createTempFile("highscore_existant_", ".hig");
        tmp.deleteOnExit();
        // Écrire manuellement des lignes
        BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
        bw.write("AAA-5000\nBBB-3000\nCCC-1000");
        bw.close();

        ArrayList<LigneHighScore> liste = HighScore.lireFichier(tmp.getAbsolutePath());
        assertEquals(3,      liste.size(),          "3 lignes lues");
        assertEquals("AAA",  liste.get(0).getNom(), "ligne 0 : nom");
        assertEquals(5000,   liste.get(0).getScore(),"ligne 0 : score");
        assertEquals("CCC",  liste.get(2).getNom(), "ligne 2 : nom");
        assertEquals(1000,   liste.get(2).getScore(),"ligne 2 : score");
    }

    static void testNouveauScoreNePasEntreDansTop10() throws Exception {
        System.out.println("--- Score trop faible n'entre pas dans le top 10 ---");
        File tmp = File.createTempFile("highscore_top10_", ".hig");
        tmp.deleteOnExit();
        String path = tmp.getAbsolutePath();

        // Remplir 10 scores élevés
        ArrayList<LigneHighScore> liste = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            HighScore.enregistrerFichier(path, liste, "P" + (char)('A' + i) + "X", i * 1000);
            liste = HighScore.lireFichier(path);
        }
        assertEquals(10, liste.size(), "10 entrées après remplissage");

        // Ajouter un score plus faible que le dernier (liste non modifiée par la méthode)
        int scoreFaible = 50; // inférieur au minimum (1000)
        ArrayList<LigneHighScore> listeAvant = new ArrayList<>(liste);
        HighScore.enregistrerFichier(path, listeAvant, "LOW", scoreFaible);
        ArrayList<LigneHighScore> listeApres = HighScore.lireFichier(path);

        // Le score LOW doit être présent dans listeAvant (ajouté) mais éliminé au trim
        assertTrue(listeApres.size() <= 10, "toujours ≤ 10 après ajout d'un faible score");
        boolean foundLow = false;
        for (LigneHighScore l : listeApres) foundLow |= "LOW".equals(l.getNom());
        assertTrue(!foundLow, "score trop faible éliminé du top 10");
    }

    // ------------------------------------------------------------------ //
    // Point d'entrée                                                       //
    // ------------------------------------------------------------------ //
    public static void main(String[] args) throws Exception {
        System.out.println("=== Tests logique HighScore ===");

        testSuivant();
        testPrecedent();
        testSuivantPrecedentInverses();
        testLireFichierInexistant();
        testEcrireEtLireFichier();
        testOrdreDecroissant();
        testScoreEgauxOrdre();
        testLimite10Entrees();
        testFichierExistantAvecContenu();
        testNouveauScoreNePasEntreDansTop10();

        System.out.println();
        System.out.println("Résultat : " + passed + " réussis, " + failed + " échoués sur " + (passed + failed) + " tests.");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
