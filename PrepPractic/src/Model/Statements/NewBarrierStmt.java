package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Expressions.Exp;
import Model.Structures.*;

import java.util.ArrayList;
import java.util.List;

public class NewBarrierStmt implements IStmt {
    private String var;
    private Exp exp;

    public NewBarrierStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    public String toString() {
        return "newBarrier(" + this.var + this.exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, InvalidOperationExc, DivByZeroExc, VarNotDefInHeapExc {
        synchronized (state.getBarrierTable()) {
            MyIDictionary<String, Integer> symtbl = state.getSymTable();
            MyIHeap<Integer, Integer> heap = state.getHeap();
            MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
            int number = exp.eval(symtbl, heap);
            List<Integer> l = new ArrayList<>();
            int lastLocation = barrierTable.add(new MyTuple<>(number, l));
            if (symtbl.isDefined(var)) {
                symtbl.update(var, lastLocation);
            } else {
                symtbl.add(var, lastLocation);
            }
        }
        return null;
    }

    public IStmt dup() {
        return new NewBarrierStmt(this.var, this.exp.dup());
    }
}
