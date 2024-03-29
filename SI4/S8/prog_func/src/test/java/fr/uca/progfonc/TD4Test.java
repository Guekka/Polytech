package fr.uca.progfonc;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fr.uca.progfonc.TD4.*;
import static org.junit.jupiter.api.Assertions.*;

class TD4Test {
    List<Integer> lacc;

    // Supply all numbers in [0, n[
    static Supplier<Optional<Integer>> generator(int n) {
        return new Supplier<>() {
            int i = 0;

            @Override
            public Optional<Integer> get() {
                return (i < n) ? Optional.of(i++) : Optional.empty();
            }
        };
    }

    @Test
    void testJoin() {
        BasicStream<Integer> s = new MockStream<>(Stream.of(1, 2, 3));
        String expected = "1-2-3";
        String result = join(s, "-");
        assertEquals(expected, result);

        BasicStream<Integer> e = new MockStream<>(Stream.empty());
        assertEquals("", join(e, "-"));
    }

    @Test
    void testMax() {
        BasicStream<Integer> s = new MockStream<>(Stream.of(10, 2, 30, 4));
        assertEquals(30, (int) max(s).orElse(-1));

        BasicStream<Integer> e = new MockStream<>(Stream.empty());
        assertTrue(max(e).isEmpty());
    }

    @Test
    void testCollect() {
        BasicStream<Integer> s = new MockStream<>(Stream.of(1, 2, 3, 4, 5));
        List<Integer> expected = List.of(1, 2, 3);
        List<Integer> result = collect(s, 3);
        assertEquals(expected, result);

        BasicStream<Integer> e = new MockStream<>(Stream.empty());
        assertEquals(List.of(), collect(e, 3));
    }

    @Test
    void testAverageEven() {
        BasicStream<Integer> s = new MockStream<>(Stream.of(1, 2, 3, 4, 5));
        double expected = 3;
        double result = averageEven(s);
        assertEquals(expected, result, 0);

        BasicStream<Integer> e = new MockStream<>(Stream.of(1, 3, 5));
        assertEquals(0, averageEven(e), 0);
    }

