package Jaume;

import com.sun.istack.internal.Nullable;


import java.util.ArrayList;

import static java.lang.Character.*;

/**
 * @author jaumeMalgosa
 */

public class Board {

    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    /**
     * Standard Board constructor, it creates a Board with no pieces.
     */

    public Board() {
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
    }

    /**
     * Board copy constructor, it copies the the board parameter to this board
     * @param board: the board to copy
     */

    public Board(Board board){
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();

        for (Piece whitePiece : board.getWhitePieces()) {
            this.whitePieces.add(whitePiece.copy());
        }
        for (Piece blackPiece : board.getBlackPieces()) {
            this.blackPieces.add(blackPiece.copy());
        }
    }

    /**
     * Board constructor with FEN parameter. It creates a Board based on the FEN given
     * @param FEN: it contains the FEN information to create the new board
     */

    public Board(String FEN) {
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
        int i = 0, y = 0, x = 0;
        char c;
        while (i < FEN.length()) {
            c = FEN.charAt(i);
            if (isDigit(c)) {
                x += getNumericValue(c);
            }
            else if (isLetter(c)) {
                //UpperCase
                if (c == 'P')
                    whitePieces.add(new Pawn(new Coord(x, y), Color.WHITE));
                else if (c == 'R')
                    whitePieces.add(new Rook(new Coord(x, y), Color.WHITE));
                else if (c == 'N')
                    whitePieces.add(new Knight(new Coord(x, y), Color.WHITE));
                else if (c == 'B')
                    whitePieces.add(new Bishop(new Coord(x, y), Color.WHITE));
                else if (c == 'Q')
                    whitePieces.add(new Queen(new Coord(x, y), Color.WHITE));
                else if (c == 'K')
                    whitePieces.add(new King(new Coord(x, y), Color.WHITE));
                //LowerCase
                else if (c == 'p')
                    blackPieces.add(new Pawn(new Coord(x, y), Color.BLACK));
                else if (c == 'r')
                    blackPieces.add(new Rook(new Coord(x, y), Color.BLACK));
                else if (c == 'n')
                    blackPieces.add(new Knight(new Coord(x, y), Color.BLACK));
                else if (c == 'b')
                    blackPieces.add(new Bishop(new Coord(x, y), Color.BLACK));
                else if (c == 'q')
                    blackPieces.add(new Queen(new Coord(x, y), Color.BLACK));
                else if (c == 'k')
                    blackPieces.add(new King(new Coord(x, y), Color.BLACK));
                x++;
            }
            else if(isDash(c)) {
                y++;
                x = 0;
            }
            i++;
        }
    }

    /**
     * It's used to send to the Machine class the evaluation of the Board, which is >0 if whites are winning and is <0
     * if the blacks are winning.
     * @return the evaluation of the board
     */

    public int getEvaluation() {
        int score = 0;
        //System.out.print("Evaluation = ");
        for (Piece whitePiece : whitePieces) {
            score += whitePiece.getValue();
            //System.out.print(" + "+whitePiece.getValue()+"("+whitePiece.toString()+")");
        }

        for (Piece blackPiece : blackPieces) {
            score -= blackPiece.getValue();
            //System.out.print(" - "+blackPiece.getValue()+"("+blackPiece.toString()+")");
        }
        ArrayList<Coord> legalMoves;
        boolean blackIsChekMate = true, whiteIsCheckMate = true;
        for (Piece whitePiece : whitePieces) {
            if (!whitePiece.getLegalMoves(this).isEmpty()) {
                whiteIsCheckMate = false;
                break;
            }

        }
        for (Piece blackPiece : blackPieces) {
            if (!blackPiece.getLegalMoves(this).isEmpty()) {
                blackIsChekMate = false;
                break;
            }
        }
        //System.out.println(" = "+score);
        if (whiteIsCheckMate)
            score -= 9000;
        else if (blackIsChekMate)
            score += 9000;
        return score;
    }

    /**
     * It's used in the FEN constructor to know when to pass to the next row of the Board
     * @param c: the character to compare
     * @return true if c is a dash, false otherwise
     */

    private boolean isDash(char c) {
        return (c == '/');
    }

    /**
     * It gives the piece which is located in a certain position
     * @param position: the position the user wants to get a Piece from
     * @return it returns a Piece if there is one, null otherwise
     */

