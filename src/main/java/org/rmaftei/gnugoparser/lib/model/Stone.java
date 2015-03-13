package org.rmaftei.gnugoparser.lib.model;


/**
 * This class is used to keep track of the<br>
 * stones position and color
 * <hr>
 * 
 * @author rmaftei
 * 
 */
public class Stone {

	private Color color;

    private Position position;

    public Stone(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Stone{");
        sb.append("color=").append(color);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stone stone = (Stone) o;

        if (color != stone.color) return false;
        if (!position.equals(stone.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }
}
