import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

    //return the value of the root of the tree
    public T getElement() {
        //to do
        return null;
    }


    protected BinarySearchTree<T> getLeft() {
        //to do
        return null;
    }

    protected void setLeft(BinarySearchTree<T> left) {
        //to do
    }

    protected BinarySearchTree<T> getRight() {
        //to do
        return null;
    }

    protected void setRight(BinarySearchTree<T> right) {
        //to do
    }



    /**
     * Construct the tree.
     */
    public BinarySearchTree(T data, BinarySearchTree<T> left, BinarySearchTree<T> right) {
        //
    }

    public BinarySearchTree(T data) {
        this(data, null, null);
    }

    public BinarySearchTree() {
        this(null, null, null);
    }

    /////////////// isEmpty

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return true;
    }

    /////////////// makeEmpty  

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {

    }

    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        return false;
    }

    //////////////// size ////////////////
    // The size of a BN is its number of
    // non-null nodes
    public int getSize() {
        //to do
        return 0;
    }

    public boolean isLeaf() {
        //to do
        return true;
    }

    /////////////// insert

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {

    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin() {
        return null;
    }

    /////////////// findMax

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item or null if empty.
     */
    public T findMax() {
        return null;
    }

    /////////////// remove

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * In the test, we replace the removed element by the largest element of the left subtree
     * @param x the item to remove.
     */
    public void remove(T x) {
        //Tip : deal with removing the root of the tree in a special way
    }

    /////////////// removeLessThan

    /**
     * Remove from the tree all the elements
     * less than min
     *
     * @param min the minimum value left in the tree
     */
    public void removeLessThan(T min) {

    }

    /////////////// removeGreaterThan

    /**
     * Remove from the tree all the elements
     * greater than max
     *
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {

    }

    /////////////// toSortedList

    /**
     * Return a sorted list (increasing) of all
     * the elements of the tree
     *
     * @return the sorted list of all the elements of the tree
     */
    public List<T> toSortedList() {
        return null;
    }

    /////////////// sorted list to binary search tree

    /**
     * Build a binary search tree with all the
     * elements of the list
     *
     * @param list a sorted (increasing) list of elements
     */
    public BinarySearchTree(List<T> list) {
        makeTree(list, 0, list.size() - 1);
    }

    //Usefull method to build a binary search tree  from a sorted list
    //The list is divided in two parts, the first part is used to build
    //the left subtree, the second part is used to build the right subtree
    private BinarySearchTree<T> makeTree(List<T> list, int i, int j) {
        return null;
    }


    /////////////// iterator on binary search tree


    /**
     * Return an iterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    public Iterator<T> iterator() {
        return new BinarySearchTree<T>.BSTiterator(this);
    }


    /**
     * Inner class to build iterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack

        Deque<BinarySearchTree<T>> stack;


        /**
         * Build an iterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BinarySearchTree<T> n) {
            stack = new ArrayDeque<>();
            // push all the left nodes on the stack
            //TODO
        }

        /**
         * Check if there are more elements in the
         * iterator
         */
        public boolean hasNext() {
            return false;
        }

        /**
         * Return and remove the next element from
         * the iterator
         */
        public T next() {
            return null;
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
        display(this, "", "");
    }
    private void display(BinarySearchTree<T> t, String r, String p) {
        if (t == null || t.isEmpty()) {
            System.out.println(r);
        } else {
            String rs = t.getElement().toString();
            System.out.println(r + rs);
            if (t.getLeft() != null || t.getRight() != null) {
                String rr = p + '|' + makeString('_', rs.length()) + ' ';
                display(t.getLeft(), rr, p + '|' + makeString(' ', rs.length() + 1));
                System.out.println(p + '|');
                display(t.getRight(), rr, p + makeString(' ', rs.length() + 2));
            }
        }
    }

    private String makeString(char c, int k) {
        return String.valueOf(c).repeat(Math.max(0, k));
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
    public static void main(String[] args)  {
            List<Integer> list = read("50 30 70 20 40 80 60");
            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
            for (Integer n : list)
                bst.insert(n);
            bst.display();
            //From the lesson
            list = Arrays.asList(12, 17, 21, 19, 14, 13, 16, 9, 11, 10, 5, 8);
            System.out.println("---- From the lesson one by one");
            bst = new BinarySearchTree<>();
            //To control the position
            for (Integer n : list) {
                bst.insert(n);
            }
            System.out.println("Before insert 3");
            bst.display();
            bst.insert(3);
            System.out.println("After insert 3");
            bst.display();
            bst.remove(17);
            System.out.println("After remove 17");
            bst.display();
            System.out.println("---- From the lesson using initialisation with Arrays.asList");
            bst = new BinarySearchTree<>(list);
            bst.display();
            bst.insert(3);
            bst.display();
            bst.remove(17);
            bst.display();
    }
}
