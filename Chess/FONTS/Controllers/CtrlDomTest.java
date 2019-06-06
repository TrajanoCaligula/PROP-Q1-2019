package Controllers;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlDomTest {

    public static void main(String[] args) throws IOException {

        CtrlDomain Ctrl = CtrlDomain.getInstance();

        Ctrl.createProblem("1N1b4/6nr/R5n1/2Ppk2r/K2p2qR/8/2N1PQ2/B6B w - - 0 1",4);

        ArrayList<String> aux = Ctrl.topScores(2314);
        for(int i = 0; i < aux.size(); i++) System.out.println(aux.get(i));

        System.out.println(Ctrl.getFENFromId(2314));

        System.out.println("after");
        //Ctrl.updateProblems();
        System.out.println("before");



    }
}
