package Model.Statements;

import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Exceptions.VarNotDefinedInLockTableExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyILockTable;
import Model.Structures.MyIStack;
import Model.Structures.PrgState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStmt implements IStmt {
    private String var;

    public UnlockStmt(String var) {
        this.var = var;
    }

    public String toString() {
        return "unlock(" + this.var + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc {
        synchronized (state.getLockTable()) {
            // Lock lock = new ReentrantLock();
            // lock.lock();

            MyIDictionary<String, Integer> symtbl = state.getSymTable();
            MyIStack<IStmt> stk = state.getStk();
            if (symtbl.lookup(var) == null)
                throw new VarNotDefInSymTblExc();
            int foundIndex = symtbl.lookup(var);

            MyILockTable<Integer, Integer> lockTable = state.getLockTable();
            if (lockTable.lookup(foundIndex) == state.getPrgId()) {
                lockTable.update(foundIndex, -1);
            }
            //lock.unlock();
        }
        return null;
    }

    public IStmt dup() {
        return new UnlockStmt(this.var);
    }
}
