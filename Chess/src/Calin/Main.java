package Calin;

import Jaume.Color;
import Pau.Match;
import Pau.Problem;
import Pau.Ranking;
import Pau.Score;

import java.io.File;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Scanner;

import static Pau.Problem.printProblems;
import static Pau.Ranking.printRankings;

public class Main {
    public static void main(String[] args) {
        int n = 0;
        String humanName = new String();

        System.out.println("Chess Game");

        while (n != 10) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println();
            System.out.println("1. New Game");
            System.out.println("2. New Game (Machine2 vs Machine)");
            System.out.println("3. Insert player");
            System.out.println("4. List Rankings");
            System.out.println("5. Create New Problem");
            System.out.println("6. Delete Existing Problem");
            System.out.println("7. List Problems");
            System.out.println("8. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {   //New Game
                if (humanName.isEmpty()) {
                    System.out.println();
                    System.out.println("No Player selected! Select one by choosing the 4 option");
                } else
                    gameMenu(humanName);
            } else if (n == 2)
                machineGameMenu();
            else if (n == 3) {
                System.out.println("Name of the player:");
                Scanner in = new Scanner(System.in);
                humanName = in.nextLine();
            }
            else if (n == 4) {
                System.out.println();
                printRankings();
            }
            else if (n == 5)//Create New Problem
                createProblem();
            else if (n == 6)//Delete Existing Problem
                deleteProblem();
            else if (n == 7)
                printProblems();
            else if (n == 8)    //Exit
                System.out.println("Exiting the game. Goodbye!");
        }
    }

    private static void deleteProblem() {
        System.out.println("Insert the id of the problem you want to delete:");
        Scanner s = new Scanner(System.in);
        int idProblem = s.nextInt();
        if(!Problem.deleteProblem(idProblem)){
            System.out.println("The problem you want to delete doesn't exists in our DB");
        }

    }

    private static void createProblem() {
        System.out.println("To create a problem first introduce the FEN:");
        Scanner in = new Scanner(System.in);
        String strFEN = in.nextLine();
        Problem problemCreator = new Problem(strFEN);

        System.out.println("Now insert the difficulty: (Mat en N, being N an int)");
        String strDiff = in.nextLine();
        System.out.println(strDiff);
        problemCreator.setDifficulty(strDiff);

        System.out.println("Problem created!");
        problemCreator.saveProblem();
        System.out.println(problemCreator);
    }


    private static ArrayList<String> getProblems() {
        ArrayList<String> problemList = new ArrayList<>();
        File[] files = new File("../").listFiles();
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                Problem probToAdd = new Problem(file);
                String[] splitted2 = splitted[1].split("\\.");
                problemList.add(splitted2[0]);
            }
        }
        return problemList;
    }

    private static void machineGameMenu() {

        int n = 0;
        Scanner s;
        Problem p = null;
        Player whitePlayer = null;
        Player blackPlayer = null;
        while (n != 3) {

            System.out.println("Game Menu");
            System.out.println();
            System.out.println("1. Load Problem");
            System.out.println("2. Play");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            s = new Scanner(System.in);
            n = s.nextInt();

            if (n == 1) {
                if (printProblems()) {
                    System.out.println("Insert the id of the problem you want to load:");
                    s = new Scanner(System.in);
                    int idProblem = s.nextInt();
                    p = Problem.loadProblem(idProblem);
                    if (p == null)
                        System.out.println("Error loading the Problem!");
                    else {
                        whitePlayer = new Machine2("whiteMachine", Color.WHITE, 4);
                        blackPlayer = new Machine("blackMachine", Color.BLACK, 2);
                    }
                }
                else
                    return;
            }
            else if (n == 2) {
                if (p == null) {
                    System.out.println("Problem not loaded! Can't play a game without a loaded problem!");
                }
                else {
                    Match match = new Match(whitePlayer, blackPlayer, p, 1, p.getFirstPlayer());
                    matchMenu(match);
                    p = null;
                }
            }
            else if (n == 3) {
                System.out.println("Exiting Game Menu...");
            }
        }

    }

    private static void gameMenu(String humanName) {

        int n = 0;
        Scanner s;
        Problem p = null;
        String secondHumanName = new String();
        Player whitePlayer = null;
        Player blackPlayer = null;
        while (n != 4) {

            System.out.println("Game Menu");
            System.out.println();
            System.out.println("1. Load Problem");
            System.out.println("2. Second Player Human");
            System.out.println("3. Play");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            s = new Scanner(System.in);
            n = s.nextInt();

            if (n == 1) {
                printProblems();
                System.out.println("Insert the id of the problem you want to load:");
                s = new Scanner(System.in);
                int idProblem = s.nextInt();
                p = Problem.loadProblem(idProblem);
                if (p == null)
                    System.out.println("Error loading the Problem!");
                else {
                    if (p.getFirstPlayer() == Color.WHITE) {
                        whitePlayer = new Human(humanName, Color.WHITE);
                        if (!secondHumanName.isEmpty())
                            blackPlayer = new Human(secondHumanName, Color.BLACK);
                        else
                            blackPlayer = new Machine("machine", Color.BLACK, 2);
                    }
                    else {
                        blackPlayer = new Human(humanName, Color.BLACK);
                        if (!secondHumanName.isEmpty())
                            whitePlayer = new Human(secondHumanName, Color.WHITE);
                        else
                            blackPlayer = new Machine("machine", Color.WHITE, 2);
                    }
                }
            }
            else if (n == 2) {
                if (p != null) {
                   System.out.println("Caution! The problem is already loaded! You won't play with another human unless you load the problem again!");
                }
                if (secondHumanName.equals(humanName)) {
                    System.out.println("You can't play with yourself!");
                    secondHumanName = new String();
                }
            }
            else if (n == 3) {
                if (p == null) {
                    System.out.println("Problem not loaded! Can't play a game without a loaded problem!");
                }
                else {
                    Match match = new Match(whitePlayer, blackPlayer, p, 1, p.getFirstPlayer());
                    matchMenu(match);
                    p = null;
                }
            }
            else if (n == 4) {
                System.out.println("Exiting Game Menu...");
            }
        }
    }


    private static void matchMenu(Match match) {
        int n = 0;
        Color color = match.getFirstColor();
        System.out.println("The Match has Started!");
        System.out.println();
        while (n != 2 && !match.checkmated(Color.WHITE) && !match.checkmated(Color.BLACK) && match.getN() > match.getRound()/2) {
            System.out.println("1. Play next round");
            System.out.println("2. Exit");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {
                match.getBoard().printBoard();
                System.out.println();
                System.out.println("Move " + (match.getRound()/2 + 1) + " out of " + match.getN());
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
                System.out.println("Round "+(match.getRound())+" successfully played");
                System.out.println("Resulting board:");
                match.getBoard().printBoard();
            }
        }
        boolean whiteCheckmated = match.checkmated(Color.WHITE), blackCheckmated = match.checkmated(Color.BLACK);
        if (match.getN() <= match.getRound()/2)
            System.out.println("Got out of moves!");
        System.out.print("The match is over, ");
        if (whiteCheckmated)
            System.out.println(match.getBlackPlayer().getName() + "wins!");
        else if (blackCheckmated)
            System.out.println(match.getWhitePlayer().getName() + "wins!");
        else if (match.getWhitePlayer() instanceof Machine && match.getBlackPlayer() instanceof Machine)
            System.out.println("nobody won");

        if ((match.getWhitePlayer() instanceof Human || match.getBlackPlayer() instanceof Human)) {
            if (match.checkmated(match.getMatchProblem().getFirstPlayer()) || match.getN() <= match.getRound()/2) {
                System.out.println("You lost! You won't be listed in the Ranking of this Problem");
            }
            else if (n != 2) {
                System.out.println("You won! You will be listed in the Ranking of this Problem");
                if (whiteCheckmated)
                    setRankingScore(match.getMatchProblem(), match.getBlackPlayer(), match.getBlackScore());
                else if (blackCheckmated)
                    setRankingScore(match.getMatchProblem(), match.getWhitePlayer(), match.getWhiteScore());
            }
        }
    }

    private static void setRankingScore(Problem problem, Player whitePlayer, int playerScore) {
        Score score = new Score(whitePlayer.getName(), playerScore);
        System.out.println("Insert the id of the problem you want to add a score to:");

        Ranking rankingScoreTest = new Ranking(problem.getId());
        rankingScoreTest.addScore(score);
    }

}
