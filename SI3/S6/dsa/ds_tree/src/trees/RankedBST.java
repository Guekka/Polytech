package trees;


import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A class for binary search tree with rank
 */
public class RankedBST<T extends Comparable<? super T>> {

    // The tree root
    private BinaryNode<T> root;

    /**
     * Construct the tree.
     */
    public RankedBST() {
        root = null;
    }

    /////////////// isEmpty


    public int getSize() {
        return size(root);
    }

    private int size(BinaryNode<T> b) {
        if (b == null) return 0;
        return 1 + b.sizeOfLeft + size(b.right);
    }




    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /////////////// makeEmpty

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }


    /////////////// rank

    /**
     * Return the rank of x in the tree
     *
     * @param x the element
     * @return the rank of x in the tree
     * if x is in the tree, 0 otherwise
     */
    public int rank(T x) {
        return rank(root, x);
    }

    private int rank(BinaryNode<T> t, T x) {
        if ( t == null )
            return 0;
        if ( x.compareTo(t.element) == 0 )
            return t.sizeOfLeft + 1;
        if ( x.compareTo(t.element) < 0 )
            return rank(t.left,x);
        int r = rank(t.right,x);
        if ( r == 0 )
            return 0;
        return t.sizeOfLeft + 1 + r;
    }

    /////////////// element

    /**
     * Return the element of rank r in the tree
     *
     * @param r the rank
     * @return the element of rank r in the tree
     * if such element exists, null otherwise
     */
    public T elementInRank(int r) {
        System.out.println("elementInRank" + r);
        this.display();
        return element(root,r);
    }
    private T element(BinaryNode<T> t, int r) {
        if ( t == null )
            return null;
        if ( t.sizeOfLeft + 1 == r )
            return t.element;
        if ( r <= t.sizeOfLeft )
            return element(t.left,r);
        return element(t.right,r - t.sizeOfLeft - 1);
    }

    public T element(int r) {
        if (r < 1) return null;
        return element(root, r);
    }

    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        if (x == null || root == null) return false;
        else return contains(x, root);
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(T x, BinaryNode<T> t) {
        if (t == null) return false;
        int cmp = x.compareTo(t.element);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return contains(x, t.left);
        } else {
            return contains(x, t.right);
        }
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        if (!contains(x)) root = insert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null) return new BinaryNode<>(x);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(x, t.left);
            t.sizeOfLeft++;
        } else // WE KNOW THAT x IS NOT IN t
            t.right = insert(x, t.right);
        return t;
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        if (contains(x))
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
        // WE KNOW THAT x IS IN t
        // if( t == null )
        //   return t;   // Item not found; do nothing

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = remove(x, t.left);
            t.sizeOfLeft--; // update the size of the left sub-tree
        } else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if(t == null) return null;
        else if(t.left == null) return t;
        return findMin(t.left);
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
            rs = "(" + t.sizeOfLeft + ")" + rs;
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
    private static class BinaryNode<AnyType> {
        // Constructors
        BinaryNode(AnyType theElement) {
            element = theElement;
            left = null;
            right = null;
            sizeOfLeft = 0;
        }

        AnyType element;            // The getData in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child

        int sizeOfLeft; // The size of the left subtree
    }

    ////////////////////////////////////////////////////
    // Convenience methods to build a list of integer from a string
    ////////////////////////////////////////////////////

    private static List<Integer> read(String inputString) {
        List<Integer> list = new LinkedList<Integer>();
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
        RankedBST<Integer> bst = new RankedBST<Integer>();
        for (Integer n : list)
            bst.insert(n);
        bst.display();
        System.out.println("Rank of 60: " + bst.rank(60));
        System.out.println("Element of rank 6: " + bst.element(6));
    }
}