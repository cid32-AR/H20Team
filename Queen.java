package pieces;

import board.Board;
import model.Color;
import model.Position;


//Represents a Queen chess piece.
public class Queen extends Piece {

    /**
     * Creates a Queen.
     *
     * @param color the Queen's color
     * @param position the Queen's position
     */
    public Queen(Color color, Position position) {

        /* ANDRES:
         * Uses the agreed Piece(Color, Position)
         * constructor.
         */
        super(color, position);
    }

    /**
     * Returns the Queen's symbol.
     *
     * @return wQ for white or bQ for black
     */
    @Override
    public String getSymbol() {

        /* ANDRES:
         * Assumes Color.WHITE and Color.BLACK
         * are defined in the Color enum.
         */

        if (getColor() == Color.WHITE) {
            return "wQ";
        }

        return "bQ";
    }

    /**
     * Checks whether the Queen can move
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

        // Queens move in straight or diagonal lines.
        boolean straight = rowChange == 0 || colChange == 0;

        boolean diagonal =
            Math.abs(rowChange) == Math.abs(colChange);

        // Cannot remain on the same square.
        if (rowChange == 0 && colChange == 0) {
            return false;
        }

        if (!straight && !diagonal) {
            return false;
        }

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int row = fromRow + rowStep;
        int col = fromCol + colStep;

        // Check whether another piece blocks the path.
        while (row != toRow || col != toCol) {

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

    /**
     * Checks whether a position is within the board.
     *
     * @param row the row
     * @param col the column
     * @return true if the position is within the board
     */
    private boolean insideBoard(int row, int col) {
        return row >= 0 && row < 8 &&
               col >= 0 && col < 8;
    }
}
