/**
 * @author Pau Charques
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    private int tx, ty;
    private Character piece = null;
    private boolean highlighted = false;
    private boolean isPicker = false;


    public Tile(int x, int y){
        super();

        this.tx = x;
        this.ty = y;
    }

    public Tile(Icon pieceIcon, int x){
        super();

        this.tx = x;
        this.ty = 0;
        this.isPicker = true;
        this.piece = 'Q';
        this.setIcon(pieceIcon);
    }

    public boolean isPicker(){ return isPicker; }

    public void setPiece(Character piece){
        this.piece = piece;
    }
    public Character getPiece(){ return this.piece; }

    public void highlightTile(){
        this.highlighted = true;
        this.setBorderPainted(true);
        this.setBorder(BorderFactory.createLineBorder(Color.green, 3));
    }

    public void undoHighlightTile(){
        this.highlighted = false;
        this.setBorderPainted(false);
    }



    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
        Tile tile = (Tile) obj;
        return (tile.tx == this.tx && tile.ty == this.ty);
    }

    public void setTileX(int xIn) {this.tx = xIn;}
    public int getTileX() {return this.tx;}

    public void setTileY(int yIn) {this.ty = yIn;}
    public int getTileY() {return this.ty;}

    public boolean getColor(){
        return Character.isUpperCase(this.piece);
    }

    public boolean isOccupied(){
        return this.piece != null;
    }
}
