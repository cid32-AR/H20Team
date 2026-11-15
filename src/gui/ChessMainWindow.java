package gui;

import javax.swing.*;
import java.awt.*;
import game.Game;

/**
 * The main window for the Chess game GUI.
 * Holds the BoardPanel and the HistoryPanel.
 * 
 */
public class ChessMainWindow extends JFrame {

    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;

    /**
     * Constructs the main game window.
     */
    public ChessMainWindow() {
        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create the SINGLE shared game logic object
        Game sharedGame = new Game();

        // Initialize panels, passing the shared game
        boardPanel = new BoardPanel(sharedGame);
        historyPanel = new HistoryPanel(sharedGame, boardPanel);

        // Give the BoardPanel a reference to the HistoryPanel so it can log moves
        boardPanel.setHistoryPanel(historyPanel);

        // Set layout and add panels
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        pack(); 
        setLocationRelativeTo(null); 
    }

    /**
     * Main method to launch the application.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessMainWindow window = new ChessMainWindow();
            window.setVisible(true);
        });
    }
}