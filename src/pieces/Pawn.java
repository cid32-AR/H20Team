package pieces;

import java.util.ArrayList;
import java.util.List;
import board.Board;
import model.Color;
import model.Position;

/** A pawn, which advances forward and captures diagonally. */
public class Pawn extends Piece {
    /** Creates a pawn at its starting square. */
    public Pawn(Color color, Position position) {
        super(color, position);
    }

    /** Returns the two-character display symbol. */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wp" : "bp";
    }

    /** Returns forward and diagonal candidate squares, without checking occupancy. */
    @Override
    public List<Position> possibleMoves() {
        List<Position> moves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getColumn();
        int step = getColor() == Color.WHITE ? 1 : -1;
        int next = row + step;
        if (inBounds(next, col)) {
            moves.add(new Position(next, col));
            if (inBounds(next, col - 1)) moves.add(new Position(next, col - 1));
            if (inBounds(next, col + 1)) moves.add(new Position(next, col + 1));
        }
        int startRow = getColor() == Color.WHITE ? 1 : 6;
        if (row == startRow && inBounds(row + 2 * step, col)) {
            moves.add(new Position(row + 2 * step, col));
        }
        return moves;
    }

    /** Checks pawn advances and captures against the actual board. */
    @Override
    public boolean isValidMove(Position destination, Board board) {
        if (destination == null || board == null || !inBounds(destination.getRow(), destination.getColumn())) return false;
        Position from = getPosition();
        int step = getColor() == Color.WHITE ? 1 : -1;
        int startRow = getColor() == Color.WHITE ? 1 : 6;
        int dr = destination.getRow() - from.getRow();
        int dc = destination.getColumn() - from.getColumn();
        Piece target = board.getPiece(destination);

        if (dc == 0) {
            if (target != null) return false;
            if (dr == step) return true;
            if (dr == 2 * step && from.getRow() == startRow) {
                Position intermediate = new Position(from.getRow() + step, from.getColumn());
                return board.getPiece(intermediate) == null;
            }
            return false;
        }
        return Math.abs(dc) == 1 && dr == step
                && target != null && target.getColor() != getColor();
    }

    private static boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
}
