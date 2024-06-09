public class TD1 {

    public static <T> Lst<T> replaceAll(Lst<T> l, T x, T y) {
        if (l == null) {
            return null;
        } else if (l.car().equals(x)) {
            return new Lst<>(y, replaceAll(l, x, y));
        } else {
            return new Lst<>(l.car(), replaceAll(l, x, y));
        }
    }

    public static <T> int length(Lst<T> l) {
        if (l == null) {
            return 0;
        } else {
            return 1 + length(l.cdr());
        }
    }

    public static <T> boolean member(T val, Lst<T> l) {
        if (l == null) {
            return false;
        } else if (l.car().equals(val)) {
            return true;
        } else {
            return member(val, l.cdr());
        }
    }

    public static <T> Lst<T> append(Lst<T> l1, Lst<T> l2) {
        if (l1 == null) {
            return l2;
        } else {
            return new Lst<>(l1.car(), append(l1.cdr(), l2));
        }
    }

    public static int sum(Lst<Integer> l) {
        if (l == null) {
            return 0;
        } else {
            return l.car() + sum(l.cdr());
        }
    }

    public static <T> Lst<T> remove(T val, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (l.car().equals(val)) {
            return l.cdr();
        } else {
            return new Lst<>(l.car(), remove(val, l.cdr()));
        }
    }

    public static <T> Lst<T> removeAll(T val, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (l.car().equals(val)) {
            return removeAll(val, l.cdr());
        } else {
            return new Lst<>(l.car(), removeAll(val, l.cdr()));
        }
    }

    public static Lst<String> fizzbuzz(int a, int b) {
        if (a == b) {
            return null;
        } else {
            Lst<String> next = fizzbuzz(a + 1, b);
            String elt = "" + a;
            if (a % 15 == 0) {
                elt = "FizzBuzz";
            } else if (a % 3 == 0) {
                elt = "Fizz";
            } else if (a % 5 == 0) {
                elt = "Buzz";
            }
            return new Lst<String>(elt, next);
        }
    }

    public static <T> Lst<T> fromArray(T[] arr) {
        return fromArray(arr, 0);
    }

    private static <T> Lst<T> fromArray(T[] arr, int i) {
        if (i == arr.length) {
            return null;
        } else {
            return new Lst<>(arr[i], fromArray(arr, i + 1));
        }
    }

    public static <T> Lst<T> reverse(Lst<T> l) {
        if (l == null) {
            return null;
        } else {
            return append(reverse(l.cdr()), new Lst<>(l.car(), null));
        }
    }

    public static <T extends Comparable<T>> Lst<T> insert(T val, Lst<T> l) {
        if ((l == null) || (val.compareTo(l.car()) < 0)) {
            return new Lst<>(val, l);
        } else {
            return new Lst<>(l.car(), insert(val, l.cdr()));
        }
    }

    public static <T extends Comparable<T>> Lst<T> sort(Lst<T> l) {
        if (l == null) {
            return null;
        } else {
            return insert(l.car(), sort(l.cdr()));
        }
    }

    // take method
    public static <T> Lst<T> take(int n, Lst<T> l) {
        if (n <= 0 || l == null)
            return null;
        return new Lst<>(l.car(), take(n - 1, l.cdr()));
    }

    // indexOf method
    public static <T> int indexOf(T val, Lst<T> l) {
        if (l == null)
            return -1;
        if (l.car().equals(val))
            return 0;
        int index = indexOf(val, l.cdr());
        if (index == -1)
            return -1;
        return index + 1;
    }

    // unique method
    public static <T> Lst<T> unique(Lst<T> l) {
        if (l == null)
            return null;
        if (member(l.car(), l.cdr()))
            return unique(l.cdr());
        return new Lst<T>(l.car(), unique(l.cdr()));
    }

    public static <T,U> boolean has(Lst<Pair<T, U>> l, T k) {
        if (l == null) {
            return false;
        } else if (l.car().key() == k) {
            return true;
        } else {
            return has(l.cdr(), k);
        }
    }

    public static <T,U> U get(Lst<Pair<T, U>> l, T k) {
        if (l == null) {
            return null;
        } else if (l.car().key() == k) {
            return l.car().value();
        } else {
            return get(l.cdr(), k);
        }
    }

    public static <T,U> Lst<Pair<T, U>> set(Lst<Pair<T, U>> l, T k, U v) {
        if (l == null) {
            return new Lst<>(new Pair<>(k, v), null);
        } else if (l.car().key().equals(k)) {
            return new Lst<>(new Pair<>(k, v), l.cdr());
        } else {
            return new Lst<>(l.car(), set(l.cdr(), k, v));
        }
    }
}
