package drivers;

import board.Coord;

import java.util.Scanner;

public class CoordDriver {

    private static void coordXYMenu() {
        System.out.println();
        System.out.print("Please, insert the x coordinate (from 0 to 7): ");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        while (x < 0 || x > 7) {
            System.out.println();
            System.out.print("Wrong x coordinate, please select a valid x coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            x = s.nextInt();
        }
        System.out.print("Now, insert the y coordinate: ");
        s = new Scanner(System.in);
        int y = s.nextInt();
        while (y < 0 || y > 7) {
            System.out.println();
            System.out.print("Wrong y coordinate, please select a valid y coordinate (from 0 to 7): ");
            s = new Scanner(System.in);
            y = s.nextInt();
        }
        Coord coordinate = new Coord(x, y);
        System.out.println();
        System.out.println("New Coord successfully created, its x coordinate is "+coordinate.getX()+" and its y coordinate is "+coordinate.getY());
        System.out.println("Its real board counterpart is "+coordinate.toRealCoord());
        System.out.println();
    }

    private static void realCoordMenu() {
        System.out.println();
        System.out.print("Please, insert the real board coordinate you desire (from a to h and from 1 to 8, for example: e8): ");
        Scanner s = new Scanner(System.in);
        String realCoord = s.next();
        while (realCoord.length() != 2 || realCoord.charAt(0) < 'a' || realCoord.charAt(0) > 'h' || realCoord.charAt(1) < '1' || realCoord.charAt(1) > '8') {
            System.out.println();
            System.out.print("Wrong coordinates, please insert a valid real coordinate (from a to h and from 1 to 8, for example: e8): ");
            s = new Scanner(System.in);
            realCoord = s.next();
        }
        Coord coordinate = new Coord(realCoord);
        System.out.println("New coordinate based on real board coordinates created, resulting coordinate: x = "+coordinate.getX()+", y = "+coordinate.getY());
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 0;
        System.out.println("Coord Driver");
        System.out.println();
        while (n != 3) {
            System.out.println("1. Create a Coord with x, y coordinates");
            System.out.println("2. Create a Coord with real board coordinates");
            System.out.println("3. Exit");
            System.out.print("Please, select an option from the above: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1)
                coordXYMenu();
            else if (n == 2)
                realCoordMenu();
            else {
                System.out.println();
                System.out.println("Exiting Coord Driver. Goodbye!");
            }
        }
    }
}
