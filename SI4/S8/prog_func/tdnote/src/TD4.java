import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TD4 {
    // Returns the maximum of a stream of comparable.
    public static <T extends Comparable<T>> Optional<T> max(BasicStream<T> s) {
        return s.reduce((x,y) -> x.compareTo(y) > 0 ? x : y);
    }

    // Returns a String composed of a representation of the stream elements joined together with specified delimiter.
    public static <T> String join(BasicStream<T> s, String delimiter) {
        return s.map(Object::toString).reduce((x, y) ->  x + delimiter + y).orElse("");
    }

    // Returns a list that contains the n first elements of a stream.
    public static <T> List<T> collect(BasicStream<T> s, int n) {
        return s.map(x -> List.of(x))
                .limit(n)
                .reduce((x,y) -> { List<T> l = new ArrayList<>(x); l.addAll(y); return l; })
                .orElse(List.of());
    }

    // Returns the average of all the even numbers in a given stream of integers. 0 if none.
    public static double averageEven(BasicStream<Integer> s) {
       Pair<Integer, Integer> r = s.filter(x -> x % 2 == 0)
                .map(x -> new Pair<>(1, x))
                .reduce((p, q) -> new Pair<>(p.key() + q.key(), p.value() + q.value()))
                .orElse(new Pair<>(1, 0));
       return (double) r.value() / r.key();
    }

    // Returns a stream based on a list.
    public static <T> BasicStream<T> toStream(List<T> l) {
        Iterator<T> it = l.iterator();
        return new SupplierStream<>(() -> it.hasNext() ? Optional.of(it.next()) : Optional.empty());
    }

    // Returns a stream based on a Lst list.
    public static <T> BasicStream<T> toStream(Lst<T> l) {
        Supplier<T> it = TD3.iterator(l);
        return new SupplierStream<>(() -> Optional.ofNullable(it.get()));
    }

    // Returns a stream of successive integers from 0 to "infinity"
    public static BasicStream<Long> longStream() {
        AtomicLong i = new AtomicLong(0);
        return new SupplierStream<>(() -> Optional.of(i.getAndIncrement()));
    }

    // Returns the sum of n first numbers containing only digits 1 and 0 that are greater or equal than m
    public static long sum10(long m, int n) {
        Predicate<Long> only01 = x -> x.toString().chars().allMatch(c -> c == '0' || c == '1');
        return longStream().filter(x -> x >= m).filter(only01).limit(n).reduce(Long::sum).orElse(0L);
    }

    // Returns the square of all even numbers in a given list of integers.
    public static List<Integer> squareOfEven(List<Integer> l) {
        return l.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // Returns the average of all the numbers in a given list of integers.
    public static OptionalDouble average(List<Integer> l) {
        return l.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    // Returns the longest word in a given list of strings.
    public static Optional<String> longest(List<String> l) {
        return l.stream()
                .reduce((w1, w2) -> w1.length() > w2.length() ? w1 : w2);
    }

    private static <T> Map<T, Integer> merge(Map<T, Integer> m, Map<T, Integer> n) {
        Map<T, Integer> r = new HashMap<>();
        r.putAll(m);
        n.keySet().stream().forEach(k -> r.put(k, m.getOrDefault(k, 0) + n.get(k)));
        return r;
    }
    // Returns the most common element in a given list of integers.
    public static <T> Optional<T> mostCommon(List<T> l) {
        return l.stream().map(x -> Map.of(x, 1))
                .reduce((m, n) -> merge(m, n))   // Counter() python
                .orElse(Map.of()).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Returns the first n Fibonacci numbers.
    public static List<Integer> fibs(int n) {
        return Stream.iterate(new Pair<Integer, Integer>(0, 1), p -> new Pair<Integer, Integer>(p.value(), p.key()+p.value()))
                .limit(n)
                .map(Pair::key)
                .toList();
    }

    static IntPredicate isPrime = x -> true;

    // Returns an int stream of prime numbers based on eratosthenes sieve.
    public static IntStream eratosthenes() {
        return IntStream.iterate(2, i -> i + 1)
                .filter(i -> isPrime.test(i)) // but not  .filter(isPrime)
                .peek(i -> isPrime = isPrime.and(v -> v % i != 0));
    }
}
