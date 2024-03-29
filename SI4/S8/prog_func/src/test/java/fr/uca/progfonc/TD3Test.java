package fr.uca.progfonc;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.function.*;

import static fr.uca.progfonc.TD1.append;
import static fr.uca.progfonc.TD3.*;
import static org.junit.jupiter.api.Assertions.*;

public class TD3Test {
    static Lst<Integer> mile = null;
    static Lst<Lst<Integer>> mimile = null;
    static Function<Integer, Integer> memFib;
    static int depth;
    static long perf;

    static int recFib(int n) {
        if (n < 2) {
            return n;
        }
        return recFib(n - 1) + recFib(n - 2);
    }

    static void recur(Lst<?> l) {
        depth += 1;
        recur(l);
    }

    @BeforeAll
    public static void initMile() {
        try {
            recur(null);
        } catch (StackOverflowError e) {
        }

        depth /= 5;
        for (int i = 0; i < depth; ++i) {
            mile = new Lst<>(i, mile);
        }

        for (int i = 0; i < depth / 1000; ++i) {
            Lst<Integer> l = null;
            for (int j = 0; j < 1000; ++j) l = new Lst<>(j, l);
            mimile = new Lst<>(l, mimile);
        }

        //memFib = memo(TD3Test::recFib);
        //assertNotNull(memFib);
        //memFib.apply(32);
    }

    @Test
    public void testFact() {
        assertEquals(1, fact(0));
        assertEquals(1, fact(1));
        assertEquals(2, fact(2));
        assertEquals(6, fact(3));
        assertEquals(24, fact(4));
        assertEquals(120, fact(5));
    }

    @Test
    public void testFib() {
        assertEquals(0, fib(0));
        assertEquals(1, fib(1));
        assertEquals(1, fib(2));
        assertEquals(2, fib(3));
        assertEquals(3, fib(4));
        assertEquals(5, fib(5));
    }

    @Test()
    @Timeout(2)
    public void testLongFib() {
        assertEquals(1134903170L, fib(45));
    }

    @Test
    public void testGcd() {
        assertEquals(1, gcd(3, 2));
        assertEquals(5, gcd(5, 0));
        assertEquals(1, gcd(7, 11));
        assertEquals(9, gcd(9, 36));
        assertEquals(6, gcd(12, 18));
    }

    @Test()
    @Timeout(2)
    public void testLongGCD() {
        assertEquals(36, gcd(12456, 8979876));
    }

    @Test
    public void testBinary() {
        assertEquals("1010", binary(10));
        assertEquals("111111", binary(63));
        assertEquals("0", binary(0));
    }

