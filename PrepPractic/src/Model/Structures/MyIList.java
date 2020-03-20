package Model.Structures;

public interface MyIList<T> {
    void add(T e);

    T get(int index);

    boolean isEmpty();

    int size();
}
