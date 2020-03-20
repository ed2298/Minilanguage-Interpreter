package Model.Exceptions;

public class InvalidOperationExc extends Exception {
    public InvalidOperationExc() {
    }

    public String getMessage() {
        return "Invalid operation!\n";
    }
}
