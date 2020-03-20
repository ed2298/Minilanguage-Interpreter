package Model.Structures;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }


    public T pop() {
        return stack.pop();
    }

    public void push(T v) {
        stack.push(v);
    }

    public T top() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.empty();
    }

    public int size() {
        return stack.size();
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        Stack<T> stack2 = new Stack<>();
        while (!stack.empty()) {
            T obj = stack.pop();
            result.append(obj).append("\r\n");
            stack2.push(obj);
        }

        while (!stack2.empty()) {
            T obj = stack2.pop();
            stack.push(obj);
        }
        return result.toString();
    }

}
