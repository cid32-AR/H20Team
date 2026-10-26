package utils;

import java.util.Locale;
import model.Position;

/**
 * Parses console chess moves such as E2 E4.
 */
public final class InputParser {

    /**
     * Prevents creating an InputParser object.
     */
    private InputParser() {
    }

    /**
     * Parses a chess move into two positions.
     */
    public static Position[] parseMove(String input) {

       
        if (input == null) {
            throw new IllegalArgumentException(
                "Enter a move such as E2 E4."
            );
        }

        String cleaned = input.trim()
                              .toUpperCase(Locale.ROOT);

        String[] parts = cleaned.split("\\s+");

        if (parts.length != 2 ||
            !isValidSquare(parts[0]) ||
            !isValidSquare(parts[1])) {

            throw new IllegalArgumentException(
                "Invalid format. Enter a move such as E2 E4."
            );
        }

        Position from = toPosition(parts[0]);
        Position to = toPosition(parts[1]);

        return new Position[] {from, to};
    }

    /**
     * Checks whether the square is between A1 and H8.
     */
    private static boolean isValidSquare(String square) {

        return square.length() == 2 &&
               square.charAt(0) >= 'A' &&
               square.charAt(0) <= 'H' &&
               square.charAt(1) >= '1' &&
               square.charAt(1) <= '8';
    }

    /**
     * Converts chess notation into board coordinates.
     */
    private static Position toPosition(String square) {

        int row = square.charAt(1) - '1';
        int column = square.charAt(0) - 'A';
        return new Position(row, column);
    }
}
