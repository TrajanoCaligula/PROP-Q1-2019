package GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Chess {
    static String fen = "1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B";
    public Chess() {

    }

    public static void main(String[] args) {

        MatchView chessView = new MatchView(fen);

        Runnable r = new Runnable() {
            public void run() {
                JFrame f = new JFrame("Chess");
                f.add(chessView.getGui());
                f.setDefaultCloseOperation(2);
                f.setLocationByPlatform(true);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
                ViewController chessViewController = new ViewController(chessView);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

