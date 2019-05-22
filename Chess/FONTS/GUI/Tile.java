package GUI;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Tile extends JButton {
    private int tx, ty;
    String piece;
    public Tile(String piece, int x, int y){
        super();

        this.tx = x;
        this.ty = y;
        this.piece = piece;
    }

    public void setTileX(int xIn) {this.tx = xIn;}
    public int getTileX() {return this.tx;}

    public void setTileY(int yIn) {this.ty = yIn;}
    public int getTileY() {return this.ty;}
}
