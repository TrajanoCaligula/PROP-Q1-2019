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
    private Match currentMatch;
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
        view.boardCard.tileAction(tile);
    }



    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                view.boardCard.tileAction((Tile) evt.getSource());
            } else if(evt.getActionCommand().equals(Actions.PLAY.name())){
                view.menuCard.showPlayOptions();
            } else if(evt.getActionCommand().equals(Actions.START.name())){
                view.startMatch();
            } else if(evt.getActionCommand().equals(Actions.RANKING.name())){
                String idP = view.menuCard.getSelectedItem();
                ArrayList<String> scores = new ArrayList<>();
                try {
                    scores = domainController.topScores(Integer.parseInt(idP));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                view.menuCard.showScores(scores);
            }
        }
    }


}