package lab2;

/**
 * A class for stacks supporting the findMin operation in THETA(1)
 */
public class StackMin<T extends Comparable<T>> {
    ArrayStack<T> stack = new ArrayStack<>();
    ArrayStack<T> min = new ArrayStack<>();

    /**
     * Build an empty stack
     * Complexity: THETA(1)
     */
    public StackMin() {
    }

    /**
     * A short main for quick testing
     */
    public static void main(String[] args) throws EmptyStackException {
        StackMin<Integer> s = new StackMin<>();
        s.push(3);
        s.push(1);
        s.push(2);
        System.out.println(s.findMin());
        s.pop();
        s.pop();
        s.push(5);
        System.out.println(s.findMin());
        s.push(2);
        s.push(4);
        s.push(6);
        System.out.println(s.findMin());
    }

    /**
     * Check if the stack is empty
     * Complexity: THETA(1)
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Return the next value to be popped from the stack
     * Throw EmptyStackException if the stack is empty
     * Complexity: THETA(1)
     */
    public T peek() throws EmptyStackException {
        return stack.peek();
    }

    /**
     * Push the value x onto the stack.
     * If needed, the underlying array
     * will be enlarged by twice its size
     * Complexity: THETA(1)
     */
    public void push(T x) {
        stack.push(x);
        try {
            if (min.isEmpty() || x.compareTo(min.peek()) <= 0) {
                min.push(x);
            }
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pop the stack and return the value popped
     * Throw EmptyStackException if the stack is empty
     * Complexity: THETA(1)
     */
    public T pop() throws EmptyStackException {
        T x = stack.pop();
		try {
			if (x.compareTo(min.peek()) == 0) {
				min.pop();
			}
		} catch (EmptyStackException e) {
			e.printStackTrace();
		}
		return x;
    }

    /**
     * Return the minimum value currently in the stack
     * Throw EmptyStackException if the stack is empty
     * Complexity: THETA(1)
     */
    public T findMin() throws EmptyStackException {
        return min.peek();
    }

	public String toString() {
		return stack.toString();
	}
    // expected output
    //
    // 1
    // 3
    // 2
}
