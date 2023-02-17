
/**
 * A class for stack-based queue
 * Not the best choice !!!
 */
public class EnhancedQueueImpl<T> implements EnhancedQueueInterface<T> {

    //////////////////////////////////////////////

    /**
     * A private class for list node
     */
    private class ListNode {
        T data;
        ListNode next;

        ListNode(T data) {
            this.data = data;
            this.next = null;
        }
    }
    ListNode head;
    ListNode tail;
    int size;
    /**
     * Build an empty queue
     * Complexity: THETA(1)
     */
    public EnhancedQueueImpl() {
        head = null;
        tail = null;
        size = 0;
    }


    /**
     * Return the number of elements
     * currently in the queue
     * Complexity: THETA(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the next value to be dequeued
     * If the queue is empty throws EmptyQueueException
     * Complexity: THETA(1)
     */
    @Override
    public T peek() throws EmptyQueueException {
        if (head == null)
            throw new EmptyQueueException();
        return head.data;
    }

    /**
     * Enqueue x in the queue
     * Complexity: THETA(1)
     */
    @Override
    public void enqueue(T x) {
        ListNode node = new ListNode(x);
        if (isEmpty()){
            head = node;
        }
        else {
            tail.next = node;
        }
        tail = node;
        size++;
    }


    /**
     * Dequeue and return the next element to
     * be dequeued
     * If the queue is empty throws EmptyQueueException
     * Complexity: THETA(1)
     */
    @Override
    public T dequeue() throws EmptyQueueException {
        if (isEmpty()){
            throw new EmptyQueueException();
        }
        T value = head.data;
        head = head.next;
        if (head==null)
            tail = null;
        size--;
        return value;
    }


    /**
     * Return a string representation of the queue
     * in the form of "<- A B C <-" where A is the
     * front and C the tail of the queue
     * Complexity: THETA(n) where n is the number
     * of items currently in the queue
     */
    public String toString() {
        StringBuilder bld = new StringBuilder("<- ");
        ListNode tmp = head;
        while ( tmp != null ) {
            bld.append(tmp.data + " ");
            tmp = tmp.next;
        }
        return bld + "<-";
    }

     @Override
     public EnhancedQueueImpl<T> copy() {
         EnhancedQueueImpl<T> copy = new EnhancedQueueImpl<T>();
         ListNode current = this.head;
         while (current != null){
             copy.enqueue(current.data);
             current = current.next;
         }
         return copy;
     }

}




