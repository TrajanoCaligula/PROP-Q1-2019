package player;

import board.Board;
import piece.Color;

public abstract class Player {

    private final String name;
    protected Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public abstract void play(Board board, Color color);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
