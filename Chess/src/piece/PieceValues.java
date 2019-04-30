package piece;

/**
 * @author jaumeMalgosa
 */

/**
 * We use this enum to know how much is the value of a certain Piece. It is mainly used in the Machine class to evaluate
 * the Board's position. It can also be used to calculate the Score of a Player in a Match
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
     * This function is used so you can modify a Piece's value with any other one from the enum
     * @param newValue: the value to change to
     */

    PieceValues(final int newValue) {
        value = newValue;
    }

    /**
     * A getter that returns the value of a Piece
     * @return it returns its value
     */

    public int getValue() { return value; }

}
