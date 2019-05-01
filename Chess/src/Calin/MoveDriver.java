package drivers;

import model.*;
import java.util.Scanner;

public class MoveDriver {

    private static void moveMenu() {
        System.out.println();
        System.out.println("Move creator");
        System.out.println();
        System.out.print("Please, select the initial x coordinate from the piece (from 0 to 7): ");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        while (x < 0 || x > 7) {
            System.out.println();
            System.out.print("Wrong x coordinate, please select a valid x coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            x = s.nextInt();
        }
        System.out.println();
        System.out.print("Now, select the initial y coordinate from the piece (from 0 to 7): ");
        s = new Scanner(System.in);
        int y = s.nextInt();
        while (y < 0 || y > 7) {
            System.out.println();
            System.out.print("Wrong y coordinate, please select a valid y coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            y = s.nextInt();
        }
        System.out.println();

        Coord initialPos = new Coord(x, y);
        Piece piece = new Pawn(initialPos, Color.WHITE);

        System.out.print("Select the moving x coordinate (from 0 to 7): ");
        s = new Scanner(System.in);
        x = s.nextInt();
        while (x < 0 || x > 7) {
            System.out.println();
            System.out.print("Wrong x coordinate, please select a valid x coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            x = s.nextInt();
        }
        System.out.println();
        System.out.print("Finally, select the moving y coordinate (from 0 to 7): ");
        s = new Scanner(System.in);
        y = s.nextInt();
        while (y < 0 || y > 7) {
            System.out.println();
            System.out.print("Wrong y coordinate, please select a valid y coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            y = s.nextInt();
        }
        System.out.println();
        Coord movingCoord = new Coord(x, y);
        Move move = new Move(piece, movingCoord);

        System.out.println("New Move successfully created, it has an imaginary Piece (in this case a "
                +move.getPiece().toString()+") that moves "+"from ("+move.getPiece().getPosition().getX()+
                ", "+move.getPiece().getPosition().getY()+") to ("+move.getFinalPos().getX()+", "
                +move.getFinalPos().getY()+")");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Move Driver Menu");
        System.out.println();
        int n = 0;
        while (n != 2) {
            System.out.println("1. Create a new Move");
            System.out.println("2. Exit");
            System.out.println();
            System.out.print("Please, select one option from the above: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1)
                moveMenu();
            else if (n == 2) {
                System.out.println();
                System.out.println("Exiting Move Driver. Goodbye!");
            }
        }
    }
}
