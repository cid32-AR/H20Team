package game;

import java.util.Scanner;

import board.Board;
import model.Color;
import model.Position;
import utils.InputParser;

/**
 * Controls the console chess game and alternates player turns.
 */
public class Game {
    /** Chessboard used by the game. */
    private final Board board;

    /** Color of the player whose turn is currently active. */
    private Color currentTurn;

    /**
     * Creates a game with a new board and White moving first.
     */
    public Game() {
        board = new Board();
        currentTurn = Color.WHITE;
    }

    /**
     * Starts and maintains the console input loop.
     */
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
                System.out.println("Invalid move: "
                        + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Game ended.");
    }

    /**
     * Changes the active player after a successful move.
     */
    private void changeTurn() {
        if (currentTurn == Color.WHITE) {
            currentTurn = Color.BLACK;
        } else {
            currentTurn = Color.WHITE;
        }
    }
}