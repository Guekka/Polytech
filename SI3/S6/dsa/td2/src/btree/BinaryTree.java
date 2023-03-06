package btree;

import java.util.Scanner;

public class BinaryTree<T> extends BinaryTreeBase<BinaryTree<T>, T> {
    public BinaryTree(T data) {
        super(data);
    }

    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
        super(data, left, right);
    }

    ///////////////////READING a Binary Tree<String> /////////////////

    /**
     * Build a BN of strings from its linear prefix representation
     * "root leftBT rightBT". We use the '$' sign to mark leaves and/or
     * null subtree:
     * - X$ means that X is a leaf
     * - $  is the empty tree
     */
    public BinaryTree<String> read(String inputString) {
        try (Scanner input = new Scanner(inputString)) {
            return read(input);
        }
    }

    private BinaryTree<String> read(Scanner input) {
        if (!input.hasNext())
            return null;
        String s = input.next();
        if (s.equals("$"))
            return null;
        if (s.endsWith("$"))
            return new BinaryTree<>(s.substring(0, s.length() - 1));
        return new BinaryTree<>(s, read(input), read(input));
    }

}