package GUI;

import Controllers.CtrlDomain;
import Jaume.Color;
import sun.jvm.hotspot.debugger.win32.coff.COFFLineNumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class ViewController{
    private ChessView view;
    private CtrlDomain domainController;
    private boolean matchStarted = false;
    private boolean humanMoves = false;
    private boolean currentPlayerTurn; //false - white, true - black

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


        if(player1type == null || player2type == null){
            //displayJOptionPane! error player not selected
        }

        String prob = view.menuCard.getidProblem();
        String[] splitted = prob.split("-");
        splitted = splitted[1].split("\\s");
        int idPr = Integer.parseInt(splitted[0]);

        String matchFEN = domainController.getFENFromId(idPr);
        splitted = matchFEN.split("\\s");
        domainController.newGameComplete(idPr, player1, player1type, 1, player2, player2type, 2);
        matchStarted = true;
        view.startMatch(splitted[0]);
        currentPlayerTurn = false;
        play();
    }

    public void updatedTerminal(){
        if(!currentPlayerTurn){
            view.matchCard.addTermLine(domainController.getPlayer1Name());
        } else {
            view.matchCard.addTermLine(domainController.getPlayer2Name());
        }
    }


    public void play(){
        view.matchCard.revalidate();
        view.matchCard.repaint();
        if(domainController.youAreDonete(!currentPlayerTurn)){
            view.matchCard.gameEnd(false);
            System.out.println("fdagfdsaf");
        }

        updatedTerminal();
        currentPlayerTurn = !currentPlayerTurn;
        if((domainController.getTurn()%2) != 0){
            if(domainController.getPlayer1Type() != 0){
                humanMoves = false;
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                view.matchCard.revalidate();
                view.matchCard.repaint();
                play();
            } else {
                humanMoves = true;
                view.matchCard.revalidate();
                view.matchCard.repaint();
            }
        } else {
            if(domainController.getPlayer2Type() != 0){
                humanMoves = false;
                String currentFEN = domainController.playMachine();
                view.matchCard.updateBoard(currentFEN);
                view.matchCard.revalidate();
                view.matchCard.repaint();
                play();
            } else {
                humanMoves = true;
                view.matchCard.revalidate();
                view.matchCard.repaint();
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
            } else if (evt.getActionCommand().equals(Actions.RANKING.name())) {
                String idP = view.menuCard.getSelectedItem();
                ArrayList<String> scores = new ArrayList<>();
                try {
                    scores = domainController.topScores(Integer.parseInt(idP));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.menuCard.showScores(scores);
            } else if(evt.getActionCommand().equals(Actions.PROBLEM_MANAGER.name())){
                view.menuCard.showProblemOptions();
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY1.name())) {
                view.menuCard.showDifficulty1();
            } else if(evt.getActionCommand().equals(Actions.DIFFICULTY2.name())) {
                view.menuCard.showDifficulty2();
            } else if(evt.getActionCommand().equals(Actions.NEW_PROBLEM.name())) {
                view.newProblem();
            } else if(evt.getActionCommand().equals(Actions.SET.name())){
                Tile currentTile = (Tile) evt.getSource();
                view.newProblemCard.move(currentTile);
            } else if(evt.getActionCommand().equals(Actions.RANKING.name())){
                view.menuCard.showRankingOptions(domainController.getRankingsId);
            }
            if(matchStarted) {
                if (humanMoves) {
                    if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                        Tile currentTile = (Tile) evt.getSource();
                        String coords = (String.valueOf(currentTile.getTileX()) + " " + String.valueOf(currentTile.getTileY()));
                        System.out.println(coords);
                        if(view.matchCard.tileAction(currentTile, currentPlayerTurn, domainController.getLegalMoves(coords))) {
                            String currentFEN = domainController.makeMove(view.matchCard.getTilesInMove()[0], view.matchCard.getTilesInMove()[1]);
                            view.matchCard.updateBoard(currentFEN);
                            view.matchCard.revalidate();
                            view.matchCard.repaint();
                            play();
                        }
                    }
                }
            }
        }
    }


}