    @Test
    public void testMin() {
        assertEquals(Integer.valueOf(-2), min(new Lst<>(-2, new Lst<>(1, new Lst<>(0, new Lst<>(5, null))))));
        assertEquals(Integer.valueOf(1), min(new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))))));
        assertEquals(Integer.valueOf(5), min(new Lst<>(5, null)));
    }

    @Test
    public void testReverse() {
        Lst<Integer> l1 = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        Lst<Integer> r1 = new Lst<>(3, new Lst<>(2, new Lst<>(1, null)));
        assertEquals(r1, reverse(l1));

        Lst<Integer> l2 = new Lst<>(5, null);
        Lst<Integer> r2 = new Lst<>(5, null);
        assertEquals(r2, reverse(l2));

        Lst<Integer> l3 = new Lst<>(1, new Lst<>(1, new Lst<>(1, new Lst<>(1, null))));
        Lst<Integer> r3 = new Lst<>(1, new Lst<>(1, new Lst<>(1, new Lst<>(1, null))));
        assertEquals(r3, reverse(l3));
    }

    // This test should fail if the reserve method is not tail recursive.
    // Behavior depends on your hardware or jvm configuration.
    @Test()
    @Timeout(10)
    public void testReversePerformances() {
        assertNotNull(TD3.reverse(mile));
    }

    @Test
    public void testPalindrome() {
        Lst<Character> list1 = new Lst<>('a', new Lst<>('b', new Lst<>('b', new Lst<>('a', null))));
        assertTrue(palindrome(list1));

        Lst<Character> list2 = new Lst<>('a', new Lst<>('b', new Lst<>('c', new Lst<>('b', new Lst<>('a', null)))));
        assertTrue(palindrome(list2));

        Lst<Character> list3 = new Lst<>('a', new Lst<>('b', new Lst<>('c', null)));
        assertFalse(palindrome(list3));

        Lst<Character> list4 = new Lst<>('a', null);
        assertTrue(palindrome(list4));

        Lst<Character> list5 = null;
        assertTrue(palindrome(list5));
    }

    @Test()
    @Timeout(20)
    public void testPalindromePerformances() {
        assertTrue(TD3.palindrome(append(mile, reverse(mile))));
    }

    @Test
    public void testFlatten() {
        Lst<Object> nestedList = new Lst<>(1, new Lst<>(new Lst<>(2, new Lst<>(3, new Lst<>(4, null))), new Lst<>(5, new Lst<>(6, null))));
        Lst<Integer> flattenedList = flatten(nestedList);
        Lst<Integer> expectedList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, new Lst<>(5, new Lst<>(6, null))))));
        assertEquals(expectedList, flattenedList);
    }

    @Test()
    @Timeout(10)
    public void testFlattenPerformances() {
        Lst<Integer> flattenedList = flatten(mimile);
        assertEquals(depth - (depth % 1000), TD1.length(flattenedList));
    }

    @Test
    public void testSup() {
        Lst<Integer> lst = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        Lst<Integer> expected = new Lst<>(3, new Lst<>(4, null));
        assertEquals(expected, sup(lst, 2));
    }

    @Test
    public void testNlist() {
        Lst<Integer> expected = new Lst<>(1, new Lst<>(1, new Lst<>(1, null)));
        assertEquals(expected, nlist(3, 1));
    }

    @Test
    public void testIndexes() {
        Lst<Integer> expected = new Lst<>(0, new Lst<>(1, new Lst<>(2, null)));
        assertEquals(expected, indexes(3));
    }

    @Test
    public void testIsVowel() {
        Predicate<Character> isVowel = isVowel("aeiou");
        assertTrue(isVowel.test('a'));
        assertFalse(isVowel.test('b'));
    }

    @Test
    public void testCountVowels() {
        Function<String, Integer> countVowels = countVowels("aeiou");
        assertEquals(2, (int) countVowels.apply("hello"));
    }

    @Test
    public void testContains() {
        Predicate<String> contains = contains("world");
        assertTrue(contains.test("hello world"));
        assertFalse(contains.test("hello"));
    }

    @Test
    public void testContainsAny() {
        Lst<String> lst = new Lst<>("foo", new Lst<>("world", new Lst<>("bar", null)));
        Predicate<String> containsAny = containsAny(lst);
        assertTrue(containsAny.test("hello world"));
        assertFalse(containsAny.test("hello"));
    }

    @Test
    public void testAccumulator() {
        UnaryOperator<Integer> acc = accumulator();
        assertEquals(3, (int) acc.apply(3));
        assertEquals(5, (int) acc.apply(2));
        assertEquals(10, (int) acc.apply(5));
    }

    @Test
    public void testSumAcc() {
        Lst<Integer> numbers1 = new Lst<Integer>(1, new Lst<Integer>(2, new Lst<Integer>(3, null)));
        assertEquals(6, sumAcc(numbers1));

        Lst<Integer> numbers2 = new Lst<Integer>(5, new Lst<Integer>(10, new Lst<Integer>(15, null)));
        assertEquals(30, sumAcc(numbers2));
    }

    @Test
    public void testIterator() {
        Lst<String> l = new Lst<>("apple", new Lst<>("banana", new Lst<>("cherry", null)));
        Supplier<String> it = iterator(l);
        assertNotNull(it);
        assertEquals("apple", it.get());
        assertEquals("banana", it.get());
        assertEquals("cherry", it.get());
        assertNull(it.get());
        assertNull(it.get());
    }

    @Test()
    @Timeout(5)
    public void testMemo() {
        assertEquals(2178309, (int) memFib.apply(32));
        assertEquals(2, (int) memFib.apply(3));
    }

    @Test
    public void testCurried() {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        Function<Integer, Function<Integer, Integer>> curriedAdd = curried(add);
        assertEquals(3, (int) curriedAdd.apply(1).apply(2));
    }

    @Test
    public void testUnCurried() {
        Function<Integer, Function<Integer, Integer>> curriedAdd = a -> b -> a + b;
        BiFunction<Integer, Integer, Integer> add = unCurried(curriedAdd);
        assertEquals(3, (int) add.apply(1, 2));
    }
}


