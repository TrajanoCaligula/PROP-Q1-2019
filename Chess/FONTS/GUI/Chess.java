//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Chess {
    public Chess() {

    }

    public static void main(String[] args) {
        MatchView v = new MatchView("1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B");
        //ViewController c = new ViewController(v);
        //c.initController();
        Runnable r = new Runnable() {
            public void run() {
                String fen = "1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B";
                MatchView cg = new MatchView(fen);
                JFrame f = new JFrame("Chess");
                f.add(cg.getGui());
                f.setDefaultCloseOperation(2);
                f.setLocationByPlatform(true);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

