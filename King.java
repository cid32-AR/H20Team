package pieces;

import board.Board;
import model.Color;
import model.Position;


public class King extends Piece {

    /**
     * Creates a King with a color and position.
     * @param color the King's color
     * @param position the starting position
     */
    public King(Color color, Position position) {
        /* ANDRES: Uses Piece(Color, Position). */
        super(color, position);
    }

    /**
     * Returns the King's display symbol.
     * @return wK for white or bK for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wK" : "bK";
    }

    /**
     * Checks whether the King can move to a destination.
     */
    @Override
    public boolean isValidMove(Position destination, Board board) {

        Position from = getPosition();

        if (from == null || destination == null || board == null) {
            return false;
        }

        int fromRow = from.getRow();
        int fromCol = from.getColumn();

        int toRow = destination.getRow();
        int toCol = destination.getColumn();

        if (!onBoard(fromRow, fromCol) ||
            !onBoard(toRow, toCol)) {
            return false;
        }

        int rowDistance = Math.abs(toRow - fromRow);
        int colDistance = Math.abs(toCol - fromCol);

        if (rowDistance == 0 && colDistance == 0) {
            return false;
        }

        if (rowDistance > 1 || colDistance > 1) {
            return false;
        }

        /*
         * JOHN:
         * Full check validation and castling
         * are not implemented in Phase 1.
         */
        Piece target = board.getPiece(destination);

        return target == null || target.getColor() != getColor();
    }

    /**
     * Checks whether a square is on the board.
     */
    private boolean onBoard(int row, int col) {
        return row >= 0 && row < 8 &&
               col >= 0 && col < 8;
    }
}
