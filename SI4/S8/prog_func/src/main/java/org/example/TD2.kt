package org.example

import java.util.function.BiFunction
import java.util.function.Function
import java.util.function.Predicate

object TD2 {
    fun <T, R> map(f: Function<T, R>, l: Lst<T>?): Lst<R>? {
        if (l != null) {
            return Lst(f.apply(l.car), map(f, l.cdr))
        }
        return null
    }

    fun squares(l: Lst<Int>): Lst<Int>? {
        return map({ i -> i * i }, l)
    }

    fun sizeOfStrings(l: Lst<String>): Lst<Int>? {
        return map({ s -> s.length }, l)
    }

    fun <T> filter(f: Predicate<T>, l: Lst<T>?): Lst<T>? {
        return l?.let {
            if (f.test(it.car)) {
                Lst(it.car, filter(f, it.cdr))
            } else {
                filter(f, it.cdr)
            }
        }
    }

    fun lowers(l: Lst<String>): Lst<String>? {
        return filter({ s -> s.chars().allMatch(Character::isLowerCase) }, l)
    }

    fun <T> count(f: Predicate<T>, l: Lst<T>?): Int {
        if (l != null) {
            val v = if (f.test(l.car)) {
                1
            } else {
                0
            }

            return v + count(f, l.cdr)
        }
        return 0
    }

    fun nbPositives(l: Lst<Int>): Int {
        return count({ i -> i > 0 }, l)
    }

    fun <T, R> reduce(f: BiFunction<T, R, R>, l: Lst<T>?, init: R?): R? {
        if (l != null) {
            val newInit = if (init != null) f.apply(l.car, init) else l.car as R
            return reduce(f, l.cdr, newInit)
        }
        return init
    }


    fun sum(l: Lst<Int>): Int? {
        return reduce({ i, j -> i + j }, l, 0)
    }

    fun <T : Comparable<T>> min(l: Lst<T>?): T? {
        return reduce({ i: T, j: T -> if (i < j) i else j }, l, null)
    }

    fun sumLengthLowers(l: Lst<String>): Int {
        return reduce({ s, i -> s.length + i }, lowers(l), 0) ?: 0
    }

    fun sumLength(l: Lst<String>): Int {
        return reduce({ s, i -> s.length + i }, l, 0) ?: 0
    }

    fun <T> repr(l: Lst<T>?): String {
        return "(" + reduce({ s, i -> "$i $s" }, l, "") + ")"
    }

    fun <T> concat(ll: Lst<Lst<T>>): Lst<T>? {
        return reduce({ l1, l2 -> TD1.append(l2, l1)!! }, ll, null as Lst<T>?)
    }

    fun <T> toSet(l: Lst<T>?): Lst<T>? {
        return l?.let {
            return toSet(l.cdr)?.let { cdr ->
                if (TD1.member(l.car, cdr)) {
                    cdr
                } else {
                    Lst(l.car, cdr)
                }
            }
        }
    }

    fun <T> union(s1: Lst<T>, s2: Lst<T>?): Lst<T>? {
        return toSet(TD1.append(s1, s2))
    }

    fun <T> equalsTo(x: T): Predicate<T> {
        return Predicate { y: T -> x == y }
    }

    fun <T : Comparable<T>> between(a: T, b: T): Predicate<T> {
        return Predicate { x: T -> x in a..b }
    }

    fun <T> countOccurence(l: Lst<T>, e: T): Int {
        return count(equalsTo(e), l)
    }
}
