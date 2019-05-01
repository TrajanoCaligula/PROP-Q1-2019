package Jaume;


import java.util.ArrayList;

import static Jaume.Board.inBounds;

/**
 * @author jaumeMalgosa
 */

public class Queen extends Piece {

    /**
     * Queen constructor
     * @param position: the position to put the Queen to
     * @param color: the color the Queen will have
     */

    public Queen(Coord position, Color color) {
        super(position, PieceValues.QUEEN_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new Queen(this.position, this.color);
    }

    /**
     * Queen constructor for promoting a Pawn
     * @param pawn: the Pawn to promote
     */

    public Queen(Pawn pawn) {
        super(pawn.getPosition(), PieceValues.QUEEN_VALUE.getValue(), pawn.getColor());
    }

    /**
     * The possible moves from a Queen are the diagonal moves and the straight moves. It can move left, up, down, right
     * and diagonally in any direction. A Queen can't jump through a Piece.
     * @param board the Board in which the Piece is
     * @return all the possible moves that the Queen can do
     */

    @Override
    public ArrayList<Coord> getPossibleMoves(Board board) {
        ArrayList<Coord> possibleMoves = new ArrayList<Coord>();
        Piece piece;
        boolean upLeft = true, up = true, upRight = true, right = true, downRight = true, down = true, downLeft = true, left = true;

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
            if (inBounds(this.position.add(new Coord(0, -i))) && up) {//Up
                piece = board.getPieceInCoord(this.position.add(new Coord(0, -i)));
                if (piece == null)
                    possibleMoves.add(new Coord(0, -i));
                else if (piece.color != this.color) {
                    up = false;
                    possibleMoves.add(new Coord(0, -i));
                }
                else
                    up = false;
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
            if (inBounds(this.position.add(new Coord(i, 0))) && right) {//Right
                piece = board.getPieceInCoord(this.position.add(new Coord(i, 0)));
                if (piece == null)
                    possibleMoves.add(new Coord(i, 0));
                else if (piece.color != this.color) {
                    right = false;
                    possibleMoves.add(new Coord(i, 0));
                }
                else
                    right = false;
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
            if (inBounds(this.position.add(new Coord(0, i))) && down) {//Down
                piece = board.getPieceInCoord(this.position.add(new Coord(0, i)));
                if (piece == null)
                    possibleMoves.add(new Coord(0, i));
                else if (piece.color != this.color) {
                    down = false;
                    possibleMoves.add(new Coord(0, i));
                }
                else
                    down = false;
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
            if (inBounds(this.position.add(new Coord(-i, 0))) && left) {//Left
                piece = board.getPieceInCoord(this.position.add(new Coord(-i, 0)));
                if (piece == null)
                    possibleMoves.add(new Coord(-i, 0));
                else if (piece.color != this.color) {
                    left = false;
                    possibleMoves.add(new Coord(-i, 0));
                }
                else
                    left = false;
            }
        }
        return possibleMoves;
    }

    @Override
    public String toFEN() {
        if (color == Color.WHITE)
            return "Q";
        else
            return "q";
    }

    /**
     * It prints the Queen as a String
     * @return the Unicode symbol for the Queen, depending on its color
     */

    @Override
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return  "\u265B";
        }
        else {
            return "\u2655";
        }
    }
}
