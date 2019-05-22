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

    String getFEN(int id){
        String FEN = null;
        //IMPLEMENTAR QUE RETORNI EL FEN D'UN CERT PROBLEMA IDENTIFICAT AMB ID
        return FEN;
    }





    String read(String path) throws IOException {
        String filepath = this.filePath + "/"+ path;

        byte[] encoded = Files.readAllBytes(Paths.get(filepath));
        return new String(encoded, Charset.defaultCharset());
    }

    ArrayList<String> readAll() throws IOException {
        File[] files = new File(filePath).listFiles();
        ArrayList<String> res = new ArrayList<String>();
        for(File file : files) {
            if (file.getName().charAt(0) != '.') {

                String[] fileName = file.getName().split("\\.");

            }
        }
        return res;
    }



    ArrayList<String> listProblems(){
        ArrayList<String> problemList = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        for(File file : files){
            if(file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
            }
        }
        return problemList;
    }
    // A PARTIR DAQUI FUNCIONA -------------------------------------------------------------------------------------------------
    boolean deleteProblem(int id){
        boolean trobat = false;
        File[] files = new File(filePath).listFiles();
        for(File file : files) {
            if (file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                if (fileName[0].equals(Integer.toString(id))) {
                    trobat = true;
                    file.delete();
                }
            }
        }
        return trobat;
    }

    void writeProblem(String FEN, int id) throws IOException {
        File problemFile = new File(filePath +  id + ".txt");
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
            if (file.getName().charAt(0) != '.') {
                String[] fileName = file.getName().split("\\.");
                if (fileName[0].equals(Integer.toString(idPR))) {
                    trobat = true;
                }
            }
        }
        return trobat;
    }
}
