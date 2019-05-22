package Jaume;

import java.util.ArrayList;
import java.util.Scanner;


public class BoardDriver {

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    private static boolean isValidPiece(String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;

        Coord position = new Coord(realCoord);
        if (board.getPieceInCoord(position) == null) {
            System.out.println("There is no piece in those coordinates");
            return false;
        }
        if (board.getPieceInCoord(position).getLegalMoves(board).isEmpty()) {
            System.out.println("This piece doesn't have legal moves.");
            return false;
        }
        return true;
    }

    private static boolean isValidMove(Piece piece, String realCoord, Board board) {
        if(realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9')
            return false;
        Coord position = new Coord(realCoord);
        ArrayList<Coord> legalMoves = piece.getLegalMoves(board);
        for (Coord legalMove : legalMoves) {
            if (piece.getPosition().add(legalMove).equals(position))
                return true;
        }
        System.out.println("Not a legal move");
        return false;
    }


    private static void boardOptions(Board board) {
        int n = 0;
        while (n != 3) {
            System.out.println("Board Menu:");
            System.out.println("1. Move a piece from the board");
            System.out.println("2. Print its resulting FEN notation");
            System.out.println("3. Exit");
            System.out.print("Select one of the options: ");
            Scanner s = new Scanner(System.in);
            System.out.println();
            n = s.nextInt();
            if (n == 1) {
                System.out.print("Please, select the coordinates of the piece you want to move (for example: e8): ");
                s = new Scanner(System.in);
                String initialPos = s.next();
                while (!isValidPiece(initialPos, board)) {
                    System.out.print("Please, select valid coordinates: ");
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
                    System.out.print("Please, select valid coordinates: ");
                    s = new Scanner(System.in);
                    movingPos = s.next();
                }
                movingCoord = new Coord(movingPos);
                board.movePiece(board.getPieceInCoord(initialCoord), movingCoord);
                System.out.println("Piece moved, resulting board:");
                board.printBoard();
                System.out.println();
            }
            else if (n == 2) {
                System.out.println("Resulting FEN notation: "+board.toFEN());
            }
            else if (n == 3) {
                System.out.println("Exiting Board Menu...");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Board Driver");
        int n = 0;
        while (n != 3) {
            System.out.println("Main Menu:");
            System.out.println("1. Create a Standard Board");
            System.out.println("2. Create a Board from FEN notation");
            System.out.println("3. Exit");
            System.out.print("Select one of the options: ");
            Scanner s = new Scanner(System.in);
            System.out.println();
            n = s.nextInt();
            if (n == 1) {
                Board board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w");
                System.out.println("Board created, resulting board:");
                board.printBoard();
                System.out.println();
                boardOptions(board);
            }
            else if (n == 2) {
                System.out.print("Insert a board in FEN notation: ");
                s = new Scanner(System.in);
                String[] FEN = s.nextLine().split(" ");
                String FENBoard, FENTurn;
                FENBoard = FEN[0];
                FENTurn = FEN[1];
                Board board = new Board(FENBoard);
                System.out.println("Board created, resulting board:");
                board.printBoard();
                System.out.println();
                boardOptions(board);
            }
            else if (n == 3) {
                System.out.println("Exiting the driver. Goodbye!");
            }
        }
    }
}
