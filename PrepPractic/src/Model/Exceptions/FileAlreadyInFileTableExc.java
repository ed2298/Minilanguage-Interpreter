package Model.Exceptions;

public class FileAlreadyInFileTableExc extends Exception {
    public FileAlreadyInFileTableExc() {
    }

    public String getMessage() {
        return "This file is already in filetable!\n";
    }

}
