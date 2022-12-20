
import java.util.Arrays;

/**
 * An array-based stack class
 */
public class ArrayStack<T> implements StackInterface<T> {

    private static final int DEFAULT_CAPACITY = 32;
    private T[] array;
    private int size;

    /**
     * Build an empty stack
     * Complexity: THETA(1)
     */
    public ArrayStack() {
		this(DEFAULT_CAPACITY);
    }

    /**
     * Build an empty stack of
     * a given capacity
     * Complexity: THETA(1)
     */
	@SuppressWarnings(value = "unchecked")
    public ArrayStack(int capacity) {
        array = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * Check if the stack is empty
     * Complexity: THETA(1)
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items
     * currently in the stack
     * Complexity: THETA(1)
     */
    public int size() {
        return size;
    }

    /**
     * Return the next value to be popped from the stack
     * Throw EmptyStackException if the stack is empty
     * Complexity: THETA(1)
     */
    public T peek() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        return array[size - 1];
    }

    /**
     * Return a copy ot the current Stack  (not deep copy)
     * Complexity: THETA(Arrays.copyOf) a priori THETA(n) n=array.length
     */
    public ArrayStack<T> copy(){
        ArrayStack<T> copy = new ArrayStack<>(array.length);
        copy.array = Arrays.copyOf(array, array.length);
        copy.size = size;
        return copy;
    }

    /**
     * Push the value x onto the stack.
     * If needed, the underlying array
     * will be enlarged by twice its size
     * Complexity: amortized O(1)
     */
    public void push(T x) {
        if (size == array.length) {
			//Version using java capabilities
			this.array = Arrays.copyOf(array, array.length*2);
			//Version using loops
            //T[] tmp = array;
            //array = (T[]) new Object[2 * size];
            /* Version with a loop
               for (int i = 0; i < size; i++)
                array[i] = tmp[i];
             */
        }
        array[size++] = x;
    }


    /**
     * Pop the stack and return the value popped
     * Throw EmptyStackException if the stack is empty
     * Complexity: THETA(1)
     */
    public T pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        T x = array[size - 1];
        array[--size] = null; // for garbage-collection
        return x;
    }

    /**
     * Remove all elements in the stack
     * Complexity: THETA(1) where n is the
     * size of the stack
     */
	@SuppressWarnings(value = "unchecked")
    public void clear() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public ArrayStack<T> reverse(){
        ArrayStack<T> tmp = copy();
        ArrayStack<T> reversed = new ArrayStack<>(array.length);
        while (! tmp.isEmpty()){
            T top = null;
            try {
                top = tmp.pop();
            } catch (EmptyStackException e) {
                assert false : "unexpected situation";
            }
            reversed.push(top);
        }
        reversed.size = size;
        return reversed;
    }


    /**
     * Return a string representation of the stack
     * in the form of "[ A B C ->" where A is the
     * bottom and C the top of the stack
     */
    public String toString() {
		//Version with String concatenation easiest to understanc
        /*String s = "[ ";
        for (int i = 0; i < size; i++)
            s += array[i] + " ";
        return s + "->";*/
		//Version with String Builder
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < size; i++)
			bld.append(array[i] + " ");
		return "[ " + bld + "->";
    }





    /**
     * A short main for quick testing
     */
    public static void main(String[] args) throws EmptyStackException {
        ArrayStack<Integer> stack = new ArrayStack<>();
        for (int i = 1; i <= 100; i++)
            stack.push(i);
        int i = 0;
        while (!stack.isEmpty()) {
            if (i++ % 25 == 0)
                System.out.println();
            System.out.print(stack.pop() + " ");
        }

        System.out.println();
    }
    // expected output:
    //
    // 100 99 98 97 96 95 94 93 92 91 90 89 88 87 86 85 84 83 82 81 80 79 78 77 76
    // 75 74 73 72 71 70 69 68 67 66 65 64 63 62 61 60 59 58 57 56 55 54 53 52 51
    // 50 49 48 47 46 45 44 43 42 41 40 39 38 37 36 35 34 33 32 31 30 29 28 27 26
    // 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
}
