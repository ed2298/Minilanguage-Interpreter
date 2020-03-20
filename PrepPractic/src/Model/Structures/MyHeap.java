package Model.Structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap implements MyIHeap<Integer, Integer> {
    private HashMap<Integer, Integer> heap;
    private int heapUniqueKey = 0;

    public MyHeap() {
        heap = new HashMap<>();
    }

    public Integer lookup(Integer key) {
        return heap.get(key);
    }

    public Integer add(Integer value) {
        int key = this.generateUniqueKey();
        heap.put(key, value);
        return key;
    }

    public void update(Integer key, Integer value) {
        heap.replace(key, value);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public Set<Integer> getAllKeys() {
        return heap.keySet();
    }

    public Collection<Integer> getAllValues() {
        return heap.values();
    }

    public boolean isDefined(Integer key) {
        return heap.containsKey(key);
    }

    public int generateUniqueKey() {
        heapUniqueKey++;
        return heapUniqueKey;
    }

    public int getUniqueKey() {
        return heapUniqueKey;
    }

    public void delete(Integer key) {
        heap.remove(key);
    }

    public Map<Integer, Integer> getContent() {
        return heap;
    }

    public void setContent(Map<Integer, Integer> content) {
        heap = (HashMap<Integer, Integer>) content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<Integer, Integer> value : heap.entrySet()) {
            result.append(value.getKey());
            result.append("-->");
            result.append(value.getValue());
            result.append("\r\n");
        }
        return result.toString();
    }
}
