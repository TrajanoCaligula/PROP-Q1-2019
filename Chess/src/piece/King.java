package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

import static board.Board.inBounds;

public class King extends Piece {

    public King(Coord position, Color color) {
        super(position, PieceValues.KING_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new King(this.position, this.color);
    }

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
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return "\u265A";
        }
        else {
            return  "\u2654";
        }
    }
}
