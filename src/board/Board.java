package board;

import model.Color;
import model.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Represents the chessboard and controls the placement and movement of pieces.
 */
public class Board {
    /** Number of rows and columns on a chessboard. */
    private static final int BOARD_SIZE = 8;

    /** Two-dimensional array containing the chess pieces. */
    private final Piece[][] squares;

    /**
     * Creates a board and places all pieces in their standard positions.
     */
    public Board() {
        squares = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    /**
     * Places all chess pieces in their standard starting positions.
     */
    public final void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                squares[row][column] = null;
            }
        }

        placeBackRank(Color.WHITE, 0);
        placePawns(Color.WHITE, 1);

        placePawns(Color.BLACK, 6);
        placeBackRank(Color.BLACK, 7);
    }

    /**
     * Places one player's pawns.
     *
     * @param color color of the pawns
     * @param row row on which the pawns are placed
     */
    private void placePawns(Color color, int row) {
        for (int column = 0; column < BOARD_SIZE; column++) {
            Position position = new Position(row, column);
            squares[row][column] = new Pawn(color, position);
        }
    }

    /**
     * Places one player's major pieces.
     *
     * @param color color of the pieces
     * @param row row on which the pieces are placed
     */
    private void placeBackRank(Color color, int row) {
        squares[row][0] = new Rook(color, new Position(row, 0));
        squares[row][1] = new Knight(color, new Position(row, 1));
        squares[row][2] = new Bishop(color, new Position(row, 2));
        squares[row][3] = new Queen(color, new Position(row, 3));
        squares[row][4] = new King(color, new Position(row, 4));
        squares[row][5] = new Bishop(color, new Position(row, 5));
        squares[row][6] = new Knight(color, new Position(row, 6));
        squares[row][7] = new Rook(color, new Position(row, 7));
    }

    /**
     * Returns the piece at a particular position.
     *
     * @param position requested board position
     * @return the piece at the position, or null if the square is empty
     */
    public Piece getPiece(Position position) {
        validatePosition(position);
        return squares[position.getRow()][position.getColumn()];
    }
    /**
     * Replaces the board contents with a restored game.
     *
     * @param restoredSquares an 8x8 array of pieces and empty squares
     * @throws IllegalArgumentException if the array is not 8x8
     */
    public void replacePieces(Piece[][] restoredSquares) {
        if (restoredSquares == null || restoredSquares.length != BOARD_SIZE) {
            throw new IllegalArgumentException("The restored board must have 8 rows.");
        }

        for (Piece[] row : restoredSquares) {
            if (row == null || row.length != BOARD_SIZE) {
                throw new IllegalArgumentException("Each restored row must have 8 columns.");
            }
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            System.arraycopy(restoredSquares[row], 0, squares[row], 0, BOARD_SIZE);
        }
    }
    /**
     * Moves a piece using the Phase 1 console rules.
     *
     * @param from starting position
     * @param to destination position
     * @param currentTurn color whose turn it is
     */
    public void movePiece(Position from, Position to, Color currentTurn) {
        movePiece(from, to, currentTurn, true);
    }

    /**
     * Moves a piece for the Phase 2 GUI without checking its movement pattern.
     *
     * @param from starting position
     * @param to destination position
     * @param currentTurn color whose turn it is
     * @return the captured piece, or null if the destination was empty
     * @throws IllegalArgumentException if a position is outside the board,
     *         the starting square is empty, it is the wrong player's turn,
     *         or the destination contains a piece of the same color
     */
    public Piece movePieceForGui(Position from, Position to, Color currentTurn) {
        return movePiece(from, to, currentTurn, false);
    }

    /**
     * Checks and applies a move, then returns the piece that was captured.
     *
     * @param from starting position
     * @param to destination position
     * @param currentTurn color whose turn it is
     * @param enforcePieceRules whether to check the piece's movement pattern
     * @return the captured piece, or null if the destination was empty
     */
    private Piece movePiece(Position from, Position to, Color currentTurn,
                            boolean enforcePieceRules) {
        validatePosition(from);
        validatePosition(to);

        Piece movingPiece = getPiece(from);
        Piece destinationPiece = getPiece(to);

        if (movingPiece == null) {
            throw new IllegalArgumentException("The starting square is empty.");
        }

        if (movingPiece.getColor() != currentTurn) {
            throw new IllegalArgumentException(
                    "You must move a " + currentTurn + " piece.");
        }

        if (destinationPiece != null
                && destinationPiece.getColor() == movingPiece.getColor()) {
            throw new IllegalArgumentException(
                    "You cannot capture your own piece.");
        }

        if (enforcePieceRules && !movingPiece.isValidMove(to, this)) {
            throw new IllegalArgumentException(
                    "That move is not valid for this piece.");
        }

        squares[to.getRow()][to.getColumn()] = movingPiece;
        squares[from.getRow()][from.getColumn()] = null;
        movingPiece.setPosition(to);

        return destinationPiece;
    }

    /**
     * Determines whether all squares between two positions are empty.
     *
     * @param from starting position
     * @param to destination position
     * @return true when the path contains no pieces
     */
    public boolean isPathClear(Position from, Position to) {
        int rowStep = Integer.compare(to.getRow(), from.getRow());
        int columnStep = Integer.compare(to.getColumn(), from.getColumn());

        int row = from.getRow() + rowStep;
        int column = from.getColumn() + columnStep;

        while (row != to.getRow() || column != to.getColumn()) {
            if (squares[row][column] != null) {
                return false;
            }

            row += rowStep;
            column += columnStep;
        }

        return true;
    }

    /**
     * Prints the board with rank and file labels.
     */
    public void display() {
        System.out.println();
        System.out.println("    A  B  C  D  E  F  G  H");

        for (int row = BOARD_SIZE - 1; row >= 0; row--) {
            System.out.print((row + 1) + "  ");

            for (int column = 0; column < BOARD_SIZE; column++) {
                Piece piece = squares[row][column];

                if (piece == null) {
                    System.out.print("-- ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }

            System.out.println(" " + (row + 1));
        }

        System.out.println("    A  B  C  D  E  F  G  H");
        System.out.println();
    }

    /**
     * Confirms that a position is inside the chessboard.
     *
     * @param position position being checked
     */
    private void validatePosition(Position position) {
        if (position == null
                || position.getRow() < 0
                || position.getRow() >= BOARD_SIZE
                || position.getColumn() < 0
                || position.getColumn() >= BOARD_SIZE) {
            throw new IllegalArgumentException(
                    "The position must be between A1 and H8.");
        }
    }
}