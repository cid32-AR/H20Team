package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import game.Game;
import game.GameState;

/**
 * A side panel that displays the move history, captured pieces, and provides an Undo button.
 */
public class HistoryPanel extends JPanel {

    private Game game;
    private BoardPanel boardPanel;
    
    /** Text area for the sequence of moves. */
    private JTextArea moveHistoryArea;
    /** Text area specifically for showing captured pieces. */
    private JTextArea capturedPiecesArea;
    
    private JButton undoButton;
    
    private Stack<GameState> stateHistory;
    private Stack<String> moveTextHistory;
    private Stack<String> capturedTextHistory;

    /**
     * Constructs the HistoryPanel.
     * 
     * @param game The shared Game instance.
     * @param boardPanel The BoardPanel instance to refresh upon undo.
     */
    public HistoryPanel(Game game, BoardPanel boardPanel) {
        this.game = game;
        this.boardPanel = boardPanel;
        this.stateHistory = new Stack<>();
        this.moveTextHistory = new Stack<>();
        this.capturedTextHistory = new Stack<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(260, 640)); 
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        moveHistoryArea = new JTextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane moveScroll = new JScrollPane(moveHistoryArea);
        moveScroll.setBorder(BorderFactory.createTitledBorder("Move History"));
        moveScroll.setPreferredSize(new Dimension(250, 350));

        capturedPiecesArea = new JTextArea();
        capturedPiecesArea.setEditable(false);
        capturedPiecesArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        JScrollPane capturedScroll = new JScrollPane(capturedPiecesArea);
        capturedScroll.setBorder(BorderFactory.createTitledBorder("Captured Pieces"));
        capturedScroll.setPreferredSize(new Dimension(250, 150));

        undoButton = new JButton("Undo Last Move");
        undoButton.setFont(new Font("Arial", Font.BOLD, 16));
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        undoButton.addActionListener(e -> undoMove());

        add(moveScroll);
        add(Box.createRigidArea(new Dimension(0, 5))); // Small gap
        add(capturedScroll);
        add(Box.createRigidArea(new Dimension(0, 10))); // Small gap
        add(undoButton);
    }

    /**
     * Saves the current game state and text history before a move is made.
     */
    public void saveCurrentState() {
        stateHistory.push(game.saveState());
        moveTextHistory.push(moveHistoryArea.getText());
        capturedTextHistory.push(capturedPiecesArea.getText());
    }

    /**
     * Discards the most recently saved state.
     * Used if a move is rejected by the Game logic, so the Undo stack doesn't get filled with duplicate states.
     */
    public void discardLastState() {
        if (!stateHistory.isEmpty()) {
            stateHistory.pop();
            moveTextHistory.pop();
            capturedTextHistory.pop();
        }
    }

    /**
     * Appends a move to the Move History area.
     */
    public void logMove(String moveText) {
        moveHistoryArea.append(moveText + "\n");
    }

    /**
     * Appends a captured piece to the Captured Pieces area.
     */
    public void logCapture(String captureText) {
        capturedPiecesArea.append(captureText + "\n");
    }

    /**
     * Reverts the game to the last saved state.
     */
    private void undoMove() {
        if (stateHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to undo!", "Undo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        GameState previousState = stateHistory.pop();
        game.loadState(previousState);
        moveHistoryArea.setText(moveTextHistory.pop());
        capturedPiecesArea.setText(capturedTextHistory.pop());
        boardPanel.refreshBoard();
    }
/** Clears move/capture logs and Undo states after New Game or Load Game. */
public void clearHistory() {
    stateHistory.clear();
    moveTextHistory.clear();
    capturedTextHistory.clear();
    moveHistoryArea.setText("");
    capturedPiecesArea.setText("");
}
}