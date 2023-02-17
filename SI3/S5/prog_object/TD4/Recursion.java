package ads.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * A class for practicing recursion
 */
public class Recursion {

    ///// computing binary words

    /**
     * return all binary words of length k
     * Complexity: THETAT(2^k)
     */
    public static List<String> binary(int k) {
        return binary("", k);
    }


    /**
     * return out all binary words of length <code>length</code>
     * prefixed by s
     * Complexity: THETA(2^length)
     */
    private static List<String> binary(String s, int length) {
        return new ArrayList<>();
    }

    ///// computing A-B words

    /**
     * return all words made of
     * nA letters 'A' and nB letters 'B'
     * Complexity: THETA( C(nA,nA+nB) = C(nB,nA+nB) )
     */
    public static List<String> words(int nA, int nB) {
        return words(nA, nB, "");
    }

    /**
     * return all words made of
     * nA letters 'A' and nB letters 'B'
     * prefixed by s
     * Complexity: THETA( C(nA,nA+nB) = C(nB,nA+nB) )
     */
    private static List<String> words(int nA, int nB, String s) {
        return new ArrayList<>();
    }

    ///// computing all permutations of { 0, 1, 2, ..., n - 1 }

    /**
     * compute all permutations of
     * (1, 2, 3, ..., n)
     * Complexity: THETA( n! )
     */
    public static List<String> permutations(int n) {
        int[] p = new int[n];
        //initialisation
        for (int i = 0; i < p.length; i++)
            p[i] = i + 1;
        return permutations(p, 0);
    }

    /**
     * compute out the list of all
     * permutations of (i, ..., n).
     * This method must NOT change the array p
     * (i.e. after the call, p is the same as
     * before the call).
     * Complexity: THETA( (n - i + 1)! )
     * where n = p.length
     */
    private static List<String> permutations(int[] p, int i) {
        var result = new ArrayList<String>();
        if (i == p.length) {
            result.add(print(p));
        } else {
            for (int j = i; j < p.length; j++) {
                // swap
                var tmp = p[i];
                p[i] = p[j];
                p[j] = tmp;
                result.addAll(permutations(p, i + 1));
                // swap back
                p[j] = p[i];
                p[i] = tmp;
            }
        }
        return result;
    }

    /**
     * return the content of the
     * array in the form of a permutation
     */
    private static String print(int[] p) {
        StringBuilder res = new StringBuilder();
        res.append("(" + p[0]);
        for (int i = 1; i < p.length; i++)
            res.append( "," + p[i]);
        res.append(")");
        return (res.toString());
    }

    ///// checks if a given value can be found by summing up items in an array

    /**
     * Check if testValue can be found by adding
     * some values taken inside pArray
     * Complexity: O( 2^n ) where n = pArray.length
     */
    public static boolean sum(int[] pArray, int testValue) {
        int max = 0;
        for (int i = 0; i < pArray.length; i++)
            max = max + pArray[i];
        if (testValue < 0 || testValue > max)
            return false;
        return sum(pArray, testValue, pArray.length);
    }

    /**
     * Check if numberToFind can be found by adding
     * some values taken inside a[0..k-1]
     * Complexity: O( 2^k )
     */
    private static boolean sum(int[] a, int numberToFind, int k) {
        if (numberToFind == 0)
            return true;
        if (a.length == 0)
            return false;

        for(int i = 0; i < a.length; i++) {
            var before = Arrays.copyOfRange(a, 0, i);
            var after = Arrays.copyOfRange(a, i + 1, a.length);

            var newArray = new int[before.length + after.length];
            System.arraycopy(before, 0, newArray, 0, before.length);
            System.arraycopy(after, 0, newArray, before.length, after.length);

            if (sum(newArray, numberToFind - a[i], k - 1))
                return true;
        }
        return false;
    }
}
