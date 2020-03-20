package Model.Exceptions;

public class MyStmtExecException extends Exception {
    public MyStmtExecException() {
    }

    public String getMessage() {
        return "Stack execution is empty!\n";
    }
}
