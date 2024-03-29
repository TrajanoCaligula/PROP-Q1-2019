/**
 * @author Pau Charques
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import java.util.ArrayList;

import static java.lang.Character.*;

/**
 * This class displays the match created, it's explained with more detail in our SegonLliurament.pdf
 */
public class MatchView extends JPanel {

    private Tile[][] chessBoardSquares = new Tile[8][8];
    private JPanel chessBoard;
    private JLabel labelTurn;
    private JButton exitButton = new JButton("Exit");
    private JLabel labelScore;
    private JTextArea term;
    private JLabel labelN = new JLabel("N: ");

    private static final String COLS = "abcdefgh";
    public static final int BLACK = 0, WHITE = 1;


    public MatchView() {
        this.setLayout(new BorderLayout(3, 3));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setPreferredSize(new Dimension(850, 500));
        this.setBackground(new Color(43, 43, 43));
        JPanel topBar = new JPanel(new FlowLayout(SwingConstants.RIGHT, 10, 5));
        labelTurn = new JLabel("0", JLabel.LEFT);
        labelScore = new JLabel("0", JLabel.CENTER);
        topBar.setBackground(new Color(255, 255, 255));
        topBar.setBorder(BorderFactory.createLineBorder(Color.black));
        topBar.add(new JLabel("Score:"));
        topBar.add(labelScore);
        topBar.add(new JLabel("Round:"));
        topBar.add(labelTurn);
        topBar.add(labelN);
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.black));
        chessBoard.setBackground(new Color(255, 255, 255));
        this.setBackground(new Color(63, 63, 68));
        this.add(chessBoard, BorderLayout.CENTER);
        this.add(topBar, BorderLayout.NORTH);

        JPanel rightBar = new JPanel(new BorderLayout());
        rightBar.setBackground(new Color(23, 23, 23));
        term = new JTextArea();
        term.setForeground(Color.WHITE);
        term.setMargin( new Insets(2,2,2,2));
        Font font = new Font("Terminal", Font.BOLD,12);
        term.setFont(font);
        term.setEditable(false);
        term.setText("...Match Started!\n");
        term.setBackground(new Color(23, 23, 23));
        term.setPreferredSize(new Dimension(200, 0));
        rightBar.add(term);
        rightBar.add(exitButton, BorderLayout.SOUTH);
        this.add(rightBar, BorderLayout.EAST);

        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                Tile b = new Tile(ii, jj);
                b.setBorderPainted(false);
                b.setOpaque(true);

                ImageIcon icon = new ImageIcon(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
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
            chessBoard.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
        }

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


    public void highilghtLegalMoves(ArrayList<String> tileLegalMoves){
        for(int i = 0; i < tileLegalMoves.size(); i++){
            int x = tileLegalMoves.get(i).charAt(0);
            int y = tileLegalMoves.get(i).charAt(1);
            chessBoardSquares[Character.getNumericValue(x)][Character.getNumericValue(y)].highlightTile();
        }
    }


    public void undoHighlightLegalMoves(ArrayList<String> tileLegalMoves){
        for(int i = 0; i < tileLegalMoves.size(); i++){
            int x = tileLegalMoves.get(i).charAt(0);
            int y = tileLegalMoves.get(i).charAt(1);
            chessBoardSquares[Character.getNumericValue(x)][Character.getNumericValue(y)].undoHighlightTile();
        }
    }


    public boolean legalMove(ArrayList<String> legalMoves, String move){
        boolean valid = false;
        for(int  i = 0; i < legalMoves.size() && !valid; i++){
            String aux = legalMoves.get(i).charAt(0) + " " + legalMoves.get(i).charAt(1);
            if(aux.equals(move))
                valid = true;
        }
        return valid;
    }


    public void setBoard(String fen){
        for(int i = 0; i < chessBoardSquares.length; i++){
            for(int j = 0; j < chessBoardSquares[0].length; j++){
                chessBoardSquares[i][j].setPiece(null);
                chessBoardSquares[i][j].setIcon(null);
            }
        }
        setMatchBoard(fen);
    }

    public void addTermLine(String lineToAdd){
        this.term.append(lineToAdd + "'s turn\n");
    }

    public void updatedScore(int currentScore){
        this.labelScore.setText(String.valueOf(currentScore));
    }

    public void updateN(int currentTurn){
        this.labelTurn.setText(String.valueOf(currentTurn));
    }

    public int gameEnd(String playerWhoWon){
        int input;
        input = JOptionPane.showOptionDialog(this, playerWhoWon + " WIN!", " END", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        return input;
    }

    public int gameOver(){
        int input;
        input = JOptionPane.showOptionDialog(this, "Game Over!", " END", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        return input;
    }

    public void addActionListenerBoard(ActionListener mal) {
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                chessBoardSquares[jj][ii].setActionCommand(Actions.MOVE.name());
                chessBoardSquares[jj][ii].addActionListener(mal);
            }
        }
        exitButton.setActionCommand(Actions.EXIT.name());
        exitButton.addActionListener(mal);
    }

    public int exitDialog(){
        int input = JOptionPane.showOptionDialog(this, "Are you sure you want to exit the game?", "Match", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        return input;
    }

    public void resetTerm(){
        this.term.setText("");
    }

    public void setN(String N){
        this.labelN.setText("N: " + N);
    }
}
