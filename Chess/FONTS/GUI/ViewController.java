/**
 * @author Pau Charques
 */

package GUI;

import Controllers.CtrlDomain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


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
    private String firstPlayer;
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

        String prob = view.menuCard.getidProblem();
        String[] splitted = prob.split("-");
        splitted = splitted[1].split("\\s");
        int idPr = Integer.parseInt(splitted[0]);

        String matchFEN = domainController.getFENFromId(idPr);
        splitted = matchFEN.split("\\s");


        //set first player to move
        firstPlayer = splitted[1];
        if (firstPlayer.equals("w")) {
            currentPlayerTurn = true;
        } else {
            currentPlayerTurn = false;
        }

        tileSelected = null;
        domainController.newGameComplete(idPr, player1, player1type, 1, player2, player2type, 1);
        view.startMatch(splitted[0]);
        matchStarted = true;
        play();
    }

    public void updateTerminal(String moveMade) {
        if (!currentPlayerTurn) {
            view.matchCard.addTermLine(moveMade, domainController.getPlayer1Name());
        } else {
            view.matchCard.addTermLine(moveMade, domainController.getPlayer2Name());
        }
        view.matchCard.updateN(domainController.getTurn()/2);
        view.matchCard.updatedScore(domainController.getScore());
    }


    public void play() {
        if(domainController.youAreDonete(currentPlayerTurn)){
            if(currentPlayerTurn){
                view.matchCard.gameEnd(domainController.getPlayer1Name());
            } else {
                view.matchCard.gameEnd(domainController.getPlayer2Name());
            }
        }
        if(domainController.getTurn() > (domainController.getN()*2)){
            view.matchCard.gameOver();
        }
        if ((domainController.getTurn() % 2) != 0) {
            if (domainController.getPlayer1Type() != 0) {
                humanMoves = false;
                String currentFEN = domainController.playMachine(1);
                view.matchCard.updateBoard(currentFEN);
                currentPlayerTurn = !currentPlayerTurn;
                updateTerminal("f");
                play();
            } else {
                humanMoves = true;
            }
        } else {
            if (domainController.getPlayer2Type() != 0) {
                humanMoves = false;
                String currentFEN = domainController.playMachine(1);
                view.matchCard.updateBoard(currentFEN);
                currentPlayerTurn = !currentPlayerTurn;
                updateTerminal("f");
                play();
            } else {
                humanMoves = true;
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
                FEN = FEN + " " + view.newProblemCard.getFirstPlayer();
                try {
                    if(domainController.createProblem(FEN, N) != -1){
                        if(view.newProblemCard.endDialog("This problem either has no solution or fen is invalid") == 0){
                            view.back();
                        }
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
                    view.newProblemCard.setMatchBoard(splitted[0]);
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
                                view.matchCard.updateBoard(fenRes);
                                currentPlayerTurn = !currentPlayerTurn;
                                updateTerminal("f");
                                play();
                            }
                            tileSelected = null;
                        }
                    }
                }
            }
        }
    }
}
