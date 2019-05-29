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
    START,
    PROBLEM_MANAGER,
    MOVE,
    RANKING,
    DIFFICULTY1,
    DIFFICULTY2,
    NAME
}

public class ChessView{


    JFrame frame = new JFrame("CardLayout demo");
    private JPanel cards = new JPanel();

    public StartView menuCard;
    public MatchView matchCard;


    public ChessView() {
        initializeGui();
        //setMatchGui();
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

        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "MENU");
    }



    public void setProblems(ArrayList<String> problems){
        menuCard.setProblems(problems);
    }


    public final JComponent getGui() {
        return this.cards;
    }


    public void addActionListenerTiles(ActionListener mal) {
        menuCard.addActionListenerChess(mal);
        matchCard.addActionListenerBoard(mal);
    }


    public void startMatch(String matchFEN){
        CardLayout cl = (CardLayout)(cards.getLayout());
        matchCard.setMatchBoard(matchFEN);
        cl.show(cards, "BOARD");
    }

}
