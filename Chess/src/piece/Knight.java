package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

/**
 * @author jaumeMalgosa
 */

import static board.Board.inBounds;

public class Knight extends Piece {

    /**
     *
     * @param position
     * @param color
     */

    public Knight(Coord position, Color color) {
        super(position, PieceValues.KNIGHT_VALUE.getValue(), color);
    }

    /**
     *
     * @return
     */

    @Override
    public Piece copy() {
        return new Knight(this.position, this.color);
    }

    /**
     *
     * @param board
     * @return
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

    /**
     *
     * @return
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
