package Jaume;

import java.util.ArrayList;

/**
 * @author jaumeMalgosa
 */

import static Jaume.Board.inBounds;

public class Knight extends Piece {

    /**
     * Knight constructor
     * @param position: the position to put the Knight to
     * @param color: the color the Knight will have
     */

    public Knight(Coord position, Color color) {
        super(position, PieceValues.KNIGHT_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new Knight(this.position, this.color);
    }

    /**
     * The possible moves from a Knight are the moves that draw an L. A Knight can jump through a Piece.
     * @param board the Board in which the Piece is
     * @return all the possible moves that the Knight can do
     */

    @Override
    public ArrayList<Coord> getPossibleMoves(Board board) {
        ArrayList<Coord> possibleMoves = new ArrayList<Coord>();
        Piece piece;

        if (inBounds(this.position.add(new Coord(-1, -2)))) {//Left Up 1
            piece = board.getPieceInCoord(this.position.add(new Coord(-1, -2)));
            if (piece == null)
                possibleMoves.add(new Coord(-1, -2));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-1, -2));
        }
        if (inBounds(this.position.add(new Coord(1, -2)))) {//Right Up 1
            piece = board.getPieceInCoord(this.position.add(new Coord(1, -2)));
            if (piece == null)
                possibleMoves.add(new Coord(1, -2));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(1, -2));
        }

        if (inBounds(this.position.add(new Coord(-2, -1)))) {//Left Up 2
            piece = board.getPieceInCoord(this.position.add(new Coord(-2, -1)));
            if (piece == null)
                possibleMoves.add(new Coord(-2, -1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-2, -1));
        }

        if (inBounds(this.position.add(new Coord(2, -1)))) {//Right Up 2
            piece = board.getPieceInCoord(this.position.add(new Coord(2, -1)));
            if (piece == null)
                possibleMoves.add(new Coord(2, -1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(2, -1));
        }

        if (inBounds(this.position.add(new Coord(-2, 1)))) {//Left Down 1
            piece = board.getPieceInCoord(this.position.add(new Coord(-2, 1)));
            if (piece == null)
                possibleMoves.add(new Coord(-2, 1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-2, 1));
        }

        if (inBounds(this.position.add(new Coord(2, 1)))) {//Right Down 1
            piece = board.getPieceInCoord(this.position.add(new Coord(2, 1)));
            if (piece == null)
                possibleMoves.add(new Coord(2, 1));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(2, 1));
        }

        if (inBounds(this.position.add(new Coord(-1, 2)))) {//Left Down 2
            piece = board.getPieceInCoord(this.position.add(new Coord(-1, 2)));
            if (piece == null)
                possibleMoves.add(new Coord(-1, 2));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(-1, 2));
        }

        if (inBounds(this.position.add(new Coord(1, 2)))) {//Right Down 2
            piece = board.getPieceInCoord(this.position.add(new Coord(1, 2)));
            if (piece == null)
                possibleMoves.add(new Coord(1, 2));
            else if (piece.color != this.color)
                possibleMoves.add(new Coord(1, 2));
        }


        return possibleMoves;
    }

    @Override
    public String toFEN() {
        if (color == Color.WHITE)
            return "N";
        else
            return "n";
    }

    /**
     * It prints the Knight as a String
     * @return the Unicode symbol for the Knight, depending on its color
     */

    @Override
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return "\u265E";
        }
        else {
            return "\u2658";
        }
    }
}
