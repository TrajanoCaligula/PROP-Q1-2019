package piece;

import board.Board;
import board.Coord;

import java.util.ArrayList;

import static board.Board.inBounds;

public class Pawn extends Piece {

    public Pawn(Coord position, Color color) {
        super(position, PieceValues.PAWN_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new Pawn(this.position, this.color);
    }

    @Override
    public ArrayList<Coord> getPossibleMoves(Board board) {

        ArrayList<Coord> possibleMoves = new ArrayList<Coord>();
        Piece piece;

        //Legal move computation for white pawn
        if(color == Color.WHITE) {
            //Checking in front of the pawn
            if(inBounds(this.position.add(new Coord(0,-1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(0,-1)));
                if(piece == null) {
                    possibleMoves.add(new Coord(0, -1));
                    //Checking double initial move
                    if(inBounds(this.position.add(new Coord(0,-2))) && !hasMoved()) {
                        piece = board.getPieceInCoord(this.position.add(new Coord(0,-2)));
                        if(piece == null) {
                            possibleMoves.add(new Coord(0, -2));
                        }
                    }
                }
            }

            //Checking 1st diagonal
            if(inBounds(this.position.add(new Coord(-1,-1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(-1,-1)));
                if(piece != null) {
                    if(piece.getColor() == Color.BLACK) {
                        possibleMoves.add(new Coord(-1, -1));
                    }
                }
            }

            //Checking 2nd diagonal
            if(inBounds(this.position.add(new Coord(1,-1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(1,-1)));
                if(piece != null) {
                    if(piece.getColor() == Color.BLACK) {
                        possibleMoves.add(new Coord(1, -1));
                    }
                }
            }

        }
        //Legal move computation for black pawn
        else if(color == Color.BLACK) {

            //Checking in front of the pawn
            if(inBounds(this.position.add(new Coord(0,1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(0,1)));
                if(piece == null) {
                    possibleMoves.add(new Coord(0, 1));
                    //Checking double initial move
                    if(inBounds(this.position.add(new Coord(0,2))) && !hasMoved()) {
                        piece = board.getPieceInCoord(this.position.add(new Coord(0,2)));
                        if(piece == null) {
                            possibleMoves.add(new Coord(0, 2));
                        }
                    }
                }
            }

            //Checking 1st diagonal
            if(inBounds(this.position.add(new Coord(-1,1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(-1,1)));
                if(piece != null) {
                    if(piece.getColor() == Color.WHITE) {
                        possibleMoves.add(new Coord(-1, 1));
                    }
                }
            }

            //Checking 2nd diagonal
            if(inBounds(this.position.add(new Coord(1,1)))) {
                piece = board.getPieceInCoord(this.position.add(new Coord(1,1)));
                if(piece != null) {
                    if(piece.getColor() == Color.WHITE) {
                        possibleMoves.add(new Coord(1, 1));
                    }
                }
            }
        }

        return possibleMoves;

    }

    private boolean hasMoved() {
        return !((color == Color.WHITE && position.getY() == 6) || (color == Color.BLACK && position.getY() == 1));
    }

    @Override
    public String toString() {
        if(color.equals(Color.WHITE)) {
            return "\u265F";
        }
        else {
            return "\u2659";
        }
    }
}
