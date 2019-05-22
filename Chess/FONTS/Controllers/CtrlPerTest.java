package Controllers;

import java.io.IOException;

public class CtrlPerTest {

    public static void main(String[] args) throws IOException {

        CtrlPersistance Ctrl = CtrlPersistance.getInstance();

        Ctrl.writeProblem("Hello",0);

        System.out.println(Ctrl.existsProblem(0));

        Ctrl.deleteProblem(0);
    }
}
