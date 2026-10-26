package game;

import java.util.List;

import model.Color;
import pieces.Piece;

/**
 * Represents a player in the game.
 */
public class Player {

    /** The color of the player. */
    private Color color;

    /** The pieces that have not been captured. */
    private List<Piece> availablePieces;

    /**
     * Creates a player.
     *
     * @param color the color of the player
     * @param availablePieces the pieces that have not been captured
     */
    public Player(Color color, List<Piece> availablePieces) {
        this.color = color;
        this.availablePieces = availablePieces;
    }

    /**
     * Allows the player to input a move and attempts to execute it on the board.
     */
    public void makeMove() {
        // input a move and attempt to execute it on the board
    }
}