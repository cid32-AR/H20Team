package model;

/**
 * Represents a chess piece color.
 */
public class Color {

    /** The white color. */
    public static final Color WHITE = new Color("WHITE");

    /** The black color. */
    public static final Color BLACK = new Color("BLACK");

    /** The name of this color. */
    private final String name;

    /**
     * Creates a color.
     *
     * @param name the name of the color
     */
    private Color(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this color.
     *
     * @return the name of the color
     */
    @Override
    public String toString() {
        return name;
    }
}