package GUI;


import javax.swing.*;
import java.awt.*;
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

public class ProblemView extends JPanel {

    private Tile[][] chessBoardSquares = new Tile[8][8];
    private JPanel chessBoard;
    private JPanel piecePicker;
    private Tile[] pieces = new Tile[16];
    private Tile[] tilesMove = new Tile[2];
    public Tile pieceSelected = null;

    private static final String COLS = "abcdefgh";
    public static final int BLACK = 0, WHITE = 1;


    public ProblemView() {

        this.setLayout(new BorderLayout(3, 3));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setPreferredSize(new Dimension(850, 500));
        this.setBackground(new Color(43, 43, 43));
        JPanel topBar = new JPanel(new FlowLayout(10, 150, 5));
        topBar.setBackground(new Color(211, 212, 209));
        topBar.setBorder(BorderFactory.createLineBorder(Color.black));
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.black));
        // Set the BG to be ochre
        chessBoard.setBackground(new Color(211, 212, 209));
        this.setBackground(new Color(63, 63, 68));
        this.add(chessBoard, BorderLayout.CENTER);
        this.add(topBar, BorderLayout.NORTH);

        piecePicker = new JPanel(new GridLayout(0,2));
        piecePicker.setBackground(new Color(23, 23, 23));
        piecePicker.setPreferredSize(new Dimension(200, 0));
        this.add(piecePicker, BorderLayout.EAST);

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

                    b.setBackground(new Color(162, 131, 91));
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

        setPiecePicker();

    }

    public void setPiecePicker(){
        String path = "../PROP4/Chess/FONTS/assets/pieces";
        File[] pieceIcons = new File("../PROP4/Chess/FONTS/assets/pieces").listFiles();
        int i = 0;
        for(File file : pieceIcons) {
            String pieceIcon = file.getName();
            Tile currenTile = new Tile(new ImageIcon(path + "/" + pieceIcon), i);
            pieces[i] = currenTile;
            piecePicker.add(pieces[i]);
            i++;
        }
    }

    public void move(Tile pressedTile){
        if(pieceSelected == null)
            pieceSelected = pressedTile;
        else {
            this.chessBoardSquares[pressedTile.getTileY()][pressedTile.getTileX()].setIcon(pieceSelected.getIcon());
            this.chessBoardSquares[pressedTile.getTileY()][pressedTile.getTileX()].setPiece(pressedTile.getPiece());
            pieceSelected = null;
        }
    }

    public void addActionListenerBoard(ActionListener mal) {
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                chessBoardSquares[jj][ii].setActionCommand(Actions.SET.name());
                chessBoardSquares[jj][ii].addActionListener(mal);
            }
        }
        for(int i = 0; i < pieces.length; i++){
            pieces[i].setActionCommand(Actions.SET.name());
            pieces[i].addActionListener(mal);
        }
    }
}

