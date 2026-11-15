package gui;

import javax.swing.*;
import java.awt.*;
import game.Game;

/**
 * The main window for the Chess game GUI.
 */
public class ChessMainWindow extends JFrame {

    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;
    private Game sharedGame; // Made an instance variable can give it to Ben
    private final DisplaySettings displaySettings =
    new DisplaySettings();
    public ChessMainWindow() {
        setTitle("Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        sharedGame = new Game();

        boardPanel = new BoardPanel(sharedGame);
        boardPanel.setDisplaySettings(displaySettings);
        historyPanel = new HistoryPanel(sharedGame, boardPanel);
        boardPanel.setHistoryPanel(historyPanel);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        // TODO: Ben will call setJMenuBar(new MenuBar(getGame(), getBoardPanel())); here
        
        JPanel toolbar = new JPanel(
        new FlowLayout(FlowLayout.LEFT)
        );
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
        SettingsDialog dialog = new SettingsDialog(
        this,
        displaySettings,
        () -> boardPanel.setDisplaySettings(displaySettings)
        );

        dialog.setVisible(true);
        });

        toolbar.add(settingsButton);
        add(toolbar, BorderLayout.NORTH);

        pack(); 
        setLocationRelativeTo(null); 
        }

    /**
     * METHOD FOR BEN: Gets the shared Game object so his Menu can call reset(), saveState(), etc.
     */
    public Game getGame() {
        return sharedGame;
    }

    /**
     * METHOD FOR BEN & ALEX: Gets the BoardPanel so they can call refreshBoard() after loading/settings.
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessMainWindow window = new ChessMainWindow();
            window.setVisible(true);
        });
    }
}
