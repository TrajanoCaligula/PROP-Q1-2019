package Controllers;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlPerTest {

    public static void main(String[] args) throws IOException {

        CtrlPersistance Ctrl = CtrlPersistance.getInstance();

        int id = 2314;
        String FEN = "4tf56/drg4/sdf";
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
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        Ctrl.addScore("KKK","350",id);
        Ctrl.addScore("tr3","353",id);
        Ctrl.addScore("final test","0",id);

        //Ctrl.deleteRanking(id);

        //Ctrl.deleteProblem(id);
    }
}
