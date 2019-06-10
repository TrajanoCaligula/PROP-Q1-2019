/**
 * @author Pau Charques
 */
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
    CREATE_PROBLEM,
    CREATE_FROM_FEN,
    MODIFY_PROBLEM,
    PROBLEM_VIEW,
    DELETE_PROBLEM,
    CLONE_PROBLEM,
    MOVE,
    SET,
    RANKING,
    SCORES,
    DIFFICULTY1,
    DIFFICULTY2,
    EXIT,
    END,
}

public class ChessView{


    private JPanel cards = new JPanel();

    public StartView menuCard;
    public MatchView matchCard;
    public ProblemView newProblemCard;


    public ChessView() {
        initializeGui();
    }

    /**
     * Initialize the GUI of the chess game. Creating three main cards: Menu, Board & Problem. Each card with its implementations.
     * Menu: sets the main menu,where you can check the existing problems (and modify them if wanted), rankings and create new matches
     * (choosing the problem, the players and the difficulty)
     * Board: sets the board from a given FEN and update it with every piece movement, checking every movement and the end of the match
     * as well as updates the turn counter, and points of every player.
     * Problem: allow the player to create a problem from zero, with an empty board and several pieces to choose from and
     * place them where ever you want. Then, the backtracking will check if exists a solution and if so it will be stored on the system
     */
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


    public final JComponent getGui() {
        return this.cards;
    }


    public void addActionListenerTiles(ActionListener mal) {
        menuCard.addActionListenerChess(mal);
        matchCard.addActionListenerBoard(mal);
        newProblemCard.addActionListenerBoard(mal);
    }

    /**
     * Sets the card "BOARD" in front and the board with the input FEN, so the player can start the match
     * @param matchFEN FEN of the new match you want to play
     */
    public void startMatch(String matchFEN){
        CardLayout cl = (CardLayout)(cards.getLayout());
        matchCard.initMatch(matchFEN);
        cl.show(cards, "BOARD");
    }

    public void back(){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "MENU");
    }

    /**
     * Let the player create a new problem and set the position of each piece and seating the card "PROBLEM" in front of
     * the others
     */
    public void newProblem(){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "PROBLEM");
    }

}
