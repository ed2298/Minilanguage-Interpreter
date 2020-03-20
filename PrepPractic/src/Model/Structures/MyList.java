package Model.Structures;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    public void add(T e) {
        list.add(e);
    }

    public T get(int index) {
        return list.get(index);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T e: list)
        {
            result.append(e);
            result.append("\r\n");
        }
        return result.toString();
        //return list.toString();
    }
}
