import java.awt.event.KeyEvent;
import java.awt.Component;

/**
 * Tests unitaires pour ClavierBorneArcade.
 * Simule des pressions / relâchements de touches via KeyEvent
 * sans afficher de fenêtre graphique.
 *
 * Usage : java -Djava.awt.headless=true -cp .:$HOME/git/MG2D/ TestClavierLogique
 */
class TestClavierLogique {

    private static int passed = 0;
    private static int failed = 0;

    /** Composant AWT factice servant de source aux KeyEvent. */
    private static final Component DUMMY_COMP = new Component() {};

    static void assertTrue(boolean condition, String message) {
        if (condition) {
            System.out.println("  [OK] " + message);
            passed++;
        } else {
            System.err.println("  [ECHEC] " + message);
            failed++;
        }
    }

    static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    // ------------------------------------------------------------------ //
    // Helpers pour créer des KeyEvent                                     //
    // ------------------------------------------------------------------ //
    static KeyEvent press(int keyCode) {
        return new KeyEvent(DUMMY_COMP, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    static KeyEvent release(int keyCode) {
        return new KeyEvent(DUMMY_COMP, KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    // ------------------------------------------------------------------ //
    // Helpers : press + release d'une touche sur un clavier               //
    // ------------------------------------------------------------------ //
    static void simulerAppuiRelachement(ClavierBorneArcade clavier, int keyCode) {
        clavier.keyPressed(press(keyCode));
        clavier.keyReleased(release(keyCode));
    }

    // ------------------------------------------------------------------ //
    // Tests de l'état initial                                              //
    // ------------------------------------------------------------------ //
    static void testEtatInitial() {
        System.out.println("--- État initial ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        assertFalse(c.getJoyJ1HautEnfoncee(),   "J1 Haut : non enfoncée au départ");
        assertFalse(c.getJoyJ1BasEnfoncee(),    "J1 Bas  : non enfoncée au départ");
        assertFalse(c.getJoyJ1GaucheEnfoncee(), "J1 Gauche : non enfoncée au départ");
        assertFalse(c.getJoyJ1DroiteEnfoncee(), "J1 Droite : non enfoncée au départ");

        assertFalse(c.getBoutonJ1AEnfoncee(),   "J1 A : non enfoncée au départ");
        assertFalse(c.getBoutonJ1BEnfoncee(),   "J1 B : non enfoncée au départ");
        assertFalse(c.getBoutonJ1CEnfoncee(),   "J1 C : non enfoncée au départ");
        assertFalse(c.getBoutonJ1XEnfoncee(),   "J1 X : non enfoncée au départ");
        assertFalse(c.getBoutonJ1YEnfoncee(),   "J1 Y : non enfoncée au départ");
        assertFalse(c.getBoutonJ1ZEnfoncee(),   "J1 Z : non enfoncée au départ");

        assertFalse(c.getJoyJ2HautEnfoncee(),   "J2 Haut : non enfoncée au départ");
        assertFalse(c.getJoyJ2BasEnfoncee(),    "J2 Bas  : non enfoncée au départ");
        assertFalse(c.getJoyJ2GaucheEnfoncee(), "J2 Gauche : non enfoncée au départ");
        assertFalse(c.getJoyJ2DroiteEnfoncee(), "J2 Droite : non enfoncée au départ");

        assertFalse(c.getBoutonJ2AEnfoncee(),   "J2 A : non enfoncée au départ");
        assertFalse(c.getBoutonJ2BEnfoncee(),   "J2 B : non enfoncée au départ");
        assertFalse(c.getBoutonJ2CEnfoncee(),   "J2 C : non enfoncée au départ");
        assertFalse(c.getBoutonJ2XEnfoncee(),   "J2 X : non enfoncée au départ");
        assertFalse(c.getBoutonJ2YEnfoncee(),   "J2 Y : non enfoncée au départ");
        assertFalse(c.getBoutonJ2ZEnfoncee(),   "J2 Z : non enfoncée au départ");

        // "Tape" aussi à false avant tout appui
        assertFalse(c.getJoyJ1HautTape(),   "J1 Haut Tape : false avant tout appui");
        assertFalse(c.getBoutonJ1ATape(),   "J1 A Tape : false avant tout appui");
    }

    // ------------------------------------------------------------------ //
    // Joueur 1 — directions joystick                                      //
    // ------------------------------------------------------------------ //
    static void testJ1Joystick() {
        System.out.println("--- J1 Joystick ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        // Haut (flèche haut)
        c.keyPressed(press(KeyEvent.VK_UP));
        assertTrue (c.getJoyJ1HautEnfoncee(),   "J1 Haut enfoncée après VK_UP pressed");
        assertFalse(c.getJoyJ1BasEnfoncee(),    "J1 Bas non enfoncée");
        c.keyReleased(release(KeyEvent.VK_UP));
        assertFalse(c.getJoyJ1HautEnfoncee(),   "J1 Haut relâchée après VK_UP released");
        assertTrue (c.getJoyJ1HautTape(),       "J1 Haut Tape true après relâchement");
        assertFalse(c.getJoyJ1HautTape(),       "J1 Haut Tape false au 2ème appel (consommé)");

        // Bas (flèche bas)
        c.keyPressed(press(KeyEvent.VK_DOWN));
        assertTrue (c.getJoyJ1BasEnfoncee(),   "J1 Bas enfoncée");
        c.keyReleased(release(KeyEvent.VK_DOWN));
        assertTrue (c.getJoyJ1BasTape(),       "J1 Bas Tape true");
        assertFalse(c.getJoyJ1BasTape(),       "J1 Bas Tape consommé");

        // Gauche (flèche gauche)
        c.keyPressed(press(KeyEvent.VK_LEFT));
        assertTrue (c.getJoyJ1GaucheEnfoncee(), "J1 Gauche enfoncée");
        c.keyReleased(release(KeyEvent.VK_LEFT));
        assertTrue (c.getJoyJ1GaucheTape(),    "J1 Gauche Tape true");
        assertFalse(c.getJoyJ1GaucheTape(),    "J1 Gauche Tape consommé");

        // Droite (flèche droite)
        c.keyPressed(press(KeyEvent.VK_RIGHT));
        assertTrue (c.getJoyJ1DroiteEnfoncee(), "J1 Droite enfoncée");
        c.keyReleased(release(KeyEvent.VK_RIGHT));
        assertTrue (c.getJoyJ1DroiteTape(),    "J1 Droite Tape true");
        assertFalse(c.getJoyJ1DroiteTape(),    "J1 Droite Tape consommé");
    }

    // ------------------------------------------------------------------ //
    // Joueur 1 — boutons                                                  //
    // ------------------------------------------------------------------ //
    static void testJ1Boutons() {
        System.out.println("--- J1 Boutons (f,g,h,r,t,y) ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        int[] keyCodes = {KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H,
                          KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y};
        String[] noms = {"J1-A(f)", "J1-B(g)", "J1-C(h)", "J1-X(r)", "J1-Y(t)", "J1-Z(y)"};

        for (int i = 0; i < keyCodes.length; i++) {
            c.keyPressed(press(keyCodes[i]));
            // Vérifier que TOUTES les autres touches ne sont pas "contaminées"
            boolean[] etatApresPress = {
                c.getBoutonJ1AEnfoncee(),
                c.getBoutonJ1BEnfoncee(),
                c.getBoutonJ1CEnfoncee(),
                c.getBoutonJ1XEnfoncee(),
                c.getBoutonJ1YEnfoncee(),
                c.getBoutonJ1ZEnfoncee()
            };
            assertTrue(etatApresPress[i], noms[i] + " enfoncée après press");

            // Les autres boutons ne doivent pas être activés
            for (int j = 0; j < keyCodes.length; j++) {
                if (j != i) {
                    assertFalse(etatApresPress[j], noms[j] + " non enfoncée pendant press de " + noms[i]);
                }
            }
            c.keyReleased(release(keyCodes[i]));
        }

        // Vérifier les "Tape" séparément
        simulerAppuiRelachement(c, KeyEvent.VK_F); assertTrue(c.getBoutonJ1ATape(), "J1-A Tape true");
        simulerAppuiRelachement(c, KeyEvent.VK_G); assertTrue(c.getBoutonJ1BTape(), "J1-B Tape true");
        simulerAppuiRelachement(c, KeyEvent.VK_H); assertTrue(c.getBoutonJ1CTape(), "J1-C Tape true");
        simulerAppuiRelachement(c, KeyEvent.VK_R); assertTrue(c.getBoutonJ1XTape(), "J1-X Tape true");
        simulerAppuiRelachement(c, KeyEvent.VK_T); assertTrue(c.getBoutonJ1YTape(), "J1-Y Tape true");
        simulerAppuiRelachement(c, KeyEvent.VK_Y); assertTrue(c.getBoutonJ1ZTape(), "J1-Z Tape true");
    }

    // ------------------------------------------------------------------ //
    // Joueur 2 — directions joystick                                      //
    // ------------------------------------------------------------------ //
    static void testJ2Joystick() {
        System.out.println("--- J2 Joystick (o,l,k,m) ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        // Haut (O)
        c.keyPressed(press(KeyEvent.VK_O));
        assertTrue(c.getJoyJ2HautEnfoncee(),    "J2 Haut enfoncée (O)");
        c.keyReleased(release(KeyEvent.VK_O));
        assertTrue (c.getJoyJ2HautTape(),       "J2 Haut Tape true");
        assertFalse(c.getJoyJ2HautTape(),       "J2 Haut Tape consommé");

        // Bas (L)
        c.keyPressed(press(KeyEvent.VK_L));
        assertTrue(c.getJoyJ2BasEnfoncee(),     "J2 Bas enfoncée (L)");
        c.keyReleased(release(KeyEvent.VK_L));
        assertTrue (c.getJoyJ2BasTape(),        "J2 Bas Tape true");

        // Gauche (K)
        c.keyPressed(press(KeyEvent.VK_K));
        assertTrue(c.getJoyJ2GaucheEnfoncee(),  "J2 Gauche enfoncée (K)");
        c.keyReleased(release(KeyEvent.VK_K));
        assertTrue (c.getJoyJ2GaucheTape(),     "J2 Gauche Tape true");

        // Droite (M)
        c.keyPressed(press(KeyEvent.VK_M));
        assertTrue(c.getJoyJ2DroiteEnfoncee(),  "J2 Droite enfoncée (M)");
        c.keyReleased(release(KeyEvent.VK_M));
        assertTrue (c.getJoyJ2DroiteTape(),     "J2 Droite Tape true");
    }

    // ------------------------------------------------------------------ //
    // Joueur 2 — boutons                                                  //
    // ------------------------------------------------------------------ //
    static void testJ2Boutons() {
        System.out.println("--- J2 Boutons (q,s,d,a,z,e) ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        // A=q, B=s, C=d, X=a, Y=z, Z=e
        simulerAppuiRelachement(c, KeyEvent.VK_Q); assertTrue(c.getBoutonJ2ATape(), "J2-A(q) Tape");
        simulerAppuiRelachement(c, KeyEvent.VK_S); assertTrue(c.getBoutonJ2BTape(), "J2-B(s) Tape");
        simulerAppuiRelachement(c, KeyEvent.VK_D); assertTrue(c.getBoutonJ2CTape(), "J2-C(d) Tape");
        simulerAppuiRelachement(c, KeyEvent.VK_A); assertTrue(c.getBoutonJ2XTape(), "J2-X(a) Tape");
        simulerAppuiRelachement(c, KeyEvent.VK_Z); assertTrue(c.getBoutonJ2YTape(), "J2-Y(z) Tape");
        simulerAppuiRelachement(c, KeyEvent.VK_E); assertTrue(c.getBoutonJ2ZTape(), "J2-Z(e) Tape");

        // État "enfoncée"
        c.keyPressed(press(KeyEvent.VK_Q));
        assertTrue (c.getBoutonJ2AEnfoncee(),  "J2-A(q) enfoncée");
        assertFalse(c.getBoutonJ2BEnfoncee(),  "J2-B non enfoncée pendant Q");
        c.keyReleased(release(KeyEvent.VK_Q));
        assertFalse(c.getBoutonJ2AEnfoncee(),  "J2-A(q) relâchée");

        c.keyPressed(press(KeyEvent.VK_S));
        assertTrue (c.getBoutonJ2BEnfoncee(),  "J2-B(s) enfoncée");
        c.keyReleased(release(KeyEvent.VK_S));

        c.keyPressed(press(KeyEvent.VK_D));
        assertTrue (c.getBoutonJ2CEnfoncee(),  "J2-C(d) enfoncée");
        c.keyReleased(release(KeyEvent.VK_D));

        c.keyPressed(press(KeyEvent.VK_A));
        assertTrue (c.getBoutonJ2XEnfoncee(),  "J2-X(a) enfoncée");
        c.keyReleased(release(KeyEvent.VK_A));

        c.keyPressed(press(KeyEvent.VK_Z));
        assertTrue (c.getBoutonJ2YEnfoncee(),  "J2-Y(z) enfoncée");
        c.keyReleased(release(KeyEvent.VK_Z));

        c.keyPressed(press(KeyEvent.VK_E));
        assertTrue (c.getBoutonJ2ZEnfoncee(),  "J2-Z(e) enfoncée");
        c.keyReleased(release(KeyEvent.VK_E));
    }

    // ------------------------------------------------------------------ //
    // "Tape" est consommée après lecture                                   //
    // ------------------------------------------------------------------ //
    static void testTapeConsommee() {
        System.out.println("--- Tape consommée après lecture ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        simulerAppuiRelachement(c, KeyEvent.VK_UP);
        assertTrue (c.getJoyJ1HautTape(),  "Tape : true au 1er appel");
        assertFalse(c.getJoyJ1HautTape(),  "Tape : false au 2ème appel");
        assertFalse(c.getJoyJ1HautTape(),  "Tape : false au 3ème appel");

        simulerAppuiRelachement(c, KeyEvent.VK_F);
        assertTrue (c.getBoutonJ1ATape(), "J1-A Tape : true au 1er appel");
        assertFalse(c.getBoutonJ1ATape(), "J1-A Tape : false au 2ème appel");
    }

    // ------------------------------------------------------------------ //
    // Appui maintenu : "Tape" pas déclenché avant relâchement              //
    // ------------------------------------------------------------------ //
    static void testAppuiMaintenuSansTape() {
        System.out.println("--- Appui maintenu : pas de Tape avant relâchement ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        c.keyPressed(press(KeyEvent.VK_UP));
        assertTrue (c.getJoyJ1HautEnfoncee(), "enfoncée pendant appui maintenu");
        assertFalse(c.getJoyJ1HautTape(),     "Tape false pendant appui maintenu");

        c.keyReleased(release(KeyEvent.VK_UP));
        assertFalse(c.getJoyJ1HautEnfoncee(), "relâchée après VK_UP released");
        assertTrue (c.getJoyJ1HautTape(),     "Tape true après relâchement");
    }

    // ------------------------------------------------------------------ //
    // Deux touches en même temps                                           //
    // ------------------------------------------------------------------ //
    static void testDeuxTouchesSimultanees() {
        System.out.println("--- Deux touches simultanées ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        c.keyPressed(press(KeyEvent.VK_UP));
        c.keyPressed(press(KeyEvent.VK_F));
        assertTrue(c.getJoyJ1HautEnfoncee(), "J1 Haut enfoncée simultanément avec F");
        assertTrue(c.getBoutonJ1AEnfoncee(), "J1-A(f) enfoncée simultanément avec UP");

        c.keyReleased(release(KeyEvent.VK_UP));
        assertFalse(c.getJoyJ1HautEnfoncee(), "J1 Haut relâchée");
        assertTrue (c.getBoutonJ1AEnfoncee(), "J1-A toujours enfoncée");

        c.keyReleased(release(KeyEvent.VK_F));
        assertFalse(c.getBoutonJ1AEnfoncee(), "J1-A relâchée");
    }

    // ------------------------------------------------------------------ //
    // reinitialisation()                                                   //
    // ------------------------------------------------------------------ //
    static void testReinitialisation() {
        System.out.println("--- reinitialisation() ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        // Enfoncer plusieurs touches
        c.keyPressed(press(KeyEvent.VK_UP));
        c.keyPressed(press(KeyEvent.VK_F));
        c.keyPressed(press(KeyEvent.VK_O));
        // Relâcher certaines pour avoir des "Tape" en attente
        c.keyReleased(release(KeyEvent.VK_UP));
        c.keyReleased(release(KeyEvent.VK_F));

        c.reinitialisation();

        assertFalse(c.getJoyJ1HautEnfoncee(),   "après reinit : J1 Haut non enfoncée");
        assertFalse(c.getBoutonJ1AEnfoncee(),   "après reinit : J1-A non enfoncée");
        assertFalse(c.getJoyJ2HautEnfoncee(),   "après reinit : J2 Haut non enfoncée");

        assertFalse(c.getJoyJ1HautTape(),       "après reinit : J1 Haut Tape false");
        assertFalse(c.getBoutonJ1ATape(),       "après reinit : J1-A Tape false");
    }

    // ------------------------------------------------------------------ //
    // Isolation entre joueurs : touches J2 n'affectent pas J1             //
    // ------------------------------------------------------------------ //
    static void testIsolationJoueurs() {
        System.out.println("--- Isolation J1 / J2 ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        c.keyPressed(press(KeyEvent.VK_O)); // J2 Haut
        assertFalse(c.getJoyJ1HautEnfoncee(), "J1 Haut non affectée par touche J2");
        assertTrue (c.getJoyJ2HautEnfoncee(), "J2 Haut bien activée");

        c.keyPressed(press(KeyEvent.VK_UP)); // J1 Haut
        assertTrue(c.getJoyJ1HautEnfoncee(),  "J1 Haut activée indépendamment");
        assertTrue(c.getJoyJ2HautEnfoncee(),  "J2 Haut toujours active");

        c.keyReleased(release(KeyEvent.VK_O));
        assertTrue (c.getJoyJ1HautEnfoncee(), "J1 Haut toujours active après relâch J2");
        assertFalse(c.getJoyJ2HautEnfoncee(), "J2 Haut relâchée");
    }

    // ------------------------------------------------------------------ //
    // Touche inconnue : pas d'effet de bord                               //
    // ------------------------------------------------------------------ //
    static void testToucheInconnue() {
        System.out.println("--- Touche inconnue ignorée ---");
        ClavierBorneArcade c = new ClavierBorneArcade();

        // VK_P n'est mappé sur aucun bouton
        c.keyPressed(press(KeyEvent.VK_P));
        c.keyReleased(release(KeyEvent.VK_P));

        assertFalse(c.getJoyJ1HautEnfoncee(),   "aucun effet de bord (J1 haut)");
        assertFalse(c.getJoyJ1HautTape(),       "aucun effet de bord (J1 haut tape)");
        assertFalse(c.getBoutonJ1AEnfoncee(),   "aucun effet de bord (J1-A)");
        assertFalse(c.getJoyJ2HautEnfoncee(),   "aucun effet de bord (J2 haut)");
    }

    // ------------------------------------------------------------------ //
    // keyTyped : méthode vide, ne doit pas provoquer d'erreur              //
    // ------------------------------------------------------------------ //
    static void testKeyTyped() {
        System.out.println("--- keyTyped sans effet ---");
        ClavierBorneArcade c = new ClavierBorneArcade();
        KeyEvent e = new KeyEvent(DUMMY_COMP, KeyEvent.KEY_TYPED,
                System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'A');
        c.keyTyped(e); // ne doit pas lever d'exception
        assertTrue(true, "keyTyped n'a pas levé d'exception");
    }

    // ------------------------------------------------------------------ //
    // Point d'entrée                                                       //
    // ------------------------------------------------------------------ //
    public static void main(String[] args) {
        System.out.println("=== Tests ClavierBorneArcade ===");

        testEtatInitial();
        testJ1Joystick();
        testJ1Boutons();
        testJ2Joystick();
        testJ2Boutons();
        testTapeConsommee();
        testAppuiMaintenuSansTape();
        testDeuxTouchesSimultanees();
        testReinitialisation();
        testIsolationJoueurs();
        testToucheInconnue();
        testKeyTyped();

        System.out.println();
        System.out.println("Résultat : " + passed + " réussis, " + failed + " échoués sur " + (passed + failed) + " tests.");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
