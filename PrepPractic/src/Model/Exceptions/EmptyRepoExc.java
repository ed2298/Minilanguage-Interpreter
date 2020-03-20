package Model.Exceptions;

public class EmptyRepoExc extends Exception {
    public EmptyRepoExc() {
    }

    public String getMessage() {
        return "The repository is empty!\n";
    }
}
