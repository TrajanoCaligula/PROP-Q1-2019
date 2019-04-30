package player;

import board.Board;
import board.Coord;
import piece.Color;
import piece.Piece;

import java.util.ArrayList;
import java.util.Scanner;

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
        Coord initialCoord = stringToCoord(initialPos);
        System.out.print("Please, select the coordinates of the tile you want to move your piece (for example: e8): ");
        s = new Scanner(System.in);
        String movingPos = s.next();
        Coord movingCoord = stringToCoord(movingPos);
        Piece movingPiece = board.getPieceInCoord(initialCoord);
        ArrayList<Coord> legalMoves = movingPiece.getLegalMoves(board);
        boolean isLegal = false;
        for (Coord legalMove : legalMoves) {
            if (initialCoord.add(legalMove).equals(movingCoord));
                isLegal = true;
        }
        while (!isValidMove(movingPos, board) || !isLegal) {
            System.out.print("Wrong coordinates, please select valid coordinates: ");
            s = new Scanner(System.in);
            movingPos = s.next();
            movingCoord = stringToCoord(movingPos);
            movingPiece = board.getPieceInCoord(initialCoord);
            legalMoves = movingPiece.getLegalMoves(board);
            isLegal = false;
            for (Coord legalMove : legalMoves) {
                if (initialCoord.add(legalMove).equals(movingCoord));
                    isLegal = true;
            }
        }

        board.movePiece(board.getPieceInCoord(initialCoord), movingCoord);
    }

    private boolean isValidPiece(String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = stringToCoord(realCoord);
        if (board.getPieceInCoord(position) == null)
            return false;
        if (board.getPieceInCoord(position).getColor() != this.color) {
            return false;
        }
        return true;
    }

    private boolean isValidMove(String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = stringToCoord(realCoord);
        if (board.getPieceInCoord(position) == null) {
            return true;
        }
        if (board.getPieceInCoord(position).getColor() == color) {
            return false;
        }
        return true;
    }

    private Coord stringToCoord(String realCoord) {
        int x = realCoord.charAt(0) - 'a';
        int y = '8' - realCoord.charAt(1);
        Coord position = new Coord(x, y);
        return position;
    }

}
