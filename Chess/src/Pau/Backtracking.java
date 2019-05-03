/**
 * @author pauCharques
 */
package Pau;

import java.util.*;

import Jaume.*;

public class Backtracking extends Match{

    /**
     * Constructor
     * @param problemToPlay problem you want to validat, see if it has a solution.
     * @param round The number of maximum rounds you can play.
     * @param firstColor the player who starts to move.
     */
    public Backtracking(Problem problemToPlay, Integer round, Color firstColor){
        super(null, null, problemToPlay, round, firstColor);
    }

    /**
     * Second part of the backtracking algorithm
     * @param pieceBT the currentpiece you are gonna use to iterate through its legal movements
     * @param boardBT the board where the piece is
     * @return Returns true if it has a solution, false otherwise.
     */
    public boolean backtrackingUtil(Piece pieceBT, Board boardBT) {

        ArrayList<Coord> whiteMoves= pieceBT.getLegalMoves(boardBT);

        if(whiteMoves.isEmpty()) { return false; }

        boolean gameOver = false;

        for(int i=0; i < whiteMoves.size() && !gameOver; i++) {
            Board imaginaryBoardMove = new Board(boardBT);

            if (isGameOver(imaginaryBoardMove, Color.BLACK)) {
                return true;
            }

            imaginaryBoardMove.movePiece(pieceBT, whiteMoves.get(i));
            ArrayList<Piece> blackPieces = imaginaryBoardMove.getBlackPieces();

            for (int j = 0; j < blackPieces.size() && gameOver; j++) {
                Piece blackPiece = blackPieces.get(j);

                ArrayList<Coord> blackMoves = blackPiece.getLegalMoves(imaginaryBoardMove);

                if (!blackMoves.isEmpty()) {
                    for (int k = 0; k < blackMoves.size() && gameOver; ++k) {

                        Board imaginaryBoardMoveBlack = new Board(imaginaryBoardMove);
                        imaginaryBoardMoveBlack.movePiece(blackPiece, blackMoves.get(i));

                        if (isGameOver(imaginaryBoardMove, Color.BLACK)) {
                            gameOver = true;
                        }
                        else {
                            gameOver = backtracking();
                        }
                    }
                }

            }
        }
        return true;

    }

    /**
     * Checks if the player is in check mate
     * @param board the board with the distribution of the pieces
     * @param color the color of the player to see if its in check mate
     * @return Returns true if color is in checkmate, false otherwise.
     */
    private boolean isGameOver(Board board, Color color){
        if (color == Color.BLACK) {
            if (board.getBlackPieces().size() == 1)
                return true;
            ArrayList<Piece> blackPieces = board.getBlackPieces();
            for (Piece blackPiece : blackPieces) {
                if (!blackPiece.getLegalMoves(board).isEmpty())
                    return false;
            }
            return true;
        }
        else //if (color == Color.WHITE)
        {
            if (board.getWhitePieces().size() == 1)
                return true;
            ArrayList<Piece> whitePieces = board.getWhitePieces();
            for (Piece whitePiece : whitePieces) {
                if (!whitePiece.getLegalMoves(board).isEmpty())
                    return false;
            }
        }
        return true;
    }
    /**
     * Retorna el moviment amb mes possibilitats de guanyar si n'hi ha. Si no retorna null
     * @return Array que conte origen i desti de la fitxa que s'hauria de moure en aquell torn.
     */

    /**
     * The caller function and the first part of the backtracking algorithm used to check if a problem has a solution.
     * @return Returns true if it does have a solution, false otherwise
     */
    public boolean backtracking(){

        ArrayList<Piece> pieces = this.getBoard().getWhitePieces();

        boolean gameover = false;
        for (int i = 0; i < pieces.size() && !gameover; i++) {
            Piece currentPiece = pieces.get(i);

            Board imaginaryBoard = new Board(this.getBoard());
            gameover = backtrackingUtil(currentPiece, imaginaryBoard);
        }
        return gameover;
    }

}
