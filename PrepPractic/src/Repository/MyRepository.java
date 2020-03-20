package Repository;

import Model.Structures.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyRepository implements MyIRepository {
    private List<PrgState> repo;
    private String logFilePath;

    public MyRepository(PrgState prg, String logFilePath) throws IOException {
        this.repo = new ArrayList<>();
        this.logFilePath = logFilePath;
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.println();
        logFile.close();
        this.addPrgState(prg);
    }

    private void addPrgState(PrgState prg) {
        this.repo.add(prg);
    }

    public List<PrgState> getPrgList() {
        return this.repo;
    }

    public void setPrgList(List<PrgState> l) {
        this.repo = l;
    }

    public void logPrgStateExec(PrgState prg) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

        logFile.print("Program id: ");
        logFile.print(Integer.toString(prg.getPrgId()));
        logFile.println();

        logFile.print("Exe stack: ");
        logFile.println();
        logFile.print(prg.getStk().toString());
        logFile.println();

        logFile.print("Symbols Table: ");
        logFile.println();
        logFile.print(prg.getSymTable().toString());
        logFile.println();

        logFile.print("Output: ");
        logFile.println();
        logFile.print(prg.getLst().toString());
        logFile.println();

        logFile.print("File Table: ");
        logFile.println();
        logFile.print(prg.getFileTable().toString());
        logFile.println();

        logFile.print("Heap: ");
        logFile.println();
        logFile.print(prg.getHeap().toString());
        logFile.println();

        logFile.print("Lock Table: ");
        logFile.println();
        logFile.print(prg.getLockTable().toString());
        logFile.println();

        logFile.print("Barrier Table: ");
        logFile.println();
        logFile.print(prg.getBarrierTable().toString());
        logFile.println();

        logFile.close();
    }

    public PrgState getPrgById(int prgId)
    {
        for (PrgState p : repo)
            if (p.getPrgId() == prgId) return p;
        return null;
    }
}
