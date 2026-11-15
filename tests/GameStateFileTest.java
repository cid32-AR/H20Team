import game.GameState;
import game.GameStateFile;
import model.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class GameStateFileTest {
    private static int passed;
    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
        passed++;
    }
    private static void reject(String contents, String label) throws Exception {
        Path file = Files.createTempFile("bad-chess", ".chess");
        try {
            Files.writeString(file, contents);
            try {
                GameStateFile.read(file);
                throw new AssertionError("Expected rejection: " + label);
            } catch (IOException expected) {
                passed++;
            }
        } finally { Files.deleteIfExists(file); }
    }
    public static void main(String[] args) throws Exception {
        GameState original = new GameState(Arrays.asList(
            new GameState.PieceState("King", Color.WHITE, 0, 4),
            new GameState.PieceState("Rook", Color.BLACK, 5, 1)), Color.BLACK, Color.BLACK);
        Path path = Files.createTempFile("chess-test", ".chess");
        try {
            GameStateFile.write(path, original);
            GameState restored = GameStateFile.read(path);
            check(restored.getCurrentTurn() == Color.BLACK, "turn restored");
            check(restored.getWinner() == Color.BLACK, "winner restored");
            check(restored.getPieces().size() == 2, "count restored");
            check(restored.getPieces().get(1).getType().equals("Rook"), "piece type restored");
            check(restored.getPieces().get(1).getRow() == 5, "model row restored");
            check(restored.getPieces().get(1).getColumn() == 1, "model column restored");
        } finally { Files.deleteIfExists(path); }
        reject("bad header\n", "header");
        reject("H20CHESS 1\nTURN GREEN\nWINNER NONE\nPIECES 0\n", "invalid turn");
        reject("H20CHESS 1\nTURN WHITE\nWINNER NONE\nPIECES 1\nKing WHITE 8 4\n", "out-of-bounds");
        reject("H20CHESS 1\nTURN WHITE\nWINNER NONE\nPIECES 2\nKing WHITE 0 4\nRook BLACK 0 4\n", "duplicate square");
        reject("H20CHESS 1\nTURN WHITE\nWINNER NONE\nPIECES 1\nDragon WHITE 0 4\n", "unknown piece");
        reject("H20CHESS 1\nTURN WHITE\nWINNER NONE\nPIECES 2\nKing WHITE 0 4\n", "truncated");
        reject("H20CHESS 1\nTURN WHITE\nWINNER NONE\nPIECES 0\nextra\n", "trailing data");
        System.out.println("PASS: " + passed + " GameStateFile checks");
    }
}
