package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.Structures.*;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp expFileId;

    public CloseRFile(Exp expFileId) {
        this.expFileId = expFileId;
    }

    public String toString() {
        return "CloseRFile(" + this.expFileId.toString() + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, InvalidOperationExc, DivByZeroExc, FileIsNotOpenExc, VarNotDefInHeapExc {
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable = state.getFileTable();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int fileUniqueKey = this.expFileId.eval(symTbl, heap);
        MyITuple<String, BufferedReader> t = fileTable.lookup(fileUniqueKey);
        if (t == null)
            throw new FileIsNotOpenExc();
        try {
            t.getSecond().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileTable.delete(fileUniqueKey);
        return null;
    }

    public IStmt dup() {
        return new CloseRFile(expFileId.dup());
    }
}
