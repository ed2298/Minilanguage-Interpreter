package Model.Structures;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyFileTable implements MyIFileTable<Integer, MyITuple<String, BufferedReader>> {

    private HashMap<Integer, MyITuple<String, BufferedReader>> filetable;
    private static int filetableUniqueKey = 0;

    public MyFileTable() {
        filetable = new HashMap<>();
    }

    public MyITuple<String, BufferedReader> lookup(Integer key) {
        return filetable.get(key);
    }

    public Integer add(MyITuple<String, BufferedReader> value) {
        int key = this.generateUniqueKey();
        filetable.put(key, value);
        return key;
    }

    public void update(Integer key, MyITuple<String, BufferedReader> value) {
        filetable.replace(key, value);
    }

    public int size() {
        return filetable.size();
    }

    public boolean isEmpty() {
        return filetable.isEmpty();
    }

    public Set<Integer> getAllKeys() {
        return filetable.keySet();
    }

    public Collection<MyITuple<String, BufferedReader>> getAllValues() {
        return filetable.values();
    }

    public boolean isDefined(Integer key) {
        return filetable.containsKey(key);
    }

    public int generateUniqueKey() {
        filetableUniqueKey++;
        return filetableUniqueKey;
    }

    public int getUniqueKey() {
        return filetableUniqueKey;
    }

    public void delete(Integer key) {
        filetable.remove(key);
    }

    public Map<Integer, MyITuple<String, BufferedReader>> getContent() {
        return filetable;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<Integer, MyITuple<String, BufferedReader>> value : filetable.entrySet()) {
            result.append(value.getKey());
            result.append("-->");
            result.append(value.getValue().getFirst());
            result.append("\r\n");
        }
        return result.toString();
        //return dictionary.toString();
    }
}
