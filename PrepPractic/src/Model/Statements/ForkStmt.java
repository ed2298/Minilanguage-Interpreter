package Model.Statements;

import Model.Structures.*;

import java.io.BufferedReader;
import java.util.List;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    public PrgState execute(PrgState state) {
        MyIStack<IStmt> s = new MyStack<>();
        MyIDictionary<String, Integer> d = new MyDictionary<>();
        MyIDictionary<String, Integer> symtbl = state.getSymTable();
        for (String key : symtbl.getAllKeys()) {
            d.add(key, symtbl.lookup(key));
        }

        MyIList<Integer> lst = state.getLst();
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable = state.getFileTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();
        MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
        return new PrgState(s, d, lst, fileTable, heap, lockTable, barrierTable, statement);
    }

    public IStmt dup() {
        return new ForkStmt(this.statement.dup());
    }
}
