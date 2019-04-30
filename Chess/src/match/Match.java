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
     * Match standard constructor
     * @param whitePlayer: the Player which is going to move the whitePieces from the board
     * @param blackPlayer: the Player which is going to move the blackPieces from the board
     * @param board: the Board to play with
     * @param round: the round that the players are in
     * @param firstColor: the first color to move
     */

    public Match(Player whitePlayer, Player blackPlayer, Board board, Integer round, Color firstColor) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        this.round = round;
        this.firstColor = firstColor;
    }

    /**
     * returns the Player which plays the whitePieces from the board
     * @return whitePlayer
     */

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * returns the Player which plays the blackPieces from the board
     * @return blackPlayer
     */

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * returns the board in which is being played the match
     * @return board
     */

    public Board getBoard() {
        return board;
    }

    /**
     * This function makes the match play. If the parameter color is BLACK, it's blackPlayer's turn to move,
     * else if the parameter color is WHITE, it's whitePlayer's turn to move. It also gets the match's round number.
     * @param color: indicates who has to play
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
    }

    /**
     * This function looks if a certain color if checkmated or not
     * @param color: the color to look for
     * @return true if the color is checkmated, false otherwise
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
