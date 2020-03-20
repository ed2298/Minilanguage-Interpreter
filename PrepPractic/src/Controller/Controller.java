package Controller;

import Model.Exceptions.*;
import Model.Structures.MyIFileTable;
import Model.Structures.MyITuple;
import Model.Structures.PrgState;
import Repository.MyIRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repo;
    private ExecutorService executor;

    public Controller(MyIRepository r) {
        this.repo = r;
    }

    private Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                               Map<Integer, Integer> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void closeOpenedFiles(MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable) {
        fileTable.getAllValues().forEach(f -> {
            try {
                f.getSecond().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, MyStmtExecException, InvalidOperationExc, DivByZeroExc, VarNotDefInSymTblExc, EmptyRepoExc, IOException, FileAlreadyInFileTableExc, FileIsNotOpenExc, VarNotDefInHeapExc {
        /*prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull).collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(p -> p.getHeap().setContent(conservativeGarbageCollector(
                p.getSymTable().getContent().values(),
                p.getHeap().getContent())));
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException, IOException, FileIsNotOpenExc, FileAlreadyInFileTableExc, InvalidOperationExc, MyStmtExecException, VarNotDefInSymTblExc, DivByZeroExc, EmptyRepoExc, VarNotDefInHeapExc {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();

        List<PrgState> tmpList = repo.getPrgList();
        closeOpenedFiles(tmpList.get(0).getFileTable());
        repo.setPrgList(prgList);
    }
}
