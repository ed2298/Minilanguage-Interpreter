package Model.Expressions;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public abstract class Exp {
    public abstract int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap)
            throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, VarNotDefInHeapExc;

    public abstract Exp dup();

    public abstract String toString();
}
