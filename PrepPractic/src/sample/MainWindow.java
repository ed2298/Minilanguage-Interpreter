package sample;

import Model.Statements.IStmt;
import Model.Structures.*;
import Repository.MyIRepository;
import Repository.MyRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

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
import java.util.stream.IntStream;

public class MainWindow {
    private MyIRepository repo;
    private ExecutorService executor;
    private List<PrgState> prgList;
    private int crtPrgId;

    @FXML
    private ListView<String> listViewExeStack;
    @FXML
    private ListView<Integer> listViewPrgId;
    @FXML
    private ListView<String> listViewOut;
    @FXML
    private Button button2;
    @FXML
    private TextField textFieldNoPrg;
    @FXML
    private TableView<TableViewColumnType> tableViewSymTbl;
    @FXML
    private TableColumn<TableViewColumnType, String> nameSymTbl;
    @FXML
    private TableColumn<TableViewColumnType, String> valueSymTbl;
    @FXML
    private TableView<TableViewColumnType> tableViewFileTable;
    @FXML
    private TableColumn<TableViewColumnType, String> identifier;
    @FXML
    private TableColumn<TableViewColumnType, String> nameFileTable;
    @FXML
    private TableView<TableViewColumnType> tableViewHeap;
    @FXML
    private TableColumn<TableViewColumnType, String> addressHeap;
    @FXML
    private TableColumn<TableViewColumnType, String> valueHeap;
    @FXML
    private TableView<TableViewColumnType> tableViewLockTable;
    @FXML
    private TableColumn<TableViewColumnType, String> indexLockTable;
    @FXML
    private TableColumn<TableViewColumnType, String> valueLockTable;
    @FXML
    private TableView<TableViewThreeColumns> tableViewBarrierTable;
    @FXML
    private TableColumn<TableViewThreeColumns, String> indexBarrierTable;
    @FXML
    private TableColumn<TableViewThreeColumns, String> valuesBarrierTable;
    @FXML
    private TableColumn<TableViewThreeColumns, String> listBarrierTable;


    private static PrgState createProgram(IStmt ex) {
        MyIStack<IStmt> s = new MyStack<>();
        MyIDictionary<String, Integer> d = new MyDictionary<>();
        MyIList<Integer> l = new MyList<>();
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> f = new MyFileTable();
        MyIHeap<Integer, Integer> h = new MyHeap();
        MyILockTable<Integer, Integer> lock = new MyLockTable();
        MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrier = new MyBarrierTable();
        PrgState prg = new PrgState(s, d, l, f, h, lock, barrier, ex);
        return prg;
    }

    @FXML
    void initialize(IStmt stmt) throws IOException {
        String logfile = "log1.txt";
        PrgState prg = createProgram(stmt);
        this.repo = new MyRepository(prg, logfile);
        int noOfPrgs = repo.getPrgList().size();

        textFieldNoPrg.setText(String.valueOf(noOfPrgs));
        crtPrgId = repo.getPrgList().get(0).getPrgId();
        listViewPrgId.getItems().addAll(crtPrgId);

        MyIStack<IStmt> st = repo.getPrgById(crtPrgId).getStk();
        listViewExeStack.getItems().addAll(st.toString());

        nameSymTbl.setCellValueFactory(new PropertyValueFactory<>("Column1"));
        valueSymTbl.setCellValueFactory(new PropertyValueFactory<>("Column2"));

        identifier.setCellValueFactory(new PropertyValueFactory<>("Column1"));
        nameFileTable.setCellValueFactory(new PropertyValueFactory<>("Column2"));

        addressHeap.setCellValueFactory(new PropertyValueFactory<>("Column1"));
        valueHeap.setCellValueFactory(new PropertyValueFactory<>("Column2"));

        indexLockTable.setCellValueFactory(new PropertyValueFactory<>("Column1"));
        valueLockTable.setCellValueFactory(new PropertyValueFactory<>("Column2"));

        indexBarrierTable.setCellValueFactory(new PropertyValueFactory<>("Column1"));
        valuesBarrierTable.setCellValueFactory(new PropertyValueFactory<>("Column2"));
        listBarrierTable.setCellValueFactory(new PropertyValueFactory<>("Column3"));

        executor = Executors.newFixedThreadPool(10);
        prgList = removeCompletedPrg(repo.getPrgList());
    }

    @FXML
    void handleListViewPrgIdClick(MouseEvent event) {
        listViewExeStack.getItems().clear();
        int prgId = listViewPrgId.getSelectionModel().getSelectedItem();
        PrgState prg = repo.getPrgById(prgId);
        if (prg != null) {
            crtPrgId = prgId;
            updateListViewExeStack();
            updateTableViewSymTable();
        }
    }

    @FXML
    void runOneStep(ActionEvent event) throws InterruptedException {
        if (prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
            updateListViewPrgId();
            updateListViewExeStack();
            updateListViewOut();
            updateTableViewSymTable();
            updateTableViewHeap();
            updateTableViewFileTable();
            updateTableViewLockTable();
            updateTableViewBarrierTable();
        } else {
            executor.shutdownNow();
            List<PrgState> tmpList = repo.getPrgList();
            closeOpenedFiles(tmpList.get(0).getFileTable());
            repo.setPrgList(prgList);
            button2.setDisable(true);
            textFieldNoPrg.setText("0");
            listViewPrgId.getItems().clear();
        }
    }

