package pieces;

import board.Board;
import model.Color;
import model.Position;

/**
 * A chess rook, which moves any number of squares horizontally or vertically.
 * A rook cannot jump over another piece or capture a friendly piece.
 *
 * <p>Castling and king-safety checks are handled outside this Phase 1 class.
 */
public class Rook extends Piece {

    /**
     * Constructs a rook with its color and current position.
     *
     * @param color the rook's color
     * @param position the rook's starting position
     */
    public Rook(Color color, Position position) {
        super(color, position);
    }

    /**
     * Returns the console-board symbol for this rook.
     *
     * @return {@code "wR"} for white or {@code "bR"} for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wR" : "bR";
    }

    /**
     * Validates a horizontal or vertical rook move. Every intervening square
     * must be empty; the destination may be empty or contain an enemy piece.
     *
     * @param destination requested destination
     * @param board current board used to inspect occupied squares
     * @return true when the rook's basic movement rules allow the move
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
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        Piece target = board.getPiece(destination);
        if (target != null && target.getColor() == getColor()) {
            return false;
        }

        int stepRow = Integer.compare(toRow, fromRow);
        int stepCol = Integer.compare(toCol, fromCol);
        int row = fromRow + stepRow;
        int col = fromCol + stepCol;
        while (row != toRow || col != toCol) {
            if (board.getPiece(new Position(row, col)) != null) {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }
        return true;
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
