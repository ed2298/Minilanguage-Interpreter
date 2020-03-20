package Model.Exceptions;

public class FileIsNotOpenExc extends Exception {
    public FileIsNotOpenExc() {
    }

    public String getMessage() {
        return "This file is not open!\n";
    }
}
