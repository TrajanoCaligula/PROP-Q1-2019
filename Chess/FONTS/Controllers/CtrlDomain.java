package Controllers;

import Calin.*;
import Pau.*;
import Jaume.*;
import com.sun.xml.internal.bind.WhiteSpaceProcessor;

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

    /**
     * Gets the problems stored on the DB
     * @return arraylist with the ids of the problems stored on the DB
     * @throws IOException
     */
    public ArrayList<String> listProblems() throws IOException {
        return ctrlIO.listProblemsid();
    }

    /**
     * Gets the FEN of the problem identified by id
     * @param id identifies the problem you want to search
     * @return the FEN of the problem identified by id
     * @throws IOException
     */
    public String getFENFromId(int id) throws IOException {
        return ctrlIO.getFEN(id);
    }

    //MATCH

    /**
     * Creates a new match with all the information
     * @param problemID identifies the problem on the DB
     * @param name1 name of the white player
     * @param type1 0 if human, 1 if machine1, 2 if machine2
     * @param depth1 depth of the algorithm if type1 > 0
     * @param name2 name of the black player
     * @param type2 0 if human, 1 if machine1, 2 if machine2
     * @param depth2 depth of the algorithm if type2 > 0
     * @return the FEN of the problem
     */
    public String newGameComplete(int problemID, String name1, int type1,int depth1,String name2, int type2, int depth2){
        Player one = null;
        Player two = null;

        switch(type1){
            case 0: {
                one = new Human(name1,Color.WHITE);
                break;
            }
            case 1: {
                one = new Machine(name1,Color.WHITE,depth1);
                break;
            }
            default:{
                one = new Machine2(name1,Color.WHITE,depth1);
                break;
            }
        }
        switch(type2){
            case 0: {
                two = new Human(name2,Color.BLACK);
                break;
            }
            case 1: {
                two = new Machine(name2,Color.BLACK,depth2);
                break;
            }
            default:{
                two = new Machine2(name2,Color.BLACK,depth2);
                break;
            }
        }

        players[0] = one;
        players[1] = two;

        Problem problem = problems.get(problemID);
        match = new Match(players[0],players[1],problem,1,problem.getFirstPlayer());
        return match.getBoard().toFEN();
    }

    public void saveMatch(int round, String turn) throws IOException {
        String FEN = match.getBoard().toFEN();
        String type1,type2;
        type1 = players[0].getType();
        type2 = players[1].getType();
        String depth1,depth2;
        depth1 = Integer.toString(players[0].getDepth());
        depth2 = Integer.toString(players[1].getDepth());
        String player = getPlayer1Name() +" "+type1+" "+depth1+" "+ getPlayer2Name()+" "+type2+" "+depth2;

        ctrlIO.saveMatchP(FEN,player,Integer.toString(round),turn);


    }

    public void loadMatch() throws IOException {
        String aux = ctrlIO.loadMatch();
        String auxSpli[] = (aux).split("\\s");
        String FEN = auxSpli[0]+" "+auxSpli[1]+" "+auxSpli[2]+" "+auxSpli[3]+" "+auxSpli[4]+" "+auxSpli[5]+" "+auxSpli[6];

        String player1 = auxSpli[7];
        String type1 = auxSpli[8];
        String depth1 = auxSpli[9];
        String player2 = auxSpli[10];
        String type2 = auxSpli[11];
        String depth2 = auxSpli[12];

        int roundn = Integer.parseInt(auxSpli[13]);

        if(type1 == "M1") {
            players[0] = new Human(player1, Color.WHITE);
        }

        else if(type1 == "M2"){
            players[0] = new Machine(player1, Color.WHITE,Integer.parseInt(depth1));
        }

        else {
            players[0] = new Machine2(player1, Color.WHITE,Integer.parseInt(depth1));
        }

        if(type2 == "M1") {
            players[1] = new Human(player2, Color.WHITE);
        }

        else if(type2 == "M2"){
            players[1] = new Machine(player2, Color.WHITE,Integer.parseInt(depth2));
        }

        else {
            players[1] = new Machine2(player2, Color.WHITE,Integer.parseInt(depth2));
        }

        Problem test = new Problem(FEN);

        int round = Integer.parseInt(auxSpli[13]);

        if(auxSpli[14] == "BLACK") match = new Match(players[0],players[1],test,roundn,Color.BLACK);
        else match = new Match(players[0],players[1],test,roundn,Color.WHITE);
    }

    /**
     * Moves a piece into a new position
     * @param piece string that describes the position of the piece you want to move
     * @param finalpos string that describes the new position of the piece
     * @return the FEN with the new position of the piece
     */
    public String makeMove(String piece, String finalpos){
        Coord coo = new Coord(piece);
        Piece pi = match.getBoard().getPieceInCoord(coo);
        coo = new Coord(finalpos);
        match.getBoard().movePiece(pi,coo);
        match.setRound();
        return match.getBoard().toFEN();
    }

    /**
     * Gets the number of turns remainding to finish the match
     * @return number of rounds until the match ends
     */
    public int getN(){
        return match.getN();
    }

    /**
     * Gets the current round of the match
     * @return the current round of the match
     */
    public int getTurn(){
        return match.getRound();
    }

    /**
     * Checks if the player's king is in danger
     * @param color if "BLACK" checks if the black team is in danger, if "WHITE" checks the white team
     * @return true if the player's(color) king is in danger, false otherwise
     */
    public boolean isInCheck(String color){
        if(color == "BLACK") return match.getBoard().isCheck(Color.BLACK);
        else return match.getBoard().isCheck(Color.WHITE);
    }

    /**
     * Chescks if the player has lost
     * @param color if true, chaecks the white team, if false, the black one
     * @return true if the player (color) has lost, false otherwise
     */
    public boolean youAreDonete(boolean color){
        if(!color) return match.getBoard().isGameOver(Color.BLACK);
        else return match.getBoard().isGameOver(Color.WHITE);
    }

    public int getScore(){
        return 1000 - match.getBlackScore();
    }

    /**
     * Get the legal movemnt that you can make with a certain piece
     * @param piece string that describes the position of the piece
     * @return arraylist with the positions where you can move the piece
     */
    public ArrayList<String> getLegalMoves(String piece){ //NEED TEST
        Coord coo = new Coord(piece);
        Piece aux = match.getBoard().getPieceInCoord(coo);
        ArrayList<Coord> tmp = aux.getLegalMoves(match.getBoard());
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < tmp.size(); ++i) {
            String toAdd = String.valueOf(aux.getPosition().add(tmp.get(i)).getX()) + String.valueOf(aux.getPosition().add(tmp.get(i)).getY());
            res.add(toAdd);
        }
        return res;
    }

    //PROBLEM

    /**
     * Creates a new problem and saves on the DB if its not already there
     * @param FEN information of the positions of the pieces of the problem
     * @param N number of turns to beat the enemy
     * @return the id of the new problem, -1 if it has not been saved successfully
     * @throws IOException
     */
    public int createProblem(String FEN,int N) throws IOException {
        Problem prob = new Problem(FEN);
        String difficulty;
        if(prob.validateFen(FEN)) {
            if(N <= 5) difficulty = "Hard";
            else if(N <= 10) difficulty = "Normal";
            else difficulty = "Easy";
            prob.setDifficulty(difficulty);
            prob.setN(N);
            int id = prob.getId();
            ctrlIO.saveProblem(FEN, id, N, difficulty);
            problems.put(id, prob);
            return id;
        } else return-1;
    }

    /**
     * Clone a problem
     * @param id identifier of the problem you want to clone
     * @return FEN of the problem
     */
    public void copyProblem(int id) throws IOException {
        Problem prob = problems.get(id);
        probToMod = new Problem(prob.getFen());
        createProblem(probToMod.getFen(),probToMod.getN());
    }

    /**
     * Modify a position in the board of a piece and return the resulting FEN
     * @param init string that describes the position of the pieace you want to move
     * @param fin string that describes the position where you want to move that piece
     * @return FEN with the new position of the pieace
     */
    public String modifyFEN(String init,String fin){
        return probToMod.movePiece(init,fin);
    }

    /**
     * Saves a problem into the DB
     * @return return the id of the problem if its has been saved, -1 otherwise
     * @throws IOException
     */
    public int saveModProb() throws IOException {
        if(probToMod.validateFen(probToMod.getFen()) && probToMod.validateProblem()){
            ctrlIO.saveProblem(probToMod.getFen(),probToMod.getId(),probToMod.getN(),probToMod.getDifficulty());
            problems.put(probToMod.getId(),probToMod);
            return probToMod.getId();
        }
        return -1;
    }

    /**
     * Destroy a problem from the DB and its ranking
     * @param id
     * @throws IOException
     */
    public void dropProblem(int id) throws IOException {
        ctrlIO.deleteProblem(id);
        problems.remove(id);
    }

    /**
     * Updates the problems existing on the program from the DB
     * @throws IOException
     */
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

    /**
     * Updates the rankings existing on the program from the DB
     * @throws IOException
     */
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
     * List the top 5 scores on a problem
     * @param id identifies the problem
     * @return arraylist with the username and puntuation of the top5 scores ofthe problem identified by id
     * @throws IOException
     */
    public ArrayList<String> topScores(int id) throws IOException {
        return ctrlIO.loadScores(id);
    }

    /**
     * Lists the problems with ranking of the system
     * @return ArrayList of string in the format "P-id" where id is the identifier of problem
     * @throws IOException
     */
    public ArrayList<String>listRankingsId() throws IOException {
        return ctrlIO.listRankingsid();
    }

    public String playMachine(){
        players[1].play(match.getBoard(), this.match.getBlackScore(), Color.BLACK);
        match.setRound();
        return this.match.getBoard().toFEN();
    }

    public void setRound(){
        match.setRound();
    }

    /**
     * Gets the type of white player
     * @return 0 if its a human, 1 if its a machine1, 2 if its a machine2
     */
    public Integer getPlayer1Type(){
        if(players[0] instanceof Human)
            return 0;
        else if (players[0] instanceof Machine)
            return 1;
        else if (players[0] instanceof Machine2)
            return 2;
        return null;
    }

    /**
     * Gets the name of the black player
     * @return the name of the player number two
     */
    public String getPlayer1Name(){
        return players[0].getName();
    }

    /**
     * Gets the type of black player
     * @return 0 if its a human, 1 if its a machine1, 2 if its a machine2
     */
    public Integer getPlayer2Type(){
        if(players[1] instanceof Human)
            return 0;
        else if (players[1] instanceof Machine)
            return 1;
        else if (players[1] instanceof Machine2)
            return 2;
        return null;
    }

    /**
     * Gets the name of the white player
     * @return the name of the player number one
     */
    public String getPlayer2Name(){
        return players[1].getName();
    }
}
