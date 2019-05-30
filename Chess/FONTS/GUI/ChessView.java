package GUI;

import Jaume.*;
import java.io.File;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import static java.lang.Character.*;

enum Actions {
    PLAY,
    PLAY_OPTIONS,
    START,
    PROBLEM_MANAGER,
    NEW_PROBLEM,
    MODIFY_PROBLEM,
    MOVE,
    SET,
    RANKING,
    DIFFICULTY1,
    DIFFICULTY2,
    NAME
}

public class ChessView{


    private JPanel cards = new JPanel();

    public StartView menuCard;
    public MatchView matchCard;
    public ProblemView newProblemCard;


    public ChessView() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        cards.setLayout(new CardLayout());


        //MENU CARD
        menuCard = new StartView();
        cards.add(menuCard, "MENU");

        //BOARD CARD
        matchCard = new MatchView();
        cards.add(matchCard, "BOARD");

        //NEW PROBLEM CARD
        newProblemCard = new ProblemView();
        cards.add(newProblemCard, "PROBLEM");

        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "MENU");
    }



    public void setRankings(ArrayList<String> rankings){
        menuCard.setRankings(rankings);
    }

    public final JComponent getGui() {
        return this.cards;
    }


    public void addActionListenerTiles(ActionListener mal) {
        menuCard.addActionListenerChess(mal);
        matchCard.addActionListenerBoard(mal);
        newProblemCard.addActionListenerBoard(mal);
    }


    public void startMatch(String matchFEN){
        CardLayout cl = (CardLayout)(cards.getLayout());
        matchCard.setMatchBoard(matchFEN);
        cl.show(cards, "BOARD");
    }

    public void newProblem(){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "PROBLEM");
    }

}
