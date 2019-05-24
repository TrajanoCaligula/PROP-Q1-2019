package Controllers;

import Pau.Problem;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CtrlPersistance {
    private static CtrlPersistance ourInstance ;
    private String filePath;
    private static final String defaultFolder = "./Chess/FONTS/data/";


    public static CtrlPersistance getInstance() {
        if(ourInstance == null) ourInstance = new CtrlPersistance();
        return ourInstance;
    }

    private CtrlPersistance() {
        new File(defaultFolder).mkdirs();
        filePath = defaultFolder;
    }

    ArrayList<String> problemRanking(int id){
        ArrayList<String> res = new ArrayList<String>();
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            if (file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                if (fileName[0].equals(Integer.toString(id))) {
                    //racollir scores i noms, i eliminar larray que hi ha just a sota
                    return new ArrayList<String>();
                }
            }
        }
        return res;
    }



    /*boolean deleteRanking(int idPR){
        boolean trobat = false;
        File[] files = new File("../").listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("R")) {
                String[] fileName = splitted[1].split("\\.");
                if (fileName[0].equals(idPR)) {
                    trobat = true;
                    file.delete();
                }
            }
        }
        return trobat;
    }*/

    /*void updateRankingFile(String id){
        File file = getFile("R-"+id);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < scoresRanking.size(); i++) {
                writer.write(scoresRanking.get(i).getPlayer() + " " + scoresRanking.get(i).getScore() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }*/

    /*void addScore(String name, String point, int id){
        ArrayList<String> scores = loadScores(id);
        for(int i = 0; i < scores.size(); i+=2){
            if(scores[i] <)
        }
    }*/

    void saveRanking(ArrayList<String> scores, int id){
        File problemFile = new File(filePath+ "R-" +  id + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile));
            for (int i = 0; i < scores.size(); i++){
                String aux = scores.get(i) +" "+scores.get(i+1);
                writer.write(aux);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
        }
    }



    // A PARTIR DAQUI FUNCIONA -------------------------------------------------------------------------------------------------

    ArrayList<String> loadScores(int id) throws IOException {
        ArrayList<String> scoresLoaded = new ArrayList<String>();
        File rankingFileLD = getFile("R-"+id);
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




    File getFile(String id) throws IOException {
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

    String getFEN(int id) throws IOException {
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

    ArrayList<String> listProblems() throws IOException {
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
                String res = id + " - " + FEN;
                problemList.add(res);
                reader.close();
            }
        }
        return problemList;
    }

    boolean deleteProblem(int id){
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if(file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                String[] fileName = splitted[1].split("\\.");
                if (fileName[0].equals(Integer.toString(id))) {
                    trobat = true;
                    file.delete();
                }
            }
        }
        return trobat;
    }

    void saveProblem(String FEN,int id){
        File problemFile = new File(filePath+ "P-" +  id + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile));
            writer.write(FEN);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    boolean existsProblem(int idPR){
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("P")) {
                String[] fileName = splitted[1].split("\\.");
                if(fileName[0].equals(Integer.toString(idPR))){
                    System.out.println(file.getName());
                    trobat = true;
                }
            }
        }
        return trobat;
    }
}
