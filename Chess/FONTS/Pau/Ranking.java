/**
 * @author Pau Charques Rius
 */
package Pau;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.*;

public class Ranking{
	private int idProblemRanking;
	private ArrayList<Score> scoresRanking = new ArrayList<Score>();


	/**Constructor*/

	/**
	 * The constructor is based on file management, that means, when we create a ranking of a problem we check if a ranking of that problem already exists
	 * if it does, it loads the ranking of that file and created the object.
	 * The second restriction we check is if the problem we are creating a ranking of exists in our DB.
	 * @param idProblem id of the problem we want to create or load from our DB.
	 * @throws IllegalArgumentException This exception its thrown whenever we create a ranking of an inexisting problem
	 */
	public Ranking(int idProblem) throws IllegalArgumentException {
		this.idProblemRanking = idProblem;
	}

	/**Getters*/

	/**
	 * Get the id of the ranking
	 * @return the identifier of the ranking
	 */
	public int getID(){
		return idProblemRanking;
	}

	/**Setters*/
	/**
	 * Updates our private variable scores of the ranking so we can order it in > order then if we want to save it in our file
	 * we call the save function later implement it. It's easier this way than ordering directly in to the file.
	 * @param scoresRankingP
	 */
	private void setScores(ArrayList<Score> scoresRankingP){
		this.scoresRanking = scoresRankingP;
	}

	/**
	 * We add the parametre (@param scoreToAdd) to our attribut scores, we sort it in major-minor order and then update the ranking file.
	 * @param scoreToAdd Score that's going to be added to our ranking.
	 */
	public Score addScore(Score scoreToAdd){
		if(scoresRanking.size() < 5) {
			scoresRanking.add(scoreToAdd);
			return scoreToAdd;
		}
		Collections.sort(scoresRanking);
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String newLine = "R- + " + this.idProblemRanking;
		builder.append(newLine);
		newLine = "-------------";
		builder.append(newLine);

		for(int i = 0; i < this.scoresRanking.size(); i++) {
			builder.append(this.scoresRanking.get(i));
		}
		return builder.toString();
	}
}