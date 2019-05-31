package GUI;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.border.*;

import static java.lang.Character.*;

public class ProblemView extends JPanel {

    private Tile[][] chessBoardSquares = new Tile[8][8];
    private JPanel chessBoard;
    private JTextField round = new JTextField("2");
    String[] players = { "White", "Black"};
    private JComboBox firstPlayer = new JComboBox(players);

    String[] difficultys = { "Easy", "Hard"};
    private JComboBox difficulty = new JComboBox(difficultys);
    private JPanel piecePicker;
    private Tile[] pieces = new Tile[12];
    public Tile pieceSelected = null;
    private JButton createProblem = new JButton();

    private static final String COLS = "abcdefgh";
    public static final int BLACK = 0, WHITE = 1;

    public ProblemView() {

        this.setLayout(new BorderLayout(3, 3));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setPreferredSize(new Dimension(850, 500));
        this.setBackground(new Color(43, 43, 43));
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 5));
        topBar.setBackground(new Color(211, 212, 209));
        topBar.setBorder(BorderFactory.createLineBorder(Color.black));
        round.setPreferredSize(new Dimension(60,26));
        round.setHorizontalAlignment(SwingConstants.RIGHT);
        topBar.add(new JLabel("Round:"));
        topBar.add(round);
        topBar.add(new JLabel("First player:"));
        topBar.add(firstPlayer);
        topBar.add(new JLabel("Difficulty:"));
        topBar.add(difficulty);
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.black));
        // Set the BG to be ochre
        chessBoard.setBackground(new Color(211, 212, 209));
        this.setBackground(new Color(63, 63, 68));
        this.add(chessBoard, BorderLayout.CENTER);
        this.add(topBar, BorderLayout.NORTH);

        JPanel eastPanel = new JPanel(new BorderLayout());
        piecePicker = new JPanel(new GridLayout(0,2));
        piecePicker.setBackground(new Color(43, 43, 43));
        piecePicker.setPreferredSize(new Dimension(200, 0));
        eastPanel.add(piecePicker);
        eastPanel.add(createProblem, BorderLayout.SOUTH);
        eastPanel.setBackground(new Color(43, 43, 43));
        this.add(eastPanel, BorderLayout.EAST);

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

    public void setOp(String c_m){
        this.createProblem.setText(c_m);
    }

    public int endDialog(String message){
        int input = JOptionPane.showOptionDialog(this, message, "Problem Manager", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if(input == JOptionPane.OK_OPTION)
        {

        }
        return input;
    }

    public void setPiecePicker(){
        String path = "../PROP4/Chess/FONTS/assets/pieces";
        File[] pieceIcons = new File("../PROP4/Chess/FONTS/assets/pieces").listFiles();
        int i = 0;
        for(File file : pieceIcons) {
            String pieceIcon = file.getName();
            Tile currenTile = new Tile(new ImageIcon(path + "/" + pieceIcon), i);
            currenTile.setPiece(getPieceFromIcon(pieceIcon));
            pieces[i] = currenTile;
            piecePicker.add(pieces[i]);
            i++;
        }
    }

    public final void setMatchBoard(String matchFEN){
        int i = 0, y = 0, x = 0;
        Character c;
        while (i < matchFEN.length()) {
            c = matchFEN.charAt(i);
            if (isDigit(c)) {
                x += getNumericValue(c);
            }
            else if (isLetter(c)) {
                //UpperCase
                setPiece(c, x, y);
                x++;
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

    private Character getPieceFromIcon(String iconFile){
        if(iconFile.equals("black_bishop.png")){
            return 'b';
        } else if(iconFile.equals("black_king.png")){
            return 'k';
        } else if(iconFile.equals("black_knight.png")){
            return 'n';
        } else if(iconFile.equals("black_pawn.png")){
            return 'p';
        } else if(iconFile.equals("black_queen.png")){
            return 'q';
        } else if(iconFile.equals("black_rook.png")){
            return 'r';
        } else if(iconFile.equals("white_bishop.png")){
            return 'B';
        } else if(iconFile.equals("white_king.png")){
            return 'K';
        } else if(iconFile.equals("white_knight.png")){
            return 'N';
        } else if(iconFile.equals("white_pawn.png")){
            return 'P';
        } else if(iconFile.equals("white_queen..png")){
            return 'Q';
        } else if(iconFile.equals("white_rook.png")){
            return 'R';
        }
        return null;
    }

    public void move(Tile pressedTile){
        System.out.println(pressedTile.isPicker());
        if(pieceSelected == null)
            pieceSelected = pressedTile;
        else {
            this.chessBoardSquares[pressedTile.getTileY()][pressedTile.getTileX()].setIcon(pieceSelected.getIcon());
            this.chessBoardSquares[pressedTile.getTileY()][pressedTile.getTileX()].setPiece(pieceSelected.getPiece());
            if(!pieceSelected.isPicker()){
                this.chessBoardSquares[pieceSelected.getTileY()][pieceSelected.getTileX()].setIcon(null);
                this.chessBoardSquares[pieceSelected.getTileY()][pieceSelected.getTileX()].setPiece('0');
            }
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
        createProblem.setActionCommand(Actions.CREATE_PROBLEM.name());
        createProblem.addActionListener(mal);
    }
    //      1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B w - - 0 1

    public String getFEN(){
        String FEN = "";
        int whiteTiles = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Tile currentTile = chessBoardSquares[i][j];
                if(!currentTile.getPiece().equals('0')){
                    if(whiteTiles != 0){
                        FEN += whiteTiles;
                        whiteTiles = 0;
                    }
                    FEN += currentTile.getPiece();
                } else {
                    whiteTiles++;
                }
            }
            if(whiteTiles != 0){
                FEN += whiteTiles;
                whiteTiles = 0;
            }
            FEN += "/";
        }
        FEN = FEN.substring(0, FEN.length() - 1);
        System.out.println(FEN);

        FEN = FEN + " " + getFirstPlayer() + " - - 0 1";
        return FEN;
    }

    public String getFirstPlayer(){
        if(firstPlayer.getSelectedItem().equals("Black")){
            return "b";
        } else {
            return "w";
        }
    }
    public String getDifficulty(){
        return (String) difficulty.getSelectedItem();
    }
    public int getRounds(){
        return Integer.parseInt(round.getText());
    }
}

