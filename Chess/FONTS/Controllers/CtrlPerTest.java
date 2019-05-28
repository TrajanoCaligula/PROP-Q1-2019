package Controllers;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlPerTest {

    public static void main(String[] args) throws IOException {

        CtrlPersistance Ctrl = CtrlPersistance.getInstance();

        int id = 2314;
        String FEN = "5B1b/1p2rR2/8/1B4N1/K2kP3/5n1R/1N6/2Q5 w - - 0 1";
        int N = 4;
        String dif = "Facil";

        Ctrl.saveProblem(FEN,id,N,dif);

        System.out.println(Ctrl.existsProblem(id));
        System.out.println(Ctrl.existsProblem(15));

        ArrayList<String> t = Ctrl.listProblems();
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        System.out.println("\n");

        System.out.println(Ctrl.getFEN(id));

        String aux = "P-" + Integer.toString(id);
        if(Ctrl.getFile(aux) != null) System.out.println("fasds");
        else System.out.println("tttttt");

        t =  new ArrayList<String>();
        t.add("hos"); t.add("1230");t.add("hfda"); t.add("1100");t.add("trb"); t.add("750");
        Ctrl.saveRanking(t,id);

        t = Ctrl.loadScores(id);
        for(int i = 0; i < t.size(); i++) {
            System.out.print(t.get(i));
            if(i %2 == 0) System.out.println();
        }

        System.out.println();
        Ctrl.listRankings();



        t = Ctrl.loadScores(id);
        for(int i = 0; i < t.size(); i++) {
            System.out.print(t.get(i));
            if(i %2 == 0) System.out.println();
        }


        Ctrl.addScore("KKK","350",id);
        Ctrl.addScore("tr3","353",id);
        Ctrl.addScore("final test","0",id);

        Ctrl.listRankings();

        Ctrl.existsRanking(2314);

        Ctrl.addScore("TEST","100",110733);

        t = Ctrl.loadScores(110733);
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        //Ctrl.deleteRanking(id);

        //Ctrl.deleteProblem(id);
    }
}
