
/**
 * An exception class for empty queue
 */
@SuppressWarnings("serial")
class EmptyQueueException extends Exception {
	public EmptyQueueException() {
		super();
	}
	public EmptyQueueException(EmptyStackException e) {
		super(e);
	}
}
