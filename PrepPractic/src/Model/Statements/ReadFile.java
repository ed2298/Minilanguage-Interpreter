package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.Structures.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private Exp expFileId;
    private String varName;

    public ReadFile(Exp expFileId, String varName) {
        this.expFileId = expFileId;
        this.varName = varName;
    }

    public String toString() {
        return "ReadFile(" + expFileId.toString() + ", " + varName + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, InvalidOperationExc, DivByZeroExc, FileIsNotOpenExc, VarNotDefInHeapExc {
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable = state.getFileTable();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int fileUniqueKey = this.expFileId.eval(symTbl, heap);
        MyITuple<String, BufferedReader> t = fileTable.lookup(fileUniqueKey);
        if (t == null)
            throw new FileIsNotOpenExc();
        int value;
        try {
            String line = t.getSecond().readLine();
            if (line == null) {
                value = 0;
            } else {
                value = Integer.parseInt(line);
            }
            if (symTbl.isDefined(this.varName)) {
                symTbl.update(this.varName, value);
            } else {
                symTbl.add(this.varName, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IStmt dup() {
        return new ReadFile(expFileId.dup(), varName);
    }
}
