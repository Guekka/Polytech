
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

public class TD3 {
    // Tail recursive factorial function
    public static int fact(int n, int acc) {
        if (n == 0) {
            return acc;
        }
        return fact(n-1, n*acc);
    }
    public static int fact(int n) {
        return fact(n, 1);
    }

    // Tail recursive Fibonacci function
    public static int fib(int n, int a, int b) {
        if (n == 0) {
            return a;
        }
        return fib(n-1, b, a+b);
    }
    public static int fib(int n) {
        return fib(n, 0, 1);
    }

    // Tail recursive function to calculate the greatest common divisor of two integers using Euclid algorithm
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static String binary(int n) {
        return n == 0 ? "0" : binary(n, "");
    }

    private static String binary(int n, String s) {
        if (n == 0) {
            return s;
        }
        return binary(n / 2, n % 2 + s);
    }

    // Tail recursive function to find minimum element in a list
    public static <T extends Comparable<T>> T min(Lst<T> lst) {
        return min(lst.cdr(), lst.car());
    }

    private static <T extends Comparable<T>> T min(Lst<T> lst, T m) {
        if (lst == null) {
            return m;
        }
        return min(lst.cdr(), m.compareTo(lst.car()) <= 0 ? m : lst.car());
    }

    // Tail recursive function to reverse a linked list
    public static <T> Lst<T> reverse(Lst<T> current) {
        return reverse(current, null);
    }

    private static <T> Lst<T> reverse(Lst<T> current, Lst<T> prev) {
        if (current == null) {
            return prev;
        }
        return reverse(current.cdr(), new Lst<>(current.car(), prev));
    }

    // Tail recursive function to test if a list is a palindrome
    public static <T> boolean palindrome(Lst<T> l) {
        return palindrome(l, reverse(l));
    }

    private static <T> boolean palindrome(Lst<T> l, Lst<T> r) {
        if (l == null) {
            return true;
        }
        if (l.car() != r.car()) {
            return false;
        }
        return palindrome(l.cdr(), r.cdr());
    }

    public static <T> Lst<T> flatten(Lst<?> lst) {
        return reverse(flatten(lst, null, null));
    }

    private static <T> Lst<T> flatten(Lst<?> current, Lst<Lst<?>> next, Lst<T> flatList) {
        if (current == null) {
            if (next == null) {
                return flatList;
            }
            return flatten(next.car(), next.cdr(), flatList);
        }
        if (current.car() instanceof Lst) {
            return flatten((Lst<?>) current.car(), new Lst<>(current.cdr(), next), flatList);
        } else {
            return flatten(current.cdr(), next, new Lst<>((T) current.car(), flatList));
        }
    }

    // Returns a new list that contains only the elements of the input list that are greater than n
    public static Lst<Integer> sup(Lst<Integer> l, int n) {
        return TD2.filter(x -> x > n, l);
    }

    // Returns a list of n occurrences of e
    public static <T> Lst<T> nlist(int n, T e) {
        if (n == 0) {
            return null;
        } else {
            return new Lst<>(e, nlist(n-1, e));
        }
    }

    static class ValueHolder<T> {
        private T inner;
        ValueHolder(T value) {
            this.inner = value;
        }
        T set(T value) {
            T old = inner;
            inner = value;
            return old;
        }
        T get() {
            return inner;
        }
    }

    // Produces the list of integers [0, 1, 2, ... n-1]
    public static Lst<Integer> indexes(int n) {
        AtomicInteger i = new AtomicInteger(0);
        return TD2.map(x -> i.getAndIncrement(), nlist(n, 0));
    }

    // Returns a closure that says if a character is a vowel.
    public static Predicate<Character> isVowel(String vowels) {
        Set<Character> vowelSet = new HashSet<>();
        for (char c : vowels.toCharArray())
            vowelSet.add(c);
        return vowelSet::contains;
    }

    public static Lst<Character> toLST(String s) {
        Lst<Character> l = null;
        for (int i = s.length()-1; i >= 0; --i) {
            l = new Lst<>(s.charAt(i), l);
        }
        return l;
    }

    // Returns a closure that counts the number of vowels in a string.
    public static Function<String, Integer> countVowels(String vowels) {
        Predicate <Character> isVowel = isVowel(vowels);
        return s -> TD1.length(TD2.filter(isVowel, toLST(s)));
    }

    // Returns a closure that computes if a string contains the substring.
    public static Predicate<String> contains(String sub) {
        return s -> s.contains(sub);
    }

    // Returns a closure that computes if the string s contains any of the elements of l.
    public static Predicate<String> containsAny(Lst<String> l) {
        return s-> TD2.reduce(Boolean::logicalOr, TD2.map(sub -> contains(sub).test(s), l), false);
    }

    public static UnaryOperator<Integer> accumulator() {
        AtomicInteger i = new AtomicInteger(0);
        return i::addAndGet;
    }

    public static int sumAcc(Lst<Integer> list) {
        UnaryOperator<Integer> acc = accumulator();
        return sumAcc(list, acc);
    }

    public static int sumAcc(Lst<Integer> list, UnaryOperator<Integer> acc) {
        if (list == null) {
            return acc.apply(0);
        }
        acc.apply(list.car());
        return sumAcc(list.cdr(), acc);
    }

    // Returns a non-safe closure for a list iterator.
    public static <T> Supplier<T> iterator(Lst<T> l) {
        ValueHolder<Lst<T>> cell = new ValueHolder<>(l);
        return () -> cell.get() == null ? null : cell.set(cell.get().cdr()).car();
    }

    // Returns the memoization of a function.
    public static <T, R> Function<T, R> memo(Function<T, R> f) {
        HashMap<T, R> map = new HashMap<>();
        return t -> { if (map.containsKey(t)) return map.get(t); R r = f.apply(t); map.put(t, r); return r; };
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> f) {
        return t -> u -> f.apply(t, u);
    }

    public static <T, U, R> BiFunction<T, U, R> unCurried(Function<T, Function<U, R>> f) {
        return (t, u) -> f.apply(t).apply(u);
    }
}

