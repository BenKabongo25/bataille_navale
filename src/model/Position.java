package model;

/**
* Classe des positions jouables
*/
public class Position {

    /**
    * ligne
    */
    private int line;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    /**
    * colonne
    */
    private int column;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /**
    * Position
    * @param line ligne
    * @param column colonne
    */
    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return line == position.line && column == position.column;
    }

    @Override
    public int hashCode() {
        return line * 10 + column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }
}
