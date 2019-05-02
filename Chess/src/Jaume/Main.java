package Jaume;

import java.util.Scanner;
import Calin.*;
import Pau.*;

public class Main {/*
    private static void MenuOptions(){
        System.out.println("1 - Create a new match");
        System.out.println("2 - Problem Manager");
        System.out.println("3 - Ranking Manager");
        System.out.println("0 - Exit");
    }

    private static void problemOptions(){
        System.out.println("2.1 - Create a new problem");
        System.out.println("2.2 - Load a problem");
        System.out.println("2.3 - Clone a problem");
        System.out.println("2.4 - Modify a problem");
        System.out.println("2.5 - Delete a problem");
        System.out.println("2.6 - List problems");
        System.out.println("2.1 - Exit");
    }

    private static void rankingOptions(){
        System.out.println("3.1 - Clear a ranking");
        System.out.println("3.2 - Delete a ranking");
        System.out.println("3.3 - List rankings");
        System.out.println("3.0 - Exit");
    }



    public static void main(String[] args) {
        System.out.println("CHESS GAME");
        Scanner scan = new Scanner(System.in);
        int element = -1;
        while(element != 0) {
            MenuOptions();
            element = scan.nextInt();
            if(element == 1){

            }
            else if(element == 2){

            }
            else if(element == 3){
                rankingOptions();

            }
        }
    }


    public void CreateMatch(){
        System.out.println("Create a new match:");
        Scanner scan = new Scanner(System.in);
        int ContMachine = 0;

        Player P1, P2;
        P1 = P2 = null;
        Problem prob = null;
        Match match = null;

        Problem.printProblems();
        System.out.println("Enter the id of problem you want to play: ");
        int idProb = scan.nextInt();

        //Buscar el problema

        while(prob == null) {
            prob = Problem.loadProblem(idProb);
            if (prob == null) {
                System.out.println("Problem you entered doesn't exist, select a new one");
                int idProb = scan.nextInt();
            }
        }


        System.out.println("Select te type of Player number 1: ('M'- machine/'H'- human)");
        char c = scan.next().charAt(0);
        if(c == 'H'){
            System.out.println("Enter the name for the player: ");
            String name = scan.nextLine();
            P1 = new Human(name);
        }
        else {
            ContMachine++;
            P1 = new Machine("Machine1");
        }

        System.out.println("Select te type of Player number 2: ('M'- machine/'H'- human)");
        c = scan.next().charAt(0);
        if(c == 'H'){
            System.out.println("Enter the name for the player: ");
            String name = scan.nextLine();
            P1 = new Human(name);
        }
        else {
            ContMachine++;
            P1 = new Machine("Machine1");
        }



        if(ContMachine == 2) {
            System.out.println("Both players are machines. How many problems do you want to make?");
            int k = scan.nextInt();
            System.out.println("Set the depth of the algorithm of player 1: (Default 3)");
            int vic1, vic2, depth1, depth2;
            depth1 = scan.nextInt();
            System.out.println("Set the depth of the algorithm of player 2: (Default 3)");
            depth2 = scan.nextInt();
            vic1 = 0;
            vic2 = 0;
            for(int i = 0 ; i < k; i++){
                Match m = new Match(P1,P2,prob,depth1,depth2);
                int gam = m.Game();
                if (gam == 1) ++vic1;
                else if(gam == 2) ++vic2;
                else System.out.println("Error in game");



                Problem.printProblems();
                System.out.println("Enter the id of problem you want to play: ");
                int idProb = scan.nextInt();

                //Buscar el problema

                while(prob == null) {
                    prob = Problem.loadProblem(idProb);
                    if (prob == null) {
                        System.out.println("Problem you entered doesn't exist, select a new one");
                        int idProb = scan.nextInt();
                    }
                }
            }
        }

        else if(ContMachine == 1){
            System.out.println("Set the depth of the algorithm of machine player: (Default 3)");
            int depth = scan.nextInt();
            if(P1 instanceof Machine){
                Match m = new Match(P1,P2,prob,depth,3);
                m.Game();
            }
            else{
                Match m = new Match(P1,P2,prob,3,depth);
                m.Game();
            }
        }

        else{
            Match m = new Match(P1,P2,prob,0,0);
            m.Game();
        }



    }

    public void ProblemManeger(){
        System.out.println("Problem Manager:");
        Scanner scan = new Scanner(System.in);
        double option = scan.nextDouble();
        while(option != 0.1) {
            Problem.printProblems();
            if(option ==  2.1) {
                System.out.println("To create a poblem first introduce the FEN:");
                String strFEN = scan.nextLine();
                Problem problemCreador = new Problem(strFEN);

                System.out.println("Now insert the difficulty:");
                String strDiff = scan.next();
                problemCreador.setDifficulty(strDiff);

                System.out.println("Insert the N, being N the max number of turns:");
                int intN = scan.nextInt();
                problemCreador.setN(intN);

                System.out.println("Problem created!");
                problemCreador.saveProblem();
                System.out.println(problemCreador);
            }
            else if(option == 2.2){
                System.out.println("Insert the id of the problem you want to load:");
                int idProblem = scan.nextInt();
                System.out.println(Problem.loadProblem(idProblem));
            }
            else if(option == 2.3){
                System.out.println("Insert the id of the problem u want to clone:");
                int id = scan.nextInt();
                Problem cloneProblem = Problem.cloneProblem(id);
                cloneProblem.saveProblem();
                System.out.println("Original:" + Problem.loadProblem(id));
                System.out.println("Clone:   " + cloneProblem);

            }
            /*else if(option == 2.4){
                System.out.println("2 - Introduce the id of the problem you want to modify:");
                int idProblem = scan.nextInt();
                Problem problemToModify = Problem.loadProblem(idProblem);
                Board board = new Board(problemToModify.getFen());
                board.printBoard();

                Scanner movements = new Scanner(System.in);
                int x, y;
                x = movements.nextInt();
                y = movements.nextInt();
                while(x != -1 && y != -1) {
                    Tile ini = boardToModify.GetTile(x, y);
                    x = movements.nextInt();
                    y = movements.nextInt();
                    Tile fi = boardToModify.GetTile(x, y);
                    problemToModify.moveInFEN(ini, fi);
                    boardToModify = Board.fenToBoard(problemToModify.getFen());
                    boardToModify.PrintBoard();
                }
            }*/
/*
            else if(option ==  2.5) {
                System.out.println("Insert the id of the problem you want to delete:");
                int idProblem = scan.nextInt();
                if(!Problem.deleteProblem(idProblem)){
                    System.out.println("The problem you want to delete doesn't exists in our DB");
                }
            }
            else if(option ==  2.6) {
                Problem.printProblems();
            }
            problemOptions();
            option = scan.nextDouble();
        }
    }

    public void RankingManeger(){
        System.out.println("Problem Manager:");
        Scanner scan = new Scanner(System.in);

        rankingOptions();
        Problem.printProblems();
        double option = scan.nextDouble();
        while(option  != 0) {
            if(option == 3.1){
                System.out.println("Write the id of the problem which ranking you want to clear/reset:");
                int idPR = scan.nextInt();
                Ranking.resetRanking(idPR);

            } else if(option == 3.2){
                System.out.println("Write the id of the problem which ranking you want to delete:");
                int idPR = scan.nextInt();
                Ranking.deleteRanking(idPR);

            } else if(option == 3.3) {
                Ranking.printRankings();
            }
            rankingOptions();
            option = scan.nextInt();
        }
    }*/
}
