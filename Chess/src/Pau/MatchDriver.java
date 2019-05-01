package Pau;

import Jaume.*;
import Calin.*;

import java.io.IOException;
import java.util.Scanner;

public class MatchDriver {

    private static void matchMenu(Match match) {
        int n = 0;
        Color color = match.getFirstColor();
        while (n != 2) {
            System.out.println("1. Play next round");
            System.out.println("2. Exit");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {
                match.getBoard().printBoard();
                System.out.println();
                if (color == Color.WHITE) {
                    System.out.println("White Player's turn");
                    match.playGame(color);
                    color = Color.BLACK;
                }

                else {
                    System.out.println("Black Player's turn");
                    match.playGame(color);
                    color = Color.WHITE;
                }
                System.out.println("Round "+match.getRound()+" successfully played");
                System.out.println("Resulting board:");
                match.getBoard().printBoard();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Match Driver");
        int n = 0;
        while (n != 2) {
            System.out.println("Main Menu:");
            System.out.println("1. Create a new Match");
            System.out.println("2. Exit");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {
                Player whitePlayer;
                Player blackPlayer;
                System.out.println("What should the white player be? (insert Human or Machine): ");
                s = new Scanner(System.in);
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
                n = 0;
                Board board = new Board();
                int round = 1;
                Color firstColor = null;
                while (n != 2 && n != 1) {
                    System.out.println("Board Menu:");
                    System.out.println("1. Create a Standard Board");
                    System.out.println("2. Create a Board from FEN notation");
                    s = new Scanner(System.in);
                    n = s.nextInt();
                    if (n == 1) {
                        board = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w");
                        System.out.println("Standard Board created, white player moves first");
                        firstColor = Color.WHITE;
                    }
                    else if (n == 2) {
                        System.out.print("Insert a board in FEN notation: ");
                        s = new Scanner(System.in);
                        String[] FEN = s.nextLine().split(" ");
                        String FENBoard, FENTurn;
                        FENBoard = FEN[0];
                        FENTurn = FEN[1];
                        board = new Board(FENBoard);
                        System.out.print("Board from FEN notation created, ");
                        if (FENTurn.equals("w")) {
                            firstColor = Color.WHITE;
                            System.out.println("white player moves first");
                        }
                        else {
                            firstColor = Color.BLACK;
                            System.out.println("black player moves first");
                        }
                    }
                }
                Match match = new Match(whitePlayer, blackPlayer, board, round, firstColor);
                System.out.println("Match created successfully");
                matchMenu(match);
            }
        }
        System.out.println("Exiting Match Driver. Goodbye!");
    }
}
