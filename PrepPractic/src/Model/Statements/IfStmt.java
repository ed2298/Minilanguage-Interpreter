package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Expressions.Exp;
import Model.Structures.MyIHeap;
import Model.Structures.MyIStack;
import Model.Structures.PrgState;
import Model.Structures.MyIDictionary;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString()
                + ")ELSE(" + elseS.toString() + ")";
    }

    public PrgState execute(PrgState state) throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, VarNotDefInHeapExc {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        if (exp.eval(symtbl, heap) != 0)
            stk.push(this.thenS);
        else
            stk.push(this.elseS);
        return null;
    }

    public IStmt dup() {
        return new IfStmt(exp.dup(), thenS.dup(), elseS.dup());
    }

}
