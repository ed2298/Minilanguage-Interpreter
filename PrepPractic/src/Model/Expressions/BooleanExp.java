package Model.Expressions;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public class BooleanExp extends Exp {
    private Exp exp1;
    private Exp exp2;
    private String op;

    public BooleanExp(Exp exp1, Exp exp2, String op)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap) throws VarNotDefInHeapExc, InvalidOperationExc, DivByZeroExc, VarNotDefInSymTblExc {
        switch (op) {
            case "<":
                if (exp1.eval(tbl, heap) < exp2.eval(tbl, heap))
                    return 1;
                return 0;
            case "<=":
                if (exp1.eval(tbl, heap) <= exp2.eval(tbl, heap))
                    return 1;
                return 0;
            case "==":
                if (exp1.eval(tbl, heap) == exp2.eval(tbl, heap))
                    return 1;
                return 0;
            case "!=":
                if (exp1.eval(tbl, heap) != exp2.eval(tbl, heap))
                    return 1;
                return 0;
            case ">=":
                if (exp1.eval(tbl, heap) >= exp2.eval(tbl, heap))
                    return 1;
                return 0;
            case ">":
                if (exp1.eval(tbl, heap) > exp2.eval(tbl, heap))
                    return 1;
                return 0;
            default:
                throw new InvalidOperationExc();
        }
    }

    public String toString()
    {
        return "(" + this.exp1.toString() + " " + this.op + " " + this.exp2.toString() + ")";
    }

    public Exp dup()
    {
        return new BooleanExp(this.exp1.dup(), this.exp2.dup(), this.op);
    }
}
