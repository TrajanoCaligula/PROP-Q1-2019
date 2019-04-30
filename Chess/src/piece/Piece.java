package piece;

import board.Board;
import board.Coord;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Piece {

    protected Coord position;
    protected int value;
    protected Color color;

    public Piece(Coord position, int value, Color color) {
        this.position = position;
        this.value = value;
        this.color = color;
    }

    public abstract Piece copy();

    public Coord getPosition() {
        return position;
    }

    public void setPosition(Coord position) {
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public abstract ArrayList<Coord> getPossibleMoves(Board board);

    //TODO getPossibleMove

    //TODO getPossibleMoveWithWall

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Coord> getLegalMoves(Board board) {


        ArrayList<Coord> possibleMoves = this.getPossibleMoves(board);
        ArrayList<Coord> legalMoves = new ArrayList<Coord>();
        Board imaginaryBoard;
        Coord initPos = this.getPosition();
        for(Coord possibleMove : possibleMoves) {
            imaginaryBoard = new Board(board);
            imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(this.getPosition()), new Coord(this.getPosition().add(possibleMove)));
            if (!imaginaryBoard.isCheck(this.color))
                legalMoves.add(possibleMove);

            this.setPosition(initPos);
        }

        return legalMoves;

    }
}
