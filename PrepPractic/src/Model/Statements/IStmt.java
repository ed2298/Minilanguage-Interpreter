package Model.Statements;

import Model.Exceptions.*;
import Model.Structures.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws DivByZeroExc, InvalidOperationExc, VarNotDefInSymTblExc, FileAlreadyInFileTableExc, FileIsNotOpenExc, VarNotDefInHeapExc, VarNotDefinedInLockTableExc, VarNotDefinedInBarrierTableExc;

    IStmt dup();
}
