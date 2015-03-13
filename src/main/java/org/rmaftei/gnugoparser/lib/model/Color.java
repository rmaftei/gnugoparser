package org.rmaftei.gnugoparser.lib.model;

/**
 * Enum for colors.
 * <hr>
 * 
 * @author rmaftei
 * 
 */
public enum Color {
	BLACK, WHITE;

	public static Color getColor(String colorName) {
		if (colorName.toLowerCase().equals("black")) {
			return Color.BLACK;
		}
		if (colorName.toLowerCase().equals("white")) {
			return Color.WHITE;
		}

		return null;
	}
}
