package player;

import board.Board;
import piece.Color;

/**
 * @author calinPirau
 */

public abstract class Player {

    private final String name;
    protected Color color;

    /**
     *
     * @param name
     * @param color
     */

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     *
     * @param board
     * @param color
     */

    public abstract void play(Board board, Color color);

    /**
     *
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */

    @Override
    public String toString() {
        return name;
    }
}
