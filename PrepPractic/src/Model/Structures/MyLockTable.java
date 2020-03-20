package Model.Structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyLockTable implements MyILockTable<Integer, Integer> {
    private HashMap<Integer, Integer> lockTable;
    private int lockTableUniqueKey = 0;

    public MyLockTable() {
        lockTable = new HashMap<>();
    }

    public Integer lookup(Integer key) {
        return lockTable.get(key);
    }

    public Integer add(Integer value) {
        int key = this.generateUniqueKey();
        lockTable.put(key, value);
        return key;
    }

    public void update(Integer key, Integer value) {
        lockTable.replace(key, value);
    }

    public int size() {
        return lockTable.size();
    }

    public boolean isEmpty() {
        return lockTable.isEmpty();
    }

    public Set<Integer> getAllKeys() {
        return lockTable.keySet();
    }

    public Collection<Integer> getAllValues() {
        return lockTable.values();
    }

    public boolean isDefined(Integer key) {
        return lockTable.containsKey(key);
    }

    public int generateUniqueKey() {
        lockTableUniqueKey++;
        return lockTableUniqueKey;
    }

    public int getUniqueKey() {
        return lockTableUniqueKey;
    }

    public void delete(Integer key) {
        lockTable.remove(key);
    }

    public Map<Integer, Integer> getContent() {
        return lockTable;
    }

    public void setContent(Map<Integer, Integer> content) {
        lockTable = (HashMap<Integer, Integer>) content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<Integer, Integer> value : lockTable.entrySet()) {
            result.append(value.getKey());
            result.append("-->");
            result.append(value.getValue());
            result.append("\r\n");
        }
        return result.toString();
    }
}
