
package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Color;

/**
 * Reads and writes a simple, versioned text format for Phase 2 chess games.
 * Coordinates are model coordinates (A1 = 0,0; A8 = 7,0).
 * The file contains only values, never Java object serialization.
 */
public final class GameStateFile {
    private static final String HEADER = "H20CHESS 1";
    private static final int MAX_PIECES = 32;

    private GameStateFile() {
        // Utility class.
    }

    /**
     * Writes the supplied snapshot without modifying the running game.
     *
     * @param path destination file
     * @param state snapshot to write
     * @throws IOException if writing fails
     */
    public static void write(Path path, GameState state) throws IOException {
        if (path == null || state == null) {
            throw new IllegalArgumentException("A file and game state are required.");
        }
        validate(state);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
            writer.write("TURN " + state.getCurrentTurn().toString());
            writer.newLine();
            writer.write("WINNER " + (state.getWinner() == null ? "NONE" : state.getWinner().toString()));
            writer.newLine();
            writer.write("PIECES " + state.getPieces().size());
            writer.newLine();
            for (GameState.PieceState piece : state.getPieces()) {
                writer.write(piece.getType() + " " + piece.getColor().toString()
                        + " " + piece.getRow() + " " + piece.getColumn());
                writer.newLine();
            }
        }
    }

    /**
     * Parses and validates a complete saved game before the caller loads it.
     * A corrupt file causes an exception and cannot partially change a game.
     *
     * @param path file to open
     * @return validated snapshot
     * @throws IOException if reading fails or the file is malformed
     */
    public static GameState read(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("A file is required.");
        }
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            if (!HEADER.equals(reader.readLine())) {
                throw new IOException("Not a supported H20 chess save file.");
            }
            String turnText = field(reader.readLine(), "TURN");
            String winnerText = field(reader.readLine(), "WINNER");
            String countText = field(reader.readLine(), "PIECES");

            Color turn = parseColor(turnText);
            Color winner = "NONE".equals(winnerText) ? null : parseColor(winnerText);
            int count = parseInteger(countText, "piece count");
            if (count < 0 || count > MAX_PIECES) {
                throw new IOException("Invalid number of saved pieces.");
            }

            List<GameState.PieceState> pieces = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IOException("Save file ended before all pieces were read.");
                }
                String[] values = line.trim().split("\\s+");
                if (values.length != 4) {
                    throw new IOException("Invalid piece entry on line " + (i + 5) + ".");
                }
                int row = parseInteger(values[2], "row");
                int column = parseInteger(values[3], "column");
                pieces.add(new GameState.PieceState(values[0], parseColor(values[1]), row, column));
            }
            if (reader.readLine() != null) {
                throw new IOException("Unexpected extra data at the end of the save file.");
            }

            GameState state = new GameState(pieces, turn, winner);
            validate(state);
            return state;
        } catch (IllegalArgumentException exception) {
            throw new IOException("Invalid saved game: " + exception.getMessage(), exception);
        }
    }

    private static String field(String line, String expectedName) throws IOException {
        if (line == null || !line.startsWith(expectedName + " ")) {
            throw new IOException("Missing " + expectedName + " field.");
        }
        String value = line.substring(expectedName.length() + 1);
        if (value.isEmpty() || !value.equals(value.trim()) || value.contains(" ")) {
            throw new IOException("Invalid " + expectedName + " field.");
        }
        return value;
    }

    private static Color parseColor(String value) throws IOException {
        if ("WHITE".equals(value)) return Color.WHITE;
        if ("BLACK".equals(value)) return Color.BLACK;
        throw new IOException("Unknown piece/turn color: " + value);
    }

    private static int parseInteger(String value, String description) throws IOException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IOException("Invalid " + description + ": " + value, exception);
        }
    }

    private static void validate(GameState state) {
        if (state.getCurrentTurn() == null || state.getPieces().size() > MAX_PIECES) {
            throw new IllegalArgumentException("Invalid game status or piece count.");
        }
        Set<Integer> occupied = new HashSet<>();
        int whiteCount = 0;
        int blackCount = 0;
        int whiteKings = 0;
        int blackKings = 0;
        for (GameState.PieceState piece : state.getPieces()) {
            if (piece == null || piece.getColor() == null || !validType(piece.getType())) {
                throw new IllegalArgumentException("Invalid saved piece type or color.");
            }
            int row = piece.getRow();
            int column = piece.getColumn();
            if (row < 0 || row >= 8 || column < 0 || column >= 8) {
                throw new IllegalArgumentException("Piece outside the chessboard.");
            }
            if (!occupied.add(row * 8 + column)) {
                throw new IllegalArgumentException("Multiple pieces on one square.");
            }
            if (piece.getColor() == Color.WHITE) {
                whiteCount++;
                if ("King".equals(piece.getType())) whiteKings++;
            } else {
                blackCount++;
                if ("King".equals(piece.getType())) blackKings++;
            }
        }
        if (whiteCount > 16 || blackCount > 16 || whiteKings > 1 || blackKings > 1) {
            throw new IllegalArgumentException("Impossible saved piece counts.");
        }
    }

    private static boolean validType(String type) {
        return "Pawn".equals(type) || "Rook".equals(type)
                || "Knight".equals(type) || "Bishop".equals(type)
                || "Queen".equals(type) || "King".equals(type);
    }
}
