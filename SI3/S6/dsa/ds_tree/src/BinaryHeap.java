


import java.util.*;

/**
 * A class for binary heap implementation
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {

    private final AnyType[] A; // to store the heap
    private int size;    // the number of elements in the heap

    // comparator to choose
    private Comparator<AnyType> c = Comparator.naturalOrder();

    ///////////// Constructors

    /**
     * Build a heap of capacity n.
     * The elements are ordered according to the
     * natural order on AnyType.
     * The heap is empty.
     * Complexity: THETA(1)
     */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int n) {
        A = (AnyType[]) new Comparable[n];
        size = 0;
    }

    /**
     * Build a heap of capacity n.
     * The elements are ordered according to c.
     * The heap is empty.
     * Complexity: THETA(1)
     */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int n, Comparator<AnyType> c) {
        this.A = (AnyType[]) new Comparable[n];
        this.size = 0;
        this.c = c;
    }

    /**
     * Build a heap based on array A.
     * The elements are ordered according to the
     * natural order on AnyType.
     * The heap is full
     */
    public BinaryHeap(AnyType[] A) {
        this.A = A;
        this.size = A.length;
        buildHeap();
    }

    /**
     * Build a heap based on array A.
     * The elements are ordered according to c.
     * The heap is full
     */
    public BinaryHeap(AnyType[] A, Comparator<AnyType> c) {
        this.A = A;
        this.size = A.length;
        this.c = c;
        buildHeap();
    }

    ///////////// Private methods

    /**
     * Swap values in the array
     * at indexes i and j.
     * Complexity: THETA(1)
     */
    private void swap(int i, int j) {
        AnyType tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * Return the number of the left
     * node of node number n.
     * Complexity: THETA(1)
     */
    private int left(int n) {
        return 2 * n + 1;
    }

    /**
     * Return the number of the right
     * node of node number n.
     * Complexity: THETA(1)
     */
    private int right(int n) {
        return 2 * (n + 1);
    }

    /**
     * Return the number of the parent
     * node of node number n.
     * Complexity: THETA(1)
     */
    private int parent(int n) {
        return (n - 1) / 2;
    }

    /**
     * Percolate down the element à node number n
     * Complexity: O(log(size))
     */
    private void percolateDown(int n) {
        int g = left(n);
        int d = right(n);
        int k = n;

        if (g < size && c.compare(A[g], A[n]) > 0)
            k = g;
        if (d < size && c.compare(A[d], A[k]) > 0)
            k = d;
        if (k != n) {
            swap(k, n);
            percolateDown(k);
        }
    }

    /**
     * Percolate up the element à node number n
     * Complexity: O(log(size))
     */
    private void percolateUp(int n) {
        int parentElem;
        AnyType e = A[n];
        while (n > 0 && c.compare(e, A[parentElem = parent(n)]) > 0) {
            A[n] = A[parentElem];
            n = parentElem;
        }
        A[n] = e;
    }

    /**
     * Arrange the elements in A such
     * that it has the heap property.
     * Complexity: O(size)
     */
    private void buildHeap() {
        for (int i = parent(size-1); i >= 0; i--) {
            percolateDown(i);
        }
    }

    ///////////// Public methods

    /**
     * Return the size of the heap
     * (the number of elements in the heap).
     * Complexity: THETA(1)
     */
    public int size() {
        return size;
    }

    /**
     * Check if the heap is empty.
     * Complexity: THETA(1)
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the extreme element.
     * Complexity: THETA(1)
     */
    public AnyType extreme() throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException();
        }
        return A[0];
    }

    /**
     * Return and delete the extreme element.
     * Complexity: O(log(size))
     */
    public AnyType deleteExtreme() throws EmptyHeapException {
        if (size == 0) {
            throw new EmptyHeapException();
        }
        AnyType value = A[0];
        A[0] = A[--size];
        if (size > 0) {
            percolateDown(0);
        }
        return value;
    }

    /**
     * Add a new element in the heap
     * Complexity: O(log(size))
     */
    public void add(AnyType e) throws FullHeapException {
        if (size == A.length) {
            throw new FullHeapException();
        }
        A[size] = e;
        percolateUp(size++);
    }

    ///////////// Part 3: deleting in the heap

    /**
     * Delete the element e from the heap.
     * Complexity: O(size)
     */
    public void delete(AnyType e) {
        for (int i = 0; i < size; i++) {
            if (A[i].equals(e)) {
                A[i] = A[--size];
                percolateDown(i);
                percolateUp(i);
                break;
            }
        }
    }

    /**
     * Delete all the elements e from the heap.
     * Complexity: O(size)
     */
    public void deleteAll(AnyType e) {
        int i = 0;
        while (i < size) {
            if (A[i].equals(e)) {
                A[i] = A[--size];
            } else {
                i++;
            }
        }
        buildHeap();
    }

    //toStringAsTab
    public String toStringAsTab() {
        if (size == 0) return "[]";
        StringBuilder res = new StringBuilder("[");
        int i = 0;
        while (i < size) {
            res.append(A[i]).append(", ");
            i++;
        }
        return res.substring(0, res.length() - 2) + "]";
    }

    ////////////////////////////////////////////////////
    // Convenience methods to build a list of integer from a string
    ////////////////////////////////////////////////////
    private static List<Integer> read(String inputString) {
        List<Integer> list = new LinkedList<>();
        Scanner input = new Scanner(inputString).useDelimiter(",\\s*");
        while (input.hasNextInt())
            list.add(input.nextInt());
        input.close();
        return list;
    }
}