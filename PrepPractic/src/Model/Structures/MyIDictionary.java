package Model.Structures;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T, U> {

    U lookup(T key);

    boolean isEmpty();

    void add(T key, U value);

    void update(T key, U value);

    int size();

    Set<T> getAllKeys();

    boolean isDefined(T key);

    Map<T, U> getContent();

    void setContent(Map<T, U> content);


}
