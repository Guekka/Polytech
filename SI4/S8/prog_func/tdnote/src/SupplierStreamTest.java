
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

class SupplierStreamTest {

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

    List<Integer> lacc;

    @Test
    public void testForEach() {
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
    public void testReduce() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(6));
        assertEquals(15, stream.reduce(Integer::sum).orElse(-1).intValue());

        stream = new SupplierStream<>(Optional::empty);
        assertFalse(stream.reduce(Integer::sum).isPresent());
    }

    @Test
    public void testFilter() {
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
    public void testLimit() {
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
    public void testMap() {
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
    public void testConcat() {
        SupplierStream<Integer> stream1 = new SupplierStream<>(generator(5));
        SupplierStream<Integer> stream2 = new SupplierStream<>(generator(5));
        SupplierStream<Integer> concatenated = SupplierStream.concat(stream1, stream2);
        assertEquals(45, concatenated.reduce(Integer::sum).orElse(-1).intValue());

        stream1 = new SupplierStream<>(generator(3));
        stream2 = new SupplierStream<>(Optional::empty);
        concatenated = SupplierStream.concat(stream1, stream2);
        assertEquals(3, concatenated.reduce(Integer::sum).orElse(-1).intValue());

        stream1 = new SupplierStream<>(Optional::empty);
        stream2 = new SupplierStream<>(generator(3));
        concatenated = SupplierStream.concat(stream1, stream2);
        assertEquals(3, concatenated.reduce(Integer::sum).orElse(-1).intValue());

        stream1 = new SupplierStream<>(Optional::empty);
        stream2 = new SupplierStream<>(Optional::empty);
        concatenated = SupplierStream.concat(stream1, stream2);
        assertFalse(concatenated.reduce(Integer::sum).isPresent());
    }

    @Test
    public void testIterate() {
        SupplierStream<Integer> stream = SupplierStream.iterate(0, x -> x + 1);
        assertEquals(45, stream.limit(10).reduce(Integer::sum).orElse(-1).intValue());

        stream = SupplierStream.iterate(1, x -> x * 2);
        assertEquals(1023, stream.limit(10).reduce(Integer::sum).orElse(-1).intValue());

        stream = SupplierStream.iterate(1, x -> x);
        assertEquals(1, stream.limit(10).reduce(Integer::sum).orElse(-1).intValue());
    }

    @Test
    public void testParallel() {
        SupplierStream<Integer> stream = new SupplierStream<>(generator(1000000));
        assertEquals(499999500000L, stream.parallel().reduce(Integer::sum).orElse(-1).longValue());

        stream = new SupplierStream<>(generator(10));
        assertEquals(45, stream.parallel().reduce(Integer::sum).orElse(-1).intValue());

        stream = new SupplierStream<>(Optional::empty);
        assertFalse(stream.parallel().reduce(Integer::sum).isPresent());
    }
}
