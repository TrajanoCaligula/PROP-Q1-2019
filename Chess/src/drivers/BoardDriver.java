package drivers;

import board.Board;
import board.Coord;
import piece.Piece;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardDriver {

    public static void main(String[] args) {
        System.out.println("Board Driver");
        System.out.println("Insert a board in FEN notation: ");
        String[] FEN = new String[6];
        String FENBoard, FENTurn, FENCastling, FENCapture, FENCaptureN, FENTurnN;
        Scanner s = new Scanner(System.in);
        FEN = s.nextLine().split(" ");
        FENBoard = FEN[0];
        FENTurn = FEN[1];
        FENCastling = FEN[2];
        FENCapture = FEN[3];
        FENCaptureN = FEN[4];
        FENTurnN = FEN[5];
        Board board = new Board(FENBoard);
        while (true) {
            board.printBoard();
            System.out.println("Select the coordinates of the piece to move [x y]: ");

            s = new Scanner(System.in);
            int x = s.nextInt();
            int y = s.nextInt();

            Piece piece = board.getPieceInCoord(new Coord(x, y));
            while (piece == null) {
                System.out.println("There's no piece there, select the coordinates of the piece to move [x y]: ");

                s = new Scanner(System.in);
                x = s.nextInt();
                y = s.nextInt();

                piece = board.getPieceInCoord(new Coord(x, y));
            }
            ArrayList<Coord> legalMoves = piece.getLegalMoves(board);
            System.out.println(piece.toString());
            if (legalMoves.isEmpty())
                System.out.println("It doesn't have legal moves");
            else {
                System.out.println("It's legal moves are: ");
                int i = -1;
                for (Coord legalMove : legalMoves) {
                    i++;
                    System.out.println(i+": "+legalMove.getX()+" "+legalMove.getY());
                }
                System.out.println("Please, select one of the legal moves (from 0 to "+i+"): ");
                s = new Scanner(System.in);
                i = s.nextInt();
                Coord legalMove = legalMoves.get(i);
                board.movePiece(piece, piece.getPosition().add(legalMove));
                System.out.println("Piece moved, resulting board: ");
            }
        }
    }
}
