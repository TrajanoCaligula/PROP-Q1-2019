package player;

import board.Board;
import board.Coord;
import piece.Color;
import piece.Piece;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author calinPirau
 */

public class Human extends Player {

    public Human(String name, Color color) {
        super(name, color);
    }

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

    private boolean isValidPiece(String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = new Coord(realCoord);
        if (board.getPieceInCoord(position) == null)
            return false;
        if (board.getPieceInCoord(position).getColor() != this.color) {
            return false;
        }
        if (board.getPieceInCoord(position).getLegalMoves(board).isEmpty())
            return false;
        return true;
    }

    private boolean isValidMove(Piece piece, String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = new Coord(realCoord);
        if (board.getPieceInCoord(position) == null) {
            return true;
        }
        if (board.getPieceInCoord(position).getColor() == color) {
            return false;
        }
        ArrayList<Coord> legalMoves = piece.getLegalMoves(board);
        for (Coord legalMove : legalMoves) {
            if (piece.getPosition().add(legalMove).equals(position))
                return true;
        }
        return false;
    }

}
