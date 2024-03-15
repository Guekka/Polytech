package fr.uca.progfonc;

import java.util.function.*;

public class TD3 {
    // Tail recursive factorial function
    public static int fact(int n) {
        return 0;
    }

    // Tail recursive Fibonacci function
    public static int fib(int n) {
        return 0;
    }

    // Tail recursive function to calculate the greatest common divisor of two integers using Euclid algorithm
    public static int gcd(int a, int b) {
        return 0;
    }

    public static String binary(int n) {
        return null;
    }

    // Tail recursive function to find minimum element in a list
    public static <T extends Comparable<T>> T min(Lst<T> lst) {
        return null;
    }

    // Tail recursive function to reverse a linked list
    public static <T> Lst<T> reverse(Lst<T> current) {
        return null;
    }

    // Tail recursive function to test if a list is a palindrome
    public static <T> boolean palindrome(Lst<T> l) {
        return false;
    }

    public static <T> Lst<T> flatten(Lst<?> lst) {
        return null;
    }

    // Returns a new list that contains only the elements of the input list that are greater than n
    public static Lst<Integer> sup(Lst<Integer> l, int n) {
        return null;
    }

    // Returns a list of n occurrences of e
    public static <T> Lst<T> nlist(int n, T e) {
        return null;
    }

    // Produces the list of integers [0, 1, 2, ... n-1]
    public static Lst<Integer> indexes(int n) {
        return null;
    }

    // Returns a closure that says if a character is a vowel.
    public static Predicate<Character> isVowel(String vowels) {
        return null;
    }

    public static Lst<Character> toLST(String s) {
        return null;
    }

    // Returns a closure that counts the number of vowels in a string.
    public static Function<String, Integer> countVowels(String vowels) {
        return null;
    }

    // Returns a closure that computes if a string contains the substring.
    public static Predicate<String> contains(String sub) {
        return null;
    }

    // Returns a closure that computes if the string s contains any of the elements of l.
    public static Predicate<String> containsAny(Lst<String> l) {
        return null;
    }

    public static UnaryOperator<Integer> accumulator() {
        return null;
    }

    public static int sumAcc(Lst<Integer> list) {
        return 0;
    }

    // Returns a non-safe closure for a list iterator.
    public static <T> Supplier<T> iterator(Lst<T> l) {
        return null;
    }

    // Returns the memoization of a function.
    public static <T, R> Function<T, R> memo(Function<T, R> f) {
        return null;
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> f) {
        return null;
    }

    public static <T, U, R> BiFunction<T, U, R> unCurried(Function<T, Function<U, R>> f) {
        return null;
    }
}
