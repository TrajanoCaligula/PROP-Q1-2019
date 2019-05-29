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
    private String currentFEN;

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


    public void update(){

        /*
        view.matchCard.setMatchBoard();
        view.matchCard.updatedScore(score);
        view.matchCard.updateN(N);

        String toAppend = playersNameTurn + "'s turn:";
        view.matchCard.addTermLine(toAppend);
        if(playersNameTurn.equals("Machine")){
            view.matchCard.addTermLine("Thinking...");
        }
        */
    }

    public void startMatch() throws IOException {
        String[] players = view.menuCard.getPlayers();
        String prob = view.menuCard.getidProblem();
        String[] splitted = prob.split("-");
        splitted = splitted[1].split("\\s");
        int idPr = Integer.parseInt(splitted[0]);

        String matchFEN = domainController.getFENFromId(idPr);
        splitted = matchFEN.split("\\s");
        domainController.newGameComplete(idPr, "Pau", 0, -1, "Jaume", 1, 2);
        view.startMatch(splitted[0]);

    }


    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {

            if(evt.getActionCommand().equals(Actions.NAME.name())){
                JRadioButton humanSelected = (JRadioButton) evt.getSource();
                if(humanSelected.getUIClassID().equals(view.menuCard.getP1id())){
                    view.menuCard.showInputPlayer("Player1");
                } else {
                    view.menuCard.showInputPlayer("Player2");
                }
            } else if (evt.getActionCommand().equals(Actions.PLAY.name())) {
                view.menuCard.showPlayOptions();
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
            }
            if (!((domainController.getTurn()%2) == 0)) {
                if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                    if(view.matchCard.tileAction((Tile) evt.getSource())){
                        String fenUp = domainController.makeMove(view.matchCard.getTilesInMove()[0], view.matchCard.getTilesInMove()[1]);
                        System.out.println(fenUp);
                    }
                }
            } else {

            }
        }
    }


}