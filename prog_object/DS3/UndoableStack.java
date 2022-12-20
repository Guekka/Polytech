public class UndoableStack<T> implements UndoInterface, StackInterface<T> {
    private final ArrayStack<T> stack = new ArrayStack<>();
    private final ArrayStack<LogItem<T>> undoStack = new ArrayStack<>();

    public LogItem<T> lastAction() throws EmptyStackException {
        return undoStack.peek();
    }

    public boolean undo() {
        if (undoStack.isEmpty())
            return false;

        try {
            var item = undoStack.pop();
            if (item.getAction().equals("push")) {
                stack.pop();
            } else {
                stack.push(item.getValue());
            }
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T peek() throws EmptyStackException {
        return stack.peek();
    }

    @Override
    public void push(T x) {
        stack.push(x);
        undoStack.push(new LogItemImpl<>("push", x));
    }

    @Override
    public T pop() throws EmptyStackException {
        var item = stack.pop();
        undoStack.push(new LogItemImpl<>("pop", item));
        return item;
    }
}