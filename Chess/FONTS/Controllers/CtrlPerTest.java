package Controllers;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlPerTest {

    public static void main(String[] args) throws IOException {

        CtrlPersistance Ctrl = CtrlPersistance.getInstance();

        int id = 2314;
        String FEN = "4tf56/drg4/sdf";

        Ctrl.saveProblem(FEN,id);

        System.out.println(Ctrl.existsProblem(id));
        System.out.println(Ctrl.existsProblem(15));

        ArrayList<String> t = Ctrl.listProblems();
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        System.out.println("\n");

        System.out.println(Ctrl.getFEN(id));

        String aux = "P-" + Integer.toString(id);
        if(Ctrl.getFile(aux) != null) System.out.println("fasds");
        else System.out.println("tttttt");

        t = Ctrl.loadScores(id);
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        t =  new ArrayList<String>();
        t.add("Marc"); t.add("232");t.add("Ka"); t.add("3432");
        Ctrl.saveRanking(t,id);
        t = Ctrl.loadScores(id);
        for(int i = 0; i < t.size(); i++) System.out.println(t.get(i));

        //Ctrl.deleteProblem(id);
    }
}
