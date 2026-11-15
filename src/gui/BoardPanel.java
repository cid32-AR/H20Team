package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import game.Game;
import game.MoveResult;
import model.Position;
import pieces.Piece;
import model.Color;

/**
 * The panel that visually represents the 8x8 chess board.
 * Handles drawing, mouse interactions, and communicates with HistoryPanel.
 * 
 */
public class BoardPanel extends JPanel {

    private static final int SQUARE_SIZE = 80; 
    private static final int BOARD_SIZE = SQUARE_SIZE * 8;
    
    private Game game;
    private HistoryPanel historyPanel; // Reference to the history panel
    private Position selectedSource; 
    
    private final java.awt.Color lightSquareColor = new java.awt.Color(240, 217, 181);
    private final java.awt.Color darkSquareColor = new java.awt.Color(181, 136, 99);

    /**
     * Constructs the BoardPanel.
     * 
     * @param game The shared Game logic object from ChessMainWindow.
     */
    public BoardPanel(Game game) {
        this.game = game; 
        this.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        setupMouseListeners();
    }

    /**
     * Sets the HistoryPanel reference so moves can be logged.
     * 
     * @param historyPanel The HistoryPanel instance.
     */
    public void setHistoryPanel(HistoryPanel historyPanel) {
        this.historyPanel = historyPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // 1. Draw the 8x8 squares
        for (int visualRow = 0; visualRow < 8; visualRow++) {
            for (int visualCol = 0; visualCol < 8; visualCol++) {
                if ((visualRow + visualCol) % 2 == 0) {
                    g2d.setColor(lightSquareColor);
                } else {
                    g2d.setColor(darkSquareColor);
                }
                g2d.fillRect(visualCol * SQUARE_SIZE, visualRow * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        // 2. Draw the pieces
        for (int visualRow = 0; visualRow < 8; visualRow++) {
            for (int visualCol = 0; visualCol < 8; visualCol++) {
                int modelRow = 7 - visualRow; 
                int modelCol = visualCol;
                Position pos = new Position(modelRow, modelCol);
                
                Piece piece = game.getPiece(pos);
                if (piece != null) {
                    drawPiece(g2d, piece, visualCol * SQUARE_SIZE, visualRow * SQUARE_SIZE);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g2d, Piece piece, int x, int y) {
        String symbol = "";
        g2d.setFont(new Font("Serif", Font.BOLD, 60));
        
        if (piece.getColor() == Color.WHITE) {
            g2d.setColor(java.awt.Color.WHITE);
        } else {
            g2d.setColor(java.awt.Color.BLACK);
        }

        String type = piece.getClass().getSimpleName(); 
        switch (type) {
            case "King": symbol = (piece.getColor() == Color.WHITE) ? "\u2654" : "\u265A"; break;
            case "Queen": symbol = (piece.getColor() == Color.WHITE) ? "\u2655" : "\u265B"; break;
            case "Rook": symbol = (piece.getColor() == Color.WHITE) ? "\u2656" : "\u265C"; break;
            case "Bishop": symbol = (piece.getColor() == Color.WHITE) ? "\u2657" : "\u265D"; break;
            case "Knight": symbol = (piece.getColor() == Color.WHITE) ? "\u2658" : "\u265E"; break;
            case "Pawn": symbol = (piece.getColor() == Color.WHITE) ? "\u2659" : "\u265F"; break;
        }
        g2d.drawString(symbol, x + 15, y + 60); 
    }

    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game.getWinner() != null) return;

                int visualCol = e.getX() / SQUARE_SIZE;
                int visualRow = e.getY() / SQUARE_SIZE;
                
                if (visualCol >= 0 && visualCol < 8 && visualRow >= 0 && visualRow < 8) {
                    if (selectedSource == null) {
                        int modelRow = 7 - visualRow;
                        selectedSource = new Position(modelRow, visualCol);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedSource == null) return;

                int visualCol = e.getX() / SQUARE_SIZE;
                int visualRow = e.getY() / SQUARE_SIZE;

                if (visualCol >= 0 && visualCol < 8 && visualRow >= 0 && visualRow < 8) {
                    int modelRow = 7 - visualRow;
                    Position destination = new Position(modelRow, visualCol);

                    if (selectedSource.getRow() != destination.getRow() || 
                        selectedSource.getColumn() != destination.getColumn()) {
                        
                        handleMove(selectedSource, destination);
                        selectedSource = null; 
                    }
                } else {
                    selectedSource = null;
                }
            }
        };

        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    /**
     * Attempts to move a piece and updates the HistoryPanel.
     * 
     * @param from The starting position.
     * @param to The destination position.
     */
    private void handleMove(Position from, Position to) {
        if (game.getWinner() != null) return;
        
        Piece movingPiece = game.getPiece(from);
        if (movingPiece == null) return;

        Piece targetPiece = game.getPiece(to);

        // Create a description for the Move History
        String moveText = movingPiece.getColor() + " " + movingPiece.getClass().getSimpleName() 
                        + " (" + from.getRow() + "," + from.getColumn() + ") -> (" 
                        + to.getRow() + "," + to.getColumn() + ")";

        // Save state to the Undo stack BEFORE the move happens
        if (historyPanel != null) {
            historyPanel.saveCurrentState();
        }

        MoveResult result = game.move(from, to);

        if (result == MoveResult.MOVED || result == MoveResult.CAPTURED || result == MoveResult.KING_CAPTURED) {
            
            if (historyPanel != null) {
                // 1. Log the move
                historyPanel.logMove(moveText);
                
                // 2. If a piece was captured, log it to the Captured Pieces area!
                if (targetPiece != null) {
                    String captureText = targetPiece.getColor() + " " + targetPiece.getClass().getSimpleName();
                    historyPanel.logCapture(captureText);
                }
            }
            
            refreshBoard();
            
            if (result == MoveResult.KING_CAPTURED) {
                Color winner = game.getWinner();
                String winnerStr = (winner == Color.WHITE) ? "White" : "Black";
                JOptionPane.showMessageDialog(this, 
                        winnerStr + " wins by capturing the King!", 
                        "Game Over", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            refreshBoard();
        }
    }

    /**
     * Refreshes the board graphics.
     */
    public void refreshBoard() {
        this.repaint();
    }
}