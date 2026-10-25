package pieces;

import java.util.ArrayList;
import java.util.List;
import board.Board;
import model.Color;
import model.Position;


public class Queen extends Piece {


     // Creates a Queen with a color and position.
    public Queen(Color color, Position position) {
        /* ANDRES: Uses Piece(Color, Position). */
        super(color, position);
    }

    /**
     * Returns the Queen's display symbol.
     * @return wQ for white or bQ for black
     */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wQ" : "bQ";
    }

     
    /**
     * Returns the squares a Queen can move to,
     * without checking for other pieces.
     *
     * @return list of possible positions
     */
    @Override
    public List<Position> possibleMoves() {
        List<Position> moves = new ArrayList<>();

        int row = getPosition().getRow();
        int column = getPosition().getColumn();

        int[][] directions = {
            {1, 0}, {-1, 0},
            {0, 1}, {0, -1},
            {1, 1}, {1, -1},
            {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) {
            for (int step = 1; step < 8; step++) {
                int nextRow = row + direction[0] * step;
                int nextColumn = column + direction[1] * step;

                if (!onBoard(nextRow, nextColumn)) {
                    break;
                }

                moves.add(new Position(nextRow, nextColumn));
            }
        }

        return moves;
    }

     
    /**
     * Checks whether the Queen can move to a destination.
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

        boolean straight =
            rowDistance == 0 || colDistance == 0;

        boolean diagonal = rowDistance == colDistance;

        if (!straight && !diagonal) {
            return false;
        }

        /*
         * Uses Board.isPathClear(Position, Position)
         * to check horizontal, vertical,
         * and diagonal paths.
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
