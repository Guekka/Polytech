package ads.poo2.lab5.sorting;

/**
 * A class for simple sorting methods
 * We use the Comparable interface to compare elements
 * It's a utility class, so we don't need to instantiate it
 * @author Marc Gaetano
 * @author Mireille blay
 */
public class SimpleSorting {

	/**
	 * Sort the array in place using the selection sort algorithm
	 */
	public static <T extends Comparable<T>> void selection(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int min = i; // index of the smallest element
			for (int j = i + 1; j < array.length; j++) {
				if (array[j].compareTo(array[min]) < 0) {
					min = j;
				}
			}
			swap(array, i, min);
		}
	}
	
	/**
	 * Sort the array in place using the insertion sort algorithm
	 */
	public static <T extends Comparable<T>> void insertion(T[] array) {
		for (int i = 1; i < array.length; i++) {
			T x = array[i];
			int j = i - 1;
			while (j >= 0 && array[j].compareTo(x) > 0) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = x;
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