    @Nullable
    public Piece getPieceInCoord(Coord position) {
        for(Piece piece : whitePieces) {
            if(piece.getPosition().equals(position)) {
                return piece;
            }
        }
        for(Piece piece : blackPieces) {
            if(piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * It says if a position is in bounds of the Board
     * @param position: the position to compare
     * @return it return true if the position is in bounds, false otherwise
     */

    public static boolean inBounds(Coord position) {
        return(position.getX() >= 0 && position.getX() <= 7 && position.getY() >= 0 && position.getY() <= 7);
    }

    /**
     * This function is used to move a Piece to another position of the board
     * @param piece: the piece to move (not null)
     * @param finalPos: the position where we want to move the piece. If there is another piece in that position, it is
     *                  removed
     */

    public void movePiece(Piece piece, Coord finalPos) {
        Piece finalPiece = getPieceInCoord(finalPos);
        if (finalPiece != null)
            removePiece(finalPiece);
        piece.setPosition(finalPos);
        if(piece instanceof Pawn && (piece.getPosition().getY() == 0 || piece.getPosition().getY() == 7)) {
            promotePawn((Pawn)piece);
        }
    }

    /**
     * Used to remove a piece from whitePieces or blackPieces
     * @param piece: the piece to remove (not null)
     */

    public void removePiece(Piece piece) {
        if(piece.getColor() == Color.WHITE) {
            whitePieces.remove(piece);
        }
        else if(piece.getColor() == Color.BLACK) {
            blackPieces.remove(piece);
        }
    }

    /**
     * Used to promote a pawn. For the moment it can only be promoted to a Queen (the best choice)
     * @param pawn: the pawn to be promoted (not null)
     */

    public void promotePawn(Pawn pawn) {
        Queen queen = new Queen(pawn);
        whitePieces.add(queen);
        removePiece(pawn);
    }

    /**
     * Says if a certain color of the board is in check or not
     * @param color: the color to compare
     * @return return true if the color is in check and false otherwise
     */

    public boolean isCheck(Color color) {
        Coord kingPosition = new Coord(-1, -1); //Inicializado a -1, -1 por si no hay rey
        ArrayList<Coord> possibleMoves;

        if(color == Color.WHITE) {

            for(Piece whitePiece : whitePieces) {
                if(whitePiece instanceof King) {
                    kingPosition = whitePiece.getPosition();
                }
            }

            for (Piece blackPiece : blackPieces) {
                possibleMoves = blackPiece.getPossibleMoves(this);
                for (Coord possibleMove : possibleMoves) {
                    if (blackPiece.getPosition().add(possibleMove).equals(kingPosition))
                        return true;
                }
            }

        }

        else if(color == Color.BLACK) {

            for(Piece blackPiece : blackPieces) {
                if(blackPiece instanceof King) {
                    kingPosition = blackPiece.getPosition();
                }
            }

            for (Piece whitePiece : whitePieces) {
                possibleMoves = whitePiece.getPossibleMoves(this);
                for (Coord possibleMove : possibleMoves) {
                    if (whitePiece.getPosition().add(possibleMove).equals(kingPosition))
                        return true;
                }
            }
        }
        return false;
    }

    //Getters and Setters


    /**
     * Used to get all the whitePieces from the Board
     * @return whitePieces
     */

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    /**
     * Used to get all the blackPieces from the Board
     * @return blackPieces
     */

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    /**
     * It prints the Board, having in mind the 1-8 and a-h coordinates
     */

    public void printBoard() {
        int fila = 8;
        int tile = 0;
        System.out.println();
        for (int y = 0; y < 8; y++) {
            System.out.print(fila+" |");
            fila--;
            for (int x = 0; x < 8; x++) {
                Piece whitePiece = getWhitePiece(x, y);
                Piece blackPiece = getBlackPiece(x, y);
                if (whitePiece == null && blackPiece == null) {
                    if (tile % 2 == 0)
                        System.out.print("- ");
                    else
                        System.out.print("X ");
                }
                else if (whitePiece != null) {
                    System.out.print(whitePiece.toString()+" ");
                }
                else if (blackPiece != null) {
                    System.out.print(blackPiece.toString()+" ");
                }
                tile++;
            }
            tile--;
            System.out.println();
        }
        System.out.println("   a|b|c|d|e|f|g|h");
        System.out.println();
    }

    /**
     * It gets a black piece from a certain position x, y
     * @param x: the x coordinate to look to
     * @param y: the y coordinate to look to
     * @return returns a black piece if there is one and null if there isn't (null if it's white)
     */

    private Piece getBlackPiece(int x, int y) {
        Piece piece = null;
        Coord position = new Coord(x, y);
        for (Piece blackPiece : blackPieces) {
            if (position.equals(blackPiece.getPosition())) {
                piece = blackPiece;
                break;
            }
        }
        return piece;
    }

    /**
     * It gets a white piece from a certain position x, y
     * @param x: the x coordinate to look to
     * @param y: the y coordinate to look to
     * @return returns a white piece if there is one and null if there isn't (null if it's black)
     */

    private Piece getWhitePiece(int x, int y) {
        Piece piece = null;
        Coord position = new Coord(x, y);
        for (Piece whitePiece : whitePieces) {
            if (position.equals(whitePiece.getPosition())) {
                piece = whitePiece;
                break;
            }
        }
        return piece;
    }

    /**
     * This setter is used to add a white piece in the board
     * @param whitePiece: the white piece to add
     */

    public void setWhitePiece(Piece whitePiece) {
        this.whitePieces.add(whitePiece);
    }

    /**
     * This setter is used to add a black piece in the board
     * @param blackPiece: the black piece to add
     */

    public void setBlackPiece(Piece blackPiece) {
        this.blackPieces.add(blackPiece);
    }

    /**
     * Says if the given color has lost the game or not
     * @param color: the color to compare
     * @return returns true if the color has lost, false otherwise
     */

    public boolean isGameOver(Color color) {
        if (color == Color.WHITE) {
            for (Piece whitePiece : whitePieces) {
                if (!whitePiece.getLegalMoves(this).isEmpty())
                    return false;
            }
            return true;
        }
        if (color == Color.BLACK) {
            for (Piece blackPiece : blackPieces) {
                if (!blackPiece.getLegalMoves(this).isEmpty())
                    return false;
            }
            return true;
        }
        return true;
    }

    /**
     * This function prints the Board into its FEN notation
     * @return FEN String of the Board
     */

    public String toFEN() {
        String FEN = new String();
        Piece piece;
        int emptyTiles = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = getPieceInCoord(new Coord(j, i));
                if (piece != null) {
                    if (emptyTiles > 0)
                        FEN += Integer.toString(emptyTiles);
                    FEN += piece.toFEN();
                    emptyTiles = 0;
                }
                else
                    emptyTiles++;
            }
            if (emptyTiles > 0)
                FEN += Integer.toString(emptyTiles);
            emptyTiles = 0;
            if (i < 7)
                FEN += "/";
        }
        return FEN;
    }
}
