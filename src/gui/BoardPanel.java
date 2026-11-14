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
 * Handles drawing the board, the pieces, and mouse interactions (click-to-move and drag-and-drop).
 * 
 * Coordinate Mapping Agreement: 
 * Visual (0,0) is Top-Left. Model (0,0) is Bottom-Left (A1).
 * Therefore, Model Row = 7 - Visual Row.
 * 
 * @author Andres
 * @version 1.0 (Phase 2)
 */
public class BoardPanel extends JPanel {

    /** The pixel size of a single square on the board. */
    private static final int SQUARE_SIZE = 80; 
    /** The total pixel size of the board (8 squares * 80 pixels). */
    private static final int BOARD_SIZE = SQUARE_SIZE * 8;
    
    private Game game;
    /** Tracks the first click for click-to-move functionality. */
    private Position selectedSource; 
    
    /** Color for light squares (Classic wooden style). */
    private final java.awt.Color lightSquareColor = new java.awt.Color(240, 217, 181);
    /** Color for dark squares (Classic wooden style). */
    private final java.awt.Color darkSquareColor = new java.awt.Color(181, 136, 99);

    /**
     * Constructs the BoardPanel, initializes the Game logic, and sets up mouse listeners.
     * Sets the preferred size based on the SQUARE_SIZE constants.
     */
    public BoardPanel() {
        this.game = new Game(); // Instantiates John's Game class
        this.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        
        setupMouseListeners();
    }

    /**
     * Overrides the default paint method to draw the 8x8 board and all pieces.
     * 
     * @param g The Graphics object used for drawing.
     */
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

        // 2. Draw the pieces using John's getPiece method
        for (int visualRow = 0; visualRow < 8; visualRow++) {
            for (int visualCol = 0; visualCol < 8; visualCol++) {
                // CONVERT TO MODEL COORDINATES (A8 is 7,0. A1 is 0,0)
                int modelRow = 7 - visualRow; 
                int modelCol = visualCol;
                Position pos = new Position(modelRow, modelCol);
                
                // Fetch piece from the shared Game object
                Piece piece = game.getPiece(pos);
                if (piece != null) {
                    drawPiece(g2d, piece, visualCol * SQUARE_SIZE, visualRow * SQUARE_SIZE);
                }
            }
        }
    }

    /**
     * Draws a single chess piece on the board using Unicode symbols.
     * 
     * @param g2d The graphics object.
     * @param piece The piece to draw.
     * @param x The visual X coordinate (pixels).
     * @param y The visual Y coordinate (pixels).
     */
    private void drawPiece(Graphics2D g2d, Piece piece, int x, int y) {
        String symbol = "";
        g2d.setFont(new Font("Serif", Font.BOLD, 60));
        
        // Set color based on your Phase 1 Color class
        if (piece.getColor() == Color.WHITE) {
            g2d.setColor(java.awt.Color.WHITE);
        } else {
            g2d.setColor(java.awt.Color.BLACK);
        }

        // Determine piece type based on your Phase 1 class names
        String type = piece.getClass().getSimpleName(); 
        switch (type) {
            case "King": symbol = (piece.getColor() == Color.WHITE) ? "\u2654" : "\u265A"; break;
            case "Queen": symbol = (piece.getColor() == Color.WHITE) ? "\u2655" : "\u265B"; break;
            case "Rook": symbol = (piece.getColor() == Color.WHITE) ? "\u2656" : "\u265C"; break;
            case "Bishop": symbol = (piece.getColor() == Color.WHITE) ? "\u2657" : "\u265D"; break;
            case "Knight": symbol = (piece.getColor() == Color.WHITE) ? "\u2658" : "\u265E"; break;
            case "Pawn": symbol = (piece.getColor() == Color.WHITE) ? "\u2659" : "\u265F"; break;
            default: symbol = "?"; break;
        }
        
        // Center the text in the square
        g2d.drawString(symbol, x + 15, y + 60); 
    }

    /**
     * Sets up the mouse listeners for click-to-move and drag-and-drop.
     * A "Click" is a press and release on the same square.
     * A "Drag" is a press on one square and release on another.
     */
    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // On press, find out which square was clicked
                int visualCol = e.getX() / SQUARE_SIZE;
                int visualRow = e.getY() / SQUARE_SIZE;
                
                // Bounds check to prevent clicking outside the board
                if (visualCol >= 0 && visualCol < 8 && visualRow >= 0 && visualRow < 8) {
                    int modelRow = 7 - visualRow;
                    int modelCol = visualCol;
                    selectedSource = new Position(modelRow, modelCol);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedSource == null) return;

                // On release, find the destination square
                int visualCol = e.getX() / SQUARE_SIZE;
                int visualRow = e.getY() / SQUARE_SIZE;

                if (visualCol >= 0 && visualCol < 8 && visualRow >= 0 && visualRow < 8) {
                    int modelRow = 7 - visualRow;
                    int modelCol = visualCol;
                    Position destination = new Position(modelRow, modelCol);

                    handleMove(selectedSource, destination);
                }
                
                // Reset selection after move attempt
                selectedSource = null;
            }
        };

        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    /**
     * Attempts to move a piece from source to destination using the Game logic.
     * Handles the results, including refreshing the board and showing the winner.
     * 
     * @param from The starting position.
     * @param to The destination position.
     */
    private void handleMove(Position from, Position to) {
        // Prevent moving from an empty square
        if (game.getPiece(from) == null) {
            return; 
        }

        // Call John's move method
        MoveResult result = game.move(from, to);

        // Handle the result
        if (result == MoveResult.MOVED || result == MoveResult.CAPTURED) {
            refreshBoard();
        } else if (result == MoveResult.KING_CAPTURED) {
            refreshBoard();
            
            // Show winner popup
            Color winner = game.getWinner();
            String winnerStr = (winner == Color.WHITE) ? "White" : "Black";
            JOptionPane.showMessageDialog(this, 
                    winnerStr + " wins by capturing the King!", 
                    "Game Over", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            // End play by removing mouse listeners so no more moves can be made
            this.removeMouseListener(this.getMouseListeners()[0]);
            this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
            
        } else if (result == MoveResult.INVALID_MOVE || result == MoveResult.GAME_OVER) {
            // Invalid move, just redraw to clear any visual selection
            refreshBoard();
        }
    }

    /**
     * Public method to refresh the board graphics.
     */
    public void refreshBoard() {
        this.repaint();
    }
}