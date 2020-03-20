package Model.Expressions;

import Model.Structures.MyIDictionary;
import Model.Structures.MyIHeap;

public class ConstExp extends Exp {
    private int number;

    public ConstExp(int number) {
        this.number = number;
    }

    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer, Integer> heap) {
        return this.number;
    }

    public String toString() {
        return Integer.toString(number);
    }

    public Exp dup() {
        return new ConstExp(number);
    }
}
