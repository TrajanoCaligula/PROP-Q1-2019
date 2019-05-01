package board;

/**
 * @author jaumeMalgosa
 */

public class Coord {

    private int x;
    private int y;

    /**
     * Coord constructor with given x, y coordinates
     * @param x: x coordinate
     * @param y: y coordinate
     */

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Coord constructor copy with another Cooord
     * @param position: the Coord to copy
     */

    public Coord(Coord position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * Coord constructor with real board coordinates
     * @param realCoord: String with the row-column values (realCoord.size() = 2), for example "e8"
     */

    public Coord(String realCoord) {
        this.x = realCoord.charAt(0) - 'a';
        this.y = '8' - realCoord.charAt(1);
    }

    /**
     * X coordinate getter
     * @return returns the x coordinate
     */

    public int getX() {
        return x;
    }

    /**
     * Y coordinate getter
     * @return the y coordinate
     */

    public int getY() {
        return y;
    }

    /**
     * This function is used to add the coordinates of this Coord to the position parameter
     * @param position: the Coord to add the coordinates
     * @return the resulting added Coord
     */

    public Coord add(Coord position) {
        return new Coord(this.x + position.x,this.y + position.y);
    }

    /**
     * Used  to compare 2 Coords
     * @param o: it would be the Coord to compare to
     * @return true if this x, y coordinates are equal to the given Coord's x, y coordinates, false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x &&
                y == coord.y;
    }

    public String toRealCoord() {
        String realCoord = new String();
        char cx = (char) ('a' + x);
        char cy = (char) ('8' - y);
        realCoord += cx;
        realCoord += cy;
        return realCoord;
    }
}
