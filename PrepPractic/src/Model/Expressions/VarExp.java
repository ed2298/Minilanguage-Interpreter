package Model.Expressions;

import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public class VarExp extends Exp {
    private String id;

    public VarExp(String id) {
        this.id = id;
    }

    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap) throws VarNotDefInSymTblExc {
        if (tbl.lookup(id) == null)
            throw new VarNotDefInSymTblExc();
        return tbl.lookup(id);
    }

    public String toString() {
        return id;
    }

    public Exp dup() {
        return new VarExp(id);
    }
}
