package Model.Expressions;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public class ArithExp extends Exp {
    private Exp e1;
    private Exp e2;
    private String op;

    public ArithExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap) throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, VarNotDefInHeapExc {
        switch (op) {
            case "+":
                return (e1.eval(tbl, heap) + e2.eval(tbl, heap));
            case "-":
                return (e1.eval(tbl, heap) - e2.eval(tbl, heap));
            case "*":
                return (e1.eval(tbl, heap) * e2.eval(tbl, heap));
            case "/":
                if (e2.eval(tbl, heap) != 0)
                    return (e1.eval(tbl, heap) / e2.eval(tbl, heap));
                else
                    throw new DivByZeroExc();
            default:
                throw new InvalidOperationExc();
        }
    }

    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    public Exp dup() {
        return new ArithExp(e1.dup(), e2.dup(), op);
    }
}
