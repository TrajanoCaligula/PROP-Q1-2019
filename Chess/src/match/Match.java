package match;

import board.Board;
import board.Coord;
import piece.Color;
import piece.Piece;
import player.Player;

import java.util.ArrayList;

public class Match {
    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Integer round;

    public Match(Player whitePlayer, Player blackPlayer, Board board, Integer round) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        this.round = round;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void playGame(Color color) {
        if (color == Color.WHITE) {
            whitePlayer.play(board, color);
            color = Color.BLACK;
        }
        else {
            blackPlayer.play(board, color);
            color = Color.WHITE;
        }
        round++;
    }


    public boolean checkmated(Color color) {
        if (color == Color.BLACK) {
            if (this.board.getBlackPieces().size() == 1)
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
            if (this.board.getWhitePieces().size() == 1)
                return true;
            ArrayList<Piece> whitePieces = board.getWhitePieces();
            for (Piece whitePiece : whitePieces) {
                if (!whitePiece.getLegalMoves(board).isEmpty())
                    return false;
            }
        }
            return true;
    }
}
