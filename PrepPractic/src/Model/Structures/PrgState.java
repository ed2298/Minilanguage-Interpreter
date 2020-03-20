package Model.Structures;

import Model.Exceptions.*;
import Model.Statements.IStmt;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Integer> symTable;
    private MyIList<Integer> out;
    private MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable;
    private MyIHeap<Integer, Integer> heap;
    private MyILockTable<Integer, Integer> lockTable;
    private MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable;
    private IStmt originalProgram; //optional field, but good to have
    private static int noPrograms = 0;
    private int prgId;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Integer> symtbl,
                    MyIList<Integer> ot, MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable,
                    MyIHeap<Integer, Integer> heap, MyILockTable<Integer, Integer> lockTable, MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.barrierTable = barrierTable;
        //optional field, but good to have
        this.originalProgram = prg.dup();
        //noPrograms++;
        //this.prgId = noPrograms;
        this.prgId = generateUniqueId();
        stk.push(prg);
    }

    //getters, setters
    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public MyIList<Integer> getLst() {
        return out;
    }

    public MyIFileTable<Integer, MyITuple<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, Integer> getHeap() {
        return heap;
    }

    public MyILockTable<Integer, Integer> getLockTable()
    {
        return lockTable;
    }

    public MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> getBarrierTable()
    {
        return barrierTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public int getPrgId() {
        return prgId;
    }

    public Boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws DivByZeroExc, InvalidOperationExc, MyStmtExecException, VarNotDefInSymTblExc, FileIsNotOpenExc, FileAlreadyInFileTableExc, VarNotDefInHeapExc, VarNotDefinedInLockTableExc, VarNotDefinedInBarrierTableExc {
        if (exeStack.isEmpty()) throw new MyStmtExecException();
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public String toString() {
        return "stack " + exeStack.toString() + "\ntable " + symTable.toString() +
                "\nout " + out.toString() + "\nfileTable " + fileTable.toString() +
                "\nheap " + heap.toString() + "\nlockTable " + lockTable.toString() + "\nid " + "\nbarrierTable " + barrierTable.toString() + prgId + "\n\n";
    }
    private static synchronized int generateUniqueId()
    {
        return ++noPrograms;
    }
}
