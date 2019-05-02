package Pau;

import java.util.*;

import Calin.Human;
import Calin.Player;
import Jaume.*;

public class Backtracking extends Match{
    public Backtracking(Problem problemToPlay, Integer round, Color firstColor){
        super(null, null, problemToPlay, round, firstColor);
    }

    public boolean backtrackingUtil(Piece pieceBT, Board boardBT, int torn) {

        ArrayList<Coord> whiteMoves= pieceBT.getLegalMoves(boardBT);

        if(whiteMoves.isEmpty()) { return false; }

        //Per a tots els moviments possibles
        boolean gameOver = false;
        for(int i=0; i < whiteMoves.size() && !gameOver; i++) {
            Board imaginaryBoardMove = new Board(boardBT);
            //if (!imaginaryBoardMove.moviment(f.getPosicio(), movimentsPossibles.get(i)))
               // System.out.println("ERROR MOVIMENT A TAULELL");
            //Si hi ha un jaque mate cap al contrari retorna true
            if (isGameOver(imaginaryBoardMove, Color.BLACK)) {
                return true;
            }
            imaginaryBoardMove.movePiece(pieceBT, whiteMoves.get(i));

            ArrayList<Piece> blackPieces = imaginaryBoardMove.getBlackPieces();
            //per a tots els contrincants possibles

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
                            gameOver = false;
                            gameOver = backtracking();

                        }
                    }
                }

            }
        }
        return true;

    }

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

    public boolean backtracking(){

        ArrayList<Piece> pieces = this.getBoard().getWhitePieces();

        boolean gameover = false;
        for (int i = 0; i < pieces.size() && !gameover; i++) {
            Piece currentPiece = pieces.get(i);

            Board imaginaryBoard = new Board(this.getBoard());
            gameover = backtrackingUtil(currentPiece, imaginaryBoard, this.getRound());
        }
        return gameover;
    }
}
