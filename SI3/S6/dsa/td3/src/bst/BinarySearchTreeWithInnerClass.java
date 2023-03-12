package bst;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTreeWithInnerClass<T extends Comparable<? super T>> implements Iterable<T> {
    // The tree root
    private BinaryNode<T> root;

    /**
     * Construct the tree.
     */
    public BinarySearchTreeWithInnerClass() {
        root = null;
    }

    /////////////// isEmpty

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    public boolean isLeaf() {
        return isLeaf(root);
    }

    private static <T> boolean isLeaf(BinaryNode<T> node) {
        if (node == null)
            return false;
        return node.left == null && node.right == null;
    }

    /////////////// makeEmpty  

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        return contains(x, root);
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private static <T> boolean contains(T x, BinaryNode<T> t) {
        if (t == null)
            return false;
        if (t.element.equals(x))
            return true;

        return contains(x, t.left) || contains(x, t.right);
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        root = insert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private static <T extends Comparable<? super T>> BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null)
            return new BinaryNode<>(x);

        if (x.compareTo(t.element) >= 0) {
            t.right = insert(x, t.right);
        } else {
            t.left = insert(x, t.left);
        }

        return t;

    }
    /////////////// findMin

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin() {
        var min = findMin(root).map(e -> e.element);
        if (min.isEmpty())
            return null;
        return min.get();
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private static <T extends Comparable<? super T>> Optional<BinaryNode<T>> findMin(BinaryNode<T> t) {
        if (t == null)
            return Optional.empty();

        if (t.left == null)
            return Optional.of(t);

        return findMin(t.left);
    }

    /////////////// findMax

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public T findMax() {
        var max = findMax(root).map(e -> e.element);
        if (max.isEmpty())
            return null;
        return max.get();
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private static <T extends Comparable<? super T>> Optional<BinaryNode<T>> findMax(BinaryNode<T> t) {
        if (t == null)
            return Optional.empty();

        if (t.right == null)
            return Optional.of(t);

        return findMin(t.right);
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        root = remove(x, root);
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null)
            return t;

        if (x.compareTo(t.element) < 0)
            t.left = remove(x, t.left);
        else if (x.compareTo(t.element) > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).orElseThrow().element;
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    /////////////// removeLessThan

    /**
     * Remove from the tree all the elements
     * less than min
     *
     * @param min the minimum value left in the tree
     */
    public void removeLessThan(T min) {
        root = removeLessThan(root, min);
    }

    private static <T extends Comparable<? super T>> BinaryNode<T> removeLessThan(BinaryNode<T> t, T min) {
        if (t == null)
            return t;

        if (min.compareTo(t.element) > 0) {
            return t.right;
        }
        t.left = removeLessThan(t.left, min);
        return t;
    }

    /////////////// removeGreaterThan

    /**
     * Remove from the tree all the elements
     * greater than max
     *
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {
        root = removeGreaterThan(root, max);
    }

    private BinaryNode<T> removeGreaterThan(BinaryNode<T> t, T max) {
        if (t == null)
            return t;

        if (max.compareTo(t.element) < 0) {
            return removeGreaterThan(t.left, max);
        }
        t.right = removeGreaterThan(t.right, max);
        return t;
    }

    /////////////// toSortedList

    /**
     * Return a sorted list (increasing) of all
     * the elements of the tree
     *
     * @return the sorted list of all the elements of the tree
     */
    public List<T> toSortedList() {
        return StreamSupport.stream(spliterator(), false).toList();
    }

    /////////////// sorted list to binary search tree

    /**
     * Build a binary search tree with all the
     * elements of the list
     *
     * @param list a sorted (increasing) list of elements
     */
    public BinarySearchTreeWithInnerClass(List<T> list) {

    }

    private BinaryNode<T> makeTree(List<T> list, int i, int j) {
        return null;
    }

    /////////////// iterator on binary search tree

    /**
     * Return an iterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    public Iterator<T> iterator() {
        return new BSTiterator(root);
    }

    public T getElement() {
        return root.element;
    }

    /////////////// getSize
    public int getSize() {
        return getSize(root);
    }

    public static <T> int getSize(BinaryNode<T> node) {
        if (node == null)
            return 0;
        return 1 + getSize(node.left) + getSize(node.right);
    }

    /**
     * Inner class to build iterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack
        Stack<BinaryNode<T>> stack = new Stack<>();

        /**
         * Build an iterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BinaryNode<T> n) {
            if (n != null)
                stack.push(n);
        }

        /**
         * Check if there are more elements in the
         * iterator
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Return and remove the next element from
         * the iterator
         */
        public T next() {
            while (!stack.isEmpty()) {
                var top = stack.pop();

                if (top.right != null) {
                    stack.push(top.right);
                }

                if (top.left == null)
                    return top.element;

                stack.push(new BinaryNode<>(top.element));
                stack.push(top.left);
            }
            throw new NoSuchElementException();
        }

        /**
         * Unsupported operation
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    ////////////////////////////////////////////////////
    // Convenience method to print a tree
    ////////////////////////////////////////////////////

    public void display() {
        display(root, "", "");
    }

    private void display(BinaryNode<T> t, String r, String p) {
        if (t == null) {
            System.out.println(r);
        } else {
            String rs = t.element.toString();
            System.out.println(r + rs);
            if (t.left != null || t.right != null) {
                String rr = p + '|' + makeString('_', rs.length()) + ' ';
                display(t.right, rr, p + '|' + makeString(' ', rs.length() + 1));
                System.out.println(p + '|');
                display(t.left, rr, p + makeString(' ', rs.length() + 2));
            }
        }
    }

    private String makeString(char c, int k) {
        String s = "";
        for (int i = 0; i < k; i++) {
            s += c;
        }
        return s;
    }

    ////////////////////////////////////////////////////
    // Inner class BinaryNode<T>
    ////////////////////////////////////////////////////

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<T> {
        // Constructors
        BinaryNode(T theElement) {
            this(theElement, null, null);
        }

        BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        T element;            // The getData in the node
        BinaryNode<T> left;   // Left child
        BinaryNode<T> right;  // Right child


    }

    ////////////////////////////////////////////////////
    // Convenience methods to build a list of integer from a string
    ////////////////////////////////////////////////////

    private static List<Integer> read(String inputString) {
        List<Integer> list = new LinkedList<>();
        Scanner input = new Scanner(inputString);
        while (input.hasNextInt())
            list.add(input.nextInt());
        input.close();
        return list;
    }

    /**
     * A short main for quick testing
     */
    public static void main(String[] args) {
        List<Integer> list = read("50 30 70 20 40 80 60");
        BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
        for (Integer n : list)
            bst.insert(n);
        bst.display();
    }
}
