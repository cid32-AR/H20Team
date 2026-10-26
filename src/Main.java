import game.Game;

/**
 * Starts the console chess application.
 */
public class Main {
    /**
     * Creates and starts a chess game.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}