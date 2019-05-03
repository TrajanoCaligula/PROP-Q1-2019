package Jaume;


import java.util.ArrayList;

/**
 * @author jaumeMalgosa
 */

public abstract class Piece {

    protected Coord position;
    protected int value;
    protected Color color;

    /**
     * Piece constructor, it is given its position, its value and its color
     * @param position: the position on the board
     * @param value: the value of the piece
     * @param color: the color of the piece
     */

    public Piece(Coord position, int value, Color color) {
        this.position = position;
        this.value = value;
        this.color = color;
    }

    /**
     * This function is made so we can copy a certain piece and make a new one from that piece
     * @return the new copied Piece
     */

    public abstract Piece copy();

    /**
     * This function gets the position of the Piece on the board
     * @return the position on the board
     */

    public Coord getPosition() {
        return position;
    }

    /**
     * Used to set a given position to the Piece
     * @param position: position of the piece on the board
     */


    public void setPosition(Coord position) {
        this.position = position;
    }

    /**
     * Used to get the Piece's value
     * @return the Piece's value
     */

    public int getValue() {
        return value;
    }

    /**
     * Used to get the Piece's color
     * @return the Piece's color
     */

    public Color getColor() {
        return color;
    }

    /**
     * This function has been made to know which moves can a specific Piece in a specific Coord can do
     * @param board the Board in which the Piece is
     * @return return all the possible moves that the Piece can do
     */

    public abstract ArrayList<Coord> getPossibleMoves(Board board);

    //TODO getPossibleMove

    //TODO getPossibleMoveWithWall

    /**
     * This function is used as a filter for getPossibleMoves, because it is needed to know if the player is in check
     * and has to defend the King or not
     * @param board: the board in which the Piece is
     * @return returns all the legal moves that the Piece can do
     */

    public ArrayList<Coord> getLegalMoves(Board board) {
        ArrayList<Coord> possibleMoves = this.getPossibleMoves(board);
        ArrayList<Coord> legalMoves = new ArrayList<Coord>();
        Board imaginaryBoard;
        Coord initPos = this.getPosition();
        for(Coord possibleMove : possibleMoves) {
            imaginaryBoard = new Board(board);
            imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(this.getPosition()), new Coord(this.getPosition().add(possibleMove)));
            if (!imaginaryBoard.isCheck(this.color))
                legalMoves.add(possibleMove);

            this.setPosition(initPos);
        }
        return legalMoves;
    }

    /**
     * Used to change a Piece's color (only in PieceDriver)
     * @param color: the color to change the Piece to
     */

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * This function returns a String with the character that represents the respective Piece in the Board
     * @return UpperCase identifier for whitePiece and LoweCase identifier for blackPiece
     */

    public abstract String toFEN();
}
