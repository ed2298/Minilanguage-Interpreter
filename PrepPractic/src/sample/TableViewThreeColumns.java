package sample;

import javafx.beans.property.SimpleStringProperty;

public class TableViewThreeColumns {
    private final SimpleStringProperty Column1;
    private final SimpleStringProperty Column2;
    private final SimpleStringProperty Column3;

    TableViewThreeColumns(String col1, String col2, String col3) {
        this.Column1 = new SimpleStringProperty(col1);
        this.Column2 = new SimpleStringProperty(col2);
        this.Column3 = new SimpleStringProperty(col3);
    }

    public String getColumn1() {
        return Column1.get();
    }

    public String getColumn2() {
        return Column2.get();
    }

    public String getColumn3() {return Column3.get(); }

    public void setColumn1(String col1) {
        Column1.set(col1);
    }

    public void setColumn2(String col2) {
        Column2.set(col2);
    }

    public void setColumn3(String col3) { Column3.set(col3);}
}
