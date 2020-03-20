package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Expressions.Exp;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;
import Model.Structures.PrgState;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String toString() {
        return id + " = " + exp.toString();
    }

    public PrgState execute(PrgState state) throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, VarNotDefInHeapExc {
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int val = exp.eval(symTbl, heap);
        if (symTbl.isDefined(id))
            symTbl.update(id, val);
        else
            symTbl.add(id, val);
        return null;
    }

    public IStmt dup() {
        return new AssignStmt(id, exp.dup());
    }

}
