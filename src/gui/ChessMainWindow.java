package gui;

import javax.swing.*;

/**
 * The main window for the Chess game GUI.
 * This class holds the board panel and will later connect to Ben's menu and Alex's settings.
 */
public class ChessMainWindow extends JFrame {

    private BoardPanel boardPanel;

    /**
     * Constructs the main game window, initializes the board panel, and sets up window properties.
     */
    public ChessMainWindow() {
        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize the board panel (we will create this next)
        boardPanel = new BoardPanel();
        add(boardPanel);

        pack();
        setLocationRelativeTo(null); // Center window on screen
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessMainWindow window = new ChessMainWindow();
            window.setVisible(true);
        });
    }
}