package sample;

import Model.Expressions.*;
import Model.Statements.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstWindow {

    @FXML
    public ListView<IStmt> list1;
    @FXML
    public Button button1;

    public void initialize() {
        IStmt ex1 = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new
                VarExp("v")));

        IStmt ex2 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new
                ArithExp(new ConstExp(3), new ConstExp(5), "*"), "+")),
                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new
                        ConstExp(1), "+")), new PrintStmt(new VarExp("b"))));

        IStmt ex3 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new
                ConstExp(2), "-")),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new
                        AssignStmt("v", new ConstExp(3))), new PrintStmt(new VarExp("v"))));

        IStmt ex4 = new CompStmt(
                new OpenRFile("var_f", "test.in"),
                new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new CompStmt(
                        new PrintStmt(new VarExp("var_c")), new CompStmt(new IfStmt(new VarExp("var_c"),
                        new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                        new PrintStmt(new ConstExp(0))), new CloseRFile(new VarExp("var_f"))))));

        IStmt ex5 = new CompStmt(
                new OpenRFile("var_f", "test.in"),
                new CompStmt(new ReadFile(new VarExp("var_f+2"), "var_c"), new CompStmt(
                        new PrintStmt(new VarExp("var_c")), new CompStmt(new IfStmt(new VarExp("var_c"),
                        new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                        new PrintStmt(new ConstExp(0))), new CloseRFile(new VarExp("var_f"))))));

        IStmt ex6 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)), new PrintStmt(new VarExp("v")))));

        IStmt ex7 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new PrintStmt(new ArithExp(new ConstExp(100), new ReadHeapExp("v"), "+")),
                                        new PrintStmt(new ArithExp(new ConstExp(100), new ReadHeapExp("a"), "+"))))));

        IStmt ex8 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new ReadHeapExp("a")))))));

        IStmt ex9 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("v", new ConstExp(20)),
                        new CompStmt(new NewStmt("a", new ConstExp(22)),
                                new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new CompStmt(new PrintStmt(new ReadHeapExp("a")),
                                                        new AssignStmt("a", new ConstExp(0))))))));

        IStmt ex10 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(10),
                new BooleanExp(new ConstExp(2), new ConstExp(6), "<"), "+")),
                new PrintStmt(new VarExp("a")));


        IStmt ex11 = new CompStmt(new AssignStmt("a",
                new BooleanExp(new ArithExp(new ConstExp(10), new ConstExp(2), "+"), new ConstExp(6), "<")),
                new PrintStmt(new VarExp("a")));

        IStmt ex12 = new CompStmt(new AssignStmt("v", new ConstExp(6)),
                new CompStmt(new WhileStmt(new ArithExp(new VarExp("v"), new ConstExp(4), "-"),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ConstExp(1), "-")))),
                        new PrintStmt(new VarExp("v"))));

        IStmt ex13 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new NewStmt("a", new ConstExp(22)),
                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ConstExp(30)),
                                new CompStmt(new AssignStmt("v", new ConstExp(32)),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp("a")))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp("a"))))));

        IStmt ex14 = new CompStmt(new AssignStmt("v", new ConstExp(20)), new CompStmt(new ForStmt(new ConstExp(0),
                new ConstExp(3), new ArithExp(new VarExp("v"),new ConstExp(1), "+"),
                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"), new ConstExp(1),
                        "+"))))), new PrintStmt(new ArithExp(new VarExp("v"), new ConstExp(10), "*"))));

        IStmt ex15 = new CompStmt(new NewStmt("v1", new ConstExp(20)),
                new CompStmt(new NewStmt("v2", new ConstExp(30)),
                        new CompStmt(new NewLockStmt("x"),
                                new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp("v1"), new ConstExp(1), "-")), new UnlockStmt("x")))),
                                        new CompStmt(new LockStmt("x"),
                                                new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp("v1"), new ConstExp(1), "+")), new UnlockStmt("x"))))),
                                        new CompStmt(new NewLockStmt("q"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                                new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp("v2"), new ConstExp(5), "+")), new UnlockStmt("x")))),
                                                new CompStmt(new AssignStmt("m", new ConstExp(100)), new CompStmt(new LockStmt("x"),
                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp("v2"), new ConstExp(1), "+")), new UnlockStmt("x")))))),
                                                new CompStmt(new AssignStmt("z", new ConstExp(200)), new CompStmt(new AssignStmt("z", new ConstExp(300)),
                                                        new CompStmt(new AssignStmt("z", new ConstExp(400)), new CompStmt(new LockStmt("x"),
                                                                new CompStmt(new PrintStmt(new ReadHeapExp("v1")), new CompStmt(new UnlockStmt("x"),
                                                                        new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new ReadHeapExp("v2")), new UnlockStmt("q")))))))))))))));

        IStmt ex16 = new CompStmt(new AssignStmt("a", new ConstExp(1)), new CompStmt(new AssignStmt("b", new ConstExp(2)),
                new CompStmt(new AssignStmt("c", new ConstExp(5)),
                        new CompStmt(new SwitchStmt(new ArithExp(new VarExp("a"), new ConstExp(10), "*"),
                                new ArithExp(new VarExp("b"), new ConstExp(5), "*"),
                                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                new ConstExp(10),
                                new CompStmt(new PrintStmt(new ConstExp(100)), new PrintStmt(new ConstExp(200))),
                                new PrintStmt(new ConstExp(300))), new PrintStmt(new ConstExp(300))))));

        IStmt ex17 = new CompStmt(new AssignStmt("v", new ConstExp(1)),
                new CompStmt(new ForkStmt(new AssignStmt("v", new ConstExp(2))), new ForkStmt(new AssignStmt("v", new ConstExp(3)))));

        IStmt ex18 = new CompStmt(new NewStmt("v1", new ConstExp(2)),
                new CompStmt(new NewStmt("v2", new ConstExp(3)),
                        new CompStmt(new NewStmt("v3", new ConstExp(4)),
                                new CompStmt(new NewBarrierStmt("cnt", new ReadHeapExp("v2")),
                                        new CompStmt(new ForkStmt(new CompStmt(new AwaitStmt("cnt"),
                                                new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp("v1"), new ConstExp(10), "*")),
                                                        new PrintStmt(new ReadHeapExp("v1"))))),
                                                new CompStmt(new ForkStmt(new CompStmt(new AwaitStmt("cnt"),
                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp("v2"), new ConstExp(10), "*")),
                                                                new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp("v2"), new ConstExp(10), "*")),
                                                                        new PrintStmt(new ReadHeapExp("v2")))))),
                                                        new CompStmt(new AwaitStmt("cnt"), new PrintStmt(new ReadHeapExp("v3")))))))));

        ObservableList<IStmt> statements = FXCollections.observableArrayList();
        statements.addAll(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14, ex15, ex16, ex17, ex18);
        list1.setItems(statements);
    }

    @FXML
    public void openMainWindow(ActionEvent event) {
        IStmt stmt = list1.getSelectionModel().getSelectedItem();
        if (stmt != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                Parent root = fxmlLoader.load();

                MainWindow mainCtrl = fxmlLoader.getController();
                mainCtrl.initialize(stmt);

                Stage newWindow = new Stage();
                newWindow.setTitle("Program execution");
                newWindow.setScene(new Scene(root));
                newWindow.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            list1.getItems().remove(stmt);
        }
    }
}
