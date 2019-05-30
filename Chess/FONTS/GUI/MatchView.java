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

public class MatchView extends JPanel {

    private Tile[][] chessBoardSquares = new Tile[8][8];
    private JPanel chessBoard;
    private JLabel labelN;
    private JLabel labelScore;
    private JTextArea term;
    public Tile tileHighlighted = null;
    private Tile[] tilesMove = new Tile[2];
    public boolean turn = true;

    private static final String COLS = "abcdefgh";
    public static final int BLACK = 0, WHITE = 1;


    public MatchView() {

        this.setLayout(new BorderLayout(3, 3));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setPreferredSize(new Dimension(850, 500));
        this.setBackground(new Color(43, 43, 43));
        JPanel topBar = new JPanel(new FlowLayout(10, 150, 5));
        labelN = new JLabel("Round: 2", JLabel.LEFT);
        labelScore = new JLabel("Score: 34", JLabel.CENTER);
        topBar.setBackground(new Color(255, 255, 255));
        topBar.setBorder(BorderFactory.createLineBorder(Color.black));
        topBar.add(labelScore);
        topBar.add(labelN);
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.black));
        // Set the BG to be ochre
        chessBoard.setBackground(new Color(255, 255, 255));
        this.setBackground(new Color(63, 63, 68));
        this.add(chessBoard, BorderLayout.CENTER);
        this.add(topBar, BorderLayout.NORTH);

        term = new JTextArea();
        term.setForeground(Color.WHITE);
        term.setMargin( new Insets(2,2,2,2));
        Font font = new Font("Terminal", Font.BOLD,12);
        term.setFont(font);
        term.setEditable(false);
        term.setText("...Match Started!\n");
        term.setBackground(new Color(23, 23, 23));
        term.setPreferredSize(new Dimension(200, 0));
        this.add(term, BorderLayout.EAST);

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

                    b.setBackground(new Color(203, 155, 100));
                } else {
                    b.setBackground(new Color(105, 72, 30));
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

    public String[] getTilesInMove(){
        String[] tilesInMove = new String[2];
        tilesInMove[0] = this.tilesMove[0].getTileX() + " " + this.tilesMove[0].getTileY();
        tilesInMove[1] = this.tilesMove[1].getTileX() + " " + this.tilesMove[1].getTileY();
        return tilesInMove;
    }

    public void highilghtLegalMoves(ArrayList<String> tileLegalMoves){
        for(int i = 0; i < tileLegalMoves.size(); i++){
            int x = tileLegalMoves.get(i).charAt(0);
            int y = tileLegalMoves.get(i).charAt(1);
            chessBoardSquares[Character.getNumericValue(y)][Character.getNumericValue(x)].highlightTile();
        }
    }

    public void undoHighlightLegalMoves(ArrayList<String> tileLegalMoves){
        for(int i = 0; i < tileLegalMoves.size(); i++){
            int x = tileLegalMoves.get(i).charAt(0);
            int y = tileLegalMoves.get(i).charAt(1);
            chessBoardSquares[Character.getNumericValue(x)][Character.getNumericValue(y)].undoHighlightTile();
        }
    }

    public boolean tileAction(Tile pressedTile, boolean turn/*, ArrayList<String> tileLegalMoves*/){
        boolean moveMade = false;
        if(this.tileHighlighted == null){
            if ((pressedTile.getPiece() != null) && pieceYourColor(turn, pressedTile)){
                this.tileHighlighted = pressedTile;
                pressedTile.highlightTile();
                //highilghtLegalMoves(tileLegalMoves);
            }
        } else {
            this.tileHighlighted.undoHighlightTile();
            if(!pressedTile.equals(this.tileHighlighted)) {
                move(this.tileHighlighted, pressedTile);
                moveMade = true;
                this.turn = false;
                //highilghtLegalMoves(tileLegalMoves);
            }
            this.tileHighlighted = null;
        }
        return  moveMade;
    }

    public boolean pieceYourColor(Boolean turn, Tile piece){
        return ((!turn && Character.isLowerCase(piece.getPiece())) || (turn && Character.isUpperCase(piece.getPiece())));
    }


    public boolean updateBoard(String fen){
        boolean ended = false;
        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j = 0; j < chessBoardSquares[0].length; j++){
                chessBoardSquares[i][j].setPiece(null);
                chessBoardSquares[i][j].setIcon(null);
            }
        }
        setMatchBoard(fen);
        ended = true;
        return ended;
    }

    public void addTermLine(String lineToAdd){
        this.term.append(lineToAdd + "'s turn:\n");
    }

    public void updatedScore(int currentScore){
        this.labelScore.setText(String.valueOf(currentScore));
    }

    public void updateN(int currentTurn){
        this.labelN.setText(String.valueOf(currentTurn));
    }

    public void gameEnd(boolean won){
        if(won){
            JOptionPane.showMessageDialog(this,"You WON!");
        } else {
            JOptionPane.showMessageDialog(this,"You LOST!");
        }
    }
    public void move(Tile init, Tile end){
        tilesMove[0] = init;
        tilesMove[1] = end;

        this.chessBoardSquares[end.getTileY()][end.getTileX()].setIcon(init.getIcon());
        this.chessBoardSquares[end.getTileY()][end.getTileX()].setPiece(init.getPiece());
        this.chessBoardSquares[init.getTileY()][init.getTileX()].setIcon(null);
        this.chessBoardSquares[init.getTileY()][init.getTileX()].setPiece(null);
    }

    public void addActionListenerBoard(ActionListener mal) {
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                chessBoardSquares[jj][ii].setActionCommand(Actions.MOVE.name());
                chessBoardSquares[jj][ii].addActionListener(mal);
            }
        }
    }

}
