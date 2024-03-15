package fr.uca.progfonc;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class TD2 {
    private TD2() {
    }

    public static <T, R> Lst<R> map(Function<T, R> f, Lst<T> l) {
        if (l == null) {
            return null;
        } else {
            return new Lst<>(f.apply(l.car()), map(f, l.cdr()));
        }
    }

    public static Lst<Integer> squares(Lst<Integer> l) {
        return map(x -> x * x, l);
    }

    public static Lst<Integer> sizeOfStrings(Lst<String> l) {
        return map(String::length, l);
    }

    public static <T> Lst<T> filter(Predicate<T> f, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (f.test(l.car())) {
            return new Lst<>(l.car(), filter(f, l.cdr()));
        } else {
            return filter(f, l.cdr());
        }
    }

    public static Lst<String> lowers(Lst<String> l) {
        return filter(s -> s.equals(s.toLowerCase()), l);
    }

    public static <T> int count(Predicate<T> f, Lst<T> l) {
        if (l == null) {
            return 0;
        } else if (f.test(l.car())) {
            return 1 + count(f, l.cdr());
        } else {
            return count(f, l.cdr());
        }
    }

    public static int nbPositives(Lst<Integer> l) {
        return count(x -> x >= 0, l);
    }

    public static <T, R> R reduce(BiFunction<T, R, R> f, Lst<T> l, R init) {
        if (l == null) {
            return init;
        } else {
            return f.apply(l.car(), reduce(f, l.cdr(), init));
        }
    }

    public static int sum(Lst<Integer> l) {
        return reduce(Integer::sum, l, 0);
    }

    public static <T extends Comparable<T>> T min(Lst<T> l) {
        return reduce((x, y) -> x.compareTo(y) < 0 ? x : y, l.cdr(), l.car());
    }

    public static int sumLengthLowers(Lst<String> l) {
        return sum(sizeOfStrings(lowers(l)));
    }

    public static int sumLength(Lst<String> l) {
        return reduce((s, x) -> s.length() + x, l, 0);
    }

    public static <T> String repr(Lst<T> l) {
        return "(" + reduce((s1, s2) -> s1.toString() + " " + s2, l, "") + ")";
    }

    public static <T> Lst<T> concat(Lst<Lst<T>> ll) {
        return reduce(TD1::append, ll, null);
    }

    public static <T> Lst<T> toSet(Lst<T> l) {
        return reduce((car, l2) -> TD1.member(car, l2) ? l2 : new Lst<>(car, l2), l, null);
    }

    public static <T> Lst<T> union(Lst<T> s1, Lst<T> s2) {
        return toSet(TD1.append(s1, s2));
    }

    public static UnaryOperator<Double> derivate(UnaryOperator<Double> f, double dx) {
        return (x -> (f.apply(x + dx / 2) - f.apply(x - dx / 2)) / dx);
    }

    public static UnaryOperator<Double> integrate(UnaryOperator<Double> f, double dx) {
        return (x -> (f.apply(x + dx / 2) - f.apply(x - dx / 2)) * dx);
    }

    public static <T> Predicate<T> equalsTo(T x) {
        return (y -> (x.equals(y)));
    }

    public static <T extends Comparable<T>> Predicate<T> between(T a, T b) {
        return (x -> (a.compareTo(x) <= 0 && x.compareTo(b) <= 0));
    }

    public static <T> int countOccurence(Lst<T> l, T e) {
        return TD1.length(filter(equalsTo(e), l));
    }

}

