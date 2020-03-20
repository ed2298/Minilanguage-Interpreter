package Model.Statements;

import Model.Exceptions.DivByZeroExc;
import Model.Exceptions.InvalidOperationExc;
import Model.Exceptions.VarNotDefInHeapExc;
import Model.Exceptions.VarNotDefInSymTblExc;
import Model.Structures.MyIDictionary;
import Model.Structures.MyILockTable;
import Model.Structures.PrgState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;

    public NewLockStmt(String var) {
        this.var = var;
    }

    public String toString() {
        return "newLock(" + this.var + ")";
    }

    public PrgState execute(PrgState state) throws VarNotDefInSymTblExc, InvalidOperationExc, DivByZeroExc, VarNotDefInHeapExc {
        synchronized (state.getLockTable()) {
            //Lock lock = new ReentrantLock();
            //lock.lock();

            MyIDictionary<String, Integer> symtbl = state.getSymTable();
            MyILockTable<Integer, Integer> lockTable = state.getLockTable();
            int lastLocation = lockTable.add(-1);
            if (symtbl.isDefined(var)) {
                symtbl.update(var, lastLocation);
            } else {
                symtbl.add(var, lastLocation);
            }
            //lock.unlock();
        }
        return null;
    }

    public IStmt dup() {
        return new NewLockStmt(this.var);
    }
}
