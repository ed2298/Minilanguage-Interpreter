package Model.Structures;

import java.util.*;

public class MyBarrierTable implements MyIBarrierTable<Integer, MyITuple<Integer, List<Integer>>> {
    private HashMap<Integer, MyITuple<Integer, List<Integer>>> barrierTable;
    private int barrierTableUniqueKey = 0;

    public MyBarrierTable() {
        barrierTable = new HashMap<>();
    }

    public MyITuple<Integer, List<Integer>> lookup(Integer key) {
        return barrierTable.get(key);
    }

    public Integer add(MyITuple<Integer, List<Integer>> value) {
        int key = this.generateUniqueKey();
        barrierTable.put(key, value);
        return key;
    }

    public void update(Integer key, MyITuple<Integer, List<Integer>> value) {
        barrierTable.replace(key, value);
    }

    public int size() {
        return barrierTable.size();
    }

    public boolean isEmpty() {
        return barrierTable.isEmpty();
    }

    public Set<Integer> getAllKeys() {
        return barrierTable.keySet();
    }

    public Collection<MyITuple<Integer, List<Integer>>> getAllValues() {
        return barrierTable.values();
    }

    public boolean isDefined(Integer key) {
        return barrierTable.containsKey(key);
    }

    public int generateUniqueKey() {
        barrierTableUniqueKey++;
        return barrierTableUniqueKey;
    }

    public int getUniqueKey() {
        return barrierTableUniqueKey;
    }

    public void delete(Integer key) {
        barrierTable.remove(key);
    }

    public Map<Integer, MyITuple<Integer, List<Integer>>> getContent() {
        return barrierTable;
    }

    public void setContent(Map<Integer, MyITuple<Integer, List<Integer>>> content) {
        barrierTable = (HashMap<Integer, MyITuple<Integer, List<Integer>>>) content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<Integer, MyITuple<Integer, List<Integer>>> value : barrierTable.entrySet()) {
            result.append(value.getKey());
            result.append("-->");
            result.append(value.getValue().getFirst());
            result.append(":::");
            result.append(value.getValue().getSecond().toString());
            result.append("\r\n");
        }
        return result.toString();
    }
}
