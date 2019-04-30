package board;

import com.sun.istack.internal.Nullable;
import piece.*;

import java.util.ArrayList;

import static java.lang.Character.*;

public class Board {

    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    public Board() {
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
    }

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
        //System.out.println(" = "+score);
        return score;
    }

    private boolean isDash(char c) {
        return (c == '/');
    }

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

    public static boolean inBounds(Coord position) {
        return(position.getX() >= 0 && position.getX() <= 7 && position.getY() >= 0 && position.getY() <= 7);
    }

    public void movePiece(Piece piece, Coord finalPos) {
        Piece finalPiece = getPieceInCoord(finalPos);
        if (finalPiece != null)
            removePiece(finalPiece);
        piece.setPosition(finalPos);
        if(piece instanceof Pawn && (piece.getPosition().getY() == 0 || piece.getPosition().getY() == 7)) {
            promotePawn((Pawn)piece);
        }
    }

    public void removePiece(Piece piece) {
        if(piece.getColor() == Color.WHITE) {
            whitePieces.remove(piece);
        }
        else if(piece.getColor() == Color.BLACK) {
            blackPieces.remove(piece);
        }
    }

    public void promotePawn(Pawn pawn) {
        Queen queen = new Queen(pawn);
        whitePieces.add(queen);
        removePiece(pawn);
    }

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

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(ArrayList<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public void printBoard() {
        int fila = 8;
        int tile = 0;
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
    }

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
}
