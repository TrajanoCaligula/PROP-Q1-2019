package Calin;

import Jaume.Color;
import Pau.Match;
import Pau.Problem;
import Pau.Ranking;
import Pau.Score;

import java.io.File;
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
            System.out.println("2. New Game (Machine vs Machine)");
            System.out.println("3. Create New Player");
            System.out.println("4. Load Existing Player");
            System.out.println("5. Delete Existing Player");
            System.out.println("6. List Rankings");
            System.out.println("7. Create New Problem");
            System.out.println("8. Delete Existing Problem");
            System.out.println("9. List Problems");
            System.out.println("10. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {   //New Game
                if (humanName.isEmpty()) {
                    System.out.println();
                    System.out.println("No Player selected! Select one by choosing the 4 option");
                }
                else
                    gameMenu(humanName);
            }
            else if (n == 2)
                machineGameMenu();
            else if (n == 3)
                createNewPlayer(humanName);
            else if (n == 4) {
                ArrayList<String> players = getPlayers();
                if (players.isEmpty()) {
                    System.out.println("There are no players in the database! Create a new one by choosing the 3 option");
                    System.out.println();
                }
                else {
                    if (!humanName.isEmpty()) {
                        System.out.println("Caution! Your selected Player is going to be changed for the next one!");
                        System.out.println();
                    }
                    for (int i = 0; i < players.size(); i++)
                        System.out.println(i + ". " + players.get(i));
                    System.out.println();
                    System.out.print("Select one from the above (from 0 to " + (players.size() - 1) + "): ");
                    int i;
                    s = new Scanner(System.in);
                    i = s.nextInt();
                    while (i < 0 || i >= players.size()) {
                        System.out.print("That's a wrong selection, please select a valid one: ");
                        s = new Scanner(System.in);
                        i = s.nextInt();
                    }
                    humanName = players.get(i);
                    System.out.println("Player successfully selected!");
                }
            }
            else if (n == 5)
                deleteExistingPlayer(humanName);
            else if (n == 6) {
                System.out.println();
                printRankings();
            }
            else if (n == 7)//Create New Problem
                createProblem();
            else if (n == 8)//Delete Existing Problem
                deleteProblem();
            else if (n == 9)
                printProblems();
            else if (n == 10)    //Exit
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

    private static void deleteExistingPlayer(String humanName) {
        boolean sure;
        String aux = humanName;
        ArrayList<String> players = getPlayers();
        if (players.isEmpty()) {
            System.out.println("There are no players in the database! Please create one by choosing the 3 option");
        }
        else {
            System.out.print("Please, insert the name of the Player to delete: ");
            Scanner s = new Scanner(System.in);
            humanName = s.next();
            while (!exists(humanName)) {
                System.out.print("That player doesn't exist, please select another one: ");
                s = new Scanner((System.in));
                humanName = s.next();
            }
            System.out.print("Are you sure you want to delete " + humanName + "? [Y/N]: ");
            s = new Scanner(System.in);
            String confirmation = s.next();
            while (!confirmation.equals("Y") && !confirmation.equals("N")) {
                System.out.print("Thats not a valid response, please insert Y or N: ");
                s = new Scanner(System.in);
                confirmation = s.next();
            }
            if (confirmation.equals("Y"))
                sure = true;
            else
                sure = false;
            if (sure) {
                System.out.println("Deleting " + humanName + "...");
                deletePlayer(humanName);
            }
            if (aux.isEmpty())
                humanName = new String();
            else {
                if (humanName.equals(aux))
                    humanName = new String();
                else
                    humanName = aux;
            }
        }
    }

    private static void loadPlayer(String humanName) {

    }

    private static void createNewPlayer(String humanName) {
        System.out.print("Please, insert the new Player's name: ");
        Scanner s = new Scanner(System.in);
        String aux = humanName;
        humanName = s.next();
        while (exists(humanName)) {
            System.out.print("This player already exists, please select another name: ");
            s = new Scanner(System.in);
            humanName = s.next();
        }
        System.out.println("New Player successfully created! Saving to the database...");
        createPlayer(humanName);
        if (!aux.isEmpty())
            humanName = aux;
        else
            humanName = new String();
    }

    private static ArrayList<String> getProblems() {
        ArrayList<String> problems = new ArrayList<String>();
        File[] files = new File("./Chess/src/DataBase/Problems/").listFiles();
        for(File file : files){
            if(file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                problems.add(fileName[0]);
            }
        }
        return problems;
    }

    private static void deletePlayer(String humanName) {
        File[] files = new File("./Chess/src/DataBase/Players/").listFiles();
        for(File file : files) {
            if (file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                if (fileName[0].equals(humanName)) {
                    file.delete();
                }
            }
        }
    }

    private static boolean exists(String humanName) {
        ArrayList<String> players = getPlayers();
        for (String player : players) {
            if (humanName.equals(player))
                return true;
        }
        return false;
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
                printProblems();
                System.out.println("Insert the id of the problem you want to load:");
                s = new Scanner(System.in);
                int idProblem = s.nextInt();
                p = Problem.loadProblem(idProblem);
                if (p == null)
                    System.out.println("Error loading the Problem!");
                else {
                    whitePlayer = new Machine("whiteMachine", Color.WHITE, 2);
                    blackPlayer = new Machine("blackMachine", Color.BLACK, 2);
                }
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
                loadPlayer(secondHumanName);
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

    public static ArrayList<String> getPlayers(){
        ArrayList<String> players = new ArrayList<String>();
        File[] files = new File("./Chess/src/DataBase/Players/").listFiles();
        for(File file : files){
            if(file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                players.add(fileName[0]);
            }
        }
        return players;
    }

    private static void createPlayer(String humanName) {
        File humanFile = new File("./Chess/src/DataBase/Players/" + humanName + ".txt");
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
