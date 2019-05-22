package Controllers;

public class CtrlDomain {
    private static CtrlDomain ourInstance = new CtrlDomain();

    public static CtrlDomain getInstance() {
        return ourInstance;
    }

    private CtrlDomain() {
    }
}
