package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends JButton implements Cloneable {
    private int tx, ty;
    private Character piece;
    public boolean highlighted = false;
    public Tile(int x, int y){
        super();

        this.tx = x;
        this.ty = y;
    }
    public Tile(Character piece, int x, int y){
        super();

        this.tx = x;
        this.ty = y;
        this.piece = piece;
    }

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

        // checking if both the object references are
        // referring to the same object.
        if(this == obj)
            return true;

        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        // type casting of the argument.
        Tile tile = (Tile) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        return (tile.tx == this.tx && tile.ty == this.ty);
    }

    public void setTileX(int xIn) {this.tx = xIn;}
    public int getTileX() {return this.tx;}

    public void setTileY(int yIn) {this.ty = yIn;}
    public int getTileY() {return this.ty;}
}
