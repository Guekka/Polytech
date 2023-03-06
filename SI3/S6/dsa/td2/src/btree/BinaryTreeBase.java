package btree;

/**
 * A class for simple binary nodes
 *
 * @param <T> the type of the data stored in the node
 *            (e.g. Integer, String, etc.)
 *            Note: the type must implement the Comparable interface
 * @author Marc Gaetano
 * @author MBF
 * @version 2.0
 */

public class BinaryTreeBase<TREE extends BinaryTreeBase<TREE, T>, T> implements BinaryTreeInterface<TREE, T> {
    private final T data;
    private TREE leftBT;
    private TREE rightBT;

    //////////////// constructors

    /**
     * Build a binary node which is
     * a leaf holding the value 'getData'
     */
    public BinaryTreeBase(T data) {
        this(data, null, null);
    }

    /**
     * Build a binary node holding the value 'getData' with
     * 'leftBT' as the leftBT sub-tree and 'rightBT' as the rightBT sub-tree
     */
    public BinaryTreeBase(T data, TREE left, TREE right) {
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
    public TREE left() {
        return leftBT;
    }

    @Override
    public TREE right() {
        return rightBT;
    }

    @Override
    public void setLeftBT(TREE node) {
        leftBT = node;
    }

    @Override
    public void setRightBT(TREE node) {
        rightBT = node;
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

    // complexity: O(n)
    private int height(BinaryTreeBase<TREE, T> t) {
        if (t == null)
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

    // complexity: O(n)
    public int lowness(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return -1;
        if (bt.isLeaf())
            return 0;
        if (bt.left() == null)
            return 1 + lowness(bt.right());
        if (bt.right() == null)
            return 1 + lowness(bt.left());
        return 1 + Math.min(lowness(bt.left()), lowness(bt.right()));
    }

    //////////////// size ////////////////
    // The size of a BN is its number of
    // non null nodes

    @Override
    public int size() {
        return size(this);
    }

    // complexity: O(n)
    private int size(BinaryTreeBase<TREE, T> bt) {
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

    // complexity: O(n)
    private int leaves(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return 0;
        if (bt.isLeaf())
            return 1;
        return leaves(bt.left()) + leaves(bt.right());
    }

    //////////////// isomorphic ////////////////
    // Two BN are isomorphic if they have exactly
    // the same structure, no matter the getData they
    // store

    @Override
    public boolean isomorphic(TREE t) {
        return isomorphic(this, t);
    }

    // complexity: O(n)
    private boolean isomorphic(BinaryTreeBase<TREE, T> bt1, BinaryTreeBase<TREE, T> bt2) {
        if (bt1 == null || bt2 == null)
            return bt1 == bt2; // if one is null, both have to be

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

    // complexity: O(n)
    private boolean balanced1(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return false;

        return Math.abs(height(bt.left()) - height(bt.right())) <= 1;
    }

    //////////////// balanceValue ////////////////
    // In this version, the complexity must be O(n)
    // where n is the size of the BN (the number of
    // non-null nodes)

    @Override
    public boolean balanced2() {
        return balanced2(this) != -1;
    }

    private int balanced2(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return 0;

        int left = balanced2(bt.left());
        int right = balanced2(bt.right());

        if (left == -1 || right == -1 || Math.abs(left - right) > 1)
            return -1;

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

    private boolean shapely1(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return true;

        return height(bt) <= 2 * lowness(bt) && shapely1(bt.left()) && shapely1(bt.right());
    }

    //////////////// shapely2 ////////////////
    // In this version, the complexity must be O(n)
    // where n is the size of the BN (the number of
    // non null nodes)

    private record Pair(int height, int lowness) {
        public Pair() {
            this(0, 0);
        }

        public boolean shapely() {
            return height() <= 2 * lowness();
        }
    }

    public boolean shapely2() {
        return shapely2(this).shapely();
    }

    private Pair shapely2(BinaryTreeBase<TREE, T> bt) {
        if (bt == null)
            return new Pair();

        if (bt.left() == null)
            return shapely2(bt.right());

        if (bt.right() == null)
            return shapely2(bt.left());

        Pair left = shapely2(bt.left());
        if (!left.shapely())
            return left;

        Pair right = shapely2(bt.right());
        if (!right.shapely())
            return right;

        return new Pair(Math.max(left.height(), right.height()), Math.min(left.lowness(), right.lowness()));
    }


    //////////////// toString ////////////////

    public String toString() {
        String result;
        //Apply
        String str = this.data.toString() + "\n";
        result = str + traversePreOrderToString(this.right(), "|", makeString('_', str.length() - 1));
        result += traversePreOrderToString(this.left(), "", "|" + makeString('_', str.length() - 1));

        return result;
    }

    private String traversePreOrderToString(TREE root, String height, String branch) {
        String str;
        String strBranch = height + branch;
        if (root == null) {
            return strBranch + "\n";
        }
        int addedChar = root.getData().toString().length();
        str = strBranch + " " + root.getData().toString() + "\n";
        if (root.isLeaf())
            return str;
        String newHeight = height + makeString(' ', branch.length() + 1) + "|";
        String newBranch = makeString('_', addedChar);
        str += traversePreOrderToString(root.right(), newHeight, newBranch);
        str += newHeight + "\n";
        //pour le 2e fils, on ne veut plus descendre la branche du parent
        newHeight = height + makeString(' ', branch.length() + 1);
        str += traversePreOrderToString(root.left(), newHeight, "|" + newBranch);
        return str;
    }

    protected String makeString(char c, int k) {
        return String.valueOf(c).repeat(k);
    }


}
