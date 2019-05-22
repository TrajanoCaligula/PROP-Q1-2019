package GUI;

import Pau.Match;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ViewController{
    private Match currentMatch;
    private MatchView view;

    public ViewController(MatchView currentView) {
        this.view = currentView;
        view.addMouseListenerToTile(new MouseListenerTile());
    }

    public void move(ActionEvent ae){
        Tile tile = (Tile) ae.getSource();
        view.tileAction(tile);
    }




    class MouseListenerTile implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            view.tileAction((Tile) e.getSource());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}