import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implements the KeyListener interface to handle keyboard events.
 * 
 * This class is designed to work with the MG2D library and is intended to
 * be used in conjunction with the Board class to manage keyboard input
 * for the game.
 * 
 * Joysticks :
 * joyJ1
 * - joyJ1Up
 * - joyJ1Down
 * - joyJ1Left
 * - joyJ1Right
 * 
 * Buttons :
 * ButtonJ1
 * (bottom)
 * - ButtonJ1A -> dig
 * - ButtonJ1B -> flag
 * - ButtonJ1C
 * (top)
 * - ButtonJ1X -> back
 * - ButtonJ1Y -> difficulty
 * - ButtonJ1Z
 */
public class KeyboardArcade implements KeyListener {

    // Attibutes
    private boolean up; // enfoncé
    private boolean upTrigger; // apputé puis relâché
    private boolean down;
    private boolean downTrigger;
    private boolean left;
    private boolean leftTrigger;
    private boolean right;
    private boolean rightTrigger;

    private boolean buttonA;
    private boolean buttonATrigger;
    private boolean buttonB;
    private boolean buttonBTrigger;
    private boolean buttonC;
    private boolean buttonCTrigger;
    private boolean buttonX;
    private boolean buttonXTrigger;
    private boolean buttonY;
    private boolean buttonYTrigger;
    private boolean buttonZ;
    private boolean buttonZTrigger;

    public KeyboardArcade() {
        // Constructor
        this.up = this.upTrigger = this.down = this.downTrigger = false;
        this.left = this.leftTrigger = this.right = this.rightTrigger = false;

        this.buttonA = this.buttonATrigger = this.buttonB = this.buttonBTrigger = false;
        this.buttonC = this.buttonCTrigger = this.buttonX = this.buttonXTrigger = false;
        this.buttonY = this.buttonYTrigger = this.buttonZ = this.buttonZTrigger = false;
    }

     * @return Vérifie si l'entrée directionnelle vers le haut est enfoncée.
    public boolean isUp() {
        return this.up;
    }

     * @return Vérifie si l'entrée directionnelle vers le haut a été appuyée puis relâchée.
    public boolean isUpTrigger() {
        boolean temp = this.upTrigger;
        this.upTrigger = false;
        return temp;
    }

     * @return Vérifie si l'entrée directionnelle vers le bas est enfoncée.
    public boolean isDown() {
        return this.down;
    }

     * @return Vérifie si l'entrée directionnelle vers le bas a été appuyée puis relâchée.
    public boolean isDownTrigger() {
        boolean temp = this.downTrigger;
        this.downTrigger = false;
        return temp;
    }

     * @return Vérifie si l'entrée directionnelle vers la gauche est enfoncée.
    public boolean isLeft() {
        return this.left;
    }

     * @return Vérifie si l'entrée directionnelle vers la gauche a été appuyée puis relâchée.
    public boolean isLeftTrigger() {
        boolean temp = this.leftTrigger;
        this.leftTrigger = false;
        return temp;
    }

     * @return Vérifie si l'entrée directionnelle vers la droite est enfoncée.
    public boolean isRight() {
        return this.right;
    }

     * @return Vérifie si l'entrée directionnelle vers la droite a été appuyée puis relâchée.
    public boolean isRightTrigger() {
        boolean temp = this.rightTrigger;
        this.rightTrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton A est enfoncé.
    public boolean isButtonA() {
        return this.buttonA;
    }

     * @return Vérifie si le bouton A a été appuyé puis relâché.
    public boolean isButtonATrigger() {
        boolean temp = this.buttonATrigger;
        this.buttonATrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton B est enfoncé.
    public boolean isButtonB() {
        return this.buttonB;
    }

     * @return Vérifie si le bouton B a été appuyé puis relâché.
    public boolean isButtonBTrigger() {
        boolean temp = this.buttonBTrigger;
        this.buttonBTrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton C est enfoncé.
    public boolean isButtonC() {
        return this.buttonC;
    }

     * @return Vérifie si le bouton C a été appuyé puis relâché.
    public boolean isButtonCTrigger() {
        boolean temp = this.buttonCTrigger;
        this.buttonCTrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton X est enfoncé.
    public boolean isButtonX() {
        return this.buttonX;
    }

     * @return Vérifie si le bouton X a été appuyé puis relâché.
    public boolean isButtonXTrigger() {
        boolean temp = this.buttonXTrigger;
        this.buttonXTrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton Y est enfoncé.
    public boolean isButtonY() {
        return this.buttonY;
    }

     * @return Vérifie si le bouton Y a été appuyé puis relâché.
    public boolean isButtonYTrigger() {
        boolean temp = this.buttonYTrigger;
        this.buttonYTrigger = false;
        return temp;
    }

     * @return Vérifie si le bouton Z est enfoncé.
    public boolean isButtonZ() {
        return this.buttonZ;
    }

     * @return Vérifie si le bouton Z a été appuyé puis relâché.
    public boolean isButtonZTrigger() {
        boolean temp = this.buttonZTrigger;
        this.buttonZTrigger = false;
        return temp;
    }

    // Methods

     * Réinitialise toutes les variables de suivi des entrées clavier et du joystick.
    public void reinitialize() {
        this.up = this.upTrigger = this.down = this.downTrigger = false;
        this.left = this.leftTrigger = this.right = this.rightTrigger = false;

        this.buttonA = this.buttonATrigger = this.buttonB = this.buttonBTrigger = false;
        this.buttonC = this.buttonCTrigger = this.buttonX = this.buttonXTrigger = false;
        this.buttonY = this.buttonYTrigger = this.buttonZ = this.buttonZTrigger = false;
    }

    @Override
     * Gère l'événement de libération d'une touche clavier.
    public void keyReleased(KeyEvent e) {
        // System.out.println("Key released: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.up = false;
                this.upTrigger = true;
                break;
            case KeyEvent.VK_DOWN:
                this.down = false;
                this.downTrigger = true;
                break;
            case KeyEvent.VK_LEFT:
                this.left = false;
                this.leftTrigger = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = false;
                this.rightTrigger = true;
                break;

            case KeyEvent.VK_A:
                this.buttonA = false;
                this.buttonATrigger = true;
                break;
            case KeyEvent.VK_B:
                this.buttonB = false;
                this.buttonBTrigger = true;
                break;
            case KeyEvent.VK_C:
                this.buttonC = false;
                this.buttonCTrigger = true;
                break;

            case KeyEvent.VK_X:
                this.buttonX = false;
                this.buttonXTrigger = true;
                break;
            case KeyEvent.VK_Y:
                this.buttonY = false;
                this.buttonYTrigger = true;
                break;
            case KeyEvent.VK_Z:
                this.buttonZ = false;
                this.buttonZTrigger = true;
                break;

            default:
        }
    }

    @Override
     * Non utilisé dans cette implémentation.
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
     * Gère l'événement de pression d'une touche clavier.
    public void keyPressed(KeyEvent e) {
        // System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.up = true;
                break;
            case KeyEvent.VK_DOWN:
                this.down = true;
                break;
            case KeyEvent.VK_LEFT:
                this.left = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = true;
                break;

            case KeyEvent.VK_A:
                this.buttonA = true;
                break;
            case KeyEvent.VK_B:
                this.buttonB = true;
                break;
            case KeyEvent.VK_C:
                this.buttonC = true;
                break;

            case KeyEvent.VK_X:
                this.buttonX = true;
                break;
            case KeyEvent.VK_Y:
                this.buttonY = true;
                break;
            case KeyEvent.VK_Z:
                this.buttonZ = true;
                break;

            default:
        }
    }
}
