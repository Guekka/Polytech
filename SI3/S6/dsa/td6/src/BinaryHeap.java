package ads.poo2.lab5.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Array class for binary heap implementation
 * @author Marc Gaetano
 */
public class BinaryHeap<T extends Comparable<? super T>> {

	public static final Logger LOGGER = Logger.getLogger(BinaryHeap.class.getName());

	static {
		LOGGER.setLevel(Level.OFF);
	}

	private T[] array; // to store the heap
	private int size;    // the number of elements in the heap
	
	// comparator to choose
	private Comparator<T> comparator = Comparator.naturalOrder();
	private int complexity;

	///////////// Constructors
	
	/**
	 * Build a heap of capacity n.
	 * The elements are ordered according to the
	 * natural order on T.
	 * The heap is empty.
	 * Complexity: THETA(1)
	 */
	public BinaryHeap(int n) {
		array = (T[]) new Comparable[n];
		size = 0;
	}
	
	/**
	 * Build a heap of capacity n.
	 * The elements are ordered according to comparator.
	 * The heap is empty.
	 * Complexity: THETA(1)
	 */
	public BinaryHeap(int n, Comparator<T> comparator) {
		this(n);
		this.comparator = comparator;
	}
	
	/**
	 * Build a heap based on an array.
	 * The elements are ordered according to the
	 * natural order on T.
	 * The heap is full
	 */
	public BinaryHeap(T... array) {
		this(array, (e1, e2) -> e1.compareTo(e2));
	}

	/**
	 * Build a heap based on array array.
	 * The elements are ordered according to comparator.
	 * The heap is full
	 */
	public BinaryHeap(T[] array, Comparator<T> comparator) {
		this.array = array;
		this.size = array.length;
		this.comparator = comparator;
		LOGGER.log(Level.FINE, "Before buildHeap: {0}", Arrays.toString(array));
		buildHeap();
	}
	
	///////////// Private methods
	
