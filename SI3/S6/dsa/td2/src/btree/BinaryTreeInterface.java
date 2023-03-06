package btree;

public interface BinaryTreeInterface<TREE extends BinaryTreeInterface<TREE, T>, T> {
    T getData();

    TREE left();

    TREE right();

    void setLeftBT(TREE node);

    void setRightBT(TREE node);

    boolean isLeaf();

    int height();

    int lowness();

    int size();

    int leaves();

    boolean isomorphic(final TREE t);

    boolean balanced1();

    boolean balanced2();

    boolean shapely1();


}
