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
     * Player constructor. It takes it's name and color
     * @param name: the name of the Player
     * @param color: the color of the Player
     */

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * The function to make the player play. It differs depending of which type of Player has to play
     * @param board: the Board to play to
     * @param color: the Color of the pieces to play with
     */

    public abstract void play(Board board, Color color);

    /**
     * Getter to get the name of the Player
     * @return the name of the Player
     */

    public String getName() {
        return name;
    }

    /**
     * To convert the Player into String it is returned it's name
     * @return the name of the Player
     */

    @Override
    public String toString() {
        return name;
    }
}
