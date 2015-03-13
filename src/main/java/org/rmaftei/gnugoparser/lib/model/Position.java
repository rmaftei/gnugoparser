package org.rmaftei.gnugoparser.lib.model;

/**
 * Created by rmaftei.
 */
public class Position {

    private final int line;

    private final int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (column != position.column) return false;
        if (line != position.line) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Position{");
        sb.append("line=").append(line);
        sb.append(", column=").append(column);
        sb.append('}');
        return sb.toString();
    }
}
