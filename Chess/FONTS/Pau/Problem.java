/**
 * @author Pau Charques Rius
 */
package Pau;

import java.io.*;
import java.util.*;

import Jaume.*;

public class Problem{

    /**ATRIBUTOS**/
    private int id;
    private String FEN;
    private String problemDifficulty;
    private String firstPlayer;
    private String machinePlayer;
    private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    private int N; //We get n from theme - (ex: Mat en 2 --> N = 2 )


    /**
     * This first constructor only needs the initial state of the board in FEN notation, then it will be assigned a random id between
     * 0 and 1000000, that's a 0.000001% chance that 2 differents problems will have the same id. We think its low enough to trust it will
     * never ocur that 2 different problems have the same id. Before this, our solution was to iterate over the files of the directory
     * where problems are saved, but that lead us to a really inefficient solution, so we ended up with the random id.
     * Inside we have two restrictions:
     * 1. The FEN is valid, that means it has de correct number of positions and the correct number of pieces.
     * 2. The problem has a valid FEN but it has no solution. That means that it can't be solved with n movements, being n less or equal than N;
     */

    /**
     * This first constructor only needs the initial state of the board in FEN notation, then it will be assigned a random id between
     * 0 and 1000000, that's a 0.000001% chance that 2 differents problems will have the same id. We think its low enough to trust it will
     * never ocur that 2 different problems have the same id. Before this, our solution was to iterate over the files of the directory
     * where problems are saved, but that lead us to a really inefficient solution, so we ended up with the random id.
     * Inside we have two restrictions:
     * 1. The FEN is valid, that means it has de correct number of positions and the correct number of pieces.
     * 2. The problem has a valid FEN but it has no solution. That means that it can't be solved with n movements, being n less than N;
     * @param FEN The starting state of the problem written in FEN notation (String).
     */
    public Problem(String FEN){
        Random rand = new Random();
        this.id = rand.nextInt(1000*1000);
        this.FEN = FEN;
        System.out.println(FEN);
        String[] splitted = FEN.split("\\s");
        this.firstPlayer = splitted[1];
        if(this.firstPlayer.equals("w")) {this.machinePlayer = "b";}
        else {this.machinePlayer = "w";}
        Board board = new Board(FEN);
        this.whitePieces = board.getWhitePieces();
        this.blackPieces = board.getBlackPieces();
    }

