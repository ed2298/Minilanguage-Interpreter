package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;
import Model.Structures.PrgState;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    public String toString() {
        return "wH(" + this.varName + ", " + this.exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, VarNotDefInHeapExc, DivByZeroExc, InvalidOperationExc {
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        if (symtbl.lookup(varName) == null)
            throw new VarNotDefInSymTblExc();
        int heapUniqueKey = symtbl.lookup(varName);
        int value = exp.eval(symtbl, heap);
        if (heap.lookup(heapUniqueKey) == null)
            throw new VarNotDefInHeapExc();
        else
            heap.update(heapUniqueKey, value);
        return null;
    }

    public IStmt dup() {
        return new WriteHeapStmt(this.varName, this.exp.dup());
    }
}
