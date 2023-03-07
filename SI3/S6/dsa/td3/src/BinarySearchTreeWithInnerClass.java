import java.util.*;

/**
 * A class for Binary Search Trees
 */
public class BinarySearchTreeWithInnerClass<T extends Comparable<? super T>> implements Iterable<T> {


    // The tree root
	private BinaryNode<T> root;

    /**
     * Construct the tree.
     */
    public BinarySearchTreeWithInnerClass( ) {
        root = null;
    }
    
    /////////////// isEmpty
    
    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return true;
    }

    public boolean isLeaf() {
        return root.left == null && root.right == null;
    }
    
    /////////////// makeEmpty  

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( ) {
        
    }

    /////////////// contains
    
    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( T x ) {
        return false;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(T x, BinaryNode<T> t ) {
            return false;
    }
        
    /////////////// insert
    
    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( T x ) {

    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t ) {
        return null;
    }
    /////////////// findMin
    
    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public T findMin( ) {
        return null;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> t ) {
        return null;
    }
    
    /////////////// findMax
    
    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public T findMax( )  {
        return null;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<T> findMax(BinaryNode<T> t ) {
        return null;
    }

    /////////////// remove
    
    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( T x ) {
       
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> t ) {
        return null;
    }

    /////////////// removeLessThan
    
    /**
     * Remove from the tree all the elements
     * less than min
     * @param min the minimum value left in the tree
     */
    public void removeLessThan(T min) {
    	
    }
    
    private BinaryNode<T> removeLessThan(BinaryNode<T> t, T min) {
    	return null;
    }
    
    /////////////// removeGreaterThan
    
    /**
     * Remove from the tree all the elements
     * greater than max
     * @param max the maximum value left in the tree
     */
    public void removeGreaterThan(T max) {
    	
    }
    
    private BinaryNode<T> removeGreaterThan(BinaryNode<T> t, T max) {
    	return null;
    }
    
    /////////////// toSortedList
    
    /**
     * Return a sorted list (increasing) of all
     * the elements of the tree
     * @return the sorted list of all the elements of the tree
     */
    public List<T> toSortedList() {
    	return null;
    }
    
    private void toSortedList(BinaryNode<T> t, List<T> list) {
 
    }
    
    /////////////// sorted list to binary search tree
    
    /**
     * Build a binary search tree with all the
     * elements of the list
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
        return 0;
    }
    /**
	 * Inner class to build iterator over the elements of
	 * a tree
	 */
	private class BSTiterator implements Iterator<T> {
		
		// we must push some binary nodes on the stack
		Stack<BinaryNode<T>> stack;
		
		/**
		 * Build an iterator over the binary node n.
		 * The elements are enumerated in increasing order.
		 */
		BSTiterator(BinaryNode<T> n) {

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
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	////////////////////////////////////////////////////
	// Convenience method to print a tree
	////////////////////////////////////////////////////
    
    public void display() {
    	display(root,"","");
    }

    private void display(BinaryNode<T> t, String r, String p) {
        if ( t == null ) {
            System.out.println(r);
        }
        else {
            String rs = t.element.toString();
            System.out.println(r + rs);
            if ( t.left != null || t.right != null ) {
                String rr = p + '|' + makeString('_',rs.length()) + ' ';
                display(t.right,rr, p + '|' + makeString(' ',rs.length() + 1));
                System.out.println(p + '|');
                display(t.left,rr, p + makeString(' ',rs.length() + 2));
            }
        }
    }

    private String makeString(char c, int k) {
        String s = "";
        for ( int i = 0; i < k; i++ ) {
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
        BinaryNode( T theElement ) {
            this( theElement, null, null );
        }

        BinaryNode( T theElement, BinaryNode<T> lt, BinaryNode<T> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
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
    	while ( input.hasNextInt() )
    		list.add(input.nextInt());
    	input.close();
    	return list;
    }
    
    /**
     * A short main for quick testing
     */
    public static void main( String [ ] args )  {
    	List<Integer> list = read("50 30 70 20 40 80 60");
    	BinarySearchTreeWithInnerClass<Integer> bst = new BinarySearchTreeWithInnerClass<>();
    	for ( Integer n : list )
    		bst.insert(n);
    	bst.display();
    }
}
