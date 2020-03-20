package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Expressions.Exp;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;
import Model.Structures.MyIList;
import Model.Structures.PrgState;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, VarNotDefInHeapExc {
        MyIList<Integer> lst = state.getLst();
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        lst.add(exp.eval(symtbl, heap));
        return null;
    }

    public IStmt dup() {
        return new PrintStmt(exp.dup());
    }
}
