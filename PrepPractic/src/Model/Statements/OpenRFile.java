package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.FileAlreadyInFileTableExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private String varFileId;
    private String filename;

    public OpenRFile(String varFileId, String filename) {
        this.varFileId = varFileId;
        this.filename = filename;
    }

    public String toString() {
        return "OpenRFile(" + this.varFileId + ", \"" + this.filename + "\")";
    }

    public PrgState execute(PrgState state) throws FileAlreadyInFileTableExc {
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable = state.getFileTable();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        for (MyITuple<String, BufferedReader> t : fileTable.getAllValues()) {
            if (t.getFirst().equals(filename)) {
                throw new FileAlreadyInFileTableExc();
            }
        }
        try {
            BufferedReader buffR = new BufferedReader(new FileReader(this.filename));
            int fileTableUniqueKey = fileTable.add(new MyTuple<>(this.filename, buffR));
            symTbl.add(this.varFileId, fileTableUniqueKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IStmt dup() {
        return new OpenRFile(varFileId, filename);
    }
}
