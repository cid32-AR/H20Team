package pieces;

import java.util.List;

import model.Color;
import model.Position;

/**
 * Serves as the blueprint for all chess pieces.
 */
public abstract class Piece {

    /** The color of the piece. */
    protected Color color;

    /** The current position of the piece. */
    protected Position position;

    /**
     * Creates a piece.
     *
     * @param color the color of the piece
     * @param position the starting position of the piece
     */
    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the moves available from the current position.
     *
     * @return a list of possible moves
     */
    public abstract List<Position> possibleMoves();

    /**
     * Moves the piece to a new position.
     *
     * @param newPosition the destination position
     */
    public void move(Position newPosition) {
        this.position = newPosition;
    }
}