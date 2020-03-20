package Model.Exceptions;

public class VarNotDefinedInLockTableExc extends Exception {
    public VarNotDefinedInLockTableExc() {
    }

    public String getMessage() {
        return "Variable not defined in Lock Table!\n";
    }
}
