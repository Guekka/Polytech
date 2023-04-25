package ads.poo2.lab5.sorting;

/**
 * A class for the heap sort algorithm.
 */
public class HeapSort {

    /**
     * Sort the array in place using the heapsort algorithm
     * Complexity: THETA( n.log(n) ) where n is the size of the array
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        // Build the heap
        var heap = new BinaryHeap<>(array);
        // Extract the elements from the heap
        while (!heap.isEmpty()) {
            try {
                array[heap.size() - 1] = heap.deleteExtreme();
            } catch (EmptyHeapException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
