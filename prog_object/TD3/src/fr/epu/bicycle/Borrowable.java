package fr.epu.bicycle;

public interface Borrowable {
    boolean isBorrowable();

    void borrow();

    void stopBorrow();
}
