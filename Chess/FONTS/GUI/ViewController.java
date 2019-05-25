package GUI;

import Controllers.CtrlDomain;
import Pau.Match;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ViewController{
    private Match currentMatch;
    private MatchView view;
    private CtrlDomain domainController;

    public ViewController(MatchView currentView) {
        this.view = currentView;
        view.addActionListenerChess(new ActionListenerChess());
        domainController = CtrlDomain.getInstance();
    }

    public void move(ActionEvent ae){
        Tile tile = (Tile) ae.getSource();
        view.tileAction(tile);
    }



    class ActionListenerChess implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals(Actions.MOVE.name())) {
                view.tileAction((Tile) evt.getSource());
            } else if(evt.getActionCommand().equals(Actions.PLAY.name())){
                view.showPlayOptions();
            }
        }
    }


}