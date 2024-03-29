package fr.uca.progfonc

import java.util.*
import java.util.function.BinaryOperator
import java.util.function.IntPredicate
import java.util.function.Predicate
import java.util.function.Supplier
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream


object TD4 {
    private val isPrime = IntPredicate { x: Int ->
        if (x < 2) {
            return@IntPredicate false
        }
        var i = 2
        while (i * i <= x) {
            if (x % i == 0) {
                return@IntPredicate false
            }
            i++
        }
        true
    }

    // Returns the maximum of a stream of comparable.
    @JvmStatic
    fun <T : Comparable<T>?> max(s: BasicStream<T>): Optional<T> {
        return s.reduce(BinaryOperator.maxBy(Comparator.naturalOrder()))
    }

    // Returns a String composed of a representation of the stream elements joined together with specified delimiter.
    @JvmStatic
    fun <T> join(s: BasicStream<T>, delimiter: String): String {
        return s
            .map { o -> o.toString() }
            .reduce { a: String, b: String -> "$a$delimiter$b" }
            .orElse("")
    }

    // Returns a list that contains the n first elements of a stream.
    @JvmStatic
    fun <T> collect(s: BasicStream<T>, n: Int): List<T> {
        val ret = ArrayList<T>()
        s.limit(n.toLong()).forEach { ret.add(it) }
        return ret
    }

    // Returns the average of all the even numbers in a given stream of integers. 0 if none.
    @JvmStatic
    fun averageEven(s: BasicStream<Int>): Double {
        var n = 0
        val sum = s.filter { e -> e % 2 == 0 }
            .map { e -> n++; e }
            .reduce { a: Int, b: Int -> a + b }
            .orElse(0)
            .toDouble()

        return if (n == 0) 0.0 else sum / n
    }

    // Returns a stream based on a list.
    fun <T : Any> toStream(l: List<T>): BasicStream<T> {
        class ListSupplier(private val list: List<T>, private var index: Int = 0) : Supplier<Optional<T>> {
            override fun get(): Optional<T> {
                return if (index < list.size) {
                    Optional.of(list[index++])
                } else {
                    Optional.empty()
                }
            }
        }

        return SupplierStream(ListSupplier(l))
    }

    // Returns a stream based on a Lst list.
    @JvmStatic
    fun <T : Any> toStream(l: Lst<T>?): BasicStream<T> {
        class LstSupplier(private var lst: Lst<T>?) : Supplier<Optional<T>> {
            override fun get(): Optional<T> {
                return if (lst != null) {
                    val ret = lst!!.car
                    lst = lst!!.cdr
                    Optional.of(ret)
                } else {
                    Optional.empty()
                }
            }
        }

        return SupplierStream(LstSupplier(l))
    }

    // Returns a stream of successive integers from 0 to "infinity"
    @JvmStatic
    fun longStream(): BasicStream<Long> {
        class LongSupplier(private var index: Long = 0) : Supplier<Optional<Long>> {
            override fun get(): Optional<Long> {
                return if (index == Long.MAX_VALUE) {
                    Optional.empty()
                } else {
                    Optional.of(index++)
                }
            }
        }

        return SupplierStream(LongSupplier())
    }

    // Returns the sum of n first numbers containing only digits 1 and 0 that are greater or equal than m
    @JvmStatic
    fun sum10(m: Long, n: Int): Long {
        // NOTE: not functional paradigm
        val onlyContains01 = Predicate { x: Long ->
            var y = x
            while (y > 0) {
                if (y % 10 != 0L && y % 10 != 1L) {
                    return@Predicate false
                }
                y /= 10
            }
            true
        }

        return longStream()
            .filter { it >= m }
            .filter(onlyContains01)
            .limit(n.toLong())
            .reduce { a: Long, b: Long -> a + b }
            .orElse(0)
    }

    @JvmStatic
    // Returns the square of all even numbers in a given list of integers.
    fun squareOfEven(l: List<Int>): List<Int> {
        return l.stream()
            .filter { e -> e % 2 == 0 }
            .map { e -> e * e }
            .toList()
    }

    @JvmStatic
    // Returns the average of all the numbers in a given list of integers.
    fun average(l: List<Int>): OptionalDouble {
        return l.stream()
            .mapToInt { it }
            .average()
    }

    @JvmStatic
    // Returns the longest word in a given list of strings.
    fun longest(l: List<String>): Optional<String> {
        return l.stream()
            .reduce { a, b -> if (a.length > b.length) a else b }
    }

    @JvmStatic
    // Returns the most common element in a given list of integers.
    fun <T : Any> mostCommon(l: List<T>): Optional<T> {
        return l.stream()
            .collect(Collectors.groupingBy { it })
            .values
            .stream()
            .max(Comparator.comparingInt { it.size })
            .map { it[0] }
    }

    // Returns the first n Fibonacci numbers.
    @JvmStatic
    fun fibs(n: Int): List<Int> {
        // with stream
        return Stream.iterate(intArrayOf(0, 1)) { t: IntArray -> intArrayOf(t[1], t[0] + t[1]) }
            .limit(n.toLong())
            .map { t: IntArray -> t[0] }
            .toList()
    }

    // Returns an int stream of prime numbers based on eratosthenes sieve.
    @JvmStatic
    fun eratosthenes(): IntStream {
        return IntStream.iterate(2) { it + 1 }
            .filter(isPrime)
            .peek { prime -> isPrime.and { x: Int -> x % prime != 0 } }

    }
}



