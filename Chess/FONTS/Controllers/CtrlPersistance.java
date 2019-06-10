package Controllers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CtrlPersistance {
    private static CtrlPersistance ourInstance ;
    private String filePath;
    private static final String defaultFolder = "./Chess/EXE/data/";


    public static CtrlPersistance getInstance() {
        if(ourInstance == null) ourInstance = new CtrlPersistance();
        return ourInstance;
    }

    private CtrlPersistance() {
        new File(defaultFolder).mkdirs();
        filePath = defaultFolder;
    }

    void saveMatchP(String FEN, String players, String round, String turn) throws IOException {
        String aux = FEN+" "+players+" "+round+" "+turn;
        if(!matchExists("Match.txt")){
            File matchFile = new File(filePath+ "Match"+ ".txt");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(matchFile));
                writer.write(aux);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            deleteMatch();
        }
    }

    String loadMatch() throws IOException {
        if(matchExists("Match.txt")) {
            File mFile = getFile("Match.txt");

            BufferedReader reader = new BufferedReader(new FileReader(mFile));
            String line = reader.readLine();
            return line;
        }
        return null;
    }

    boolean matchExists(String name) throws IOException {
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            String nFile = file.getName();
            if(nFile.equals(name)) return true;
        }
        return false;
    }

    void deleteMatch() throws IOException {
        File del = getFile("Match.txt");
        del.delete();
    }

    /**
     * Adds a new score into a ranking
     * @param name name of the player that made the new score
     * @param points amount of points that deserve to be saved on a ranking
     * @param id identifier of the problem where the new score has been achieved
     * @throws IOException
     */
    void addScore(String name, String points, int id) throws IOException {
        if(existsRanking(id)) {
            ArrayList<String> scores = loadScores(id);
            File del = getFile("R-" + id);
            del.delete();
            boolean trobat = false;
            ArrayList<String> res = new ArrayList<String>();
            for (int i = 0; i < scores.size(); i += 2) {
                if (!trobat && Integer.parseInt(scores.get(i + 1)) < Integer.parseInt(points)) {
                    res.add(name);
                    res.add(points);
                    trobat = true;
                    i -= 2;
                } else {
                    res.add(scores.get(i));
                    res.add(scores.get(i + 1));
                }
            }
            if (!trobat && scores.size() < 10) {
                res.add(name);
                res.add(points);
            }
            saveRanking(res, id);
        }else{
            ArrayList<String> str = new ArrayList<String>();
            str.add(name);str.add(points);
            saveRanking(str,id);
        }
    }

    /**
     * Deletes a ranking from the DB
     * @param id identifier of the problem with the ranking
     * @throws IOException
     */
    void deleteRanking(int id) throws IOException {
        File del = getFile("R-"+Integer.toString(id));
        del.delete();
    }

    /**
     * Saves a new ranking on the DB
     * @param scores ArrayList with size 10 with the username of the bests on the problem identified by id and its puntuation
     * @param id identifies the problem of the ranking
     */
    void saveRanking(ArrayList<String> scores, int id){
        File problemFile = new File(filePath+ "R-" +  id + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile));
            for (int i = 0; i < scores.size(); i+=2){
                String aux = scores.get(i) +" "+scores.get(i+1)+"\n";
                writer.write(aux);
                writer.flush();
            }

            writer.close();
        } catch (IOException e) {
        }
    }

    /**
     * Gets the arraylist with the names and puntuations of the top5 players of the problem
     * @param id identifies the problem related to the scores
     * @return arrayList with the names and puntuations of the top5 players of the problem
     * @throws IOException
     */
    ArrayList<String> loadScores(int id) throws IOException {
        if(existsRanking(id)) {
            ArrayList<String> scoresLoaded = new ArrayList<String>();
            File rankingFileLD = getFile("R-" + id);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(rankingFileLD));
                String line = reader.readLine();
                while (line != null) {
                    String[] lineSplitted = (line).split("\\s");
                    scoresLoaded.add(lineSplitted[0]);
                    scoresLoaded.add(lineSplitted[1]);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
            }
            return scoresLoaded;
        }
        return null;
    }

    /**
     * Save all the problems with ranking and stores the its identificator and the top 5 puntuations
     * @return an ArrayList of ArrayList that contains the top 5 scores of each problem existing in the DB
     * @throws IOException
     */
    ArrayList<ArrayList<String>> listRankings() throws IOException {
        ArrayList<ArrayList<String>> rankingsList = new ArrayList<ArrayList<String>>();
        File[] files = new File(filePath).listFiles();
        int i = 0;
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("R")) {
                splitted = splitted[1].split("\\.");
                String id = splitted[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                ArrayList<String> aux = new ArrayList<String>();
                aux.add(id);
                while((line = reader.readLine()) != null) {
                    String[] lineSplitted = (line).split("\\s");
                    String name = lineSplitted[0];
                    String points = lineSplitted[1];
                    aux.add(name);
                    aux.add(points);
                }
                rankingsList.add(aux);
                reader.close();
                i++;
            }
        }
        for(int l = 0; l < rankingsList.size(); l++){
            for(int j = 0; j < rankingsList.get(l).size();j++) {
                ++j;
            }

        }
        return rankingsList;
    }

    /**
     * Check if there is a ranking of the problem identified by id
     * @param id identifies the ranking to search
     * @return true if exists, otherwise false
     */
    boolean existsRanking(int id){
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("R")) {
                String[] fileName = splitted[1].split("\\.");
                if(fileName[0].equals(Integer.toString(id))){
                    trobat = true;
                }
            }
        }
        return trobat;
    }

    /**
     * Get the whole file of the problem identified by id
     * @param id identifies the problem to get
     * @return returns a file with all the information of a problem
     * @throws IOException
     */
    File getFile(String id) throws IOException {//FUNCIONA
        id += ".txt";
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String filename = file.getName();
            if (filename.equals(id)){
                return file;
            }
        }
        return null;
    }

    /**
     * Gets the FEN notation of the problem identified by id
     * @param id identifies the problem to search
     * @return return an string with the information of the positions, turns and so one of the problem
     * @throws IOException
     */
    String getFEN(int id) throws IOException {//FUNCIONA
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                String[] fileName = splitted[1].split("\\.");
                if(fileName[0].equals(Integer.toString(id))){
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    String[] lineSplitted = (line).split("\\.");
                    String FEN = lineSplitted[0];
                    reader.close();
                    return FEN;
                }
            }
        }
        return null;
    }

    /**
     * Iterates through our database and we take all the problems and save it in an array.
     * @return Returns the array list with all the problems that exist in our DB.
     */
    ArrayList<String> listProblems() throws IOException {//FUNCIONA
        ArrayList<String> problemList = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                splitted = splitted[1].split("\\.");
                String id = splitted[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] lineSplitted = (line).split("\\.");
                String FEN = lineSplitted[0];
                String res = "P-"+id + "-" + FEN;
                problemList.add(res);
                reader.close();
            }
        }
        return problemList;
    }

    /**
     * Iterates through our database and we take all the problems and save it in an array.
     * @return Returns the array list with all the ids' of the problems that exist in our DB.
     */
    ArrayList<String> listProblemsid() throws IOException {//FUNCIONA
        ArrayList<String> problemList = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                splitted = splitted[1].split("\\.");
                String id = splitted[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] lineSplitted = (line).split("\\.");
                lineSplitted = lineSplitted[0].split("\\s");
                String dif = lineSplitted[7];
                String res = "P-"+id+" "+dif;
                problemList.add(res);
                reader.close();
            }
        }
        return problemList;
    }

    ArrayList<String> listRankingsid() throws IOException {//FUNCIONA
        ArrayList<String> problemList = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("R")) {
                splitted = splitted[1].split("\\.");
                String id = splitted[0];
                String res = "R-"+id;
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] lineSplitted = (line).split("\\.");
                lineSplitted = lineSplitted[0].split("\\s");
                //String dif = lineSplitted[7];
                //String res = id + "-" + FEN;
                //String res = "P-"+id+" "+dif;
                problemList.add(res);
                reader.close();
            }
        }
        return problemList;
    }

    /**
     *  Checks if the problem already exists in our directory, we use it for the class Ranking to check if a ranking is being created of a problem
     *  in our DB.
     * @param idPR identifier of the problem you want find
     * @return Returns true if it founds a file of the problem identified by (@param idPR)
     */
    boolean problemExists(String inFEN) throws IOException {//FUNCIONA
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                splitted = splitted[1].split("\\.");
                String id = splitted[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] lineSplitted = (line).split("\\.");
                String FEN = lineSplitted[0];
                if(FEN.equals(inFEN)) {
                    reader.close();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Deletes (if exists) the problem on our DB identified by (@param id).
     * We also use it as a bool to check if the problem has a ranking. If so, it will be deleted as well.
     * @param id identifier of the problem we want to delete
     * @return Returns true if exists a ranking related to the problem identified by id, false otherwise.
     */
    boolean deleteProblem(int id) throws IOException {//FUNCIONA
        File[] files = new File(filePath).listFiles();
        File del = getFile("P-"+id);
        del.delete();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                String[] fileName = splitted[1].split("\\.");
                if (fileName[0].equals(Integer.toString(id))) {
                    file.delete();
                    if(existsRanking(id)) deleteRanking(id);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Saves a new problem on the DB
     * @param FEN contains all the information of the problem on FEN notation
     * @param id identifier of the problem we want to save
     * @param N number of turns to beat the enemy
     * @param difficulty defined by the number of turns (N) ans describes how hard is to beat the enemy
     * @throws IOException
     */
    void saveProblem(String FEN,int id, int N, String difficulty) throws IOException {//FUNCIONA
        File problemFile = new File(filePath+ "P-" +  id + ".txt");

        String test[]= FEN.split("\\s");
        String aux = FEN+" "+difficulty;
        if(test.length <= 6) aux = FEN+" "+ N +" "+difficulty;

        System.out.println(aux+"--------------------");

        try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile));
                writer.write(aux);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /**
     *  Checks if the problem already exists in our directory, we use it for the class Ranking to check if a ranking is being created of a problem
     *  in our DB.
     * @param idPR identifier of the problem we want find
     * @return Returns true if it founds a file of the problem identified by (@param idPR)
     */
    boolean existsProblem(int idPR){ //FUNCIONA
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                String[] fileName = splitted[1].split("\\.");
                if(fileName[0].equals(Integer.toString(idPR))){
                    trobat = true;
                }
            }
        }
        return trobat;
    }
}
