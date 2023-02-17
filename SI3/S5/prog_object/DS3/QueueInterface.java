interface QueueInterface<T> {
	
	/**
	 * Check if the queue is empty
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Return the number of elements
	 * currently in the queue
	 */
	int size();
	
	/**
	 * Return the next value to be dequeued
	 * If the queue is empty throws EmptyQueueException
	 */
	 T peek() throws EmptyQueueException;
	
	/**
	 * Enqueue x in the queue
	 */
	void enqueue(T x);
	
	/**
	 * Dequeue and return the next element to
	 * be dequeued
	 * If the queue is empty throws EmptyQueueException
	 */
	 T dequeue() throws EmptyQueueException;
}
