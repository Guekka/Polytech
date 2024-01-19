package org.example

object TD1 {
    /**
     * Returns the length of the list
     */
    fun <T> length(l: Lst<T>?): Int {
        if (l == null) {
            return 0
        }

        if (l.cdr == null) {
            return 1
        }

        return 1 + length(l.cdr)
    }

    /**
     * checks if the value val is a member of the list l.
     */
    fun <T> member(value: T, l: Lst<T>?): Boolean {
        if (l == null) {
            return false
        }

        if (l.car == value) {
            return true
        }

        return member(value, l.cdr)
    }

    /**
     * adds the elements of list l1 to the beginning of list l2.
     */
    fun <T> append(l1: Lst<T>?, l2: Lst<T>?): Lst<T>? {
        if (l1 == null) {
            return l2
        }

        return Lst(l1.car, append(l1.cdr, l2))
    }

    /**
     * calculates the sum of the elements in the list of Integers l.
     */
    fun sum(l: Lst<Int>?): Int {
        if (l == null) {
            return 0
        }

        return l.car + sum(l.cdr)
    }

    /**
     * removes the first occurrence of the value val from the list l.
     */
    fun <T> remove(value: T, l: Lst<T>?): Lst<T>? {
        if (l == null) {
            return null
        }

        if (l.car == value) {
            return l.cdr
        }

        return Lst(l.car, remove(value, l.cdr))
    }

    /**
     * removes all occurrences of the value val from the list l.
     */
    fun <T> removeAll(value: T, l: Lst<T>?): Lst<T>? {
        if (l == null) {
            return null
        }

        if (l.car == value) {
            return removeAll(value, l.cdr)
        }

        return Lst(l.car, removeAll<T>(value, l.cdr))
    }

    /**
     * returns a list of strings containing the numbers from a to b, replacing multiples of 3 with "Fizz", multiples of 5 with "Buzz", and multiples of 15 with "FizzBuzz".
     */
    fun fizzbuzz(a: Int, b: Int): Lst<String>? {
        if (a > b - 1) {
            return null
        }

        if (a % 15 == 0) {
            return Lst("FizzBuzz", fizzbuzz(a + 1, b))
        }

        if (a % 5 == 0) {
            return Lst("Buzz", fizzbuzz(a + 1, b))
        }

        if (a % 3 == 0) {
            return Lst("Fizz", fizzbuzz(a + 1, b))
        }

        return Lst(a.toString(), fizzbuzz(a + 1, b))
    }

    /**
     * creates a list from a given array of elements of type T. To work with a shorter array, you may consider using an additional (private) method fromArray(tab, Int index)
     */
    fun <T> fromArray(arr: Array<T>): Lst<T>? {
        return fromArray(arr, 0)
    }

    private fun <T> fromArray(arr: Array<T>, i: Int): Lst<T>? {
        if (i >= arr.size) {
            return null
        }

        return Lst(arr[i], fromArray<T>(arr, i + 1))
    }

    /**
     * reverses the order of the elements in the list l.
     */
    fun <T> reverse(l: Lst<T>?): Lst<T>? {
        if (l == null) {
            return null
        }

        return append(reverse(l.cdr), Lst(l.car, null))
    }

    /**
     * inserts the value val Into the list l in a way that maIntains the order of the elements in the list.
     */
    fun <T : Comparable<T>> insert(value: T, l: Lst<T>?): Lst<T> {
        if (l == null) {
            return Lst(value, null)
        }

        if (value < l.car) {
            return Lst(value, l)
        }

        return Lst(l.car, insert(value, l.cdr))
    }

    /**
     * sorts the list l using the insertion sort algorithm. I.e. you can take benefit from the insert method.
     */
    fun <T : Comparable<T>> sort(l: Lst<T>?): Lst<T>? {
        if (l == null) {
            return null
        }

        return insert(l.car, sort(l.cdr))
    }

    /**
     * returns a list containing the first n elements of the given list l. If n is less than or equal to 0 or l is null, it returns null.
     */
    fun <T> take(n: Int, l: Lst<T>?): Lst<T>? {
        if (n <= 0 || l == null) {
            return null
        }

        return Lst(l.car, take(n - 1, l.cdr))
    }

    /**
     * returns the index of the first occurrence of the given value val in the list l, or -1 if val is not found in l.
     */
    fun <T> indexOf(value: T, l: Lst<T>?): Int {
        if (l == null) {
            return -1
        }

        if (l.car == value) {
            return 0
        }

        val index = indexOf(value, l.cdr)
        if (index == -1) {
            return -1
        }

        return 1 + index
    }

    /**
     * returns a list containing only a unique occurrence of the elements of the given list l.
     */
    fun <T> unique(l: Lst<T>?): Lst<T>? {
        if (l == null) {
            return null
        }

        return Lst(l.car, unique(removeAll(l.car, l.cdr)))
    }

    /**
     * returns true if the given key k is present in the list of pairs l, and false otherwise.
     */
    fun <T, U> has(l: Lst<Pair<T, U>>?, k: T): Boolean {
        if (l == null) {
            return false
        }

        if (l.car.first == k) {
            return true
        }

        return has(l.cdr, k)
    }

    /**
     * returns the value associated with the given key k in the list of pairs l, or null if k is not present in l.
     */
    fun <T, U> get(l: Lst<Pair<T, U>>?, k: T): U? {
        if (l == null) {
            return null
        }

        if (l.car.first == k) {
            return l.car.second
        }

        return get<T, U>(l.cdr, k)
    }

    /**
     * change the value associated with the given key k to the given value v. If k is not present in l, it adds a new pair.
     */
    fun <T, U> set(l: Lst<Pair<T, U>>?, k: T, v: U): Lst<Pair<T, U>> {
        if (l == null) {
            return Lst(Pair(k, v), null)
        }

        if (l.car.first == k) {
            return Lst(Pair(k, v), l.cdr)
        }

        return Lst(l.car, set<T, U>(l.cdr, k, v))
    }

    fun max(l: Lst<Int>): Long {
        if (l.cdr == null) {
            return l.car.toLong()
        }

        val m = max(l.cdr)
        return if (l.car > m) l.car.toLong() else m
    }
}
