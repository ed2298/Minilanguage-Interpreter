package sample;

import javafx.beans.property.SimpleStringProperty;

public class TableViewColumnType {
    private final SimpleStringProperty Column1;
    private final SimpleStringProperty Column2;

    TableViewColumnType(String col1, String col2) {
        this.Column1 = new SimpleStringProperty(col1);
        this.Column2 = new SimpleStringProperty(col2);
    }

    public String getColumn1() {
        return Column1.get();
    }

    public String getColumn2() {
        return Column2.get();
    }

    public void setColumn1(String col1) {
        Column1.set(col1);
    }

    public void setColumn2(String col2) {
        Column2.set(col2);
    }
}
