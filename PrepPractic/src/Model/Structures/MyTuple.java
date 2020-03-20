package Model.Structures;

public class MyTuple<T, U> implements MyITuple<T, U> {
    private T first;
    private U second;

    public MyTuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

}
