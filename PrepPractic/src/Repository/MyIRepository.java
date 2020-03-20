package Repository;

import Model.Structures.PrgState;

import java.io.IOException;
import java.util.List;

public interface MyIRepository {

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> l);

    void logPrgStateExec(PrgState prg) throws IOException;

    PrgState getPrgById(int prgId);
}
