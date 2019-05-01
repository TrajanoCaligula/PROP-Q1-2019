package model;



import java.util.ArrayList;

import static model.Board.inBounds;

/**
 * @author jaumeMalgosa
 */

public class King extends Piece {

    /**
     * King constructor
     * @param position: the position to put the King to
     * @param color: the color the King will have
     */

    public King(Coord position, Color color) {
        super(position, PieceValues.KING_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new King(this.position, this.color);
    }

    /**
     * The possible moves from a King are the 1 step moves. It can move only one tile at a time.
     * @param board the Board in which the Piece is
     * @return all the possible moves that the King can do
     */

    @Override
    public ArrayList<Coord> getPossibleMoves(Board board) {

        ArrayList<Coord> possibleMoves = new ArrayList<Coord>();
        Piece piece;

        if (inBounds(this.position.add(new Coord(-1, -1)))) {//Up Left
            piece = board.getPieceInCoord(this.position.add(new Coord(-1, -1)));
            if (piece == null)
                possibleMoves.add(new Coord(-1, -1));
            else if (piece.color != this.color)
                    possibleMoves.add(new Coord(-1, -1));
        }
        if (inBounds(this.position.add(new Coord(0, -1)))) {//Up
            piece = board.getPieceInCoord(this.position.add(new Coord(0, -1)));
            if (piece == null)
                possibleMoves.add(new Coord(0, -1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(0, -1));
        }
        if (inBounds(this.position.add(new Coord(1, -1)))) {//Up Right
            piece = board.getPieceInCoord(this.position.add(new Coord(1, -1)));
            if (piece == null)
                possibleMoves.add(new Coord(1, -1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(1, -1));
        }
        if (inBounds(this.position.add(new Coord(1, 0)))) {//Right
            piece = board.getPieceInCoord(this.position.add(new Coord(1, 0)));
            if (piece == null)
                possibleMoves.add(new Coord(1, 0));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(1, 0));
        }
        if (inBounds(this.position.add(new Coord(1, 1)))) {//Down Right
            piece = board.getPieceInCoord(this.position.add(new Coord(1, 1)));
            if (piece == null)
                possibleMoves.add(new Coord(1, 1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(1, 1));
        }
        if (inBounds(this.position.add(new Coord(0, 1)))) {//Down
            piece = board.getPieceInCoord(this.position.add(new Coord(0, 1)));
            if (piece == null)
                possibleMoves.add(new Coord(0, 1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(0, 1));
        }
        if (inBounds(this.position.add(new Coord(-1, 1)))) {//Down Left
            piece = board.getPieceInCoord(this.position.add(new Coord(-1, 1)));
            if (piece == null)
                possibleMoves.add(new Coord(-1, 1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-1, 1));
        }
        if (inBounds(this.position.add(new Coord(-1, 0)))) {//Left
            piece = board.getPieceInCoord(this.position.add(new Coord(-1, 0)));
            if (piece == null)
                possibleMoves.add(new Coord(-1, 0));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-1, 0));
        }
        return possibleMoves;
    }

    @Override
    public String toFEN() {
        if (color == Color.WHITE)
            return "K";
        else
            return "k";
    }

    /**
     * It prints the King as a String
     * @return the Unicode symbol for the King, depending on its color
     */

    @Override
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return "\u265A";
        }
        else {
            return  "\u2654";
        }
    }
}
