package Model.Structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T, U> implements MyIDictionary<T, U> {
    private HashMap<T, U> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    public U lookup(T key) {
        return dictionary.get(key);
    }

    public void add(T key, U value) {
        dictionary.put(key, value);
    }

    public void update(T key, U value) {
        dictionary.replace(key, value);
    }

    public int size() {
        return dictionary.size();
    }

    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    public Set<T> getAllKeys() {
        return dictionary.keySet();
    }

    public boolean isDefined(T key) {
        return dictionary.containsKey(key);
    }

    public Map<T, U> getContent() {
        return dictionary;
    }

    public void setContent(Map<T, U> content) {
        dictionary = (HashMap<T, U>) content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (HashMap.Entry<T, U> value : dictionary.entrySet()) {
            result.append(value.getKey());
            result.append("-->");
            result.append(value.getValue());
            result.append("\r\n");
        }
        return result.toString();
        //return dictionary.toString();
    }

}
