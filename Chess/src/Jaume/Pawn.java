package Jaume;


import java.util.ArrayList;

import static Jaume.Board.inBounds;

/**
 * @author jaumeMalgosa
 */

public class Pawn extends Piece {

    /**
     * Pawn constructor
     * @param position: the position to put the Pawn to
     * @param color: the color the Pawn will have
     */

    public Pawn(Coord position, Color color) {
        super(position, PieceValues.PAWN_VALUE.getValue(), color);
    }

    @Override
    public Piece copy() {
        return new Pawn(this.position, this.color);
    }

    /**
     * The possible moves from a Pawn are the one step forward moves, the two step forward moves if the Pawn is at it's original position, ore one step diagonally forward to attack an enemy Piece.
     * A Pawn can't jump through a Piece.
     * @param board the Board in which the Piece is
     * @return all the possible moves that the Pawn can do
     */

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

    @Override
    public String toFEN() {
        if (color == Color.WHITE)
            return "P";
        else
            return "p";
    }

    /**
     * It looks if the Pawn can do the two step forward move. If it's in its original place it would be a possible move
     * @return true if it has moved (so it won't do the two step forward move), or false otherwise
     */

    private boolean hasMoved() {
        return !((color == Color.WHITE && position.getY() == 6) || (color == Color.BLACK && position.getY() == 1));
    }

    /**
     * It prints the Pawn as a String
     * @return the Unicode symbol for the Pawn, depending on its color
     */

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
