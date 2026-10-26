package pieces;

import java.util.ArrayList;
import java.util.List;
import board.Board;
import model.Color;
import model.Position;

/** A rook, which moves along ranks and files without jumping pieces. */
public class Rook extends Piece {
    /** Creates a rook at its starting square. */
    public Rook(Color color, Position position) {
        super(color, position);
    }

    /** Returns the two-character display symbol. */
    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wR" : "bR";
    }

    /** Returns same-row and same-column candidate squares, without checking occupancy. */
    @Override
    public List<Position> possibleMoves() {
        List<Position> moves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getColumn();
        for (int i = 0; i < 8; i++) {
            if (i != row) moves.add(new Position(i, col));
            if (i != col) moves.add(new Position(row, i));
        }
        return moves;
    }

    /** Checks straight-line movement, a clear path, and friendly occupancy. */
    @Override
    public boolean isValidMove(Position destination, Board board) {
        if (destination == null || board == null || !inBounds(destination.getRow(), destination.getColumn())) return false;
        Position from = getPosition();
        int dr = destination.getRow() - from.getRow();
        int dc = destination.getColumn() - from.getColumn();
        if ((dr == 0 && dc == 0) || (dr != 0 && dc != 0)) return false;
        Piece target = board.getPiece(destination);
        return (target == null || target.getColor() != getColor())
                && board.isPathClear(from, destination);
    }

    private static boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
}
