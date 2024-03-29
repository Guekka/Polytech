package fr.uca.progfonc

import fr.uca.progfonc.TD1.length
import fr.uca.progfonc.TD2.filter
import fr.uca.progfonc.TD2.map
import java.util.function.*
import java.util.function.Function

object TD3 {
    // Tail recursive factorial function
    @JvmStatic
    fun fact(n: Int): Int {
        return n * fact(n - 1)
    }

    // Tail recursive Fibonacci function
    @JvmStatic
    fun fib(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fibTco(n - 2, 1, 0)
        }
    }

    private fun fibTco(n: Int, minus1: Int, minus2: Int): Int {
        return when (n) {
            0 -> minus1 + minus2
            else -> fibTco(n - 1, minus1 + minus2, minus1)
        }
    }

    // Tail recursive function to calculate the greatest common divisor of two integers using Euclid algorithm
    @JvmStatic
    fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    @JvmStatic
    fun binary(n: Int): String {
        return binaryImpl(n, "")
    }

    private fun binaryImpl(n: Int, acc: String): String {
        return if (n == 0) acc else binaryImpl(n / 2, if (n % 2 == 0) "0$acc" else "1$acc")
    }

    // Tail recursive function to find minimum element in a list
    @JvmStatic
    fun <T : Comparable<T>?> min(lst: Lst<T>?): T? {
        return minImpl(lst, null)
    }

    private fun <T : Comparable<T>?> minImpl(lst: Lst<T>?, curMin: T?): T? {
        return if (lst == null) curMin else minImpl(
            lst.cdr(),
            if (curMin == null || lst.car()!! < curMin) lst.car() else curMin
        )
    }

    // Tail recursive function to reverse a linked list
    @JvmStatic
    fun <T> reverse(current: Lst<T>?): Lst<T>? {
        return reverseImpl(current, null)
    }

    private fun <T> reverseImpl(current: Lst<T>?, acc: Lst<T>?): Lst<T>? {
        return if (current == null) acc else reverseImpl(current.cdr(), Lst(current.car(), acc))
    }

    // Tail recursive function to test if a list is a palindrome
    @JvmStatic
    fun <T> palindrome(l: Lst<T>?): Boolean {
        return false
    }

    @JvmStatic
    fun <T> flatten(lst: Lst<*>?): Lst<T>? {
        return null
    }

    // Returns a new list that contains only the elements of the input list that are greater than n
    @JvmStatic
    fun sup(l: Lst<Int>?, n: Int): Lst<Int>? {
        return filter({ i: Int -> i > n }, l)
    }

    // Returns a list of n occurrences of e
    @JvmStatic
    fun <T> nlist(n: Int, e: T): Lst<T> {
        return Lst(e, nlist(n - 1, e))
    }

    // Produces the list of integers [0, 1, 2, ... n-1]
    @JvmStatic
    fun indexes(n: Int): Lst<Int>? {
        var i = 0
        return map(
            { el -> el + i++ },
            nlist(n, 0)
        )
    }

    // Returns a closure that says if a character is a vowel.
    @JvmStatic
    fun isVowel(vowels: String): Predicate<Char> {
        return Predicate { c: Char -> vowels.contains(c) }
    }

    fun toLST(s: String?): Lst<Char>? {
        return toLstImpl(s, 0)
    }

    private fun toLstImpl(s: String?, idx: Int): Lst<Char>? {
        if (s.isNullOrEmpty())
            return null

        return Lst(s[idx], toLstImpl(s, idx + 1))
    }


    // Returns a closure that counts the number of vowels in a string.
    @JvmStatic
    fun countVowels(vowels: String): Function<String, Int> {
        return Function { s: String -> length(filter(isVowel(vowels), toLST(s))) }
    }

    // Returns a closure that computes if a string contains the substring.
    @JvmStatic
    fun contains(sub: String): Predicate<String> {
        return Predicate { s: String -> s.contains(sub) }
    }

    // Returns a closure that computes if the string s contains any of the elements of l.
    @JvmStatic
    fun containsAny(l: Lst<String>?): Predicate<String> {
        return Predicate { false }
    }

    @JvmStatic
    fun accumulator(): UnaryOperator<Int>? {
        return null
    }

    @JvmStatic
    fun sumAcc(list: Lst<Int?>?): Int {
        return 0
    }

    // Returns a non-safe closure for a list iterator.
    @JvmStatic
    fun <T> iterator(l: Lst<T>?): Supplier<T>? {
        return null
    }

    // Returns the memoization of a function.
    @JvmStatic
    fun <T, R> memo(f: Function<T, R>?): Function<T, R>? {
        return null
    }

    @JvmStatic
    fun <T, U, R> curried(f: BiFunction<T, U, R>?): Function<T, Function<U, R>>? {
        return null
    }

    @JvmStatic
    fun <T, U, R> unCurried(f: Function<T, Function<U, R>?>?): BiFunction<T, U, R>? {
        return null
    }
}
