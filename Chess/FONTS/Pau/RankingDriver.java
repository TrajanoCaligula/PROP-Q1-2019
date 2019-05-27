/**
 * @author Pau Charques Rius
 */

package Pau;

import Jaume.*;
import java.util.Scanner;

public class RankingDriver {

    public static void main(String[] args) {
        System.out.println("Ranking Driver:");
        Scanner input = new Scanner(System.in);
        Ranking rankingTest = null;

        displayOptions();
        int option = input.nextInt();

        while(option  != 0) {
            if(option == 1) {
                System.out.println("Insert the id of the problem you want to create a ranking of:");
                int idProblem = input.nextInt();
                rankingTest = new Ranking(idProblem);
            } else if(option == 2){
                System.out.println("Insert the id of the problem you want to add a score to:");
                int idProblemR = input.nextInt();

                Ranking rankingScoreTest = new Ranking(idProblemR);
                System.out.println("Insert the player who did the score");
                String player = input.next();
                System.out.println("Insert the score");
                int score = input.nextInt();
                Score scoreToAdd = new Score(player, score);
                System.out.println("Score to add: " + scoreToAdd);
                rankingScoreTest.addScore(scoreToAdd);
                System.out.println("Score Added");

            } else if(option == 3){
                System.out.println("Write the id of the problem which ranking you want to clear/reset:");
                int idPR = input.nextInt();
                Ranking.resetRanking(idPR);
            } else if(option == 4){
                System.out.println("Write the id of the problem which ranking you want to delete:");
                int idPR = input.nextInt();
                Ranking.deleteRanking(idPR);

            } else if(option == 5) {
                Ranking.printRankings();
            }
            displayOptions();
            option = input.nextInt();
        }
    }

    private static void displayOptions(){
        System.out.println("1 - Create a new ranking");
        System.out.println("2 - Add score to a ranking");
        System.out.println("3 - Clear a ranking");
        System.out.println("4 - Delete a ranking");
        System.out.println("5 - List rankings");
        System.out.println("0 - Exit");
    }


}
