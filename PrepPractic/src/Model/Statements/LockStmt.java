package Model.Statements;

import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Exceptions.VarNotDefinedInLockTableExc;
import Model.Structures.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt {
    private String var;

    public LockStmt(String var) {
        this.var = var;
    }

    public String toString() {
        return "lock(" + this.var + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, VarNotDefinedInLockTableExc {
        synchronized (state.getLockTable()) {
            //Lock lock = new ReentrantLock();
            //lock.lock();

            MyIDictionary<String, Integer> symtbl = state.getSymTable();
            MyIStack<IStmt> stk = state.getStk();
            if (symtbl.lookup(var) == null)
                throw new VarNotDefInSymTblExc();
            int foundIndex = symtbl.lookup(var);

            MyILockTable<Integer, Integer> lockTable = state.getLockTable();
            if (lockTable.lookup(foundIndex) == null)
                throw new VarNotDefinedInLockTableExc();
            else if (lockTable.lookup(foundIndex) == -1)
                lockTable.update(foundIndex, state.getPrgId());
            else {
                stk.push(new LockStmt(var));
            }
            //lock.unlock();
        }
        return null;
    }

    public IStmt dup() {
        return new LockStmt(this.var);
    }

}
