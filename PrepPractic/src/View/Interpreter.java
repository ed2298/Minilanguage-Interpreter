package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Structures.*;
import Repository.MyIRepository;
import Repository.MyRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

class Interpreter {

    private static PrgState createProgram(IStmt ex) {
        MyIStack<IStmt> s = new MyStack<>();
        MyIDictionary<String, Integer> d = new MyDictionary<>();
        MyIList<Integer> l = new MyList<>();
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> f = new MyFileTable();
        MyIHeap<Integer, Integer> h = new MyHeap();
        MyILockTable<Integer, Integer> lockTable = new MyLockTable();
        MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrier = new MyBarrierTable();
        PrgState prg = new PrgState(s, d, l, f, h, lockTable, barrier, ex);
        return prg;
    }

    public static void main(String[] args) throws IOException {
        /*IStmt ex1 = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new
                VarExp("v")));
        PrgState prg1 = createProgram(ex1);
        MyRepository repo1 = new MyRepository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new
                ArithExp(new ConstExp(3), new ConstExp(5), "*"), "+")),
                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new
                        ConstExp(1), "+")), new PrintStmt(new VarExp("b"))));
        PrgState prg2 = createProgram(ex2);
        MyRepository repo2 = new MyRepository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new
                ConstExp(2), "-")),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new
                        AssignStmt("v", new ConstExp(3))), new PrintStmt(new VarExp("v"))));
        PrgState prg3 = createProgram(ex3);
        MyRepository repo3 = new MyRepository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(
                new OpenRFile("var_f", "test.in"),
                new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new CompStmt(
                        new PrintStmt(new VarExp("var_c")), new CompStmt(new IfStmt(new VarExp("var_c"),
                        new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                        new PrintStmt(new ConstExp(0))), new CloseRFile(new VarExp("var_f"))))));
        PrgState prg4 = createProgram(ex4);
        MyRepository repo4 = new MyRepository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStmt ex5 = new CompStmt(
                new OpenRFile("var_f", "test.in"),
                new CompStmt(new ReadFile(new VarExp("var_f+2"), "var_c"), new CompStmt(
                        new PrintStmt(new VarExp("var_c")), new CompStmt(new IfStmt(new VarExp("var_c"),
                        new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                        new PrintStmt(new ConstExp(0))), new CloseRFile(new VarExp("var_f"))))));
        PrgState prg5 = createProgram(ex5);
        MyRepository repo5 = new MyRepository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        //v=10;new(v,20);new(a,22);print(v)
        IStmt ex6 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)), new PrintStmt(new VarExp("v")))));
        PrgState prg6 = createProgram(ex6);
        MyRepository repo6 = new MyRepository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        //v=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))
        IStmt ex7 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new PrintStmt(new ArithExp(new ConstExp(100), new ReadHeapExp("v"), "+")),
                                        new PrintStmt(new ArithExp(new ConstExp(100), new ReadHeapExp("a"), "+"))))));
        PrgState prg7 = createProgram(ex7);
        MyRepository repo7 = new MyRepository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a))

        IStmt ex8 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new ReadHeapExp("a")))))));
        PrgState prg8 = createProgram(ex8);
        MyRepository repo8 = new MyRepository(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
        IStmt ex9 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new CompStmt(new PrintStmt(new ReadHeapExp("a")),
                                                        new AssignStmt("a", new ConstExp(0))))))));
        PrgState prg9 = createProgram(ex9);
        MyRepository repo9 = new MyRepository(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStmt ex10 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(10),
                new BooleanExp(new ConstExp(2), new ConstExp(6), "<"), "+")),
                new PrintStmt(new VarExp("a")));
        PrgState prg10 = createProgram(ex10);
        MyRepository repo10 = new MyRepository(prg10, "log10.txt");
        Controller ctr10 = new Controller(repo10);

        IStmt ex11 = new CompStmt(new AssignStmt("a",
                new BooleanExp(new ArithExp(new ConstExp(10), new ConstExp(2), "+"), new ConstExp(6),"<")),
                new PrintStmt(new VarExp("a")));
        PrgState prg11 = createProgram(ex11);
        MyRepository repo11 = new MyRepository(prg11, "log11.txt");
        Controller ctr11 = new Controller(repo11);

        //v=6; (while (v-4) print(v);v=v-1);print(v)
        IStmt ex12 = new CompStmt(new AssignStmt("v", new ConstExp(6)),
                new CompStmt(new WhileStmt(new ArithExp(new VarExp("v"), new ConstExp(4), "-"),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ConstExp(1), "-")))),
                        new PrintStmt(new VarExp("v"))));
        PrgState prg12 = createProgram(ex12);
        MyRepository repo12 = new MyRepository(prg12, "log12.txt");
        Controller ctr12 = new Controller(repo12);
*/

        IStmt ex9 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new CompStmt(new PrintStmt(new ReadHeapExp("a")),
                                                        new AssignStmt("a", new ConstExp(0))))))));
        PrgState prg9 = createProgram(ex9);
        MyRepository repo9 = new MyRepository(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStmt ex13 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("a", new ConstExp(22)),
                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                new CompStmt(new AssignStmt("v", new ConstExp(32)),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp("a")))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp("a"))))));
        PrgState prg13 = createProgram(ex13);
        MyRepository repo13 = new MyRepository(prg13, "log13.txt");
        Controller ctr13 = new Controller(repo13);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        /*menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        menu.addCommand(new RunExample("11", ex11.toString(), ctr11));
        menu.addCommand(new RunExample("12", ex12.toString(), ctr12));*/
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExample("13", ex13.toString(), ctr13));
        menu.show();
    }
}