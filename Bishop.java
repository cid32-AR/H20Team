package pieces;

import board.Board;
import model.Color;
import model.Position;

/**
 * Represents a Bishop chess piece.
 * Bishops move diagonally across the board.
 */
public class Bishop extends Piece {

    /**
     * Creates a Bishop.
     *
     * @param color the Bishop's color
     * @param position the Bishop's position
     */
    public Bishop(Color color, Position position) {

        /* ANDRES:
         * Uses the agreed Piece(Color, Position)
         * constructor.
         */
        super(color, position);
    }

    /**
     * Returns the Bishop's symbol.
     *
     * @return wB for white or bB for black
     */
    @Override
    public String getSymbol() {

        /* ANDRES:
         * Assumes Color.WHITE and Color.BLACK
         * are defined in the Color enum.
         */

        if (getColor() == Color.WHITE) {
            return "wB";
        }

        return "bB";
    }

    /**
     * Checks whether the Bishop can move
     * to the specified destination.
     *
     * @param destination the target position
     * @param board the current chessboard
     * @return true if the move is valid
     */
    @Override
    public boolean isValidMove(Position destination, Board board) {

        /* JOHN:
         * Board must provide:
         * Piece getPiece(Position position)
         */

        if (destination == null || board == null ||
            getPosition() == null) {
            return false;
        }

        int fromRow = getPosition().getRow();
        int fromCol = getPosition().getColumn();

        int toRow = destination.getRow();
        int toCol = destination.getColumn();

        if (!insideBoard(fromRow, fromCol) ||
            !insideBoard(toRow, toCol)) {
            return false;
        }

        int rowChange = toRow - fromRow;
        int colChange = toCol - fromCol;

        // Bishops must move diagonally.
        if (rowChange == 0 ||
            Math.abs(rowChange) != Math.abs(colChange)) {
            return false;
        }

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int row = fromRow + rowStep;
        int col = fromCol + colStep;

        // Check for pieces blocking the diagonal.
        while (row != toRow) {

            if (board.getPiece(new Position(row, col)) != null) {
                return false;
            }

            row += rowStep;
            col += colStep;
        }

        // Cannot capture a piece of the same color.
        Piece target = board.getPiece(destination);

        return target == null ||
               target.getColor() != getColor();
    }

    //Checks whether a position is within the board.
    private boolean insideBoard(int row, int col) {
        return row >= 0 && row < 8 &&
               col >= 0 && col < 8;
    }
}