    /**
     * This second constructor uses a File as a parametre. We use this constructor only when we list/print problems to make easier the
     * conversion of a file to a problem object.
     * The only restriction it checks is if the file exists in our directory of problems saved. In case it's not, it throws and exception.
     * @param problemFile file of the problem we want to create/load.
     */
    public Problem(File problemFile){
        if(problemFile.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(problemFile));
                String line = reader.readLine();
                String[] lineSplitted = (line).split("\\s");
                this.id = Integer.parseInt(lineSplitted[0]);
                this.FEN = lineSplitted[1];
                this.setDifficulty(lineSplitted[4]);
                this.firstPlayer = lineSplitted[2];
                this.N = Integer.parseInt(lineSplitted[3]);
                reader.close();
                if(this.firstPlayer.equals("w")) {this.machinePlayer = "b";}
                else {this.machinePlayer = "w";}
                Board board = new Board(this.FEN);
                this.whitePieces = board.getWhitePieces();
                this.blackPieces = board.getBlackPieces();
            } catch (IOException e) {

            }

        } else {
            System.out.println("File doesn't exists");
        }
    }

    /**GETTERS*/

    /**
     * Getter for idProblem
     * @return Returns the id of the problem.
     */
    public int getId(){ return this.id; }
    /**
     * Getter for topic of the problem.
     * @return Returns the topic of the problem.
     */
    public String getTema(){
        return "Mat en " + this.N + "per part de les " + firstPlayer;
    }
    /**
     * Getter of the First Player.
     * @return Returns de player who will move first and has to win the game.
     */
    public Color getFirstPlayer(){
        if(this.firstPlayer.equals("w"))
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    /**
     * Getter of the Second Player.
     * @return Returns the Second Player color.
     */
    public Color getSecondPlayer(){
        if(this.machinePlayer.equals("w"))
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    /**
     * Getter of the Problem state in FEN notation.
     * @return Returns the problem FEN notation
     */
    public String getFen(){ return this.FEN; }
    /**
     * Getter for N
     * @return Returns N, being N the maxmimum number of turns that first player needs to win the game.
     */
    public int getN(){ return this.N; }
    /**
     *  Getter for the number of white pieces
     * @return Returns the number of pieces that the white players have in the board.
     */
    public int getNumberWhite(){
        return whitePieces.size();
    }
    /**
     *  Geter for the number of black pieces
     * @return Returns the number of pieces that the black players have in the board.
     */
    public int getNumberBlack(){
        return blackPieces.size();
    }
    /**
     * Getter for the piece
     * @param i The position of the piece in our array of (@param team) pieces
     * @param team If team is true you'll get the char that represents the piece in the i position of the BLACK team, if false, you'll get the char of the piece on the WHITE team.
     * @return Returns a char that represents the piece.
     */
    public Piece getPiece(int i, boolean team){
        if(team){
            return whitePieces.get(i-1);
        } else {
            return blackPieces.get(i-1);
        }
    }

    /**
     * Use this function to modify the initial fen as you want.
     */
    public String movePiece(String init,String fin){
        Board boardMovements = new Board(this.FEN);

        Coord initialCoord = new Coord(init);

        Coord movingCoord = new Coord(fin);

        Piece pieceToMove = boardMovements.getPieceInCoord(initialCoord);
        boardMovements.movePiece(pieceToMove, movingCoord);

        this.FEN = boardMovements.toFEN();
        return FEN;
    }


    /**SETTERS*/
    /**
     * Sets N of the problem
     * @param N will be the N variable of the problem.
     */
    public void setN(int N){ this.N = N; }

    /**
     * Sets id of the problem
     * @param idProblem this will be the id of the problem
     */
    public void setId(int idProblem){
        this.id = idProblem;
    }
    /**
     * Sets FEN of the problem
     * @param strFEN this will be the FEN of the problem your are setting
     */
    public void setFEN(String strFEN){ this.FEN = strFEN; }

    /**
     * Sets Difficulty of the problem
     * @param strDifficulty this will be the difficulty of the problem your are setting
     */
    public void setDifficulty(String strDifficulty){
        this.problemDifficulty = "Easy";
    }

    /**
     * We use backtracking algorithm to check if the problem has a solution
     * @return Returns true if it does, have a solution, otherwise returns false;
     */
    public boolean validateProblem(){

        //Mirar que haya al menos dos piezas por color
        if(whitePieces.size() < 2 && blackPieces.size() < 2) { return false; }  //Al menos uno de los dos color tiene que tener más de una pieza

        //Mirar que haya un rey de cada color
        boolean found = false;
        for(Piece piece : whitePieces) {
            if(piece instanceof King) { found = true; }
        }
        if(!found) { return false; }
        found = false;
        for(Piece piece : blackPieces) {
            if(piece instanceof King) { found = true; }
        }
        if(!found) { return false; }

        //Si se cumple, backtracking
        Board board = new Board(FEN);
        return backtracking(board, N, this.getFirstPlayer());

    }

    private boolean backtracking(Board board, int roundsLeft, Color playingColor) {
        boolean result = false;
        //Esta es la iteracion sobre el color del jugador
        if(playingColor == this.getFirstPlayer()) {
            //Condiciones inciales
            if(board.isGameOver(this.getFirstPlayer())) {return false;}

            //Caso recursivo
            ArrayList<Piece> pieces = new ArrayList<>();
            if(playingColor == Color.WHITE) { pieces = board.getWhitePieces(); }
            else { pieces = board.getBlackPieces(); }
            for(Piece piece : pieces) {
                for(Coord move : piece.getLegalMoves(board)) {
                    Board newBoard = new Board(board);
                    newBoard.movePiece(newBoard.getPieceInCoord(piece.getPosition()), piece.getPosition().add(move));
                    result = result || backtracking(newBoard, roundsLeft - 1, this.getSecondPlayer());
                }
            }
        }

        //Esta es la iteracion sobre el color de la maquina
        else {
            //Condiciones inciales
            if(board.isGameOver(this.getSecondPlayer())) {
                board.printBoard();
                return true;}
            else if(roundsLeft == 0) {return false;}

            //Caso recursivo
            ArrayList<Piece> pieces = new ArrayList<>();
            if(playingColor == Color.WHITE) { pieces = board.getWhitePieces(); }
            else { pieces = board.getBlackPieces(); }
            for(Piece piece : pieces) {
                for(Coord move : piece.getLegalMoves(board)) {
                    Board newBoard = new Board(board);
                    newBoard.movePiece(newBoard.getPieceInCoord(piece.getPosition()), piece.getPosition().add(move));
                    result = result || backtracking(newBoard, roundsLeft, this.getFirstPlayer());
                }
            }
        }

        return result;
    }

    public String getDifficulty(){
        return problemDifficulty;
    }

    /**
     *
     * @param inputFEN FEN notation string we want to check if it's valid, that means:
     * 1. The correct number of instances of each type of piece
     * 2. The correct number of white tiles
     * @return Devuelve si el problema tiene un fen valido o no
     */
    public boolean validateFen(String inputFEN){
        System.out.println(inputFEN);
        String[] splits = inputFEN.split("\\s");

        String vFEN = splits[0];
        this.FEN = vFEN;

        boolean valid = true;
        int i = 0;
        int j = 0;
        Map<Character, Integer> allPieces = new HashMap<Character, Integer>();
        for(int k = 0; k < vFEN.length() && valid && (!Character.isWhitespace(vFEN.charAt(k))); k++){
            char actual = vFEN.charAt(k);
            if(!Character.isDigit(actual)){
                if(actual == '/'){
                    if(i%8 != 0){
                        valid = false;
                    }
                    else {
                        i = 0;
                        j++;
                    }
                } else {
                    Integer keyValue = allPieces.get(actual);

                    if(keyValue != null) {
                        if(validPiece(actual, keyValue)) {
                            allPieces.put(actual, allPieces.get(actual) + 1);
                        }
                        else {
                            valid = false;
                        }
                    }
                    else {
                        allPieces.put(actual, 1);
                    }
                    i++;
                }
            } else {
                i += Character.getNumericValue(vFEN.charAt(k));
            }
        }
        return valid;
    }

    /**
     * Checks that the parametre (@param pieceToMap) if has the correct number of instances
     * only 1 king, 1 queen, 2 bishops...
     * @param pieceToMap Piece you want to check if it's valid
     * @param numberOfPieces number of pieces already created of that type (type being Kingm, Queen...)
     * @return Returns true if it's valid (pass the restriction described above) otherwise false
     */
    private boolean validPiece(char pieceToMap, int numberOfPieces){
        boolean validPiece = false;

        switch(pieceToMap) {
            case 'p':
            case 'P':
                if(numberOfPieces < 8) validPiece = true;
                break;
            case 'n':
            case 'N':
                if(numberOfPieces < 2) validPiece = true;
                break;
            case 'b':
            case 'B':
                if(numberOfPieces < 2) validPiece = true;
                break;
            case 'r':
            case 'R':
                if(numberOfPieces < 2) validPiece = true;
                break;
            case 'q':
            case 'Q':
                if(numberOfPieces < 1) validPiece = true;
                break;
            case 'k':
            case 'K':
                if(numberOfPieces < 1) validPiece = true;
                break;
        }
        return validPiece;
    }

    @Override
    public String toString(){
        return (this.id + " " + this.FEN + " " + this.firstPlayer + " " + this.N + " " + this.problemDifficulty);
    }

}


