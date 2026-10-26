package pieces;

import board.Board;
import model.Color;
import model.Position;

/**
 * A chess pawn. Pawns move forward one square, or two squares from their
 * starting rank if both squares are empty. They capture one square diagonally.
 *
 * <p>This Phase 1 implementation does not handle en passant, promotion, or
 * moves that would leave the moving player's king in check.
 */
public class Pawn extends Piece {

    /**
     * Constructs a pawn with its color and current board position.
     *
     * @param color the pawn's color
     * @param position the pawn's starting position
     */
    public Pawn(Color color, Position position) {
        super(color, position);
    }

    /**
     * Returns the two-character symbol required by the console board.
     *
     * @return {@code "wp"} for white or {@code "bp"} for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wp" : "bp";
    }

    /**
     * Checks whether this pawn can move to the requested square, taking board
     * occupancy and the pawn's starting rank into account.
     *
     * @param destination requested destination
     * @param board current board, used to inspect occupied squares
     * @return true if the pawn's basic movement rules allow the move
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

        int direction = getColor() == Color.WHITE ? -1 : 1;
        int startingRow = getColor() == Color.WHITE ? 6 : 1;
        int rowChange = toRow - fromRow;
        int colChange = toCol - fromCol;
        Piece target = board.getPiece(destination);

        // A forward move is legal only when the destination is empty.
        if (colChange == 0) {
            if (target != null) {
                return false;
            }
            if (rowChange == direction) {
                return true;
            }
            if (fromRow == startingRow && rowChange == 2 * direction) {
                Position intermediate = new Position(fromRow + direction, fromCol);
                return board.getPiece(intermediate) == null;
            }
            return false;
        }

        // A diagonal move must capture an opposing piece.
        return Math.abs(colChange) == 1 && rowChange == direction
                && target != null && target.getColor() != getColor();
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
