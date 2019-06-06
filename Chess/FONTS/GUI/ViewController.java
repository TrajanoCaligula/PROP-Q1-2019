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
 * The father class of all views, controls everything that happens in the view and displays it, explained with more details in our
 * SegonLliurament.pdf
 */
public class ViewController{
    private ChessView view;
    private CtrlDomain domainController;
    private boolean matchStarted = false;
    private boolean humanMoves = false;
    private boolean currentPlayerTurn = false; //false - white, true - black

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
        domainController.newGameComplete(idPr, player1, player1type, 1, player2, player2type, 2);
        matchStarted = true;
        view.startMatch(splitted[0]);
        play();
    }

    public void updatedTerminal(){
        if(!currentPlayerTurn){
            view.matchCard.addTermLine(domainController.getPlayer1Name());
        } else {
            view.matchCard.addTermLine(domainController.getPlayer2Name());
        }
        view.matchCard.updatedScore(domainController.getScore());
    }


    public void play(){
        view.matchCard.updateN(domainController.getTurn());
        if(domainController.youAreDonete(!currentPlayerTurn)){
            if(view.matchCard.gameEnd(false) == 0){
                view.back();
            }
        }

        updatedTerminal();

        System.out.println("play");
        if((domainController.getTurn()%2) != 0){
            if(domainController.getPlayer1Type() != 0){
                System.out.println("p1m");
                humanMoves = false;
                System.out.println("play");
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                currentPlayerTurn = !currentPlayerTurn;
                play();
            } else {
                humanMoves = true;
            }
        } else {
            if(domainController.getPlayer2Type() != 0){
                System.out.println("p2m");
                humanMoves = false;
                System.out.println("play");
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                currentPlayerTurn = !currentPlayerTurn;
                play();
            } else {
                System.out.println("p2h");
                humanMoves = true;
            }
        }
    }

    public void createProblem(){
        int N = view.newProblemCard.getRounds();
        String FEN = view.newProblemCard.getFEN();
        int id = 0;
        try {
            id = domainController.createProblem(FEN, N);
        } catch (IOException e) {
            e.printStackTrace();
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
            } else if(evt.getActionCommand().equals(Actions.EXIT.name())){
                if(view.matchCard.exitDialog() == 0){
                    view.back();
                }
            } else if(evt.getActionCommand().equals(Actions.PROBLEM_MANAGER.name())){
                try {
                    view.menuCard.showProblemOptions(domainController.listProblems());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY1.name())) {
                view.menuCard.showDifficulty1();
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY2.name())) {
                view.menuCard.showDifficulty2();
            } else if(evt.getActionCommand().equals(Actions.NEW_PROBLEM.name())) {
                view.newProblem();
                view.newProblemCard.setOp("Create Problem!");
            } else if(evt.getActionCommand().equals(Actions.SET.name())){
                Tile currentTile = (Tile) evt.getSource();
                view.newProblemCard.move(currentTile);
            } else if(evt.getActionCommand().equals(Actions.RANKING.name())){
                try {
                    view.menuCard.showRankingOptions(domainController.listRankingsId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }  else if(evt.getActionCommand().equals(Actions.SCORES.name())){
                String str = view.menuCard.getIdRanking();
                String[] splitted = str.split("-");
                int idProblemRanking = Integer.parseInt(splitted[1]);
                try {
                    view.menuCard.showScores(domainController.topScores(idProblemRanking));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(evt.getActionCommand().equals(Actions.CREATE_PROBLEM.name())){
                createProblem();
                if(view.newProblemCard.endDialog("Done!") == 0){
                    view.back();
                }
            } else if(evt.getActionCommand().equals(Actions.MODIFY_PROBLEM.name())){
                String str = view.menuCard.getIdProblemToManage();
                String[] splitted = str.split("-");
                splitted = splitted[1].split("\\s");
                int idProblemToManage = Integer.parseInt(splitted[0]);
                try {
                    String FEN = domainController.getFENFromId(idProblemToManage);
                    splitted = FEN.split("\\s");
                    view.newProblemCard.setMatchBoard(splitted[0]);
                    view.newProblemCard.setOp("Modify Problem!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.newProblem();
            } else if(evt.getActionCommand().equals(Actions.DELETE_PROBLEM.name())){
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
            } else if(evt.getActionCommand().equals(Actions.CLONE_PROBLEM.name())){
                String str = view.menuCard.getIdProblemToManage();
                String[] splitted = str.split("-");
                splitted = splitted[1].split("\\s");
                int idProblemToManage = Integer.parseInt(splitted[0]);
                domainController.copyProblem(idProblemToManage);
            }
            if(matchStarted) {
                if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                    if (humanMoves) {
                        Tile currentTile = (Tile) evt.getSource();
                        ArrayList<String> movements = new ArrayList<String>();
                        String coords;
                        if (view.matchCard.tilesMove[0] == null) {
                            coords = currentTile.getTileX() + " " + currentTile.getTileY();
                            movements = domainController.getLegalMoves(coords);
                        } else {
                            coords = view.matchCard.tilesMove[0].getTileX() + " " + view.matchCard.tilesMove[0].getTileY();
                            movements = domainController.getLegalMoves(coords);
                        }

                        if (view.matchCard.tileAction(currentTile, currentPlayerTurn, movements)) {
                            String currentFEN = domainController.makeMove(view.matchCard.getTilesInMove()[0], view.matchCard.getTilesInMove()[1]);
                            view.matchCard.updateBoard(currentFEN);
                            currentPlayerTurn = !currentPlayerTurn;
                            view.matchCard.resetTilesInMove();
                            play();
                        }
                    }
                }
            }
        }
    }

}