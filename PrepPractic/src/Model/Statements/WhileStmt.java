package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;
import Model.Structures.MyIStack;
import Model.Structures.PrgState;

public class WhileStmt implements IStmt {
    private Exp exp;
    private IStmt statement;

    public WhileStmt(Exp exp, IStmt statement)
    {
        this.exp = exp;
        this.statement = statement;
    }

    public String toString()
    {
        return "while(" + this.exp.toString() + ") " + this.statement.toString();
    }

    public PrgState execute(PrgState state) throws VarNotDefInHeapExc, InvalidOperationExc, DivByZeroExc, VarNotDefInSymTblExc {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        if (exp.eval(symtbl, heap) != 0)
        {
            stk.push(new WhileStmt(exp, statement));
            stk.push(statement);
        }
        return null;
    }

    public IStmt dup()
    {
        return new WhileStmt(this.exp.dup(), this.statement.dup());
    }
}
