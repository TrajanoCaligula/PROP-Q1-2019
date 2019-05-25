package Controllers;

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

    /* TODO
    UPDATE RANKINGS
    CREATE MATCH TO MODIFY
    PARTIDA
     */

    // A PARTIR DAQUI FUNCIONA -------------------------------------------------------------------------------------------------



    void addScore(String name, String points, int id) throws IOException {
        if(existsRanking(id)) {
            ArrayList<String> scores = loadScores(id);
            File del = getFile("R-" + id);
            del.delete();
            boolean trobat = false;
            ArrayList<String> res = new ArrayList<String>();
            int aux = 0;
            for (int i = 0; i < scores.size(); i += 2) {
                if (Integer.parseInt(scores.get(i + 1)) < Integer.parseInt(points) && !trobat) {
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

    void deleteRanking(int id) throws IOException {
        File del = getFile("R-"+Integer.toString(id));
        del.delete();
    }

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

    ArrayList<ArrayList<String>> listRankings() throws IOException { //fer? CREC QUE NO CAL-------------+-+-++++++++++++++++++++++++++++++++
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
                if(j != 0) {
                    System.out.println(rankingsList.get(l).get(j));
                }else {
                    System.out.println(rankingsList.get(l).get(j) + " " + rankingsList.get(l).get(j + 1));
                    ++j;
                }
            }
            System.out.println();
        }
        return rankingsList;
    }

    boolean existsRanking(int id){ //FUNCIONA
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            String[] splitted = file.getName().split("-");
            if (file.getName().charAt(0) != '.' && splitted[0].equals("R")) {
                String[] fileName = splitted[1].split("\\.");
                if(fileName[0].equals(Integer.toString(id))){
                    System.out.println(file.getName());
                    trobat = true;
                }
            }
        }
        return trobat;
    }

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
                String res = id + " - " + FEN;
                System.out.println(res);
                problemList.add(res);
                reader.close();
            }
        }
        return problemList;
    }

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

    boolean deleteProblem(int id) throws IOException {//FUNCIONA
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        File del = getFile("R-"+id);
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
        return trobat;
    }

    void saveProblem(String FEN,int id, int N, String difficulty) throws IOException {//FUNCIONA
        File problemFile = new File(filePath+ "P-" +  id + ".txt");
        String aux = FEN+" "+N+" "+difficulty;
        if(!problemExists(aux)) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(problemFile));
                writer.write(aux);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean existsProblem(int idPR){ //FUNCIONA
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