	/**
	 * Swap values in the array
	 * at indexes i and j.
	 * Complexity: THETA(1)
	 */
	private void swap(int i, int j) {
		complexity++;
		LOGGER.log(Level.INFO, "swap({0}, {1})", new Object[]{array[i], array[j]});
		T tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	 
	/**
	 * Return the index of the left
	 * node of node number n.
	 * Complexity: THETA(1)
	 */
	private int leftIndex(int n) {
		return 2*n + 1;
	}
	
	/**
	 * Return the index of the right
	 * node of node of index n.
	 * Complexity: THETA(1)
	 */
	private int rightIndex(int n) {
		return 2*(n + 1);
	}
	
	/**
	 * Return the number of the parentIndex
	 * node of node number n.
	 * Complexity: THETA(1)
	 */
	private int parentIndex(int n) {
		return (n - 1)/2;
	}
	
	/**
	 * Percolate down the element of node number n
	 * Complexity: O(log(size))
	 */
	private void percolateDown(int n) {
		LOGGER.log(Level.INFO,"Percolate down value : {0} from index: {1}", new Object[]{array[n],n});
		int left = leftIndex(n);
		int right = rightIndex(n);
		int indexToPercolateDown = n;
		//find the index of the biggest child if it is greater than the parent n
		if ( left < size && comparator.compare(array[left], array[n]) > 0 )
			indexToPercolateDown = left;
		if ( right < size && comparator.compare(array[right], array[indexToPercolateDown]) > 0 )
			indexToPercolateDown = right;
		if ( indexToPercolateDown != n ) {
			swap(indexToPercolateDown,n);
			percolateDown(indexToPercolateDown);
		}
		LOGGER.log(Level.INFO,"After percolate down: {0}", Arrays.toString(array));
	}
		
	/**
	 * Percolate up the element of node number n
	 * n is the index of the element to percolate up
	 * Complexity: O(log(size))
	 */
	private void percolateUp(int n) {
		T e = array[n];
		//while the element is greater than the parent of the node of index n
		while ( n > 0 && n < size() && comparator.compare(e, array[parentIndex(n)]) > 0 ) {
			LOGGER.log(Level.INFO,"Percolate up value : {0} from index: {1}", new Object[]{array[n],n});
			complexity++;
			array[n] = array[parentIndex(n)]; //move the parent down
			n = parentIndex(n);
		}
		array[n] = e; //insert the element in the right place
	}
	
	/**
	 * Arrange the elements in array such
	 * that it has the heap property.
	 * Complexity: O(size)
	 */
	private void buildHeap() {
		complexity = 0;
		for (int i = parentIndex(size - 1); i >= 0; i-- )
			percolateDown(i);
		LOGGER.log(Level.FINE,"Complexity: {0} ", complexity);
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
	public T extreme() throws EmptyHeapException {
		if ( size == 0 )
			throw new EmptyHeapException();
		return array[0];
	}

	/**
	 * return the inverse extreme element, i.e. the smallest element according to the comparator
	 * @return the smallest element according to the comparator
	 * @throws EmptyHeapException if the heap is empty
	 */
	public T inverseExtreme() throws EmptyHeapException {
		if ( size == 0 )
			throw new EmptyHeapException();
		T min = extreme();
		for (int i = size-1; i >= size/2; i--) {
			LOGGER.log(Level.INFO, "test inverseExtreme() against min: {0}, array[{1}]: {2}", new Object[]{min, i, array[i]});
			if (comparator.compare(array[i], min) < 0) {
				min = array[i];

			}
		}
		return min;
	}


	/**
	 * Useful for implementing a priority queue.
	 * We could use the methods extreme() and delete() avoiding the need to maintain more codes
	 * Return and delete the extreme element.
	 * Complexity: O(log(size))
	 */
	public T deleteExtreme() throws EmptyHeapException {
		if ( size == 0 )
			throw new EmptyHeapException();		
		T extreme = array[0];
		array[0] = array[--size];
		array[size] = null; //added for garbage collection
		if ( size > 0 )
			percolateDown(0);
		return extreme;
	}
	
	/**
	 * Add a new element in the heap
	 * Complexity: O(log(size))
	 */
	public void add(T e) throws FullHeapException {
		if ( size == array.length )
			throw new FullHeapException();		
		array[size++] = e;
		percolateUp(size-1);
	}
	
	///////////// Part 3: deleting in the heap
	
	/**
	 * Delete the element e from the heap.
	 * Complexity: O(size)
	 */
	public void delete(T e) {
		for ( int i = 0; i < size; i++ )
			if ( array[i].compareTo(e) == 0 ) {
				array[i] = array[--size];
				array[size] = null; //added for garbage collection
				percolateDown(i);
			} 
	}
	
	/**
	 * Delete all the elements e from the heap.
	 * Complexity: O(size)
	 */	
	public void deleteAll(T e) {
		int i = 0;
		while ( i < size )
			if ( array[i].compareTo(e) == 0 ) {
				array[i] = array[--size];
				array[size] = null; //added for garbage collection
			}
			else
				i++;
		buildHeap();
	}

	public void setComplexity(int i) {
		complexity = i;
	}

	public int getComplexity() {
		return complexity;
	}

	/**
	 * Return the array of the heap.
	 * This method is only for testing purposes.
	 * @return the array of the heap
	 */
	protected T[] getArray() {
		return array;
	}

	@Override
	public String toString() {
		return "BinaryHeap{" +
				"array=" + Arrays.toString(array) +
				", size=" + size +
				'}';
	}

	/**
	 *
	 * @return a string representation of the heap by levels
	 */
	public String toStringByLevels() {
		StringBuilder bld = new StringBuilder();
		int level = 0;
		int nbNodes = 1;
		for (int i = 0; i < size; i++) {
			if (i == nbNodes) {
				bld.append( "\n");
				level++;
				nbNodes += Math.pow(2, level);
			}
			bld.append( "("+i+")"+ array[i] + " ");
		}
		return bld.toString();
	}

	/**
	 * @param t the element to search
	 * @return true if t is a leaf, false otherwise
	 */
	public boolean isLeaf(T t) {
		int i = find(t, 0);
		if (i == -1) {
			return false;
		}
		return isLeafByIndex(i);
	}

	/**
	 *
	 * @param t the element to search
	 * @param from the index from which to start the search
	 * @return the index of t in the array, -1 if not found
	 */
	private int find(T t, int from) {
		LOGGER.log(Level.INFO,	() -> "Searching for " + t + " from "+ from);
		if (from < size) {
			if (array[from].equals(t)) {
				return from; // found
			} else {
				if (comparator.compare(t, array[from]) < 0) { // t > array[from]
					int res = find(t,rightIndex(from)); // search in right subtree
					if (res != -1) {
						return res;
					}
					res = find(t,leftIndex(from)); // search in left subtree
					return res; // if not found, return -1
				} else
					return -1;
			}
		}
		return -1;
	}


	/*
	 * Return the number of leaves in the heap.
	 * The number of leaves can be greater than half the size of the heap.
	 * For example a heap of size 5 has 3 leaves.
	 * But the index of the leaves is always greater than size/2.
	 * For example a heap of size 5 has leaves at index 2, 3 and 4.
	 * @return the number of leaves in the heap
	 */
	public int numberOfLeaves() {
		return size - size/2;
	}

	/**
	 *
	 * @param i the index of the element to check
	 * @return true if the element at index i is a leaf, false otherwise
	 */
	public boolean isLeafByIndex(int i) {
		return leftIndex(i) >= size && rightIndex(i) >= size;
	}
}
