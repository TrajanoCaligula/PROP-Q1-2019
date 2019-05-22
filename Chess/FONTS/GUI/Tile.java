package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends JButton {
    private int tx, ty;
    public Character piece;
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

    public void highlightTile(){
        this.setBorderPainted(true);
        this.setBorder(BorderFactory.createLineBorder(Color.green, 3));
    }

    public void undoHighliightTile(){
        this.setBorderPainted(false);
    }
    public void setTileX(int xIn) {this.tx = xIn;}
    public int getTileX() {return this.tx;}

    public void setTileY(int yIn) {this.ty = yIn;}
    public int getTileY() {return this.ty;}
}
