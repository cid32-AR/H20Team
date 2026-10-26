package pieces;

import java.util.ArrayList;
import java.util.List;
import board.Board;
import model.Color;
import model.Position;

/** A knight, which moves in an L shape and can jump over pieces. */
public class Knight extends Piece {
    /** Creates a knight at its starting square. */
    public Knight(Color color, Position position) {
        super(color, position);
    }

    /** Returns the two-character display symbol. */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wN" : "bN";
    }

    /** Returns in-bounds L-shaped candidate squares, without checking occupancy. */
    @Override
    public List<Position> possibleMoves() {
        List<Position> moves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getColumn();
        int[][] offsets = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
        for (int[] offset : offsets) {
            int r = row + offset[0];
            int c = col + offset[1];
            if (inBounds(r, c)) moves.add(new Position(r, c));
        }
        return moves;
    }

    /** Checks L-shaped movement, bounds, and friendly occupancy. */
    @Override
    public boolean isValidMove(Position destination, Board board) {
        if (destination == null || board == null || !inBounds(destination.getRow(), destination.getColumn())) return false;
        int dr = Math.abs(destination.getRow() - getPosition().getRow());
        int dc = Math.abs(destination.getColumn() - getPosition().getColumn());
        if (!((dr == 2 && dc == 1) || (dr == 1 && dc == 2))) return false;
        Piece target = board.getPiece(destination);
        return target == null || target.getColor() != getColor();
    }

    private static boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
}
