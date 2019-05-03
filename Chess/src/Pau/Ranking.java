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
	private File rankingFile;


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
		try {
			if(Problem.existsProblem(idProblem)) {
				rankingFile = new File("../" + "R-" + this.idProblemRanking + ".txt");
				if (!rankingFile.createNewFile()) {
					ArrayList<Score> scoresRankingLD = loadScores(rankingFile);
					setScores(scoresRankingLD);
				}
			} else {
				throw new IllegalArgumentException("The problem of which you want to create a ranking is not in our DB!");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**Getters*/
	/**
	 * Get file name
	 * @return Returns the name of the file of our Ranking
	 */
	public String getFileName(){
		return this.rankingFile.getName();
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
	 *This function loads a problem from a file and initializes the object attributes. We use a buffered reader and read line by line
	 * getting the score, then parsing it into a Score object and then adding it in to and array list.
	 * @param rankingFileLD File of the ranking we want to load
	 * @return Returns de array list of scores that where found inside the rankingFileLD
	 */
	public static ArrayList<Score> loadScores(File rankingFileLD) {
		ArrayList<Score> scoresLoaded = new ArrayList<Score>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(rankingFileLD));
			String line = reader.readLine();
			while (line != null) {
				String[] lineSplitted = (line).split("\\s");
				Score scoreFromLineFile = new Score(lineSplitted[0], Integer.parseInt(lineSplitted[1]));
				scoresLoaded.add(scoreFromLineFile);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
		}
		return scoresLoaded;
	}

	/**
	 * We add the parametre (@param scoreToAdd) to our attribut scores, we sort it in major-minor order and then update the ranking file.
	 * @param scoreToAdd Score that's going to be added to our ranking.
	 */
	public void addScore(Score scoreToAdd){
		if(scoresRanking.size() < 5) {
			scoresRanking.add(scoreToAdd);
		}
		Collections.sort(scoresRanking);
		updateRankingFile();
	}

	/**
	 * It uses the attribute scores to update the the scores inside the file of our object. We also keep sorted inside the file. Being the top
	 * scores the higher ones.
	 */
	private void updateRankingFile(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.rankingFile));
			for (int i = 0; i < scoresRanking.size(); i++) {
				writer.write(scoresRanking.get(i).getPlayer() + " " + scoresRanking.get(i).getScore() + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * This function resets the ranking/ clears the file by overwriting it with an empty string and also we use it to see if the ranking file
	 * exists by returnin a boolean.
	 * @param idPR The identifier of the problem we want to reset. We'll use it to compare it to the name of the files.
	 * @return Returns true in case we find a file of the problem, false otherwise.
	 */
	public static boolean resetRanking(int idPR){
		boolean trobat = false;
		File[] files = new File("../").listFiles();
		for(File file : files) {
			if (file.getName().charAt(0) != '.') {
				String[] fileName = file.getName().split("\\.");
				String idProblemLD = fileName[0].substring(2);
				if (idProblemLD.equals(idPR)) {
					try {
						trobat = true;
						BufferedWriter writer = new BufferedWriter(new FileWriter(file));
						writer.write("");
						writer.flush();
						writer.close();
					} catch (IOException e){

					}
				}
			}
		}
		return trobat;
	}

	/**
	 * Deletes the file of the ranking identified by the parametre. We also use it as a checker if the file exists in our DB
	 * just as the function above
	 * @param idPR identifier of the ranking
	 * @return Returns true in case we find a file of the problem, false otherwise.
	 */
	public static boolean deleteRanking(int idPR){
		boolean trobat = false;
		File[] files = new File("../").listFiles();
		for(File file : files) {
			if (file.getName().charAt(0) != '.') {
				String[] fileName = file.getName().split("\\.");
				String idProblemLD = fileName[0].substring(2);
				if (idProblemLD.equals(Integer.toString(idPR))) {
					trobat = true;
					file.delete();
				}
			}
		}
		return trobat;
	}

	/**
	 * Prints every ranking saved in our directory.
	 */
	public static void printRankings(){
		File[] files = new File("../").listFiles();
		for(File file : files){
			String[] fileName = file.getName().split("\\.");
			if(file.getName().charAt(0) != '.') {
				System.out.println("");
				System.out.println(fileName[0]);
				System.out.println("--------");
				ArrayList<Score> scoresLD = Ranking.loadScores(file);
				for(int i = 0; i < scoresLD.size(); i++){
					Score scoreToPrint = scoresLD.get(i);
					System.out.println("[" + scoreToPrint.getPlayer() + ": " + scoreToPrint.getScore() + "]");
				}
				System.out.println("--------");
				System.out.println("");
			}
		}
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