package model;

/**
 * Represents a square on the chessboard, such as E2.
 */
public class Position {

    /** The row of the square (0 through 7). */
    private int row;

    /** The column of the square (0 through 7). */
    private int column;

    /**
     * Creates a position.
     *
     * @param row the row of the square
     * @param column the column of the square
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Converts chess notation such as "E2" to a position.
     *
     * @param notation the square in chess notation
     * @return the matching position
     */
    public static Position fromAlgebraic(String notation) {
        char file = Character.toUpperCase(notation.charAt(0));
        char rank = notation.charAt(1);
        return new Position(rank - '1', file - 'A');
    }

    /**
     * Returns the row of this square.
     *
     * @return the row, from 0 through 7
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of this square.
     *
     * @return the column, from 0 through 7
     */
    public int getColumn() {
        return column;
    }
}