package Pau;

import java.util.Scanner;

public class ProblemDriver {

    public static void main(String[] args) {
        int n = -1;
        while (n != 2) {
            System.out.println("Problem Driver");
            System.out.println();
            System.out.println("1. Create New Problem");
            System.out.println("2. Exit");
            System.out.println();
            System.out.print("Please, select one of the above options: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            switch(n) {
                case 1:
                    createProblem();
                    break;
            }
        }


    }

    private static void createProblem() {
        System.out.println("Please, insert its FEN (example: 1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B w - - 0 1): ");
        Scanner s = new Scanner(System.in);
        String FEN = s.nextLine();
        //System.out.println(FEN);
        Problem createdProblem = new Problem(FEN);
        System.out.println("Please, insert N: ");
        s = new Scanner(System.in);
        int N = s.nextInt();
        createdProblem.setN(N);
        System.out.println(createdProblem.validateProblem());

    }

}
