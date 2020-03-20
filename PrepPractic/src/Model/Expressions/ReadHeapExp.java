package Model.Expressions;

import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public class ReadHeapExp extends Exp {
    private String varName;

    public ReadHeapExp(String varName) {
        this.varName = varName;
    }

    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap) throws VarNotDefInSymTblExc, VarNotDefInHeapExc {
        if (tbl.lookup(varName) == null)
            throw new VarNotDefInSymTblExc();
        int heapUniqueKey = tbl.lookup(varName);
        if (heap.lookup(heapUniqueKey) == null)
            throw new VarNotDefInHeapExc();
        int value = heap.lookup(heapUniqueKey);
        return value;
    }

    public String toString() {
        return "rH(" + this.varName + ")";
    }

    public Exp dup() {
        return new ReadHeapExp(this.varName);
    }
}
