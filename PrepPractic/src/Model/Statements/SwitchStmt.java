package Model.Statements;

import Model.Exceptions.*;
import Model.Expressions.BooleanExp;
import Model.Expressions.Exp;
import Model.Structures.MyIStack;
import Model.Structures.PrgState;

public class SwitchStmt implements IStmt {
    private Exp exp;
    private Exp exp1;
    private Exp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3)
    {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    public String toString()
    {
        return "switch(" + exp.toString() + ") (case" + exp1.toString() + ":" + stmt1.toString() + ") (case" + exp2.toString() + ":" + stmt2.toString() + ") (default: " + stmt3.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getStk();
        IStmt statement = new IfStmt(new BooleanExp(exp, exp1, "=="), stmt1, new IfStmt(new BooleanExp(exp, exp2, "=="), stmt2, stmt3));
        stk.push(statement);
        return null;
    }

    public IStmt dup()
    {
        return new SwitchStmt(exp.dup(), exp1.dup(), stmt1.dup(), exp2.dup(), stmt2.dup(), stmt3.dup());
    }
}
