public class Lst<T> {
    private final T car;
    private final Lst<T> cdr;

    public Lst(T car, Lst<T> cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public T car() {
        return car;
    }

    public Lst<T> cdr() {
        return cdr;
    }
}