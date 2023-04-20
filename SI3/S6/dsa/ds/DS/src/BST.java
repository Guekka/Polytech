
import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    public static final String ELEMENT_NOT_FOUND = "Element not found";

    private T element;
    private BST<T> left;
    private BST<T> right;

    public T getElement() {
        return element;
    }

    public BST<T> getLeft() {
        return left;
    }

    public void setLeft(BST<T> left) {
        this.left = left;
    }

    public BST<T> getRight() {
        return right;
    }

    public void setRight(BST<T>right) {
        this.right = right;
    }


    /**
     * Cette méthode  recherche l'ancêtre commun d'un arbre binaire de recherche (Binary Search Tree - BST)
     * pour deux étiquettes de nœuds donnés.
     * La méthode prend deux nœuds "node1" et "node2" en entrée et renvoie l'ancêtre commun le plus bas des deux nœuds.
     *
     * @param small label of the depart node
     * @param big label of the target node
     * @return the common ancestor of the two nodes
     */
    public BST<T> findCommonAncestor(T lnode, T rnode) {
        if (lnode == null || rnode == null)
            throw new NoSuchElementException(ELEMENT_NOT_FOUND);

        if (!contains(lnode) || !contains(rnode))
            throw new NoSuchElementException(ELEMENT_NOT_FOUND);

        if (lnode.compareTo(rnode) > 0) {
            // put the smaller node in lnode and the bigger in rnode to simplify the code
            T swap = lnode;
            lnode = rnode;
            rnode = swap;
        }

        if (lnode.compareTo(element) <= 0 && rnode.compareTo(element) >= 0)
            return this;
        if (lnode.compareTo(element) < 0)
            return left.findCommonAncestor(lnode, rnode);
        return right.findCommonAncestor(lnode, rnode);
    }



    /**
     * Construct the tree.
     */
    public BST(T data, BST<T> left, BST<T> right) {
        this.element = data;
        this.left = left;
        this.right = right;
    }

    public BST(T data) {
        this(data, null, null);
    }

    public BST() {
        this(null, null, null);
    }


    public boolean isEmpty() {
        return element == null;
    }

    /////////////// getSize

    public int getSize() {
        if (isEmpty())
            return 0;
        if (isLeaf())
            return 1;
        if (left == null)
            return 1 + right.getSize();
        if (right == null)
            return 1 + left.getSize();
        return 1 + left.getSize() + right.getSize();
    }


    public boolean isLeaf() {
        return ((left == null || left.isEmpty()) &&
                (right == null || right.isEmpty()));
    }


    /////////////// contains

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */

    public boolean contains(T x) {
        if (x == null || isEmpty())
            return false;
        int compareResult = x.compareTo(this.element);

        if (compareResult == 0)
            return true;
        if (compareResult < 0 && left != null)
            return left.contains(x);
        else if (compareResult > 0 && right != null)
            return right.contains(x);
        else
            return false; // Not found
    }

    /////////////// insert

    public BST<T> createTree(T x) {
        return new BST<>(x);
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */

    public void insert(T x) {
        if (isEmpty()) {
            element = x;
            return;
        }
        int compareResult = x.compareTo(element);

        if (compareResult < 0) {
            if (left == null)
                left = createTree(x);
            else
                left.insert(x);
        } else if (compareResult > 0) {
            if (right == null)
                right = createTree(x);
            else
                right.insert(x);
        }
        // Duplicate; do nothing

    }

    /////////////// primeIterator on binary search tree

    /**
     * Return an primeIterator over the elements of the tree.
     * The elements are enumerated in increasing order.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTiterator(this);
    }


    /**
     * Inner class to build primeIterator over the elements of
     * a tree
     */
    private class BSTiterator implements Iterator<T> {

        // we must push some binary nodes on the stack
        Deque<BST<T>> stack;

        /**
         * Build an primeIterator over the binary node n.
         * The elements are enumerated in increasing order.
         */
        BSTiterator(BST<T> n) {
            stack = new ArrayDeque<>();
            while (n != null && !n.isEmpty()) {
                stack.push(n);
                n = n.left;
            }
        }

        /**
         * Check if there are more elements in the
         * tree
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Return and remove the next element
         */
        public T next() {
            try {
                BST<T> bst = stack.pop();
                T value = bst.element;
                bst = bst.right;
                while (bst != null) {
                    stack.push(bst);
                    bst = bst.left;
                }
                return value;
            } catch (EmptyStackException e) {
                throw new NoSuchElementException("EMPTY STACK MOODLE ZUT");
            }
        }

        /**
         * Unsupported operation
         */

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}