    private void updateListViewPrgId() {
        int nrPrg = repo.getPrgList().size();
        int nrPrgListViewIds = listViewPrgId.getItems().size();
        //textFieldNoPrg.setText(String.valueOf(nrPrg));
        for (int i = 1; i < nrPrg; i++) {
                int prgId = repo.getPrgList().get(i).getPrgId();
                if (!listViewPrgId.getItems().contains(prgId))
                    listViewPrgId.getItems().addAll(prgId);
        }
        boolean removedThreads = false;
        for (int i = nrPrgListViewIds - 1; i >= 0; i--) {
            int ID = listViewPrgId.getItems().get(i);
            PrgState pr = repo.getPrgById(ID);
            if (pr == null) { // remove ended thread from listViewID
                listViewPrgId.getItems().remove(i); // i is the index in listViewIds
                removedThreads = true;
            }
        }
        textFieldNoPrg.setText(String.valueOf(repo.getPrgList().size()));
        if(removedThreads) crtPrgId = repo.getPrgList().get(0).getPrgId();
    }

    private void updateListViewExeStack() {
        listViewExeStack.getItems().clear();
        MyIStack<IStmt> st;
        st = repo.getPrgById(crtPrgId).getStk();
        String exe = st.toString();
        String[] exestr = exe.split("\n");
        for (String s : exestr) {
            listViewExeStack.getItems().add(s);
        }
    }

    private void updateListViewOut() {
        listViewOut.getItems().clear();
        MyIList<Integer> out = repo.getPrgList().get(0).getLst();
        String output = out.toString();
        String[] outstr = output.split("\n");
        for (String s : outstr) {
            listViewOut.getItems().add(s);
        }
    }

    private void updateTableViewSymTable() {
        ObservableList<TableViewColumnType> columns = FXCollections.observableArrayList();
        MyIDictionary<String, Integer> symtbl = repo.getPrgById(crtPrgId).getSymTable();
        String symbolsTable = symtbl.toString();
        String[] symTblstr = symbolsTable.split("\n");
        for (String pair : symTblstr) {
            String[] var = pair.split("-->");
            columns.add(new TableViewColumnType(var[0], var[var.length - 1]));
        }
        tableViewSymTbl.setItems(columns);
    }

    private void updateTableViewHeap() {
        ObservableList<TableViewColumnType> columns = FXCollections.observableArrayList();
        MyIHeap<Integer, Integer> heap = repo.getPrgList().get(0).getHeap();
        String heapTable = heap.toString();
        String[] heapstr = heapTable.split("\n");
        for (String pair : heapstr) {
            String[] var = pair.split("-->");
            columns.add(new TableViewColumnType(var[0], var[var.length - 1]));
        }
        tableViewHeap.setItems(columns);
    }

    private void updateTableViewFileTable() {
        ObservableList<TableViewColumnType> columns = FXCollections.observableArrayList();
        MyIFileTable<Integer, MyITuple<String, BufferedReader>> fileTable = repo.getPrgList().get(0).getFileTable();
        String fileTablestr = fileTable.toString();
        String[] ftstr = fileTablestr.split("\n");
        for (String pair : ftstr) {
            String[] var = pair.split("-->");
            columns.add(new TableViewColumnType(var[0], var[var.length - 1]));
        }
        tableViewFileTable.setItems(columns);
    }

    private void updateTableViewLockTable()
    {
        ObservableList<TableViewColumnType> columns = FXCollections.observableArrayList();
        MyILockTable<Integer, Integer> lockTable = repo.getPrgList().get(0).getLockTable();
        String locktbl = lockTable.toString();
        String[] lockstr = locktbl.split("\n");
        for (String pair : lockstr) {
            String[] var = pair.split("-->");
            columns.add(new TableViewColumnType(var[0], var[var.length - 1]));
        }
        tableViewLockTable.setItems(columns);
    }

    private void updateTableViewBarrierTable()
    {
        ObservableList<TableViewThreeColumns> columns = FXCollections.observableArrayList();
        MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> barrierTable = repo.getPrgList().get(0).getBarrierTable();
        String barriertbl = barrierTable.toString();
        String[] barrierstr = barriertbl.split("\n");
        for (String pair : barrierstr) {
            String[] var = pair.split("-->");
            String[] otherVar = var[var.length - 1].split(":::");
            columns.add(new TableViewThreeColumns(var[0], otherVar[0], otherVar[otherVar.length - 1]));
        }
        tableViewBarrierTable.setItems(columns);
    }

    private void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        executor.shutdownNow();
                        List<PrgState> tmpList = repo.getPrgList();
                        closeOpenedFiles(tmpList.get(0).getFileTable());
                        repo.setPrgList(prgList);
                        button2.setDisable(true);
                        textFieldNoPrg.setText("0");
                        listViewPrgId.getItems().clear();

                        Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                        error.setTitle("Exception! The program crashed!");
                        error.setHeaderText("Error");
                        error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        error.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                        error.show();
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
}
