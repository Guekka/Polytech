public class ReversibleQueueImpl<T> extends EnhancedQueueImpl<T> {

    public static void main(String[] args) throws EmptyQueueException {
        ReversibleQueueImpl<Integer> q = new ReversibleQueueImpl<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        QueueInterface<Integer> res = q.reverse();
        System.out.println("Reversed queue :  " + res);
        System.out.println("q is not modified : " + q);
        int value = q.peek();
        assertEquals(1, value);
        assertEquals(3, q.size);
        q.dequeue();
        q.dequeue();
        value = q.peek();
        assertEquals(3, value);

        value = res.peek();
        assertEquals(3, value);
        res.dequeue();
        value = res.peek();
        assertEquals(2, value);
        res.dequeue();
        value = res.peek();
        assertEquals(1, value);
    }

    public static void assertEquals(int i, int value) {
        if (i != value) throw new AssertionError("Expected " + i + " but was " + value);
    }

    public QueueInterface<T> reverse() throws EmptyQueueException {
        var copy = copy();
        var tmp = new ArrayStack<T>();
        while (!copy.isEmpty()) {
            tmp.push(copy.dequeue());
        }
        var res = new ReversibleQueueImpl<T>();
        while (!tmp.isEmpty()) {
            try {
                res.enqueue(tmp.pop());
            } catch (EmptyStackException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }
}
