package player;

import board.Board;
import board.Coord;
import piece.Color;
import piece.Piece;
import piece.PieceValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class Machine2 extends Player {

    private int depth;

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Machine2(String name, Color color, int depth) {
        super(name, color);
        this.depth = depth;
    }

    @Override
    public void play(Board board, Color color) {
        long start = System.currentTimeMillis();
        ArrayList<Piece> myPieces;
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        if (color == Color.WHITE)
            myPieces = board.getWhitePieces();
        else //if (color == Color.BLACK
            myPieces = board.getBlackPieces();
        int maxEval = MIN_VALUE, eval = MIN_VALUE;
        for (Piece myPiece : myPieces) {
            System.out.println("Looking at "+myPiece.toString()+" ("+myPiece.getPosition().getX()+" "+myPiece.getPosition().getY()+"): ");
            ArrayList<Coord> legalMoves = myPiece.getLegalMoves(board);
            for (Coord legalMove : legalMoves) {
                System.out.println(legalMove.getX()+" "+legalMove.getY()+": ");
                Board imaginaryBoard = new Board(board);
                Move move = new Move(imaginaryBoard.getPieceInCoord(myPiece.getPosition()), new Coord(legalMove));
                int score = 0;
                eval = minimax(imaginaryBoard, move, this.depth, true);
                System.out.println("eval: "+eval);
                if (eval > maxEval) {
                    maxEval = eval;
                    bestMoves = new ArrayList<Move>();
                    bestMoves.add(new Move(myPiece, move.getFinalPos()));
                }
                else if (eval == maxEval)
                    bestMoves.add(new Move(myPiece, move.getFinalPos()));
            }
        }
        System.out.println("Best eval: "+maxEval);
        System.out.println("Best moves:");
        for (Move bestMove : bestMoves) {
            System.out.print(bestMove.getPiece().toString()+": "+bestMove.getFinalPos().getX()+" "+bestMove.getFinalPos().getY()+", ");
         }
        Random r = new Random();
        int i = r.nextInt(bestMoves.size());
        Move definitiveMove = new Move(bestMoves.get(i).getPiece(), bestMoves.get(i).getFinalPos());
        /*
        for (Move bestMove : bestMoves) {
            if (!betterMoves.contains(bestMove)) {
                Board imaginaryBoard = new Board(board);
                imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(bestMove.getPiece().getPosition()), bestMove.getPiece().getPosition().add(bestMove.getFinalPos()));
                Piece newPiece = imaginaryBoard.getPieceInCoord(bestMove.getPiece().getPosition().add(bestMove.getFinalPos()));
                ArrayList<Coord> legalMoves = newPiece.getLegalMoves(imaginaryBoard);
                for (Coord legalMove : legalMoves) {
                    Move move = new Move(imaginaryBoard.getPieceInCoord(bestMove.getPiece().getPosition().add(bestMove.getFinalPos())), legalMove);
                    int aux = minimax(imaginaryBoard, move, 0, true);
                    if (aux == PieceValues.KING_VALUE.getValue()) {
                        value = aux;
                        definitiveMove = bestMove;
                    }
                }
            }
        }*/
        System.out.println("Definitive move value: "+maxEval);
        //promptEnterKey();
        //Move definitiveMove = new Move(decidedPiece, decidedPiece.getPosition().add(decidedMove.getFinalPos()));
        //Coord negativePos = new Coord(-decidedMove.getFinalPos().getX(), -decidedMove.getFinalPos().getY());
        System.out.println("Moving "+definitiveMove.getPiece().toString()+" from: "+definitiveMove.getPiece().getPosition().getX()+" "+definitiveMove.getPiece().getPosition().getY()+" to: "+definitiveMove.getFinalPos().getX()+" "+definitiveMove.getFinalPos().getY());
        board.movePiece(definitiveMove.getPiece(), definitiveMove.getPiece().getPosition().add(definitiveMove.getFinalPos()));
        //board.movePiece(board.getPieceInCoord(decidedMove.getPiece().getPosition().add(negativePos)), board.getPieceInCoord(decidedMove.getPiece().getPosition().add(negativePos)).getPosition().add(decidedMove.getFinalPos()));
        long finish = System.currentTimeMillis();
        long elapsedTime = finish - start;
        System.out.println(elapsedTime);
    }

    private int minimax(Board board, Move move, int depth, boolean isMaximizing) {
        int score = 0;
        Piece piece = move.getPiece();
        Coord finalPos = move.getFinalPos();

        if(depth == 0) {

            Board imaginaryBoard = new Board(board);

            imaginaryBoard.movePiece(imaginaryBoard.getPieceInCoord(piece.getPosition()), piece.getPosition().add(finalPos));


            if (piece.getColor() == Color.WHITE) {
                if (imaginaryBoard.isCheck(Color.BLACK)) {
                    System.out.println("Success!");
                    if (isMaximizing)
                        return PieceValues.KING_VALUE.getValue();
                    else
                        return -PieceValues.KING_VALUE.getValue();
                }

            }
            if (piece.getColor() == Color.BLACK) {
                if (imaginaryBoard.isCheck(Color.WHITE)) {
                    System.out.println("Success!");
                    if (isMaximizing) {
                        return PieceValues.KING_VALUE.getValue();
                    }

                    else
                        return PieceValues.KING_VALUE.getValue();
                }

            }

            Piece finalPiece = board.getPieceInCoord(piece.getPosition().add(finalPos));
            if (finalPiece == null)
                return 0;
            if (finalPiece.getColor() != piece.getColor()) {
                if(isMaximizing)
                    return finalPiece.getValue();
                return -finalPiece.getValue();
            }
        }
        Piece finalPiece = board.getPieceInCoord(move.getPiece().getPosition().add(move.getFinalPos()));
        if (finalPiece != null) {
            if (isMaximizing)
                score = finalPiece.getValue();
            else
                score = -finalPiece.getValue();
        }

        board.movePiece(move.getPiece(), piece.getPosition().add(move.getFinalPos()));

        int initialEval, currentEval = 0;
        if (isMaximizing)
            initialEval = MIN_VALUE;
        else
            initialEval = MAX_VALUE;
        ArrayList<Piece> enemyPieces;
        if (piece.getColor() == Color.WHITE)
            enemyPieces = board.getBlackPieces();
        else //if (piece.getColor() == Color.BLACK)
            enemyPieces = board.getWhitePieces();
        for (Piece enemyPiece : enemyPieces) {
            System.out.println("\tLooking at "+enemyPiece.toString()+" ("+enemyPiece.getPosition().getX()+" "+enemyPiece.getPosition().getY()+"): ");
            ArrayList<Coord> legalMoves = enemyPiece.getLegalMoves(board);
            for (Coord legalMove : legalMoves) {
                System.out.println("\t"+legalMove.getX()+" "+legalMove.getY()+": ");
                Board imaginaryBoard = new Board(board);
                Move enemyMove = new Move(imaginaryBoard.getPieceInCoord(enemyPiece.getPosition()), new Coord(legalMove));
                currentEval = minimax(imaginaryBoard, enemyMove, depth - 1, !isMaximizing);
                System.out.println("\teval: "+currentEval);
                if (isMaximizing && currentEval > initialEval) {
                    initialEval = currentEval;
                }
                else if (!isMaximizing && currentEval < initialEval) {
                    initialEval = currentEval;
                }
            }
            System.out.println();
        }
        //System.out.println("initialEval: "+initialEval);
        return initialEval + score;
    }
}
