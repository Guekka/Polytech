package binarypk;

import java.util.*;

/**
 * A class for simple binary nodes
 * @author Marc Gaetano
 * @author MBF
 * @version 2.0
 * @param <T> the type of the data stored in the node
 *           (e.g. Integer, String, etc.)
 *           Note: the type must implement the Comparable interface
 *
 */

public class BinaryTree<T> implements BinaryTreeInterface<T> {
	private T data;
	private BinaryTreeInterface<T> leftBT;
	private BinaryTreeInterface<T> rightBT;

	//////////////// constructors
	
	/**
	 * Build a binary node which is
	 * a leaf holding the value 'getData'
	 */
	public BinaryTree(T data) {
		this(data,null,null);
	}

	/**
	 * Build a binary node holding the value 'getData' with
	 * 'leftBT' as the leftBT sub-tree and 'rightBT' as the rightBT sub-tree
	 */
	public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
		this.data = data;
		this.leftBT = left;
		this.rightBT = right;
	}
	
	//////////////// accessors
	
	@Override
	public T getData() {
		return data;
	}
	
	@Override
	public BinaryTreeInterface<T> left() {
		return leftBT;
	}
	
	@Override
	public BinaryTreeInterface<T> right() {
		return rightBT;
	}

	@Override
	public void setLeftBT(BinaryTreeInterface<T> node) {
		leftBT =node;
	}

	@Override
	public void setRightBT(BinaryTreeInterface<T> node) {
		rightBT =node;
	}
	//////////////// utility methods
	
	@Override
	public boolean isLeaf() {
		return leftBT == null && rightBT == null;
	}
	
	//////////////// the example of the height: 
	//////////////// apply the same scheme to the other methods
	
	@Override
	public int height() {
		return height(this);
	}
	
	private int height(BinaryTreeInterface<T> t) {
		if ( t == null )
			return -1;
		return 1 + Math.max(height(t.left()), height(t.right()));
	}

	//////////////// methods you have to complete ////////////////
	
	//////////////// lowness ////////////////
	// The lowness of a BN is the length of a
	// shortest path from the root to a leaf
	
	@Override
	public int lowness() {
		return lowness(this);
	}

	public int lowness(BinaryTreeInterface<T> bt) {
		if (bt.left() == null && bt.right() == null) {
			return 0;
		}
		if (bt.left() == null) {
			return 1 + lowness(bt.right());
		}
		if (bt.right() == null) {
			return 1 + lowness(bt.left());
		}
		return 1 + Math.min(lowness(bt.left()), lowness(bt.right()));
	}
	
	//////////////// size ////////////////
	// The size of a BN is its number of
	// non null nodes
	
	@Override
	public int size() {
		return size(this);
	}

	private int size(BinaryTreeInterface<T> bt) {
		if (bt == null)
			return 0;
		return 1 + size(bt.left()) + size(bt.right());
	}
	
	//////////////// leaves ////////////////
	// The leaves method returns the number
	// of leaves of the BN
	
	@Override
	public int leaves() {
		return leaves(this);
	}
	
	private int leaves(BinaryTreeInterface<T> bt) {
        if (bt == null)
			return 0;
		if (bt.left() == null && bt.right() == null) {
			return 1;
		}
		return leaves(bt.left()) + leaves(bt.right());
	}
	
	//////////////// isomorphic ////////////////
	// Two BN are isomorphic if they have exactly
	// the same structure, no matter the getData they
	// store
	
	@Override
	public boolean isomorphic(BinaryTreeInterface<T> t) {
		return isomorphic(this,t);
	}
	
	private boolean isomorphic(BinaryTreeInterface<T> bt1, BinaryTreeInterface<T> bt2) {
        		if (bt1 == null && bt2 == null) {
			return true;
		}
		if (bt1 == null || bt2 == null) {
			return false;
		}
		return isomorphic(bt1.left(), bt2.left()) && isomorphic(bt1.right(), bt2.right());
	}

	//////////////// balanced1 ////////////////
	// A BN is said balanced if for each node
	// (including the root node) the absolute
	// value of the difference between the height
	// of the leftBT node and the height of the
	// rightBT node is at most 1
	
	// First version: you are to use the height method
	
	@Override
	public boolean balanced1() {
		return balanced1(this);
	}
	
	private boolean balanced1(BinaryTreeInterface<T> bt) {
        if (bt == null)
			return true;
		if (Math.abs(height(bt.left()) - height(bt.right())) > 1) {
			return false;
		}
		return balanced1(bt.left()) && balanced1(bt.right());
	}

	//////////////// balanceValue ////////////////
	// In this version, the complexity must be O(n)
	// where n is the size of the BN (the number of
	// non null nodes)
	
	@Override
	public boolean balanced2() {
		return balanced2(this) != -1;
	}
	
	private int balanced2(BinaryTreeInterface<T> bt) {
		if (bt == null)
			return 0;
		int left = balanced2(bt.left());
		int right = balanced2(bt.right());
		if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
			return -1;
		}
		return 1 + Math.max(left, right);
	}
	
	//////////////// shapely1 ////////////////
	// A BN is said to be shapely if its height
	// is less or equal to the double of its lowness
	//And it’s true on all the subtrees


	// First version: you are to use the height and the lowness methods
	
	@Override
	public boolean shapely1() {
		return shapely1(this);
	}
	
	private boolean shapely1(BinaryTreeInterface<T> bt) {
		if (bt == null)
			return true;
		if (height(bt) > 2 * lowness(bt)) {
			return false;
		}
		return shapely1(bt.left()) && shapely1(bt.right());
	} 
	
	//////////////// shapely2 ////////////////
	// In this version, the complexity must be O(n)
	// where n is the size of the BN (the number of
	// non null nodes)

	private record Pair (int height, int lowness){
		public Pair() {
			this(0,0);
		}
	}

	public boolean shapely2() {
		Pair p = shapely2(this);
		return p != null;
	}

	private Pair shapely2(BinaryTreeInterface<T> bt) {
		if ( bt.left() == null && bt.right() == null )
			return new Pair();
		int lowness, height;
		if ( bt.right() == null ) {
			Pair l = shapely2(bt.left());
			if ( l == null )
				return null;
			lowness = 1 + l.lowness;
			height = 1 + l.height;
		}
		else if ( bt.left() == null ) {
			Pair r = shapely2(bt.right());
			if ( r == null )
				return null;
			lowness = 1 + r.lowness;
			height = 1 + r.height;
		}
		else {
			Pair l = shapely2(bt.left());
			if ( l == null )
				return null;
			Pair r = shapely2(bt.right());
			if ( r == null )
				return null;
			height = 1 + Math.max(l.height, r.height);
			lowness = 1 + Math.min(l.lowness, r.lowness);
		}
		if ( height <= 2*lowness )
			return new Pair(height,lowness);
		return null;
 	}

	//////////////// toString ////////////////

	public String toString(){
		String result;
		//Apply
		String str = this.data.toString() + "\n";
		result = str + traversePreOrderToString(this.right(),"|", makeString('_',str.length()-1) );
		result  += traversePreOrderToString(this.left(),"", "|"+makeString('_',str.length()-1) );

		return result;
	}

	private String traversePreOrderToString(BinaryTreeInterface<T> root, String height, String branch) {
		String str;
		String strBranch = height + branch;
		if (root == null) {
			return strBranch + "\n";
		}
		int addedChar = root.getData().toString().length();
		str = strBranch + " " + root.getData().toString() + "\n";
		if (root.isLeaf())
			return str ;
		String newHeight = height + makeString(' ',branch.length()+1 )+"|";
		String newBranch = makeString('_',addedChar);
		str+= traversePreOrderToString(root.right(),newHeight,newBranch);
		str += newHeight + "\n";
		//pour le 2e fils, on ne veut plus descendre la branche du parent
		newHeight =height + makeString(' ',branch.length()+1 );
		str += traversePreOrderToString(root.left(),newHeight,"|"+newBranch);
		return str;
	}

	protected String makeString(char c, int k) {
		return  String.valueOf(c).repeat(k);
	}


	///////////////////READING a Binary Tree<String> /////////////////

	/**
	 * Build a BN of strings from its linear prefix representation
	 * "root leftBT rightBT". We use the '$' sign to mark leaves and/or
	 * null subtree:
	 * - X$ means that X is a leaf
	 * - $  is the empty tree
	 */
	public static BinaryTree<String> read(String inputString) {
		try(Scanner input = new Scanner(inputString)) {
			return read(input);
		}
	}

	private static BinaryTree<String> read(Scanner input) {
		if ( ! input.hasNext() )
			return null;
		String s = input.next();
		if ( s.equals("$") )
			return null;
		if ( s.endsWith("$") )
			return new BinaryTree<>(s.substring(0,s.length()-1));
		return new BinaryTree<>(s,read(input),read(input));
	}


    /**
     * A short main for quick testing the read and display methods
     */
	public static void main(String[] args) {
		BinaryTree<String> bt = read("A B D X 1 10$ 20$ $ Y$ E V$ W$ C F$ G$");
		System.out.println(bt);
		if ( bt != null && bt.shapely2() )
			System.out.println("shapely");
		else
			System.out.println("not shapely");
	}
}
