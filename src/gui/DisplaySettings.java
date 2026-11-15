
package gui;

import java.awt.Color;

/**
 * Stores the appearance settings for the chessboard.
 * These settings do not affect the actual game state.
 */
public final class DisplaySettings {

    /**
     * Available ways to display chess pieces.
     */
    public enum PieceStyle {
        TEXT("Text labels (wK, bQ)"),
        UNICODE("Chess symbols");

        private final String label;

        PieceStyle(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // Default chessboard settings.
    private Color lightSquareColor = new Color(240, 217, 181);
    private Color darkSquareColor = new Color(181, 136, 99);
    private PieceStyle pieceStyle = PieceStyle.UNICODE;
    private int squareSize = 80;

    /**
     * Returns the light square color.
     *
     * @return light square color
     */
    public Color getLightSquareColor() {
        return lightSquareColor;
    }

    /**
     * Returns the dark square color.
     *
     * @return dark square color
     */
    public Color getDarkSquareColor() {
        return darkSquareColor;
    }

    /**
     * Returns the selected piece style.
     *
     * @return selected piece style
     */
    public PieceStyle getPieceStyle() {
        return pieceStyle;
    }

    /**
     * Returns the size of each square.
     *
     * @return square size in pixels
     */
    public int getSquareSize() {
        return squareSize;
    }

    /**
     * Updates all display settings.
     *
     * @param light light square color
     * @param dark dark square color
     * @param style piece appearance
     * @param size square size in pixels
     */
    public void apply(Color light, Color dark,
                      PieceStyle style, int size) {

        if (light == null || dark == null ||
            style == null || size < 32) {
            throw new IllegalArgumentException(
                "Invalid display settings."
            );
        }

        lightSquareColor = light;
        darkSquareColor = dark;
        pieceStyle = style;
        squareSize = size;
    }

    /**
     * Converts piece labels into the selected
     * display appearance.
     *
     * @param pieceCode the piece's symbol, such as wK
     * @return the symbol to display
     */
    public String displayPiece(String pieceCode) {

        if (pieceCode == null || pieceCode.equals("--")) {
            return "";
        }

        if (pieceStyle == PieceStyle.TEXT) {
            return pieceCode;
        }

        // Unicode chess symbols.
        switch (pieceCode) {
            case "wp":
            case "wP":
                return "\u2659";

            case "wR":
                return "\u2656";

            case "wN":
                return "\u2658";

            case "wB":
                return "\u2657";

            case "wQ":
                return "\u2655";

            case "wK":
                return "\u2654";

            case "bp":
            case "bP":
                return "\u265f";

            case "bR":
                return "\u265c";

            case "bN":
                return "\u265e";

            case "bB":
                return "\u265d";

            case "bQ":
                return "\u265b";

            case "bK":
                return "\u265a";

            default:
                return pieceCode;
        }
    }
}
