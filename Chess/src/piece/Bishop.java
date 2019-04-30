package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

import static board.Board.inBounds;

/**
 * @author jaumeMalgosa
 */

public class Bishop extends Piece {

    /**
     *
     * @param position
     * @param color
     */

    public Bishop(Coord position, Color color) {
        super(position, PieceValues.BISHOP_VALUE.getValue(), color);
    }

    /**
     *
     * @return
     */

    @Override
    public Piece copy() {
        return new Bishop(this.position, this.color);
    }

    /**
     *
     * @param board
     * @return
     */

    @Override
    public ArrayList<Coord> getPossibleMoves(Board board) {
        ArrayList<Coord> possibleMoves = new ArrayList<Coord>();
        Piece piece = null;
        boolean upLeft = true, upRight = true, downRight = true, downLeft = true;
        for (int i = 1; i < 8; i++) {
            if (inBounds(this.position.add(new Coord(-i, -i))) && upLeft) {//Up Left
                piece = board.getPieceInCoord(this.position.add(new Coord(-i, -i)));
                if (piece == null)
                    possibleMoves.add(new Coord(-i, -i));
                else if (piece.color != this.color) {
                    upLeft = false;
                    possibleMoves.add(new Coord(-i, -i));
                }
                else
                    upLeft = false;
            }
            if (inBounds(this.position.add(new Coord(i, -i))) && upRight) {//Up Right
                piece = board.getPieceInCoord(this.position.add(new Coord(i, -i)));
                if (piece == null)
                    possibleMoves.add(new Coord(i, -i));
                else if (piece.color != this.color) {
                    upRight = false;
                    possibleMoves.add(new Coord(i, -i));
                }
                else
                    upRight = false;
            }
            if (inBounds(this.position.add(new Coord(i, i))) && downRight) {//Down Right
                piece = board.getPieceInCoord(this.position.add(new Coord(i, i)));
                if (piece == null)
                    possibleMoves.add(new Coord(i, i));
                else if (piece.color != this.color) {
                    downRight = false;
                    possibleMoves.add(new Coord(i, i));
                }
                else
                    downRight = false;
            }
            if (inBounds(this.position.add(new Coord(-i, i))) && downLeft) {//Down Left
                piece = board.getPieceInCoord(this.position.add(new Coord(-i, i)));
                if (piece == null)
                    possibleMoves.add(new Coord(-i, i));
                else if (piece.color != this.color) {
                    downLeft = false;
                    possibleMoves.add(new Coord(-i, i));
                }
                else
                    downLeft = false;
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
            return "\u265D";
        }
        else {
            return  "\u2657";
        }
    }
}
