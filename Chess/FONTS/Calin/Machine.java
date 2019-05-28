package Calin;

import Jaume.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.*;

/**
 * @author calinPirau
 */

public class Machine extends Player {

    private int depth;

    /**
     * Machine constructor, which gives its name, color and depth
     * @param name: the name of the Machine
     * @param color: the color of the Machine
     * @param depth: the depth of the Machine used for minimax
     */

    public Machine(String name, Color color, int depth) {
        super(name, color);
        this.depth = depth;
    }

    /**
     * In this case, the computer has to decide which move to make, so we added the minimax AI to make it happen
     * @param board: the Board in which the Machine plays
     * @param color: the color of the pieces of Machine
     */

    @Override
    public void play(Board board, int score, Color color, Piece p, Coord c) {
        long start = System.currentTimeMillis();
        ArrayList<Piece> myPieces;
        if (color == Color.WHITE)
            myPieces = board.getWhitePieces();
        else //if (color == Color.BLACK
            myPieces = board.getBlackPieces();
        int value;
        if (color == Color.BLACK) {
            value = MAX_VALUE;
        }
        else
            value = MIN_VALUE;

        ArrayList<Move> bestMoves = new ArrayList<>();

        for (Piece myPiece : myPieces) {
            ArrayList<Coord> legalMoves = myPiece.getLegalMoves(board);
            for (Coord legalMove : legalMoves) {
                if (color == Color.BLACK) {
                    int aux = minimax(board, myPiece, legalMove, false, depth);
                    if (aux < value) {
                        bestMoves = new ArrayList<>();
                        bestMoves.add(new Move(myPiece, legalMove));
                        value = aux;
                    }
                   else if (aux == value) {
                       bestMoves.add(new Move(myPiece, legalMove));
                    }
                }
                else if (color == Color.WHITE) {
                    int aux = minimax(board, myPiece, legalMove, true, depth);
                    if (aux > value) {
                        bestMoves = new ArrayList<>();
                        bestMoves.add(new Move(myPiece, legalMove));
                        value = aux;
                    }
                    else if (aux == value) {
                        bestMoves.add(new Move(myPiece, legalMove));
                    }
                }
            }

        }

        Random r = new Random();
        int i = r.nextInt(bestMoves.size());
        Move move = bestMoves.get(i);
        System.out.println("Moving "+move.getPiece().toString()+" from "+move.getPiece().getPosition().toRealCoord()+" to "+move.getPiece().getPosition().add(move.getFinalPos()).toRealCoord());
        if (board.getPieceInCoord(move.getPiece().getPosition().add(move.getFinalPos())) != null) {
            score += board.getPieceInCoord(move.getPiece().getPosition().add(move.getFinalPos())).getValue() * 100;
        }
        board.movePiece(move.getPiece(), move.getPiece().getPosition().add(move.getFinalPos()));
        long finish = System.currentTimeMillis();
        long elapsedTime = finish - start;
        System.out.println(elapsedTime);
    }

    @Override
    public void playView(String FEN) {

    }

    /**
     * This is the function that makes the Machine think. It uses the minimax algorithm to make a decision on which
     * piece to move where, depending on how the board will be evaluated after doing the move. Black pieces look for
     * the board evaluation to be as small as possible. White pieces look for the board evaluation to be as big as possible
     * @param board
     * @param piece
     * @param movement
     * @param isMaximizing
     * @param depth
     * @return
     */

    private int minimax(Board board, Piece piece, Coord movement, boolean isMaximizing, int depth) {
        Board imaginaryBoard = new Board(board);
        imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(piece.getPosition()), piece.getPosition().add(movement));
        boolean isBlackOver = imaginaryBoard.isGameOver(Color.BLACK);
        boolean isWhiteOver = imaginaryBoard.isGameOver(Color.WHITE);
        if (isBlackOver)
            return 9000;
        else if (isWhiteOver)
            return -9000;
        else if (depth == 0) {
            return imaginaryBoard.getEvaluation();
        }
        if (isMaximizing) {
            int maxEval = MIN_VALUE;
            for (Piece whitePiece : imaginaryBoard.getWhitePieces()) {
                ArrayList<Coord> legalMoves = whitePiece.getLegalMoves(imaginaryBoard);
                for (Coord legalMove : legalMoves) {
                    int aux = minimax(imaginaryBoard, whitePiece, legalMove, !isMaximizing, depth - 1 );
                    maxEval = max(maxEval, aux);
                }
            }
            return maxEval;
        }
        else {
            int minEval = MAX_VALUE;
            for (Piece blackPiece : imaginaryBoard.getBlackPieces()) {
                ArrayList<Coord> legalMoves = blackPiece.getLegalMoves(imaginaryBoard);
                for (Coord legalMove : legalMoves) {
                    int aux = minimax(imaginaryBoard, blackPiece, legalMove, !isMaximizing, depth - 1);
                    minEval = min(minEval, aux);
                }
            }
            return minEval;
        }
    }

    /**
     * Getter to get the Machine's depth
     * @return the Machine's depth
     */

    public int getDepth() {
        return depth;
    }
}
