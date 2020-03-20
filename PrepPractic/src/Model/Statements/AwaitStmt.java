package Model.Statements;

import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Exceptions.VarNotDefinedInBarrierTableExc;
import Model.Exceptions.VarNotDefinedInLockTableExc;
import Model.Structures.*;

import java.util.List;

public class AwaitStmt implements IStmt {
    private String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    public String toString() {
        return "await(" + this.var + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, VarNotDefinedInBarrierTableExc {
        synchronized (state.getBarrierTable()) {
            MyIDictionary<String, Integer> symtbl = state.getSymTable();
            MyIStack<IStmt> stk = state.getStk();
            if (symtbl.lookup(var) == null)
                throw new VarNotDefInSymTblExc();
            int foundIndex = symtbl.lookup(var);
            MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
            if (barrierTable.lookup(foundIndex) == null)
                throw new VarNotDefinedInBarrierTableExc();
            else {
                int N1 = barrierTable.lookup(foundIndex).getFirst();
                List<Integer> list = barrierTable.lookup(foundIndex).getSecond();
                int NL = list.size();
                if (N1 > NL)
                {
                    if (list.contains(state.getPrgId()))
                        stk.push(this);
                    else
                    {
                        list.add(state.getPrgId());
                        stk.push(this);
                    }
                }
            }
        }
        return null;
    }

    public IStmt dup() {
        return new LockStmt(this.var);
    }
}
