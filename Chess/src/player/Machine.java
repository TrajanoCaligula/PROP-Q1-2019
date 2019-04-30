package player;

import board.Board;
import board.Coord;
import piece.Color;
import piece.Piece;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.*;

public class Machine extends Player {

    private int depth;

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Machine(String name, Color color, int depth) {
        super(name, color);
        this.depth = depth;
    }

    @Override
    public void play(Board board, Color color) {
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
        System.out.println("value = "+value);
        System.out.println("Moving "+move.getPiece().toString()+" from ("+move.getPiece().getPosition().getX()+", "+move.getPiece().getPosition().getY()+") to: ("+move.getPiece().getPosition().add(move.getFinalPos()).getX()+", "+move.getPiece().getPosition().add(move.getFinalPos()).getY()+")");
        board.movePiece(move.getPiece(), move.getPiece().getPosition().add(move.getFinalPos()));
        //promptEnterKey();
        long finish = System.currentTimeMillis();
        long elapsedTime = finish - start;
        System.out.println(elapsedTime);
    }

    private int minimax(Board board, Piece piece, Coord movement, boolean isMaximizing, int depth) {
        Board imaginaryBoard = new Board(board);
        imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(piece.getPosition()), piece.getPosition().add(movement));
        if (depth == 0 || imaginaryBoard.isGameOver(Color.BLACK) || imaginaryBoard.isGameOver(Color.WHITE)) {
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
}
