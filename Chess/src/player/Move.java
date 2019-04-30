package player;

import board.Coord;
import piece.Piece;

/**
 * @author calinPirau
 */

public class Move {
    private Piece piece;
    private Coord finalPos;

    /**
     * Move constructor. It contains the Piece to move and the position in which is being moved
     * @param piece: the Piece to move
     * @param finalPos: the position where the Piece is going to move
     */

    public Move(Piece piece, Coord finalPos) {
        this.piece = piece;
        this.finalPos = finalPos;
    }

    /**
     * Getter to get the Piece to move from Move
     * @return The Piece from Move
     */

    public Piece getPiece() {
        return piece;
    }

    /**
     * Getter to get the moving position from Move
     * @return The position to move the Piece to
     */

    public Coord getFinalPos() {
        return finalPos;
    }
}
