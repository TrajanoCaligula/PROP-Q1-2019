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
    HELLO,
    GOODBYE
}

public class MatchView{


    JFrame frame = new JFrame("CardLayout demo");
    private JPanel cards = new JPanel();

    final static String TEXTPANEL = "MENU";
    JButton playButton = new JButton("Start Match");
    JButton problemsButton = new JButton("Problems");
    JButton rankingsButton = new JButton("Rankings");
    private final JPanel menuCard = new JPanel();
    private Tile[][] chessBoardSquares = new Tile[8][8];

    final static String BUTTONPANEL = "BOARD";
    private final JPanel boardCard = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private String matchFEN;
    private JLabel labelN;
    private JLabel labelTime;
    private Tile tileHighlighted = null;


    private static final String COLS = "abcdefgh";
    public static final int BLACK = 0, WHITE = 1;

    public MatchView(String matchFEN) {
        initializeGui();
        this.matchFEN = matchFEN;
        setMatchGui();

    }

    public final void initializeGui() {
        // create the images for the chess pieces

        // set up the main GUI
        cards.setLayout(new CardLayout());

        //Start Card
        menuCard.setLayout(new BorderLayout());
        JLabel title = new JLabel("Chess");
        title.setFont(new Font("Serif", Font.PLAIN, 65));
        Border border = title.getBorder();
        Border margin = new EmptyBorder(30,15,10,10);
        title.setBorder(new CompoundBorder(border, margin));
        menuCard.add(title, BorderLayout.NORTH);

        JPanel botonesInit = new JPanel(new GridLayout(0,2));
        botonesInit.add(playButton);
        botonesInit.add(problemsButton);
        botonesInit.add(rankingsButton);

        menuCard.add(botonesInit, BorderLayout.CENTER);


        cards.add(menuCard, "MENU");



        //BOARD CARD
        boardCard.setBorder(new EmptyBorder(5, 5, 5, 5));
        boardCard.setPreferredSize(new Dimension(500, 500));
        boardCard.setBackground(new Color(43,43,43));
        JPanel topBar = new JPanel(new FlowLayout(10, 150, 5));
        labelN = new JLabel("Username :", JLabel.LEFT);
        labelTime = new JLabel("1:32", JLabel.CENTER);
        topBar.setBackground(new Color(211, 212, 209));
        topBar.setBorder(BorderFactory.createLineBorder(Color.black));
        topBar.add(labelTime);
        topBar.add(labelN);
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.black));
        // Set the BG to be ochre
        chessBoard.setBackground(new Color(211, 212, 209));
        boardCard.setBackground(new Color(63, 63, 68));
        boardCard.add(chessBoard, BorderLayout.CENTER);
        boardCard.add(topBar, BorderLayout.NORTH);
        cards.add(boardCard, "BOARD");
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "MENU");

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                Tile b = new Tile(ii, jj);
                b.setBorderPainted(false);
                b.setOpaque(true);
                //b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {

                    b.setBackground(new Color(162, 115, 60));
                } else {
                    b.setBackground(new Color(74, 48, 23));
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    public void setN(String nTofuck){
        this.labelN.setText(nTofuck);
    }

    public final void setMatchGui(){
        int i = 0, y = 0, x = 0;
        Character c;
        while (i < this.matchFEN.length()) {
            c = this.matchFEN.charAt(i);
            if (isDigit(c)) {
                x += getNumericValue(c);
            }
            else if (isLetter(c)) {
                //UpperCase
                setPiece(c, x, y);
            }
            else if(c == '/') {
                y++;
                x = 0;
            }
            i++;
        }
    }


    public void setPiece(char c, int x, int y){
        if (c == 'P')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_pawn.png"));
        else if (c == 'R')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_rook.png"));
        else if (c == 'N')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_knight.png"));
        else if (c == 'B')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_bishop.png"));
        else if (c == 'Q')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_queen.png"));
        else if (c == 'K')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/white_king.png"));

            //LowerCase
        else if (c == 'p')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_pawn.png"));
        else if (c == 'r')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_rook.png"));
        else if (c == 'n')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_knight.png"));
        else if (c == 'b')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_bishop.png"));
        else if (c == 'q')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_queen.png"));
        else if (c == 'k')
            chessBoardSquares[x][y].setIcon(new ImageIcon("../PROP4/Chess/FONTS/assets/pieces/black_king.png"));
        chessBoardSquares[x][y].setPiece(c);
        x++;
    }
    public final JComponent getGui() {
        return this.cards;
    }

    public void tileAction(Tile pressedTile){
        if(this.tileHighlighted == null){
            if (pressedTile.getPiece() != null){
                this.tileHighlighted = pressedTile;
                pressedTile.highlightTile();
            }
        } else {
            this.tileHighlighted.undoHighlightTile();
            if(!pressedTile.equals(this.tileHighlighted)) {
                move(this.tileHighlighted, pressedTile);
            }
            this.tileHighlighted = null;
        }
    }

    public void move(Tile init, Tile end){

        this.chessBoardSquares[end.getTileY()][end.getTileX()].setIcon(init.getIcon());
        this.chessBoardSquares[end.getTileY()][end.getTileX()].setPiece(init.getPiece());
        this.chessBoardSquares[init.getTileY()][init.getTileX()].setIcon(null);
        this.chessBoardSquares[init.getTileY()][init.getTileX()].setPiece(null);
    }

    public void addActionListenerChess(ActionListener mal) {
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                chessBoardSquares[jj][ii].setActionCommand(Actions.HELLO.name());
                chessBoardSquares[jj][ii].addActionListener(mal);
            }
        }

        playButton.setActionCommand(Actions.GOODBYE.name());
        playButton.addActionListener(mal);
    }

    public void changeState(){
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "BOARD");
    }

}
