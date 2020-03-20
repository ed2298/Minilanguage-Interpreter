package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;
import Model.Structures.PrgState;

public class NewStmt implements IStmt {
    private String varName;
    private Exp exp;

    public NewStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    public String toString() {
        return "new(" + this.varName + ", " + this.exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, InvalidOperationExc, DivByZeroExc, VarNotDefInHeapExc {
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int value = exp.eval(symtbl, heap);
        int heapUniqueKey = heap.add(value);
        //int heapUniqueKey = heap.getUniqueKey();
        if (symtbl.isDefined(varName)) {
            symtbl.update(varName, heapUniqueKey);
        } else {
            symtbl.add(varName, heapUniqueKey);
        }
        return null;
    }

    public IStmt dup() {
        return new NewStmt(this.varName, this.exp.dup());
    }
}
