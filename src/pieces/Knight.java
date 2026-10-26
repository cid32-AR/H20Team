package pieces;

import board.Board;
import model.Color;
import model.Position;

/**
 * A chess knight, which moves two squares along one axis and one along the
 * other. Knights can jump over other pieces, but cannot land on friendly pieces.
 *
 * <p>King-safety checks are outside this Phase 1 class.
 */
public class Knight extends Piece {

    /**
     * Constructs a knight with its color and current position.
     *
     * @param color the knight's color
     * @param position the knight's starting position
     */
    public Knight(Color color, Position position) {
        super(color, position);
    }

    /**
     * Returns the console-board symbol for this knight.
     *
     * @return {@code "wN"} for white or {@code "bN"} for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wN" : "bN";
    }

    /**
     * Validates an L-shaped knight move. Occupied intermediate squares do not
     * block the move, but the destination cannot contain a friendly piece.
     *
     * @param destination requested destination
     * @param board current board used to inspect the destination
     * @return true when the knight's basic movement rules allow the move
     */
    @Override
    public boolean isValidMove(Position destination, Board board) {
        if (destination == null || board == null || getPosition() == null) {
            return false;
        }
        int fromRow = getPosition().getRow();
        int fromCol = getPosition().getColumn();
        int toRow = destination.getRow();
        int toCol = destination.getColumn();
        if (!inBounds(fromRow, fromCol) || !inBounds(toRow, toCol)) {
            return false;
        }
        int rowChange = Math.abs(toRow - fromRow);
        int colChange = Math.abs(toCol - fromCol);
        if (!((rowChange == 2 && colChange == 1)
                || (rowChange == 1 && colChange == 2))) {
            return false;
        }
        Piece target = board.getPiece(destination);
        return target == null || target.getColor() != getColor();
    }

    /**
     * Checks the coordinates against an eight-by-eight board.
     *
     * @param row zero-based row
     * @param column zero-based column
     * @return true when both coordinates are on the board
     */
    private static boolean inBounds(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }
}
