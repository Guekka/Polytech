public interface RankedBSTInterface<T extends Comparable<? super T>> {
    int rank(T e);

    int getSize();

    void insert(T x);

    T elementInRank(int r);

    boolean isEmpty();

    void makeEmpty();

    boolean contains(T i);

    void remove(T i);
}
