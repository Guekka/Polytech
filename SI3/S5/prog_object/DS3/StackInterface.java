interface StackInterface<T> {
    boolean isEmpty();

    int size();

    T peek() throws EmptyStackException;

    void push(T x);

    T pop() throws EmptyStackException;
}
