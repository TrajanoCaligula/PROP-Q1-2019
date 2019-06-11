/**
 * @author Pau Charques
 */

package GUI;

import Controllers.CtrlDomain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * The controller class of all views, controls everything that happens in the view and displays it, explained with more details in our
 * SegonLliurament.pdf
 */
public class ViewController {

    private ChessView view;
    private CtrlDomain domainController;
    private boolean matchStarted = false;
    private boolean humanMoves = false;
    private Tile tileSelected = null;
    private int currentIdProb;
    private int currentScore = 0;
    private String firstPlayer;
    private int K = 0;
    private boolean twoMachines = false;
    private boolean currentPlayerTurn; //true - white, false - black

    public ViewController(ChessView currentView) throws IOException {
        this.view = currentView;
        domainController = CtrlDomain.getInstance();

        this.view.addActionListenerTiles(new ActionListenerChess());
    }


    public void startMatch() throws IOException {
        String player1 = view.menuCard.getPlayer1Name();
        String player2 = view.menuCard.getPlayer2Name();

        Integer player1type = view.menuCard.getPlayer1Type();
        Integer player2type = view.menuCard.getPlayer2Type();

        twoMachines = (player1type != 0 && player2type != 0);
        String prob = view.menuCard.getidProblem();
        String[] splitted = prob.split("-");
        splitted = splitted[1].split("\\s");
        currentIdProb = Integer.parseInt(splitted[0]);

        String matchFEN = domainController.getFENFromId(currentIdProb);
        splitted = matchFEN.split("\\s");


        //set first player to move
        firstPlayer = splitted[1];
        if (firstPlayer.equals("w")) {
            currentPlayerTurn = true;
        } else {
            currentPlayerTurn = false;
        }

        tileSelected = null;
        domainController.newGameComplete(currentIdProb, player1, player1type, 1, player2, player2type, 1);
        view.startMatch(splitted[0]);
        view.matchCard.resetTerm();
        updateTerminal();

        matchStarted = true;
        K = view.menuCard.getK();
        currentScore = 0;
        play();
    }

    public void updateTerminal() {
        if(firstPlayer.equals("w")) {
            if (currentPlayerTurn) {
                view.matchCard.addTermLine(domainController.getPlayer1Name());
            } else {
                view.matchCard.addTermLine(domainController.getPlayer2Name());
            }
        } else {
            if (!currentPlayerTurn) {
                view.matchCard.addTermLine(domainController.getPlayer1Name());
            } else {
                view.matchCard.addTermLine(domainController.getPlayer2Name());
            }
        }
        view.matchCard.updateN(domainController.getTurn()/2);
        view.matchCard.updatedScore(currentScore);
    }


