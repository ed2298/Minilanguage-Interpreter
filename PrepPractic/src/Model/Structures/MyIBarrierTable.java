package Model.Structures;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIBarrierTable<T, U> {
    U lookup(T key);

    boolean isEmpty();

    T add(U value);

    void update(T key, U value);

    int size();

    Set<T> getAllKeys();

    Collection<U> getAllValues();

    boolean isDefined(T key);

    int generateUniqueKey();

    int getUniqueKey();

    void delete(T key);

    Map<T, U> getContent();

    void setContent(Map<T, U> content);
}
