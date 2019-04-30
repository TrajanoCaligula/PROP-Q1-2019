package player;

import board.Coord;
import piece.Piece;

public class Move {
    private Piece piece;
    private Coord finalPos;

    public Move(Piece piece, Coord finalPos) {
        this.piece = piece;
        this.finalPos = finalPos;
    }

    public Piece getPiece() {
        return piece;
    }

    public Coord getFinalPos() {
        return finalPos;
    }
}
