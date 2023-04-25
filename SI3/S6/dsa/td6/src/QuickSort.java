package ads.poo2.lab5.sorting;

/**
 * A class for the quicksort algorithm
 */
public class QuickSort {

    private static final int CUTOFF = 10;

    /**
     * Sort the array in place using the quicksort algorithm
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * Sort the portion array[lo,hi] in place using the quicksort algorithm
     */
    private static <T extends Comparable<T>> void sort(T[] array, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            insertion(array, lo, hi);
            return;
        }
        int j = partition(array, lo, hi);
        sort(array, lo, j - 1);
        sort(array, j + 1, hi);
    }

    /**
     * Partition the portion array[lo,hi] and return the index of the pivot
     */
    private static <T extends Comparable<T>> int partition(T[] array, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        T pivot = array[lo];
        while (true) {
            while (array[++i].compareTo(pivot) < 0) {
                if (i == hi) {
                    break;
                }
            }
            while (array[--j].compareTo(pivot) > 0) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(array, i, j);
        }
        swap(array, lo, j);
        return j;
    }

    /**
     * Return the index of the median of { array[lo], array[mid], array[hi] }
     */
    private static <T extends Comparable<T>> int median(T[] array, int lo, int mid, int hi) {
        if (array[lo].compareTo(array[mid]) < 0) {
            if (array[mid].compareTo(array[hi]) < 0) {
                return mid;
            } else if (array[lo].compareTo(array[hi]) < 0) {
                return hi;
            } else {
                return lo;
            }
        } else { // array[mid] <= array[lo]
            if (array[lo].compareTo(array[hi]) < 0) {
                return lo;
            } else if (array[mid].compareTo(array[hi]) < 0) {
                return hi;
            } else {
                return mid;
            }
        }
    }

    /**
     * Sort array[lo, hi] in place using the insertion sort algorithm
     */
    private static <T extends Comparable<T>> void insertion(T[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            T tmp = array[i];
            int j = i;
            while (j > lo && tmp.compareTo(array[j - 1]) < 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = tmp;
        }
    }

    /**
     * Swap array[i] and array[j]
     */
    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
