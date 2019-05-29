package Controllers;

import Calin.*;
import Pau.*;
import Jaume.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CtrlDomain {
    private static CtrlDomain ourInstance;
    private static CtrlPersistance ctrlIO;
    private HashMap <Integer, Problem> problems;
    private Player[] players;
    private Match match;
    private Problem probToMod;

    public static CtrlDomain getInstance() throws IOException {
        if(ourInstance == null) ourInstance = new CtrlDomain();
        return ourInstance;
    }

    private CtrlDomain() throws IOException {
        ctrlIO = CtrlPersistance.getInstance();
        problems = new HashMap<Integer, Problem>();
        match = null;
        players = new Player[2];
        probToMod=null;
        updateProblems();
        updateRankings();
    }

    //PROBLEM

    //CAL FER UNA FUNCIO UPDATE PER ACTUALITZAR problems SI JA NHI HAVIA DABANS

    public ArrayList<String> listProblems() throws IOException {
        return ctrlIO.listProblemsid();
    }

    public String getFENFromId(int id) throws IOException {
        return ctrlIO.getFEN(id);
    }

    //MATCH

    public void newPlayerMachine(String name, String color, int type, int depth){
        Color cl;
        if(color == "WHITE")cl = Color.WHITE;
        else cl = Color.BLACK;
        Player pl = null;
        if(type == 1){
            pl = new Machine(name, cl,depth);
        }
        else {
            pl = new Machine2(name,cl, depth);
        }
        if(color == "WHITE") players[0] = pl;
        else players[1] = pl;
    }

    public void newPlayerHuman(String name, String color){
        if(color == "WHITE") players[0] = new Human(name,Color.WHITE);
        else players[1] = new Human(name,Color.BLACK);
    }

    public boolean newGame(int problemID){
        Problem problem = problems.get(problemID);
        if(problem != null){
            match = new Match(players[0],players[1],problem,0,problem.getFirstPlayer());
            return true;
        }
        return false;
    }

    public String newGameComplete(int problemID, String name1, int type1,int depth1,String name2, int type2, int depth2){
        Player one = null;
        Player two = null;
        switch (type1){
            case 0: one = new Human(name1,Color.WHITE);
            case 1: one = new Machine(name1,Color.WHITE,depth1);
            default: one = new Machine2(name1,Color.WHITE,depth1);
        }
        switch (type2){
            case 0: two = new Human(name2,Color.BLACK);
            case 1: two = new Machine(name2,Color.BLACK,depth2);
            default: two = new Machine2(name2,Color.BLACK,depth2);
        }

        players[0] = one;
        players[1] = two;

        Problem problem = problems.get(problemID);
        match = new Match(players[0],players[1],problem,1,problem.getFirstPlayer());
        return match.getBoard().toFEN();
    }

    public String makeMove(String piece, String finalpos){
        Coord coo = new Coord(piece);
        Piece pi = match.getBoard().getPieceInCoord(coo);
        coo = new Coord(finalpos);
        match.getBoard().movePiece(pi,coo);
        return match.getBoard().toFEN();
    }

    public int getN(){
        return match.getN();
    }

    public int getTurn(){
        return match.getRound();
    }

    public ArrayList<String> getLegalMoves(String piece){ //NEED TEST
        Coord coo = new Coord(piece);
        Piece aux = match.getBoard().getPieceInCoord(coo);
        ArrayList<Coord> tmp = aux.getLegalMoves(match.getBoard());
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < tmp.size(); ++i) {
            res.add(aux.getPosition().add(tmp.get(i)).toRealCoord());
        }
        return res;
    }

    //PROBLEM

    public int createProblem(String FEN,int N, String difficulty) throws IOException {
        Problem prob = new Problem(FEN);
        if(prob.validateFen(FEN) && prob.validateProblem()) {
            if(N <= 5) difficulty = "Hard";
            else if(N <= 10) difficulty = "Normal";
            else difficulty = "Easy";
            prob.setDifficulty(difficulty);
            prob.setN(N);
            int id = prob.getId();
            ctrlIO.saveProblem(FEN, id, N, difficulty);
            problems.put(id, prob);
            return id;
        }else return-1;
    }

    public String copyProblem(int id){
        Problem prob = problems.get(id);
        probToMod = new Problem(prob.getFen());
        return probToMod.getFen();
    }

    public String modifyFEN(String init,String fin){
        return probToMod.movePiece(init,fin);
    }

    public int saveModProb() throws IOException {
        if(probToMod.validateFen(probToMod.getFen()) && probToMod.validateProblem()){
            ctrlIO.saveProblem(probToMod.getFen(),probToMod.getId(),probToMod.getN(),probToMod.getDifficulty());
            problems.put(probToMod.getId(),probToMod);
            return probToMod.getId();
        }
        return -1;
    }

    public void dropProblem(int id) throws IOException {
        ctrlIO.deleteProblem(id);
        problems.remove(id);
    }

    void updateProblems() throws IOException {
        ArrayList<String> aux = ctrlIO.listProblems();
        for(int i = 0; i < aux.size(); i++) {
            String[] splitted = aux.get(i).split("-");
            int id = Integer.parseInt(splitted[1]);
            String FEN = splitted[2]+"- -"+splitted[3];
            splitted = splitted[4].split("\\s");
            FEN += splitted[0]+splitted[1]+" "+splitted[2]+" "+splitted[3];
            int N = Integer.parseInt(splitted[3]);
            String diff = splitted[4];
            Problem res = new Problem(FEN);
            res.setN(N);
            res.setDifficulty(diff);
            problems.put(id,res);
        }
    }

    //RANKING

    void updateRankings() throws IOException { //NEED TEST
        ArrayList<ArrayList<String>> aux = ctrlIO.listRankings();
        for(int i = 0; i < aux.size(); i++) {
            Ranking rank = new Ranking(Integer.parseInt(aux.get(i).get(0)));

            for(int j = 1; j < aux.get(i).size();j+=2) {
                Score score = new Score(aux.get(i).get(j),Integer.parseInt(aux.get(i).get(j+1)));
                rank.addScore(score);
            }

        }
    }

    /**
     *
     * @param id identifies the problem
     * @return arraylist with the username and puntuation of the top5 scores ofthe problem identified by id
     * @throws IOException
     */
    public ArrayList<String> topScores(int id) throws IOException {
        return ctrlIO.loadScores(id);
    }
}