    public void play() throws IOException {

        if(domainController.getTurn() <= (domainController.getN()*2)) {
            if (domainController.youAreDonete(currentPlayerTurn)) {
                if (currentPlayerTurn) {
                    if (view.matchCard.gameEnd(domainController.getPlayer1Name()) == 0) {
                        view.back();
                    }
                } else {
                    if (view.matchCard.gameEnd(domainController.getPlayer2Name()) == 0) {
                        view.back();
                    }
                }
            }
            if ((domainController.getTurn() % 2) != 0) {
                if (domainController.getPlayer1Type() != 0) {
                    humanMoves = false;
                    String currentFEN = domainController.playMachine(1);
                    view.matchCard.setBoard(currentFEN);
                    currentPlayerTurn = !currentPlayerTurn;
                    updateTerminal();
                    setCurrentScore();
                    play();
                } else {
                    humanMoves = true;
                }
            } else {
                if (domainController.getPlayer2Type() != 0) {
                    humanMoves = false;
                    String currentFEN = domainController.playMachine(1);
                    view.matchCard.setBoard(currentFEN);
                    currentPlayerTurn = !currentPlayerTurn;
                    updateTerminal();
                    setCurrentScore();
                    play();
                } else {
                    humanMoves = true;
                }
            }
        } else {
            if(twoMachines && K > 0){
                startMatch();
                K--;
            }
            domainController.addScore(domainController.getPlayer1Name(), String.valueOf(currentScore), currentIdProb);
            if (view.matchCard.gameOver() == 0) {
                view.back();
            }
        }
    }



    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals(Actions.PLAY.name())) {
                try {
                    view.menuCard.showPlayOptions(domainController.listProblems());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.START.name())) {
                try {
                    startMatch();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.EXIT.name())) {
                if (view.matchCard.exitDialog() == 0) {
                    view.back();
                }
            } else if (evt.getActionCommand().equals(Actions.PROBLEM_MANAGER.name())) {
                try {
                    view.menuCard.showProblemOptions(domainController.listProblems());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.DIFFICULTY1.name())) {
                view.menuCard.showDifficulty1();
            } else if (evt.getActionCommand().equals(Actions.DIFFICULTY2.name())) {
                view.menuCard.showDifficulty2();
            } else if (evt.getActionCommand().equals(Actions.NEW_PROBLEM.name())) {
                view.newProblem();
            } else if (evt.getActionCommand().equals(Actions.SET.name())) {
                Tile currentTile = (Tile) evt.getSource();
                view.newProblemCard.move(currentTile);
            } else if (evt.getActionCommand().equals(Actions.RANKING.name())) {
                try {
                    view.menuCard.showRankingOptions(domainController.listRankingsId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.SCORES.name())) {
                String str = view.menuCard.getIdRanking();
                String[] splitted = str.split("-");
                int idProblemRanking = Integer.parseInt(splitted[1]);
                try {
                    view.menuCard.showScores(domainController.topScores(idProblemRanking));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.CREATE_PROBLEM.name())) {
                String FEN = view.menuCard.getFENtoCreate();
                int probN = view.menuCard.getN();
                try {
                    if(domainController.createProblem(FEN, probN) != -1){
                        view.menuCard.createDialog("Problem created!");
                        view.menuCard.setProblemsToManage(domainController.listProblems());
                    } else {
                        view.menuCard.createDialog("Couldn't create the problem!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(evt.getActionCommand().equals(Actions.MODIFY_PROBLEM.name())){
                String FEN = view.newProblemCard.getFEN();
                int N = view.newProblemCard.getRounds();
                try {
                    if(domainController.createProblem(FEN, N) == -1){
                        view.newProblemCard.endDialog("This problem either has no solution or fen is invalid");
                    } else {
                        if(view.newProblemCard.endDialog("Created!") == 0){
                            view.back();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.PROBLEM_VIEW.name())) {
                String str = view.menuCard.getIdProblemToManage();
                String[] splitted = str.split("-");
                splitted = splitted[1].split("\\s");
                int idProblemToManage = Integer.parseInt(splitted[0]);
                try {
                    String FEN = domainController.getFENFromId(idProblemToManage);
                    splitted = FEN.split("\\s");
                    view.newProblemCard.setBoard(splitted[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.newProblem();
            } else if (evt.getActionCommand().equals(Actions.DELETE_PROBLEM.name())) {
                String str = view.menuCard.getIdProblemToManage();
                String[] splitted = str.split("-");
                splitted = splitted[1].split("\\s");
                int idProblemToManage = Integer.parseInt(splitted[0]);
                try {
                    domainController.dropProblem(idProblemToManage);
                    view.menuCard.setProblemsToManage(domainController.listProblems());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (evt.getActionCommand().equals(Actions.CLONE_PROBLEM.name())) {
                String str = view.menuCard.getIdProblemToManage();
                String[] splitted = str.split("-");
                splitted = splitted[1].split("\\s");
                int idProblemToManage = Integer.parseInt(splitted[0]);
                try {
                    domainController.copyProblem(idProblemToManage);
                    view.menuCard.setProblemsToManage(domainController.listProblems());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (matchStarted) {
                if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                    Tile currentTile = (Tile) evt.getSource();
                    if (humanMoves) {
                        if (tileSelected == null) {
                            if (currentTile.isOccupied() && currentPlayerTurn == currentTile.getColor()) {
                                tileSelected = currentTile;
                                String coords = currentTile.getTileX() + " " + currentTile.getTileY();
                                ArrayList<String> movements = domainController.getLegalMoves(coords);
                                view.matchCard.highilghtLegalMoves(movements);
                            }
                        } else {
                            String coords = tileSelected.getTileX() + " " + tileSelected.getTileY();
                            ArrayList<String> movements = domainController.getLegalMoves(coords);
                            view.matchCard.undoHighlightLegalMoves(movements);

                            String endCoords = currentTile.getTileY() + " " + currentTile.getTileX();
                            String endCoordsN = currentTile.getTileX() + " " + currentTile.getTileY();
                            if(view.matchCard.legalMove(movements, endCoords)){
                                String fenRes = domainController.makeMove(coords, endCoordsN);
                                view.matchCard.setBoard(fenRes);
                                currentPlayerTurn = !currentPlayerTurn;
                                updateTerminal();
                                setCurrentScore();
                                try {
                                    play();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            tileSelected = null;
                        }
                    }
                }
            }
        }
    }

    public void setCurrentScore(){
        Random rand = new Random();
        this.currentScore += rand.nextInt(348);
    }
}
