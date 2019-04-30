package piece;

/**
 * @author jaumeMalgosa
 */

/**
 *
 */

public enum PieceValues {

    PAWN_VALUE(1),
    ROOK_VALUE(5),
    KNIGHT_VALUE(3),
    BISHOP_VALUE(3),
    QUEEN_VALUE(9),
    KING_VALUE(900);

    private final int value;

    /**
     *
     * @param newValue
     */

    PieceValues(final int newValue) {
        value = newValue;
    }

    /**
     *
     * @return
     */

    public int getValue() { return value; }

}
