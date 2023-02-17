package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueTest {
    ListQueue<String> listQueue;
    StackQueue<String> stackQueue;

    @BeforeEach
    void setUp() {
        listQueue = new ListQueue<>();
        stackQueue = new StackQueue<>();
    }



    void initialiseWith3Strings(QueueInterface<String> queue){
        queue.enqueue("first");
        queue.enqueue("second");
        queue.enqueue("third");
    }

    void enqueueOne(QueueInterface<String> queue) throws EmptyQueueException {
        assertTrue(queue.isEmpty());
        queue.enqueue("a");
        assertEquals(1, queue.size());
        String res = queue.peek();
        assertEquals("a", res);
    }

    void dequeueOne(QueueInterface<String> queue) throws EmptyQueueException {
        assertTrue(queue != null);
        assertFalse(queue.isEmpty());
        String resPeek = queue.peek();
        int size = queue.size();
        String resDequeue =queue.dequeue();
        assertEquals(resPeek, resDequeue);
        assertEquals(size-1,queue.size());
    }

    void enqueueDequeueEnqueueOne(QueueInterface<String> queue) throws EmptyQueueException {
        assertTrue(queue.isEmpty());
        queue.enqueue("first");
        String resDequeue =queue.dequeue();
        assertEquals("first", resDequeue);
        assertEquals(0,queue.size());
        queue.enqueue("second");
        assertEquals(1,queue.size());
        assertEquals("second",queue.peek());
        assertEquals("second", queue.dequeue());
    }

    @Test
    void enqueueOne4listQueue() throws EmptyQueueException {
        enqueueOne(listQueue);
    }
    @Test
    void enqueueOne4StackQueue() throws EmptyQueueException {
        enqueueOne(stackQueue);
    }
    @Test
    void enqueueDequeueEnqueueOne4list() throws EmptyQueueException {
        enqueueDequeueEnqueueOne(listQueue);
    }
    @Test
    void enqueueDequeueEnqueueOne4stack() throws EmptyQueueException {
        enqueueDequeueEnqueueOne(stackQueue);
    }

    @Test
    void dequeueList() throws EmptyQueueException {
        initialiseWith3Strings(listQueue);
        dequeueOne(listQueue);
    }

    @Test
    void dequeueStack() throws EmptyQueueException {
        initialiseWith3Strings(stackQueue);
        dequeueOne(stackQueue);
    }
    @Test
    void testToStringList() {
        initialiseWith3Strings(listQueue);
        assertEquals("<- first second third <-",listQueue.toString());
    }
    @Test
    void testToStringStack() {
        stackQueue = new StackQueue<>();
        initialiseWith3Strings(stackQueue);
        String s = stackQueue.toString();
        assertEquals("<- first second third <-",s);
        stackQueue.enqueue("fourth");
        s = stackQueue.toString();
        assertEquals("<- first second third fourth <-",s);
    }
    @Test
    void initialSize4ListTest() {
        assertEquals(0, listQueue.size());
    }
    @Test
    void initialSize4StackTest() {
        assertEquals(0, stackQueue.size());
    }

    @Test
    void size4ListTest() {
        initialiseWith3Strings(listQueue);
        assertEquals(3, listQueue.size());
    }
    @Test
    void size4StackTest() {
        initialiseWith3Strings(stackQueue);
        assertEquals(3, stackQueue.size());
    }

    @Test
    void peek4ListTest() throws EmptyQueueException {
        initialiseWith3Strings(listQueue);
        assertEquals("first", listQueue.peek());
    }
    @Test
    void peek4StackTest() throws EmptyQueueException {
        initialiseWith3Strings(stackQueue);
        assertEquals("first", stackQueue.peek());
    }
}