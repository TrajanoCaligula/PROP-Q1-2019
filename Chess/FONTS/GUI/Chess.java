package GUI;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.IOException;

public class Chess {
    public Chess() {

    }

    public static void main(String[] args) {

        ChessView chessView = new ChessView();

        Runnable r = new Runnable() {
            public void run() {
                JFrame f = new JFrame("Chess");
                f.add(chessView.getGui());
                f.setDefaultCloseOperation(2);
                f.setLocationByPlatform(true);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
                try {
                    ViewController chessViewController = new ViewController(chessView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

