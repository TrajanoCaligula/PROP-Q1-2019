package drivers;

import board.Board;
import board.Coord;
import match.Match;
import piece.Color;
import piece.Piece;
import player.Human;
import player.Machine;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatchDriver {

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Player whitePlayer;
        Player blackPlayer;
        System.out.println("What should the white player be? (insert Human or Machine): ");
        Scanner s = new Scanner(System.in);
        String playerType = s.next();
        while(!playerType.equals("Human") && !playerType.equals("Machine")) {
            System.out.println("Wrong input, please insert Human or Machine): ");
            s = new Scanner(System.in);
            playerType = s.next();
        }
        System.out.println("Insert white player's name:");
        s = new Scanner(System.in);
        String name = s.next();
        if (playerType.equals("Human"))
            whitePlayer = new Human(name, Color.WHITE);
        else { //if (playerType.equals("Machine")
            System.out.println("Insert machine's depth: ");
            s = new Scanner(System.in);
            int depth = s.nextInt();
            whitePlayer = new Machine(name, Color.WHITE, depth);
        }

        System.out.println("What should the black player be? (insert Human or Machine): ");
        s = new Scanner(System.in);
        playerType = s.next();
        while(!playerType.equals("Human") && !playerType.equals("Machine")) {
            System.out.println("Wrong input, please insert Human or Machine): ");
            s = new Scanner(System.in);
            playerType = s.next();
        }
        System.out.println("Insert black player's name:");
        s = new Scanner(System.in);
        name = s.next();
        if (playerType.equals("Human"))
            blackPlayer = new Human(name, Color.BLACK);
        else { //if (playerType.equals("Machine")
            System.out.println("Insert machine's depth: ");
            s = new Scanner(System.in);
            int depth = s.nextInt();
            blackPlayer = new Machine(name, Color.BLACK, depth);
        }
        System.out.println("Insert a board in FEN notation: ");
        String[] FEN = new String[6];
        String FENBoard, FENTurn, FENCastling, FENCapture, FENCaptureN, FENRoundN;
        s = new Scanner(System.in);
        FEN = s.nextLine().split(" ");
        FENBoard = FEN[0];
        FENTurn = FEN[1];
        FENCastling = FEN[2];
        FENCapture = FEN[3];
        FENCaptureN = FEN[4];
        FENRoundN = FEN[5];
        Integer round = Integer.parseInt(FENRoundN);
        Board board = new Board(FENBoard);
        Match match = new Match(whitePlayer, blackPlayer, board, round);
        Color color;
        if (FENTurn.equals("w"))
            color = Color.WHITE;
        else
            color = Color.BLACK;
        match.getBoard().printBoard();
        while (!match.checkmated(Color.BLACK) && !match.checkmated(Color.WHITE)) {
            System.out.println("Round "+round);
            if (color == Color.WHITE)
                System.out.println("White's turn");
            else if (color == Color.BLACK)
                System.out.println("Black's turn");
            match.playGame(color);
            if (color == Color.WHITE) {
                System.out.println("Player " + match.getWhitePlayer().getName() + " has moved, resulting board:");
                color= Color.BLACK;
            }
            else if (color == Color.BLACK) {
                System.out.println("Player " + match.getBlackPlayer().getName() + " has moved, resulting board:");
                color = Color.WHITE;
            }
            match.getBoard().printBoard();
            round++;
            //promptEnterKey();
        }
    }
}
