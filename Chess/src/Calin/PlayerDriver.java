package drivers;

import model.*;
import java.util.Scanner;

public class PlayerDriver {

    private static void machineMenu() {
        System.out.println();
        System.out.println("Machine Menu");
        System.out.println();
        System.out.print("Please, insert the new Machine's name: ");
        Scanner s = new Scanner(System.in);
        String name = s.next();
        System.out.println();
        System.out.print("Now, insert the Machine's depth: ");
        s = new Scanner(System.in);
        int depth = s.nextInt();
        System.out.println();
        System.out.print("Finally, insert the Machine's color (white or black): ");
        s = new Scanner(System.in);
        String stringColor = s.next();
        while (!stringColor.equals("white") && !stringColor.equals("black")) {
            System.out.println();
            System.out.print("That's not a valid color, please select a color (white or black): ");
            s = new Scanner(System.in);
            stringColor = s.next();
        }
        Color color;
        if (stringColor.equals("white"))
            color = Color.WHITE;
        else
            color = Color.BLACK;
        Machine machine = new Machine(name, color, depth);
        System.out.println();
        System.out.println("Machine successfully created, its name is "+machine.getName()+", its depth is "+machine.getDepth()+" and its color is "+machine.getColor().toString());
        System.out.println();
    }

    private static void humanMenu() {
        System.out.println();
        System.out.println("Human Menu");
        System.out.println();
        System.out.print("Please, insert the new Human's name: ");
        Scanner s = new Scanner(System.in);
        String name = s.next();
        System.out.println();
        System.out.print("Now, insert the Human's color (white or black): ");
        s = new Scanner(System.in);
        String stringColor = s.next();
        while (!stringColor.equals("white") && !stringColor.equals("black")) {
            System.out.println();
            System.out.print("That's not a valid color, please select a color (white or black): ");
            s = new Scanner(System.in);
            stringColor = s.next();
        }
        Color color;
        if (stringColor.equals("white"))
            color = Color.WHITE;
        else
            color = Color.BLACK;
        Human human = new Human(name, color);
        System.out.println();
        System.out.println("Human successfully created, its name is "+human.getName()+" and its color is "+human.getColor().toString());
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Player Driver");
        System.out.println();
        int n = 0;
        Scanner s;
        while (n != 3) {
            System.out.println("1. Create Human Player");
            System.out.println("2. Create Machine Player");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1)
                humanMenu();
            else if (n == 2)
                machineMenu();
            else if (n == 3) {
                System.out.println();
                System.out.println("Exiting PlayerDriver. Goodbye!");
            }
        }
    }
}
