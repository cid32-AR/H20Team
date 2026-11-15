
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Provides a Settings window for changing the
 * chessboard colors, piece appearance, and size.
 */
public final class SettingsDialog extends JDialog {

    private final DisplaySettings settings;
    private final Runnable refreshBoard;

    // Temporary selections before Apply is clicked.
    private Color selectedLight;
    private Color selectedDark;

    private final JButton lightColorButton;
    private final JButton darkColorButton;

    private final JComboBox<DisplaySettings.PieceStyle> pieceStyleBox;
    private final JComboBox<Integer> squareSizeBox;

    /**
     * Creates the Settings window.
     *
     * @param owner the main game window
     * @param settings shared display settings
     * @param refreshBoard method for redrawing the board
     */
    public SettingsDialog(Window owner,
                          DisplaySettings settings,
                          Runnable refreshBoard) {

        super(owner, "Chess Settings",
              Dialog.ModalityType.APPLICATION_MODAL);

        if (settings == null || refreshBoard == null) {
            throw new IllegalArgumentException(
                "Settings and refresh callback are required."
            );
        }

        this.settings = settings;
        this.refreshBoard = refreshBoard;

        selectedLight = settings.getLightSquareColor();
        selectedDark = settings.getDarkSquareColor();

        // Buttons for selecting board colors.
        lightColorButton = new JButton("Choose light color");
        darkColorButton = new JButton("Choose dark color");

        updateColorButtons();

        lightColorButton.addActionListener(e -> chooseLightColor());
        darkColorButton.addActionListener(e -> chooseDarkColor());

        // Options for chess piece appearance.
        pieceStyleBox = new JComboBox<>(
            DisplaySettings.PieceStyle.values()
        );

        pieceStyleBox.setSelectedItem(
            settings.getPieceStyle()
        );

        // Available board square sizes.
        squareSizeBox = new JComboBox<>(
            new Integer[] {48, 64, 80, 96}
        );

        squareSizeBox.setSelectedItem(
            settings.getSquareSize()
        );

        // Arrange settings in the window.
        JPanel fields = new JPanel(
            new GridLayout(4, 2, 12, 12)
        );

        fields.add(new JLabel("Light squares:"));
        fields.add(lightColorButton);

        fields.add(new JLabel("Dark squares:"));
        fields.add(darkColorButton);

        fields.add(new JLabel("Piece appearance:"));
        fields.add(pieceStyleBox);

        fields.add(new JLabel("Square size (pixels):"));
        fields.add(squareSizeBox);

        // Apply and Cancel buttons.
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        applyButton.addActionListener(e -> applyChanges());
        cancelButton.addActionListener(e -> dispose());

        JPanel actions = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        actions.add(cancelButton);
        actions.add(applyButton);

        JPanel content = new JPanel(
            new BorderLayout(0, 16)
        );

        content.setBorder(
            new EmptyBorder(16, 16, 12, 16)
        );

        content.add(fields, BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);

        setContentPane(content);

        getRootPane().setDefaultButton(applyButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Opens the color chooser for light squares.
     */
    private void chooseLightColor() {

        Color choice = JColorChooser.showDialog(
            this, "Light square color", selectedLight
        );

        if (choice != null) {
            selectedLight = choice;
            updateColorButtons();
        }
    }

    /**
     * Opens the color chooser for dark squares.
     */
    private void chooseDarkColor() {

        Color choice = JColorChooser.showDialog(
            this, "Dark square color", selectedDark
        );

        if (choice != null) {
            selectedDark = choice;
            updateColorButtons();
        }
    }

    /**
     * Updates the color button previews.
     */
    private void updateColorButtons() {

        lightColorButton.setBackground(selectedLight);
        lightColorButton.setOpaque(true);

        darkColorButton.setBackground(selectedDark);
        darkColorButton.setOpaque(true);
    }

    /**
     * Applies the chosen settings and refreshes
     * the board without resetting the game.
     */
    private void applyChanges() {

        DisplaySettings.PieceStyle style =
            (DisplaySettings.PieceStyle)
            pieceStyleBox.getSelectedItem();

        Integer size =
            (Integer) squareSizeBox.getSelectedItem();

        if (style == null || size == null) {
            return;
        }

        settings.apply(
            selectedLight,
            selectedDark,
            style,
            size
        );

        /*
         * Integration with ChessMainWindow:
         *
         * refreshBoard is supplied when this dialog
         * is created.
         *
         * The callback must update BoardPanel using
         * the selected settings and redraw the board.
         *
         * It must not reset the Game object.
         */
        refreshBoard.run();

        dispose();
    }
}
