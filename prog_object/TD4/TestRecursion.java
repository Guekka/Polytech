package ads.lab1;
import ads.util.TestClass;

import java.util.List;
import java.util.Scanner;

/**
 * A class for interactive testing recursive methods from class Recursion
 */
public class TestRecursion extends TestClass {

    private Scanner refToScanner;

    public TestRecursion() {
        super(new Scanner(System.in));
        refToScanner = this.getInput();

    }

    public void binary() {
        System.out.print("how many digits ? ");
        int d = refToScanner.nextInt();
        List<String> res = Recursion.binary(d);
        System.out.println(res);
    }

    public void words() {
        System.out.print("how many 'A' ? ");
        int a = refToScanner.nextInt();
        System.out.print("how many 'B' ? ");
        int b = refToScanner.nextInt();
        List<String> res = Recursion.words(a, b);
        System.out.println(res);
    }

    public void permutations() {
        System.out.print("size of the permutation ? ");
        int n = refToScanner.nextInt();
        List<String> res = Recursion.permutations(n);
        System.out.println(res);
    }

    public void sum() {
        System.out.print("size of the array ? ");
        int n = refToScanner.nextInt();
        int[] intArray = new int[n];
        for ( int i = 0; i < intArray.length; i++ ) {
            System.out.print("integer #" + (i + 1) + " ? ");
            intArray[i] = refToScanner.nextInt();
        }
        System.out.print("the sum to make ? ");
        int expectedSum = refToScanner.nextInt();
        if ( Recursion.sum(intArray, expectedSum) )
            System.out.println("you can make " + expectedSum + " from these values!");
        else
            System.out.println("impossible to make " + expectedSum + " from these values");
    }

    public static void main(String[] args) {
        TestRecursion test = new TestRecursion();
        test.tester();
    }
}
