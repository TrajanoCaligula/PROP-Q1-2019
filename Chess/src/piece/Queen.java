package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

import static board.Board.inBounds;

public class Queen extends Piece {

    public Queen(Coord position, Color color) {
        super(position, PieceValues.QUEEN_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new Queen(this.position, this.color);
    }

    public Queen(Pawn pawn) {
        super(pawn.getPosition(), PieceValues.QUEEN_VALUE.getValue(), pawn.getColor());
    }

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
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return  "\u265B";
        }
        else {
            return "\u2655";
        }
    }
}
