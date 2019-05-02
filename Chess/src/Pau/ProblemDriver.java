/**
 * @author Pau Charques Rius
 */

package Pau;

import java.util.Scanner;
import Jaume.*;
public class ProblemDriver {

    public static void main(String[] args) {
        System.out.println("Problem Driver");
        Scanner input = new Scanner(System.in);

        displayOptions();
        int option = input.nextInt();
        while(option != 0) {
            Problem.printProblems();
            if(option ==  1) {
                System.out.println("To create a problem first introduce the FEN:");
                Scanner in = new Scanner(System.in);
                String strFEN = in.nextLine();
                Problem problemCreador = new Problem(strFEN);

                System.out.println("Now insert the difficulty: (Mat en N, being N an int)");
                String strDiff = in.nextLine();
                System.out.println(strDiff);
                problemCreador.setDifficulty(strDiff);

                System.out.println("Problem created!");
                problemCreador.saveProblem();
                System.out.println(problemCreador);
            }
            else if(option == 2){
                System.out.println("Insert the id of the problem you want to load:");
                int idProblem = input.nextInt();
                System.out.println(Problem.loadProblem(idProblem));
            }
            else if(option == 3){
                System.out.println("Insert the id of the problem u want to clone:");
                int id = input.nextInt();
                Problem cloneProblem = Problem.cloneProblem(id);
                cloneProblem.saveProblem();
                System.out.println("Original:" + Problem.loadProblem(id));
                System.out.println("Clone:   " + cloneProblem);

            }
            else if(option == 4){
                System.out.println("2 - Introduce the id of the problem you want to modify:");
                int idProblem = input.nextInt();
                Problem problemToModify = Problem.loadProblem(idProblem);
                problemToModify.movePiece();
            }

            else if(option ==  5) {
                System.out.println("Insert the id of the problem you want to delete:");
                int idProblem = input.nextInt();
                if(!Problem.deleteProblem(idProblem)){
                    System.out.println("The problem you want to delete doesn't exists in our DB");
                }
            }
            else if(option ==  6) {
                Problem.printProblems();
            }
            displayOptions();
            option = input.nextInt();
        }
    }
    private static void displayOptions(){
        System.out.println("1 - Create a new problem");
        System.out.println("2 - Load a problem");
        System.out.println("3 - Clone a problem");
        System.out.println("4 - Modify a problem");
        System.out.println("5 - Delete a problem");
        System.out.println("6 - List problems");
        System.out.println("0 - Exit");
    }

}
