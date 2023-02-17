package lab2;

/**
 * An exception class for empty queue
 */
@SuppressWarnings("serial")
public class EmptyQueueException extends Exception {
	public EmptyQueueException() {
		super();
	}
	public EmptyQueueException(EmptyStackException e) {
		super(e);
	}
}
