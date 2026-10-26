package pieces;

import java.util.ArrayList;
import java.util.List;
import board.Board;
import model.Color;
import model.Position;

/**
 * Represents a Bishop that moves diagonally.
 */
public class Bishop extends Piece {

    
     // Creates a Bishop with a color and position.
    public Bishop(Color color, Position position) {
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
    
   
    /**
     * Returns the squares a Bishop can move to,
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
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
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
     * Checks whether a Bishop can move to a destination.
     *
     * @param destination the target position
     * @param board the current chessboard
     * @return true if the move is valid
     */
    @Override
    public boolean isValidMove(Position destination, Board board) {

        Position from = getPosition();

        if (from == null || destination == null || board == null) {
            return false;
        }

        int rowDistance = Math.abs(
            destination.getRow() - from.getRow()
        );

        int columnDistance = Math.abs(
            destination.getColumn() - from.getColumn()
        );

        if (!onBoard(from.getRow(), from.getColumn()) ||
            !onBoard(destination.getRow(), destination.getColumn())) {
            return false;
        }

        if (rowDistance == 0 || rowDistance != columnDistance) {
            return false;
        }

        if (!board.isPathClear(from, destination)) {
            return false;
        }

        Piece target = board.getPiece(destination);

        return target == null || target.getColor() != getColor();
    }

    /**
     * Checks whether a square is on the board.
     */
private boolean onBoard(int row, int column) {
        return row >= 0 && row < 8 &&
               column >= 0 && column < 8;
    }
}
