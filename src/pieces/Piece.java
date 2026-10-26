package pieces;

import java.util.List;

import board.Board;
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
     * Checks whether this piece may move to a destination on the board.
     *
     * @param destination the destination position
     * @param board the board containing all pieces
     * @return true when the move is legal for this piece
     */
    public abstract boolean isValidMove(Position destination, Board board);

    /**
     * Returns the display symbol for this piece.
     *
     * @return the two-character symbol, such as wB or bP
     */
    public abstract String getSymbol();

    /**
     * Moves the piece to a new position.
     *
     * @param newPosition the destination position
     */
    public void move(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Returns the color of this piece.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the current position of this piece.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the position of this piece.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}