package GUI;

import Controllers.CtrlDomain;
import Pau.Match;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;


public class ViewController{
    private ChessView view;
    private CtrlDomain domainController;

    public ViewController(ChessView currentView) throws IOException {
        this.view = currentView;
        domainController = CtrlDomain.getInstance();

        this.view.addActionListenerTiles(new ActionListenerChess());
        this.view.setProblems(domainController.listProblems());
    }

    public void move(ActionEvent ae){
        Tile tile = (Tile) ae.getSource();
        view.matchCard.tileAction(tile);
    }

    public String[] humanMoves(){
        view.matchCard.turn = true;

        while(view.matchCard.turn){
            //waiting for player inputs
        }

        String[] tilesMove = view.matchCard.getTilesInMove();
        return tilesMove;
    }


    public void update(String updatedFEN, int score, int N, String playersNameTurn){
        view.matchCard.setMatchBoard(updatedFEN);
        view.matchCard.updatedScore(score);
        view.matchCard.updateN(N);

        String toAppend = playersNameTurn + "'s turn:";
        view.matchCard.addTermLine(toAppend);
        if(playersNameTurn.equals("Machine")){
            view.matchCard.addTermLine("Thinking...");
        }
    }

    public void startMatch(){
        String[] players = view.menuCard.getPlayers();
        int idProblem = view.menuCard.getidProblem();
        /*
        String matchFEN = domainController.getFENFromId(idProblem);
        domainController.newGame(idProblem, Players);
        view.startMatch(matchFEN);
         */
    }


    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {

            if(evt.getActionCommand().equals(Actions.NAME.name())){
                System.out.println("HEdd");
                JRadioButton humanSelected = (JRadioButton) evt.getSource();
                if(humanSelected.getUIClassID().equals(view.menuCard.getP1id())){
                    view.menuCard.showInputPlayer("Player1");
                } else {
                    view.menuCard.showInputPlayer("Player2");
                }
            }
            if (view.matchCard.turn) {
                if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                    view.matchCard.tileAction((Tile) evt.getSource());
                } else if (evt.getActionCommand().equals(Actions.PLAY.name())) {
                    view.menuCard.showPlayOptions();
                } else if (evt.getActionCommand().equals(Actions.START.name())) {
                    startMatch();
                } else if (evt.getActionCommand().equals(Actions.RANKING.name())) {
                    String idP = view.menuCard.getSelectedItem();
                    ArrayList<String> scores = new ArrayList<>();
                    try {
                        scores = domainController.topScores(Integer.parseInt(idP));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    view.menuCard.showScores(scores);
                }
            } else {

            }
        }
    }


}