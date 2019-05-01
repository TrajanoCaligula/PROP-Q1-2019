package drivers;

import board.Board;
import board.Coord;
import piece.Color;
import piece.*;

import java.util.Scanner;

public class PieceDriver {

    private static void pieceMenu(Piece piece, Board board) {
        int n = 0;
        Scanner s;
        String stringColor = new String();
        String realCoord = new String();
        Color color = null;
        while (n != 7) {
            System.out.println();
            System.out.println("Piece Creator Menu");
            System.out.println();
            System.out.println("1. Create a new Pawn");
            System.out.println("2. Create a new Rook");
            System.out.println("3. Create a new Knight");
            System.out.println("4. Create a new Bishop");
            System.out.println("5. Create a new Queen");
            System.out.println("6. Create a new King");
            System.out.println("7. Exit");
            System.out.println();
            System.out.print("Please, select one of the above options: ");
            s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the Pawn to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new Pawn(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("Pawn successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 2) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the Rook to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new Rook(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("Rook successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 3) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the Knight to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new Knight(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("Knight successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 4) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the Bishop to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new Bishop(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("Bishop successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 5) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the Queen to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new Queen(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("Queen successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 6) {
                board.printBoard();
                System.out.print("Please, select a real board coordinate to put the King to (for example, e8): ");
                s = new Scanner(System.in);
                realCoord = s.next();
                while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                    System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                    s = new Scanner(System.in);
                    realCoord = s.next();
                }
                Coord position = new Coord(realCoord);
                System.out.print("Now, select the piece's color (white or black): ");
                s  = new Scanner(System.in);
                stringColor = s.next();
                while (!stringColor.equals("white") && !stringColor.equals("black")) {
                    System.out.print("Not a valid color, please select a valid color: ");
                    s  = new Scanner(System.in);
                    stringColor = s.next();
                }
                if (stringColor.equals("white"))
                    color = Color.WHITE;
                else
                    color = Color.BLACK;
                piece = new King(position, color);
                if (color == Color.WHITE)
                    board.setWhitePiece(piece);
                else
                    board.setBlackPiece(piece);
                System.out.println("King successfully created, resulting piece: "+piece.toString());
                System.out.println("Resulting board:");
                board.printBoard();
            }
            else if (n == 7) {
                System.out.println();
                System.out.println("Exiting Piece Menu...");
                System.out.println();
            }
        }
    }

    private static void changeColor(Board board) {
        board.printBoard();
        if (board.getBlackPieces().isEmpty() && board.getWhitePieces().isEmpty()) {
            System.out.println("No pieces on the board!");
            System.out.println();
        }
        else {
            System.out.print("Please, select a real board coordinate to select a Piece (for example, e8): ");
            Scanner s = new Scanner(System.in);
            String realCoord = s.next();
            while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9' || board.getPieceInCoord(new Coord(realCoord)) == null) {
                System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                s = new Scanner(System.in);
                realCoord = s.next();
            }

            Coord position = new Coord(realCoord);

            Piece piece = board.getPieceInCoord(position);

            System.out.println();
            System.out.println("Piece's original color: "+piece.toString());
            System.out.println();
            if (piece.getColor() == Color.WHITE)
                piece.setColor(Color.BLACK);
            else
                piece.setColor(Color.WHITE);
            System.out.println("Piece's color successfully changed, resulting piece: "+piece.toString());
            System.out.println();
            System.out.println("Resulting board:");
            board.printBoard();
        }
    }

    private static void movePiece(Board board) {
        board.printBoard();
        if (board.getBlackPieces().isEmpty() && board.getWhitePieces().isEmpty()) {
            System.out.println("No pieces on the board!");
            System.out.println();
        }
        else {
            System.out.print("Please, select a real board coordinate to select a Piece (for example, e8): ");
            Scanner s = new Scanner(System.in);
            String realCoord = s.next();
            while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9' || board.getPieceInCoord(new Coord(realCoord)) == null) {
                System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                s = new Scanner(System.in);
                realCoord = s.next();
            }
            Piece piece = board.getPieceInCoord(new Coord(realCoord));
            System.out.println("You've selected the "+piece.toString()+" from "+piece.getPosition().toRealCoord());
            System.out.println();
            System.out.print("Now, select a real coordinate to move the piece to (doesn't have to be legal): ");
            s = new Scanner(System.in);
            realCoord = s.next();
            while (realCoord.length() != 2 || realCoord.charAt(0) > 'h' || realCoord.charAt(0) < 'a' || realCoord.charAt(1) < '0' || realCoord.charAt(1) > '9') {
                System.out.print("Not a valid board coordinate, please select a valid coordinate: ");
                s = new Scanner(System.in);
                realCoord = s.next();
            }
            Coord movePosition = new Coord(realCoord);
            board.movePiece(piece, movePosition);
            System.out.println("Piece moved successfully, resulting board:");
            board.printBoard();
        }

    }

    public static void main(String[] args) {
        Piece piece = new Pawn(new Coord(0, 0), Color.WHITE);
        System.out.println("Piece Driver");
        System.out.println();
        System.out.println("Creating a new empty board");
        System.out.println();
        Board board = new Board();
        int n = 0;
        while (n != 4) {
            System.out.println("Main Menu: ");
            System.out.println();
            System.out.println("1. Create a new Piece");
            System.out.println("2. Change the color of a Piece");
            System.out.println("3. Move a Piece (no legal moves required)");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Please, select one of the above options: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1)
                pieceMenu(piece, board);
            else if (n == 2) {
                changeColor(board);
            }
            else if (n == 3)
                movePiece(board);
            else if (n == 4) {
                System.out.println();
                System.out.println("Exiting Piece Driver. Goodbye!");
            }

        }
    }
}
