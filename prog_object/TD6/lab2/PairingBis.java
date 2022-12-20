package lab2;

import lab2.ArrayStack;
import lab2.EmptyQueueException;
import lab2.EmptyStackException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class to find pairs (x,y) of integers inside an increasing
 * sequence matching y = x + n for a given n.
 */
public class PairingBis {

	/**
	 * Display all the pairs (x,y), x and y  in the input, such that y = x + n
	 * The input contains an entirely increasing (strict) sequence of integers
	 * Running time complexity: THETA(S) where S is the size of the input
	 * Extra memory usage: O(n)
	 */
	public static void showPairs(int n, Scanner input) throws EmptyQueueException {

	}


	static private class Pair{
		int x;
		int sum;

		Pair(int x, int sum) {
			this.x = x;
			this.sum = sum;
		}

		@Override
		public String toString() {
			return "Pair{" +
					"x=" + x +
					", sum=" + sum +
					'}';
		}
	}

	public static ArrayStack<Pair> computePairs(int n, int[] input) throws EmptyQueueException {
		return new ArrayStack<>();
	}

	static final int maxSize = 10000;
	private static int[] getIntArray(Scanner input) {
		int i = 0;
		int[] inputToTest = new int[maxSize];
		while ( input.hasNextInt() && i<inputToTest.length ) {
			inputToTest[i] = input.nextInt();
			i++;
		}
		return inputToTest;
	}

	/**
     * A short main for quick testing
     */
	/**
	 * A short main for quick testing
	 */
	public static void main(String[] args) throws FileNotFoundException, EmptyQueueException, EmptyStackException {

		//------------------------ Using Show Pairs
		System.out.println("\n Computing pairs for " + "1 3 5 6 9 10 11 12 14 16");
		Scanner sc = new Scanner("1 3 5 6 9 10 11 12 14 16");
		showPairs(3,sc);
		System.out.println("Expected : (3,6), (6,9), (9,12) and (11,14)");

		System.out.println("\nThe calculations must quickly eliminate 1 and 2 which are too small.");
		System.out.println("Computing pairs for " + "1 2  6 7 9 10 11 12 14 16");
		sc = new Scanner("1 2  6 7 9 10 11 12 14 16");
		showPairs(3,sc);
		System.out.println("Expected : (6,9), (7,10), (9,12) and (11,14)");

		// put the right path here
		System.out.println("\nComputing pairs from file : " + 1273);
		String filepath = "Resources/big-file.txt";
		Scanner input = new Scanner(new File(filepath));
		showPairs(1273,input);
		input.close();

		//------- Using Compute Pairs
		System.out.println("\n\n------- Using Compute Pairs");
		int[] inputToTest = new int[]{1,3, 5, 6, 9, 10, 11, 12, 14, 16};
		System.out.println("\n Computing pairs for 3 among " + Arrays.toString(inputToTest));
		ArrayStack<Pair> res = computePairs(3, inputToTest);
		System.out.println(res);
		System.out.println("Expected : (3,6), (6,9), (9,12) and (11,14)");

		System.out.println("\n the calculations must quickly eliminate 1 and 2 which are too small.");
		inputToTest = new int[]{1,2, 6, 7, 9, 10, 11, 12, 14, 16};
		System.out.println("Computing pairs for 3 among " + Arrays.toString(inputToTest));
		res = computePairs(3, inputToTest);
		System.out.println(res);
		System.out.println("Expected : (6,9), (9,12) and (11,14)");

		input = new Scanner(new File(filepath));
		inputToTest = getIntArray(input);
		res = computePairs(1273, inputToTest);
		System.out.println(res);
		Pair pair = res.peek();
		System.out.println(pair.x + " + 1273 = " + pair.sum + " == " + (pair.x +1273));
	}

}

