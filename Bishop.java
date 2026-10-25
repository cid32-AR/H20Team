package pieces;

import board.Board;
import model.Color;
import model.Position;


public class Bishop extends Piece {

    
     // Creates a Bishop with a color and position.
    public Bishop(Color color, Position position) {
        /* ANDRES: Uses Piece(Color, Position). */
        super(color, position);
    }

    /**
     * Returns the Bishop's display symbol.
     * @return wB for white or bB for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wB" : "bB";
    }

   
     // Checks whether the Bishop can move to a destination.
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

        if (rowDistance == 0 || rowDistance != colDistance) {
            return false;
        }

        /*
         * Uses Board.isPathClear(Position, Position)
         * to prevent moving through other pieces.
         */
        if (!board.isPathClear(from, destination)) {
            return false;
        }

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
