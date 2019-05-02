package Calin;

import Jaume.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author calinPirau
 */

public class Human extends Player {

    /**
     * Human constructor, giving it's name and color
     * @param name: the name of the Human
     * @param color: the color of the Human
     */

    public Human(String name, Color color) {
        super(name, color);
    }

    /**
     * In this case, knowing that it will be a human that will play, we made this function ask for the coordinates of
     * the piece to move and the coordinates of the position to move to, made in real Chess Board numeration.
     * @param board: the Board to play with
     * @param color: the Color to play with
     */

    @Override
    public void play(Board board, Color color) {
        System.out.print("Please, select the coordinates of the piece you want to move (for example: e8): ");
        Scanner s = new Scanner(System.in);
        String initialPos = s.next();
        while (!isValidPiece(initialPos, board)) {
            System.out.print("Wrong coordinates, please select valid coordinates: ");
            s = new Scanner(System.in);
            initialPos = s.next();
        }
        Coord initialCoord = new Coord(initialPos);
        System.out.print("Please, select the coordinates of the tile you want to move your piece (for example: e8): ");
        s = new Scanner(System.in);
        String movingPos = s.next();
        Coord movingCoord;
        Piece movingPiece, piece = board.getPieceInCoord(initialCoord);
        while (!isValidMove(piece, movingPos, board)) {
            System.out.print("Wrong coordinates, please select valid coordinates: ");
            s = new Scanner(System.in);
            movingPos = s.next();
        }
        movingCoord = new Coord(movingPos);
        board.movePiece(board.getPieceInCoord(initialCoord), movingCoord);
    }

    /**
     * This function was made to check if a coordinate that the Human has given has a valid piece on it. It first
     * looks if the coordinates are in bounds of the board, and then checks if there is a piece in the given coordinates
     * @param realCoord: the real board coordinates to check (for example "f8")
     * @param board: the board to check to
     * @return true if it is a piece of the same color as the Human, false otherwise
     */

    private boolean isValidPiece(String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = new Coord(realCoord);
        if (board.getPieceInCoord(position) == null) {
            System.out.println("There's no piece here");
            return false;
        }

        if (board.getPieceInCoord(position).getColor() != this.color) {
            System.out.println("That's an enemy piece");
            return false;
        }
        if (board.getPieceInCoord(position).getLegalMoves(board).isEmpty()) {
            System.out.println("This piece doesn't have legal moves");
            return false;
        }

        return true;
    }

    /**
     * This function was made to check if a coordinate that the Human has given is a legal move for the piece that was
     * already selected
     * @param piece: the Piece that the Human wants to move
     * @param realCoord: the real board coordinates that the Human wants to move the Piece to
     * @param board: the Board to check
     * @return true if the realCoord given is legal, false otherwise
     */

    static boolean isValidMove(Piece piece, String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = new Coord(realCoord);
        ArrayList<Coord> legalMoves = piece.getLegalMoves(board);
        for (Coord legalMove : legalMoves) {
            if (piece.getPosition().add(legalMove).equals(position))
                return true;
        }
        return false;
    }

}
