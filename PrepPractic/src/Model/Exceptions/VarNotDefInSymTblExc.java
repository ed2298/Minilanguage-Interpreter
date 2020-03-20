package Model.Exceptions;

public class VarNotDefInSymTblExc extends Exception {
    public VarNotDefInSymTblExc() {
    }

    public String getMessage() {
        return "Variable not defined in Symbol Table!\n";
    }
}
