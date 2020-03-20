package Model.Exceptions;

public class VarNotDefinedInBarrierTableExc extends Exception {
    public VarNotDefinedInBarrierTableExc() {
    }

    public String getMessage() {
        return "Variable not defined in Barrier Table!\n";
    }
}
