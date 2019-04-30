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
     *
     * @param piece
     * @param finalPos
     */

    public Move(Piece piece, Coord finalPos) {
        this.piece = piece;
        this.finalPos = finalPos;
    }

    /**
     *
     * @return
     */

    public Piece getPiece() {
        return piece;
    }

    /**
     *
     * @return
     */

    public Coord getFinalPos() {
        return finalPos;
    }
}
