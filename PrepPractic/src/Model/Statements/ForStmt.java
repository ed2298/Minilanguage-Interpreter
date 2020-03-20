package Model.Statements;

import Model.Expressions.BooleanExp;
import Model.Expressions.Exp;
import Model.Expressions.VarExp;
import Model.Structures.MyIStack;
import Model.Structures.PrgState;

public class ForStmt implements IStmt {
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt statement;

    public ForStmt(Exp exp1, Exp exp2, Exp exp3, IStmt statement)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.statement = statement;
    }

    public String toString()
    {
        return "for(v=" + exp1.toString() + ";v<" + exp2.toString() + ";v=" + exp3.toString() + ") " + statement.toString();
    }

    public PrgState execute(PrgState state)
    {
        MyIStack<IStmt> stk = state.getStk();
        IStmt newStmt = new CompStmt(new AssignStmt("v", exp1), new WhileStmt(
                new BooleanExp(new VarExp("v"), exp2, "<"), new CompStmt(statement, new AssignStmt("v", exp3))));
        stk.push(newStmt);
        return null;
    }

    public IStmt dup()
    {
        return new ForStmt(this.exp1.dup(), this.exp2.dup(), this.exp3.dup(), this.statement.dup());
    }
}
