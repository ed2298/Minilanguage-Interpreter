package View;

import Controller.Controller;
import Model.Exceptions.*;

import java.io.IOException;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        } catch (InvalidOperationExc | MyStmtExecException | VarNotDefInSymTblExc | DivByZeroExc | EmptyRepoExc | FileIsNotOpenExc | FileAlreadyInFileTableExc | VarNotDefInHeapExc e) {
            System.out.print(e.getMessage());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

