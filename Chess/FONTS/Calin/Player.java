package Calin;


/**
 * @author calinPirau
 */

import Jaume.*;
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
     * @param score: Score of the Player from the team Color
     */

    public abstract void play(Board board, int score, Color color);

    /**
     * Getter to get the name of the Player
     * @return the name of the Player
     */

    public String getName() {
        return name;
    }

    /**
     * Getter to get the color of the Player
     * @return the color of the Player
     */

    public Color getColor() {
        return color;
    }

    public abstract String getType();

    public abstract int getDepth();

    /**
     * To convert the Player into String it is returned it's name
     * @return the name of the Player
     */

    @Override
    public String toString() {
        return name;
    }

}
