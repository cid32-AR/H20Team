package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Color;

/** Stores a snapshot of a chess game for saving and loading. */
public final class GameState {
    private final List<PieceState> pieces;
    private final Color currentTurn;
    private final Color winner;

    /**
     * Creates a snapshot from the given pieces and game status.
     *
     * @param pieces pieces currently on the board
     * @param currentTurn whose turn it is
     * @param winner winning color, or null if the game is active
     */
    public GameState(List<PieceState> pieces, Color currentTurn, Color winner) {
        if (pieces == null || currentTurn == null) {
            throw new IllegalArgumentException("The pieces and current turn are required.");
        }

        List<PieceState> copy = new ArrayList<>(pieces);
        if (copy.contains(null)) {
            throw new IllegalArgumentException("A saved piece is missing.");
        }

        this.pieces = Collections.unmodifiableList(copy);
        this.currentTurn = currentTurn;
        this.winner = winner;
    }

    /** @return the pieces currently on the board */
    public List<PieceState> getPieces() {
        return pieces;
    }

    /** @return the saved player's turn */
    public Color getCurrentTurn() {
        return currentTurn;
    }

    /** @return the winning color, or null if the game is active */
    public Color getWinner() {
        return winner;
    }

    /** Stores the type, color, and square of one piece. */
    public static final class PieceState {
        private final String type;
        private final Color color;
        private final int row;
        private final int column;

        /**
         * Creates a saved piece.
         *
         * @param type piece name, such as "Rook"
         * @param color piece color
         * @param row model row, from 0 to 7
         * @param column model column, from 0 to 7
         */
        public PieceState(String type, Color color, int row, int column) {
            if (type == null || color == null) {
                throw new IllegalArgumentException("A saved piece needs a type and color.");
            }

            this.type = type;
            this.color = color;
            this.row = row;
            this.column = column;
        }

        /** @return the piece type */
        public String getType() {
            return type;
        }

        /** @return the piece color */
        public Color getColor() {
            return color;
        }

        /** @return the model row */
        public int getRow() {
            return row;
        }

        /** @return the model column */
        public int getColumn() {
            return column;
        }
    }
}