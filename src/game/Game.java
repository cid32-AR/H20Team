package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.Board;
import model.Color;
import model.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import utils.InputParser;

/** Manages chess game state for the console and the Phase 2 GUI. */
public class Game {
    private static final int BOARD_SIZE = 8;

    private final Board board;
    private Color currentTurn;
    private Color winner;

    /** Creates a new game with White moving first. */
    public Game() {
        board = new Board();
        currentTurn = Color.WHITE;
    }

    /**
     * Returns the piece on a square.
     *
     * @param position square to look up
     * @return the piece, or null if the square is empty
     */
    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    /** @return the color whose turn it is */
    public Color getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Attempts a move using the Phase 2 GUI rules.
     *
     * @param from starting square
     * @param to destination square
     * @return the outcome of the move
     */
    public MoveResult move(Position from, Position to) {
        if (winner != null) {
            return MoveResult.GAME_OVER;
        }

        Piece capturedPiece;
        try {
            capturedPiece = board.movePieceForGui(from, to, currentTurn);
        } catch (IllegalArgumentException exception) {
            return MoveResult.INVALID_MOVE;
        }

        if (capturedPiece instanceof King) {
            winner = currentTurn;
            return MoveResult.KING_CAPTURED;
        }

        changeTurn();
        return capturedPiece == null ? MoveResult.MOVED : MoveResult.CAPTURED;
    }

    /** @return the winner's color, or null while the game is active */
    public Color getWinner() {
        return winner;
    }

    /** Restores the starting board and gives White the first turn. */
    public void reset() {
        board.initializeBoard();
        currentTurn = Color.WHITE;
        winner = null;
    }

    /** @return a snapshot of the board, current turn, and winner */
    public GameState saveState() {
        List<GameState.PieceState> pieces = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Piece piece = board.getPiece(new Position(row, column));
                if (piece != null) {
                    pieces.add(new GameState.PieceState(
                            piece.getClass().getSimpleName(),
                            piece.getColor(), row, column));
                }
            }
        }

        return new GameState(pieces, currentTurn, winner);
    }

    /**
     * Restores a snapshot into this game.
     *
     * @param state snapshot to restore
     * @throws IllegalArgumentException if the snapshot contains invalid data
     */
    public void loadState(GameState state) {
        if (state == null) {
            throw new IllegalArgumentException("Game state is missing.");
        }

        // Build the new board first so a bad save leaves the current game intact.
        Piece[][] restoredSquares = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (GameState.PieceState savedPiece : state.getPieces()) {
            int row = savedPiece.getRow();
            int column = savedPiece.getColumn();

            if (row < 0 || row >= BOARD_SIZE
                    || column < 0 || column >= BOARD_SIZE) {
                throw new IllegalArgumentException("Saved square is outside the board.");
            }

            if (restoredSquares[row][column] != null) {
                throw new IllegalArgumentException("Two pieces occupy the same saved square.");
            }

            restoredSquares[row][column] = restorePiece(savedPiece);
        }

        board.replacePieces(restoredSquares);
        currentTurn = state.getCurrentTurn();
        winner = state.getWinner();
    }

    /** Recreates a piece from its saved type, color, and position. */
    private Piece restorePiece(GameState.PieceState savedPiece) {
        Color color = savedPiece.getColor();
        Position position = new Position(savedPiece.getRow(), savedPiece.getColumn());

        switch (savedPiece.getType()) {
            case "Pawn":
                return new Pawn(color, position);
            case "Rook":
                return new Rook(color, position);
            case "Knight":
                return new Knight(color, position);
            case "Bishop":
                return new Bishop(color, position);
            case "Queen":
                return new Queen(color, position);
            case "King":
                return new King(color, position);
            default:
                throw new IllegalArgumentException(
                        "Unknown piece type: " + savedPiece.getType());
        }
    }

    /** Starts and maintains the Phase 1 console input loop. */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Console Chess");
        System.out.println("Enter moves such as E2 E4.");
        System.out.println("Enter quit to stop the game.");

        while (running) {
            board.display();
            System.out.print(currentTurn + " move: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                running = false;
                continue;
            }

            try {
                Position[] move = InputParser.parseMove(input);
                board.movePiece(move[0], move[1], currentTurn);
                changeTurn();
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid move: " + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Game ended.");
    }

    /** Switches to the other player's turn. */
    private void changeTurn() {
        if (currentTurn == Color.WHITE) {
            currentTurn = Color.BLACK;
        } else {
            currentTurn = Color.WHITE;
        }
    }
}