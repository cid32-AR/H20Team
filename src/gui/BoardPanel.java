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
 * Allows dynamic updates to board size, colors, and piece styles for Alex's Settings feature.
 */
public class BoardPanel extends JPanel {

    private int squareSize = 80; 
    private int boardSize = squareSize * 8;
    
    private Game game;
    private HistoryPanel historyPanel;
    private Position selectedSource; 
    
    private java.awt.Color lightSquareColor = new java.awt.Color(240, 217, 181);
    private java.awt.Color darkSquareColor = new java.awt.Color(181, 136, 99);
    private DisplaySettings displaySettings =
    new DisplaySettings();
    public BoardPanel(Game game) {
        this.game = game; 
        this.setPreferredSize(new Dimension(boardSize, boardSize));
        setupMouseListeners();
    }

    public void setHistoryPanel(HistoryPanel historyPanel) {
        this.historyPanel = historyPanel;
    }

    /**
     * METHOD FOR ALEX: Updates the board colors dynamically.
     */
    public void setBoardColors(java.awt.Color light, java.awt.Color dark) {
        this.lightSquareColor = light;
        this.darkSquareColor = dark;
        refreshBoard();
    }

    /**
     * METHOD FOR ALEX: Updates the board size dynamically.
     */
    public void setBoardSize(int newSquareSize) {
        this.squareSize = newSquareSize;
        this.boardSize = squareSize * 8;
        this.setPreferredSize(new Dimension(boardSize, boardSize));
        
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null); // Re-center
        }
        
        refreshBoard();
    }
    
/**
 * Applies new display settings without changing
 * the chess pieces or current game state.
 *
 * @param settings the selected display settings
 */
public void setDisplaySettings(DisplaySettings settings) {

    if (settings == null) {
        throw new IllegalArgumentException(
            "Display settings cannot be null."
        );
    }

    this.displaySettings = settings;

    setBoardColors(
        settings.getLightSquareColor(),
        settings.getDarkSquareColor()
    );

    setBoardSize(settings.getSquareSize());
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
                g2d.fillRect(visualCol * squareSize, visualRow * squareSize, squareSize, squareSize);
            }
        }

        // 2. Draw the pieces
        for (int visualRow = 0; visualRow < 8; visualRow++) {
            for (int visualCol = 0; visualCol < 8; visualCol++) {
                int modelRow = 7 - visualRow; 
                Position pos = new Position(modelRow, visualCol);
                
                Piece piece = game.getPiece(pos);
                if (piece != null) {
                    // Scale the font size based on the square size
                    int fontSize = (int)(squareSize * 0.75); 
                    drawPiece(g2d, piece, visualCol * squareSize, visualRow * squareSize, fontSize);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g2d, Piece piece, int x, int y, int fontSize) {
       
    String symbol = displaySettings.displayPiece(piece.getSymbol());
    int actualFontSize;
    if (displaySettings.getPieceStyle()
        == DisplaySettings.PieceStyle.TEXT) {actualFontSize = Math.max(14, squareSize / 3);
    } else {
    actualFontSize = fontSize;
    }

g2d.setFont(
    new Font("Serif", Font.BOLD, actualFontSize)
);

        
        if (piece.getColor() == Color.WHITE) {
            g2d.setColor(java.awt.Color.WHITE);
        } else {
            g2d.setColor(java.awt.Color.BLACK);
        }
        
        // Calculate centering based on the dynamic font/size
        int xOffset = (squareSize - g2d.getFontMetrics().stringWidth(symbol)) / 2;
        int yOffset = (squareSize + g2d.getFontMetrics().getAscent()) / 2 - 5;
        
        g2d.drawString(symbol, x + xOffset, y + yOffset); 
    }

    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game.getWinner() != null) return;

                int visualCol = e.getX() / squareSize;
                int visualRow = e.getY() / squareSize;
                
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

                int visualCol = e.getX() / squareSize;
                int visualRow = e.getY() / squareSize;

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

    private void handleMove(Position from, Position to) {
        if (game.getWinner() != null) return;
        
        Piece movingPiece = game.getPiece(from);
        if (movingPiece == null) return;
        Piece targetPiece = game.getPiece(to);

        String moveText = movingPiece.getColor() + " " + movingPiece.getClass().getSimpleName() 
                        + " (" + from.getRow() + "," + from.getColumn() + ") -> (" 
                        + to.getRow() + "," + to.getColumn() + ")";

        // 1. Save state BEFORE the move
        if (historyPanel != null) {
            historyPanel.saveCurrentState();
        }

        MoveResult result = game.move(from, to);

        if (result == MoveResult.MOVED || result == MoveResult.CAPTURED || result == MoveResult.KING_CAPTURED) {
            // Success! Log the move
            if (historyPanel != null) {
                historyPanel.logMove(moveText);
                if (targetPiece != null) {
                    String captureText = targetPiece.getColor() + " " + targetPiece.getClass().getSimpleName();
                    historyPanel.logCapture(captureText);
                }
            }
            refreshBoard();
            
            if (result == MoveResult.KING_CAPTURED) {
                Color winner = game.getWinner();
                String winnerStr = (winner == Color.WHITE) ? "White" : "Black";
                JOptionPane.showMessageDialog(this, winnerStr + " wins by capturing the King!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // 2. The move FAILED. Remove the state we just saved so it doesn't mess up Undo!
            if (historyPanel != null) {
                historyPanel.discardLastState();
            }
            refreshBoard();
        }
    }

    public void refreshBoard() {
        this.revalidate();
        this.repaint();
    }
}
