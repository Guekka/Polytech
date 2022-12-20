package lab2;

import lab2.EmptyQueueException;

import java.io.*;
import java.util.*;

/**
 * A class to find pairs (x,y) of integers inside an increasing
 * sequence matching y = x + n for a given n.
 */
public class Pairing {
	
	/**
	 * Display all the pairs (x,y), x and y  in the input, such that y = x + n
	 * The input contains an entirely increasing (strict) sequence of integers
	 * Running time complexity: THETA(S) where S is the size of the input
	 * Extra memory usage: O(n)
	 */
	public static void showPairs(int n, Scanner input) throws EmptyQueueException {

	}
	
    /**
     * A short main for quick testing
     */
	public static void main(String[] args) throws FileNotFoundException, EmptyQueueException {
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

	}

}
