package Model.Exceptions;

public class VarNotDefInHeapExc extends Exception {
    public VarNotDefInHeapExc() {
    }

    public String getMessage() {
        return "Variable not defined in Heap!\n";
    }
}
