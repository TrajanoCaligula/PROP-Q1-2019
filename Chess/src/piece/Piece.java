package piece;

import board.Board;
import board.Coord;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jaumeMalgosa
 */

public abstract class Piece {

    protected Coord position;
    protected int value;
    protected Color color;

    /**
     *
     * @param position
     * @param value
     * @param color
     */

    public Piece(Coord position, int value, Color color) {
        this.position = position;
        this.value = value;
        this.color = color;
    }

    /**
     *
     * @return
     */

    public abstract Piece copy();

    /**
     *
     * @return
     */

    public Coord getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */


    public void setPosition(Coord position) {
        this.position = position;
    }

    /**
     *
     * @return
     */

    public int getValue() {
        return value;
    }

    /**
     *
     * @return
     */

    public Color getColor() {
        return color;
    }

    /**
     *
     * @param board
     * @return
     */

    public abstract ArrayList<Coord> getPossibleMoves(Board board);

    //TODO getPossibleMove

    //TODO getPossibleMoveWithWall

    /**
     *
     * @param board
     * @return
     */

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
