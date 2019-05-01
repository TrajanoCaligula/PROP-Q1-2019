package Main;

import java.util.Scanner;
import model.*;

public class Main {
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
                problemOptions();

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
        Problem.printProblems();
        System.out.println("Enter the id of problem you want to play: ");
        int idProb = scan.nextInt();


    }
}