    @Test
    void testForEach() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(6));
        lacc = new ArrayList<>();
        stream.forEach(x -> lacc.add(x));
        List<Integer> excepted = List.of(0, 1, 2, 3, 4, 5);
        assertEquals(excepted, lacc);

        stream = new SupplierStream<>(Optional::empty);
        lacc = new ArrayList<>();
        stream.forEach(x -> lacc.add(x));
        assertTrue(lacc.isEmpty());
    }

    @Test
    void testReduce() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(6));
        assertEquals(15, stream.reduce(Integer::sum).orElse(-1).intValue());

        stream = new SupplierStream<>(Optional::empty);
        assertFalse(stream.reduce(Integer::sum).isPresent());
    }

    @Test
    void testFilter() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(7));
        Predicate<Integer> p = x -> x > 10;
        BasicStream<Integer> filtered = stream.filter(p);
        assertFalse(filtered.reduce(Integer::sum).isPresent());

        stream = new SupplierStream<>(generator(7));
        p = x -> x % 2 == 0;
        filtered = stream.filter(p);
        assertEquals(12, filtered.reduce(Integer::sum).orElse(-1).intValue());
    }

    @Test
    void testLimit() {
        assertEquals(6, new SupplierStream<>(generator(7)).
                limit(4).reduce(Integer::sum).orElse(-1).intValue());
        assertEquals(6, new SupplierStream<>(generator(4)).
                limit(10).reduce(Integer::sum).orElse(-1).intValue());
        assertEquals(45, new SupplierStream<>(generator(Integer.MAX_VALUE)).
                limit(10).reduce(Integer::sum).orElse(-1).intValue());
        assertEquals(-1, new SupplierStream<>(generator(42)).
                limit(0).reduce(Integer::sum).orElse(-1).intValue());
        assertEquals(-1, new SupplierStream<Integer>(Optional::empty).
                limit(4).reduce(Integer::sum).orElse(-1).intValue());
    }

    @Test
    void testMap() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(10));
        BasicStream<Integer> mapped = stream.map(x -> x * 10);
        assertEquals(450, mapped.reduce(Integer::sum).orElse(-1).intValue());

        stream = new SupplierStream<>(generator(7));
        mapped = stream.map(x -> 1);
        assertEquals(7, mapped.reduce(Integer::sum).orElse(-1).intValue());

        stream = new SupplierStream<>(Optional::empty);
        mapped = stream.map(x -> 1);
        assertFalse(mapped.reduce(Integer::sum).isPresent());
    }

    @Test
    void testToStream_List() {
        /*List<Integer> l = Arrays.asList(1, 2, 4, 10);
        BasicStream<Integer> stream = toStream(l);
        assertEquals(17, stream.reduce(Integer::sum).orElse(-1).intValue());*/
        // FIXME
    }

    @Test
    void testToStream_Lst() {
        Lst<Integer> l = new Lst<>(1, new Lst<>(42, new Lst<>(3, null)));
        BasicStream<Integer> stream = toStream(l);
        assertEquals(46, stream.reduce(Integer::sum).orElse(-1).intValue());
    }

    @Test
    void testLongStream() {
        BasicStream<Long> stream = longStream();
        assertEquals(45, stream.limit(10).reduce(Long::sum).orElse(-1L).intValue());
    }

    @Test
    void testSum10() {
        assertEquals(222, sum10(3, 4));
        assertEquals(0, sum10(0, 1));
        assertEquals(1584421, sum10(42, 42));
    }

    @Test
    void testSquareOfEven() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9, 12);
        List<Integer> expected = Arrays.asList(4, 36, 64, 144);
        assertEquals(expected, squareOfEven(numbers));
    }

    @Test
    void testAverage() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 42, 5, 6, 7, 8, 9, 100);
        OptionalDouble expected = OptionalDouble.of(18.3);
        assertEquals(expected, average(numbers));

        assertFalse(average(List.of()).isPresent());
    }

    @Test
    void testLongest() {
        List<String> words = Arrays.asList("Hello", "World", "Java", "Stream", "API");
        Optional<String> expected = Optional.of("Stream");
        assertEquals(expected, longest(words));

        assertFalse(longest(List.of()).isPresent());
    }

    @Test
    void testMostCommon() {
        List<Integer> numbers = Arrays.asList(7, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Optional<Integer> expected = Optional.of(7);
        assertEquals(expected, mostCommon(numbers));

        assertFalse(mostCommon(List.of()).isPresent());
    }

    @Test
    void testFibs() {
        List<Integer> expected = Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
        assertEquals(expected, fibs(10));
    }

    @Test
    void testEratosthenes() {
        int[] expectedPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        IntStream primeStream = eratosthenes();
        int[] primes = Objects.requireNonNull(primeStream).limit(15).toArray();
        assertArrayEquals(expectedPrimes, primes);
    }

    static class MockStream<T> implements BasicStream<T> {
        final Stream<T> stream;

        MockStream(Stream<T> stream) {
            this.stream = stream;
        }

        @Override
        public BasicStream<T> filter(Predicate<T> predicate) {
            return new MockStream<>(stream.filter(predicate));
        }

        @Override
        public BasicStream<T> limit(long maxSize) {
            return new MockStream<>(stream.limit(maxSize));
        }

        @Override
        public <R> BasicStream<R> map(Function<T, R> mapper) {
            return new MockStream<>(stream.map(mapper));
        }

        @Override
        public void forEach(Consumer<T> action) {
            stream.forEach(action);
        }

        @Override
        public Optional<T> reduce(BinaryOperator<T> accumulator) {
            return stream.reduce(accumulator);
        }
    }
}
