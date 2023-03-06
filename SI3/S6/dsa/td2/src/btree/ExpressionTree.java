package btree;

import java.util.List;
import java.util.Scanner;

/**
 * A class for arithmetic expression tree
 */
public class ExpressionTree extends BinaryTreeBase<ExpressionTree, String> {
    public static final List<String> OPERATIONS = List.of("+", "-", "*", "/");

    /**
     * Build a leaf expression (a number)
     */
    public ExpressionTree(String x) {
        super(x);
    }

    /**
     * Build a tree expression 'left op right'
     * where 'op' is "+", "*", "/" or "^"
     */
    public ExpressionTree(String op, ExpressionTree left, ExpressionTree right) {
        super(op, left, right);
    }

    /**
     * Return an expression tree whose linear form
     * is given as the string 'inputString'
     */
    public static ExpressionTree read(String inputString) {
        try (Scanner input = new Scanner(inputString)) {
            return read(input);
        }
    }

    private static ExpressionTree read(Scanner input) {
        if (!input.hasNext())
            return null;
        String s = input.next();
        if (s.equals("$"))
            return null;
        if (s.endsWith("$"))
            return new ExpressionTree(s.substring(0, s.length() - 1));
        return new ExpressionTree(s, read(input), read(input));
    }

    ////////////////////////////////////

    /**
     * A short main for quick testing
     */
    public static void main(String[] args) {
        ExpressionTree e = read("- * 2$ 5$ ^ 3$ 2$");
        System.out.println(e);
        System.out.println("\n(2 x 5) - (3 ^ 2) = " + e.eval() + " ==> expected " + 1.0 + "\n");
        e = read("+ 5$ * 2$ - 7$ 3$");
        System.out.println(e);
        System.out.println("\n5 + (2 x (7 - 3)) = " + e.eval() + " ==> expected " + 13.0 + "\n");
        e = read("- * / - 10$ 4$ 2$ 5$ + 2$ * 3$ 4$");
        System.out.println(e);
        System.out.println("\n((10 - 4) / 2) x 5) - (2 + (3 x 4)) = " + e.eval() + " ==> expected " + 1.0 + "\n");
    }

    /**
     * Return the value of the expression
     */
    public double eval() throws NumberFormatException {
        if (getData().isEmpty())
            return 0;
        if (OPERATIONS.contains(getData()))
            return eval(getData(), left().eval(), right().eval());
        return Integer.parseInt(getData());
    }

    /**
     * Return the value of 'x op y'
     * where 'op' is "+", "*", "/" or "^"
     */
    private double eval(String op, double x, double y) {
        return 0;
    }

// Expected output
//
//	-
//	|_ ^
//	|  |_ 2
//	|  |
//	|  |_ 3
//	|
//	|_ *
//	   |_ 5
//	   |
//	   |_ 2
//
//	(2 x 5) - (3 ^ 2) = 1.0
//
//	+
//	|_ *
//	|  |_ -
//	|  |  |_ 3
//	|  |  |
//	|  |  |_ 7
//	|  |
//	|  |_ 2
//	|
//	|_ 5
//
//	5 + (2 x (7 - 3)) = 13.0
//
//	-
//	|_ +
//	|  |_ *
//	|  |  |_ 4
//	|  |  |
//	|  |  |_ 3
//	|  |
//	|  |_ 2
//	|
//	|_ *
//	   |_ 5
//	   |
//	   |_ /
//	      |_ 2
//	      |
//	      |_ -
//	         |_ 4
//	         |
//	         |_ 10
//
//	((10 - 4) / 2) x 5) - (2 + (3 x 4)) = 1.0
}
