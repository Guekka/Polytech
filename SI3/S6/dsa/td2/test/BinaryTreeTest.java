

/**
 * A class for simple binary nodes
 */

class BinaryTree<T> {

    private  T data;
    private BinaryTree<T> leftBT;
    private BinaryTree<T> rightBT;


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
     * 'leftBT' as the leftBT subtree and 'rightBT' as the rightBT subtree
     */
    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
        this.data = data;
        this.leftBT = left;
        this.rightBT = right;
    }

    //////////////// accessors

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryTree<T> left() {
        return leftBT;
    }

    public BinaryTree<T> right() {
        return rightBT;
    }

    public void setLeftBT(BinaryTree<T> node) {
        leftBT = node;

    }
    public void setRightBT(BinaryTree<T> node) {
        rightBT = node;

    }

    //////////////// utility methods

    public boolean isLeaf() {
        return leftBT == null && rightBT == null;
    }


    /**
     * a Tree is full if all the nodes have 0 or 2 children
     * @return true if the tree is full
     */
    public boolean isFull() {
        if (isLeaf()) {
            return true;
        } else if (leftBT == null || rightBT == null) { //the both can't be null
            return false;
        } else {
            return leftBT.isFull() && rightBT.isFull();
        }
    }



    /**
     * a Tree is perfect if all the nodes have 0 or 2 children
     * and all the leaves are at the same level
     * @return true if the tree is perfect
     */
    public boolean isPerfect() {
        if (isLeaf()) {
            return true;
        } else if (leftBT == null || rightBT == null) {
            return false;
        } else {
            return leftBT.isPerfect() && rightBT.isPerfect() && leftBT.height() == rightBT.height();
        }
    }
/*
    private int height() {
        if (isLeaf()) {
            return 0;
        } else if (leftBT == null) {
            return 1 + rightBT.height();
        } else if (rightBT == null) {
            return 1 + leftBT.height();
        } else {
            return 1 + Math.max(leftBT.height(), rightBT.height());
        }
    }
*/
    public int height() {
        return height(this);
    }

    private int height(BinaryTree<T> t) {
        if ( t == null )
            return -1;
        return 1 + Math.max(height(t.leftBT), height(t.rightBT));
    }


    //////////////// toString ////////////////

    public String toString(){
        String result;
        //Apply
        String str = this.data + "\n";
        result = str + traversePreOrderToString(this.left(),"|", makeString('_',str.length()-1) );
        result  += traversePreOrderToString(this.right(),"", "|"+makeString('_',str.length()-1) );

        return result;
    }

    private String traversePreOrderToString(final BinaryTree<T> root, String height, String branch) {
        String str;
        String strBranch = height + branch;
        if (root == null) {
            return strBranch + "\n";
        }
        int addedChar = root.getData().toString().length();
        str = strBranch + " " + root.getData() + "\n";
        if (root.isLeaf())
            return str ;
        String newHeight = height + makeString(' ',branch.length()+1 )+"|";
        String newBranch = makeString('_',addedChar);
        str+= traversePreOrderToString(root.left(),newHeight,newBranch);
        str += newHeight + "\n";
        //pour le 2e fils, on ne veut plus descendre la branche du parent
        newHeight = height + makeString(' ',branch.length()+1 );
        str += traversePreOrderToString(root.right(),newHeight,"|"+newBranch);
        return str;
    }
    protected String makeString(char c, int k) {
        return  String.valueOf(c).repeat(k);
    }


}

