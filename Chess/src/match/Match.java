package match;

import board.Board;
import piece.Color;
import piece.Piece;
import player.Player;

import java.util.ArrayList;

/**
 * @author pauCharques
 */

public class Match {
    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Integer round;
    private Color firstColor;

    /**
     *
     * @param whitePlayer
     * @param blackPlayer
     * @param board
     * @param round
     * @param firstColor
     */

    public Match(Player whitePlayer, Player blackPlayer, Board board, Integer round, Color firstColor) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        this.round = round;
        this.firstColor = firstColor;
    }

    /**
     *
     * @return
     */

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     *
     * @return
     */

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     *
     * @return
     */

    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param color
     */

    public void playGame(Color color) {
        if (color == Color.WHITE) {
            whitePlayer.play(board, color);
            color = Color.BLACK;
            if (firstColor == Color.BLACK)
                round++;
        }
        else {
            blackPlayer.play(board, color);
            color = Color.WHITE;
            if (firstColor == Color.WHITE)
                round++;
        }
        round++;
    }

    /**
     *
     * @param color
     * @return
     */

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
