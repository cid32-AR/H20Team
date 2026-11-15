package gui;

import game.Game;
import game.GameState;
import game.GameStateFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Ben's Phase 2 Game menu: start a new game, save, and load.
 * All actions operate on the existing shared Game instance.
 */
public final class GameMenuBar extends JMenuBar {
    private final Game game;
    private final BoardPanel boardPanel;
    private final HistoryPanel historyPanel;

    /**
     * @param game shared game also used by BoardPanel
     * @param boardPanel board to redraw after New Game or Load
     * @param historyPanel move/undo history to clear when a game is replaced
     */
    public GameMenuBar(Game game, BoardPanel boardPanel, HistoryPanel historyPanel) {
        if (game == null || boardPanel == null || historyPanel == null) {
            throw new IllegalArgumentException("Game, board, and history are required.");
        }
        this.game = game;
        this.boardPanel = boardPanel;
        this.historyPanel = historyPanel;

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveItem = new JMenuItem("Save Game...");
        JMenuItem loadItem = new JMenuItem("Load Game...");

        newGameItem.addActionListener(event -> newGame());
        saveItem.addActionListener(event -> saveGame());
        loadItem.addActionListener(event -> loadGame());

        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        add(gameMenu);
    }

    /** Resets the shared game and removes Undo entries from the previous game. */
    private void newGame() {
        game.reset();
        historyPanel.clearHistory();
        boardPanel.refreshBoard();
    }

    /** Opens a save dialog and writes the current snapshot to disk. */
    private void saveGame() {
        JFileChooser chooser = createChooser();
        chooser.setDialogTitle("Save Chess Game");
        if (chooser.showSaveDialog(boardPanel) != JFileChooser.APPROVE_OPTION) return;

        Path path = withExtension(chooser.getSelectedFile().toPath());
        if (Files.exists(path)) {
            int choice = JOptionPane.showConfirmDialog(boardPanel,
                    "Replace the existing file?", "Confirm Overwrite",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) return;
        }

        try {
            GameStateFile.write(path, game.saveState());
            JOptionPane.showMessageDialog(boardPanel, "Game saved successfully.",
                    "Save Game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | IllegalArgumentException exception) {
            showError("Could not save game", exception);
        }
    }

    /** Loads only after the entire file has been read and validated. */
    private void loadGame() {
        JFileChooser chooser = createChooser();
        chooser.setDialogTitle("Load Chess Game");
        if (chooser.showOpenDialog(boardPanel) != JFileChooser.APPROVE_OPTION) return;

        try {
            GameState state = GameStateFile.read(chooser.getSelectedFile().toPath());
            game.loadState(state);
            historyPanel.clearHistory();
            boardPanel.refreshBoard();
            JOptionPane.showMessageDialog(boardPanel, "Game loaded successfully.",
                    "Load Game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | IllegalArgumentException exception) {
            showError("Could not load game", exception);
        }
    }

    private JFileChooser createChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Chess Save Files (*.chess)", "chess"));
        return chooser;
    }

    private Path withExtension(Path path) {
        String name = path.getFileName().toString();
        if (!name.toLowerCase(java.util.Locale.ROOT).endsWith(".chess")) {
            return path.resolveSibling(name + ".chess");
        }
        return path;
    }

    private void showError(String heading, Exception exception) {
        JOptionPane.showMessageDialog(boardPanel,
                heading + ":\n" + exception.getMessage(),
                "Chess Game Error", JOptionPane.ERROR_MESSAGE);
    }
}
