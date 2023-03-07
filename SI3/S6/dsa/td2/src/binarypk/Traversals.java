package binarypk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * A utility class for traversing binary trees.
 */
public final class Traversals {

    private Traversals() {
    }

    //An example
    public static <T, R> void  preOrderTraversal(BinaryTreeInterface<T> root,
                                                 Function<T, R> fn, List<R> toCollect) {
        if (root == null) {
            return ;
        }
        toCollect.add(fn.apply(root.getData()));
        preOrderTraversal(root.left(), fn, toCollect);
        preOrderTraversal(root.right(), fn, toCollect);
        toCollect.removeIf(Objects::isNull);
    }


    public static <T, R> void inOrderTraversal(BinaryTreeInterface<T> root,
                                               Function<T, R> fn, List<R> toCollect) {
        //Implement In order Traversal
        if (root == null) {
            return ;
        }
        inOrderTraversal(root.left(), fn, toCollect);
        toCollect.add(fn.apply(root.getData()));
        inOrderTraversal(root.right(), fn, toCollect);
        toCollect.removeIf(Objects::isNull);
    }

    public static <T, R> void postOrderTraversal(BinaryTreeInterface<T> root,
                                                 Function<T, R> fn, List<R> toCollect) {
        //Implement postOrderTraversal
        if (root == null) {
            return ;
        }
        postOrderTraversal(root.left(), fn, toCollect);
        postOrderTraversal(root.right(), fn, toCollect);
        toCollect.add(fn.apply(root.getData()));
        toCollect.removeIf(Objects::isNull);
    }


    public static Integer toInteger(String s) {
        try {
            return (int) Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * A short main for quick testing
     */
    public static void main(String[] args) {
  //Tests with a method on String
        BinaryTreeInterface<String> bt = BinaryTree.read("A B B1$ B2$ C C1 C11$ C12$ C2$");
        System.out.println(bt);
        List<String> names = new ArrayList<>();
        preOrderTraversal(bt, String::toLowerCase, names);
        System.out.println("preOrderTraversal :" + names);
        names = new ArrayList<>();
        postOrderTraversal(bt, String::toLowerCase, names);
        System.out.println("postOrderTraversal :" + names);
        names = new ArrayList<>();
        inOrderTraversal(bt, String::toLowerCase, names);
        System.out.println("inOrderTraversal :" + names);

        //Test with a named Function
        ExpressionTree e = ExpressionTree.read("- * 2.2$ 10.5$ ^ 2$ + 1$ 2$");
        System.out.println(e);
        System.out.println("Evaluate to : " + e.eval());
        List<Double> numbers = new ArrayList<>();
        Function<String, Double> extractNumber = s -> {
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException nfe) {
                return null;
            }
        };
        preOrderTraversal(e, extractNumber, numbers);
        System.out.println("extracted numbers : " + numbers);

        //Test with a static method
        List<Integer> ints = new ArrayList<>();
        preOrderTraversal(e, Traversals::toInteger, ints);
        System.out.println(ints);


        //Test with an anonymous function (Lambda function)
        //Avoid using this approach if your function exceeds 2 lines, as is the case here.
        int i = 4;
        ints = new ArrayList<>();
        preOrderTraversal(e,
                s -> {
                    Integer n = Traversals.toInteger(s);
                    if (n == null)
                        return null;
                    if (n < i)
                        return n;
                    else
                        return null;
                },
                ints);
        System.out.println("only less than 4 :" + ints);



    }
}
