package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

import static board.Board.inBounds;

/**
 * @author jaumeMalgosa
 */

public class Rook extends Piece {

    /**
     *
     * @param position
     * @param color
     */

    public Rook(Coord position, Color color) {
        super(position, PieceValues.ROOK_VALUE.getValue(), color);
    }

    /**
     *
     * @return
     */

    @Override
    public Piece copy() {
        return new Rook(this.position, this.color);
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
        boolean up = true, right = true, down = true, left = true;
        for (int i = 1; i < 8; i++) {
            if (inBounds(this.position.add(new Coord(0, -i))) && up) {//Up
                piece = board.getPieceInCoord(this.position.add(new Coord(0, -i)));
                if (piece == null)
                    possibleMoves.add(new Coord(0, -i));
                else if (piece.color != this.color) {
                    possibleMoves.add(new Coord(0, -i));
                    up = false;
                }
                else if (piece.color == this.color)
                    up = false;
            }
            if (inBounds(this.position.add(new Coord(i, 0)))&& right) {//Right
                piece = board.getPieceInCoord(this.position.add(new Coord(i, 0)));
                if (piece == null)
                    possibleMoves.add(new Coord(i, 0));
                else if (piece.color != this.color) {
                    possibleMoves.add(new Coord(i, 0));
                    right = false;
                }
                else if (piece.color == this.color)
                    right = false;
            }
            if (inBounds(this.position.add(new Coord(0, i))) && down) {//Down
                piece = board.getPieceInCoord(this.position.add(new Coord(0, i)));
                if (piece == null)
                    possibleMoves.add(new Coord(0, i));
                else if (piece.color != this.color) {
                    possibleMoves.add(new Coord(0, i));
                    down = false;
                }
                else if (piece.color == this.color)
                    down = false;
            }
            if (inBounds(this.position.add(new Coord(-i, 0))) && left) {//Left
                piece = board.getPieceInCoord(this.position.add(new Coord(-i, 0)));
                if (piece == null)
                    possibleMoves.add(new Coord(-i, 0));
                else if (piece.color != this.color) {
                    possibleMoves.add(new Coord(-i, 0));
                    left = false;
                }
                else if (piece.color == this.color)
                    left = false;
            }
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
            return  "\u265C";
        }
        else {
            return "\u2656";
        }
    }
}
