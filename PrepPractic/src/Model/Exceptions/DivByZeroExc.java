package Model.Exceptions;

public class DivByZeroExc extends Exception {
    public DivByZeroExc() {
    }

    public String getMessage() {
        return "Cannot divide by 0!\n";
    }
}
