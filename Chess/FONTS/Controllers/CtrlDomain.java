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

    public static CtrlDomain getInstance() {
        if(ourInstance == null) ourInstance = new CtrlDomain();
        return ourInstance;
    }

    private CtrlDomain() {
        ctrlIO = CtrlPersistance.getInstance();
        problems = new HashMap<Integer, Problem>();
        match = null;
        players = new Player[2];

    }

    //PROBLEM

    //CAL FER UNA FUNCIO UPDATE PER ACTUALITZAR problems SI JA NHI HAVIA DABANS

    int newProblem(String FEN,int N, String dif) throws IOException {
        Problem prob = new Problem(FEN);
        int id = prob.getId();
        problems.put(id,prob);
        ctrlIO.saveProblem(FEN,id,N,dif);
        return id;
    }

    /*ArrayList<String> listProblems(){
        return ctrlIO.listProblems();
    }*/

    String getFENFromId(int id) throws IOException {
        return ctrlIO.getFEN(id);
    }

    //MATCH

    void newPlayerMachine(String name, String color, int type, int depth){
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

    void newPlayerHuman(String name, String color){
        if(color == "WHITE") players[0] = new Human(name,Color.WHITE);
        else players[1] = new Human(name,Color.BLACK);
    }

    boolean newGame(int problemID){
        Problem problem = problems.get(problemID);
        if(problem != null){
            match = new Match(players[0],players[1],problem,0,problem.getFirstPlayer());
            return true;
        }
        return false;
    }

    String newGameComplete(int problemID, String name1, Color color1, int type1,int depth1,String name2, Color color2, int type2, int depth2){
        Player one = null;
        Player two = null;
        switch (type1){
            case 0: one = new Human(name1,color1);
            case 1: one = new Machine(name1,color1,depth1);
            default: one = new Machine2(name1,color1,depth1);
        }
        switch (type2){
            case 0: two = new Human(name2,color2);
            case 1: two = new Machine(name2,color2,depth2);
            default: two = new Machine2(name2,color2,depth2);
        }
        if(color1 == Color.WHITE){
            players[0] = one;
            players[1] = two;
        } else {
            players[0] = two;
            players[1] = one;
        }

        Problem problem = problems.get(problemID);
        if(problem != null){
            match = new Match(players[0],players[1],problem,0,problem.getFirstPlayer());
            return match.getBoard().toFEN();
        }
        return null;
    }

    String makeMove(String piece, String finalpos){
        Coord coo = new Coord(piece);
        Piece pi = match.getBoard().getPieceInCoord(coo);
        coo = new Coord(finalpos);
        match.getBoard().movePiece(pi,coo);
        return match.getBoard().toFEN();
    }

    int getN(){
        return match.getN();
    }

    ArrayList<String> getLegalMoves(String piece){
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

    int createProblem(String FEN,int N, String difficulty) throws IOException {
        Problem prob = new Problem(FEN);
        prob.setDifficulty(difficulty);
        prob.setN(N);
        int id = prob.getId();
        ctrlIO.saveProblem(FEN,id,N,difficulty);
        problems.put(id,prob);
        return id;
    }

    /*int copyProblem(int id){
        String mod = ctrlIO.getFEN(id);
        mod = mod ;
    }*/

    void updateProblems() throws IOException {
        ArrayList<String> aux = ctrlIO.listProblems();
        for(int i = 0; i < aux.size(); i++) {
            String[] splitted = aux.get(i).split(" - ");//POSSIBLE ERROR--------------------------------------------
            splitted = splitted[1].split(" ");
            Problem res = new Problem(splitted[0]);
            problems.put(Integer.parseInt(splitted[0]),res);
            System.out.println(i+"    "+res.getId()+"    "+res.getFen());
        }
    }

    //RANKING

    ArrayList<String> topScores(int id) throws IOException {
        return ctrlIO.loadScores(id);
    }


}
