package board;

/**
 * @author jaumeMalgosa
 */

public class Coord {

    private int x;
    private int y;

    /**
     *
     * @param x
     * @param y
     */

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param position
     */

    public Coord(Coord position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     *
     * @param realCoord
     */

    public Coord(String realCoord) {
        this.x = realCoord.charAt(0) - 'a';
        this.y = '8' - realCoord.charAt(1);
    }

    /**
     *
     * @return
     */

    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */

    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */

    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */

    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param position
     * @return
     */

    public Coord add(Coord position) {
        return new Coord(this.x + position.x,this.y + position.y);
    }

    /**
     *
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x &&
                y == coord.y;
    }

}
