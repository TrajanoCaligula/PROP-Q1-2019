package Calin;

import Pau.Problem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Pau.Ranking.printRankings;

public class Main {

    public static void main(String[] args) {

        int n = 0;
        String humanName = new String();

        System.out.println("Chess Game");
        while (n != 9) {
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
            System.out.println("9. Exit");
            System.out.println();
            System.out.print("Please, select an option from the above: ");
            Scanner s = new Scanner(System.in);
            n = s.nextInt();
            if (n == 1) {   //New Game
                if (humanName.isEmpty()) {
                    System.out.println();
                    System.out.println("No Player selected! Select or create a new one by choosing the 4 option");
                }
                else
                    gameMenu(humanName);
            }
            else if (n == 2) {  //Machine vs Machine
                machineGameMenu();
            }
            else if (n == 3) {  //Create New Player
                createNewPlayer(humanName);
            }
            else if (n == 4) {  //Load Player
                loadPlayer(humanName);

            }
            else if (n == 5) {  //Delete Existing Player
                deleteExistingPlayer(humanName);
            }
            else if (n == 6) {  //List Rankings
                System.out.println();
                printRankings();

            }
            else if (n == 7) {  //Create New Problem
                createProblem();
            }
            else if (n == 8) {  //Delete Existing Problem
                deleteProblem();
            }
            else if (n == 9) {  //Exit
                System.out.println("Exiting the game. Goodbye!");
            }
        }
    }

    private static void deleteProblem() {

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
        ArrayList<String> players = getPlayers();
        if (players.isEmpty()) {
            System.out.println("There are no players in the database! Create a new one by selecting the 2 option");
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
            System.out.print("Select one from the above: ");
            int i;
            Scanner s = new Scanner(System.in);
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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(humanFile));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private static void gameMenu(String humanName) {

    }

}
