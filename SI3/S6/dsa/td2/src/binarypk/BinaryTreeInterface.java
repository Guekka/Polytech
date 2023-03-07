package binarypk;

public interface BinaryTreeInterface<T> {


    T getData();

    BinaryTreeInterface<T> left();

    BinaryTreeInterface<T> right();

    void setLeftBT(BinaryTreeInterface<T> node);

    void setRightBT(BinaryTreeInterface<T> node);

    boolean isLeaf();

    int height();

    int lowness();

    int size();

    int leaves();

    boolean isomorphic(final BinaryTreeInterface<T> t);

    boolean balanced1();

    boolean balanced2();

    boolean shapely1();


}
