package fr.uca.progfonc

import java.util.*
import java.util.function.Predicate
import java.util.stream.Stream

object TD5 {
    @JvmStatic
    fun <T> length(list1: Optional<LSTO<T>>): Int {
        return list1.map { 1 + length(it.cdr) }.orElse(0)
    }

    @JvmStatic
    fun <T> member(list1: Optional<LSTO<T>>, i: T): Boolean {
        return list1.map { it.car == i || member(it.cdr, i) }.orElse(false)
    }

    @JvmStatic
    fun <T : Any> append(list1: Optional<LSTO<T>>, list2: Optional<LSTO<T>>): Optional<LSTO<T>> {
        return list1.map {
            Optional.of(
                LSTO(it.car, append(it.cdr, list2))
            )
        }
            .orElse(list2)
    }

    @JvmStatic
    fun <T, U> map(list1: Optional<LSTO<T>>, f: (T) -> U): Optional<LSTO<U>> {
        return list1.map { LSTO(f(it.car), map(it.cdr, f)) }
    }

    @JvmStatic
    fun <T> filter(list1: Optional<LSTO<T>>, predicate: Predicate<T>): Optional<LSTO<T>> {
        return list1.map {
            if (predicate.test(it.car)) {
                Optional.of(LSTO(it.car, filter(it.cdr, predicate)))
            } else {
                filter(it.cdr, predicate)
            }
        }.orElse(Optional.empty())
    }

    @JvmStatic
    fun groupByInitial(stringStream: Stream<String>): Map<Char, List<String>> {
        return stringStream.collect(
            { HashMap<Char, MutableList<String>>() },
            { map, s ->
                val key = s.toString()[0]
                map.computeIfAbsent(key) { ArrayList() }.add(s.toString())
            },
            { map1, map2 ->
                map2.forEach { (key, value) ->
                    map1.computeIfAbsent(key) { ArrayList() }.addAll(value)
                }
            }
        )
    }

    @JvmStatic
    fun groupByClass(objectStream: Stream<Any>): Map<Class<*>, Set<Any>> {
        return objectStream.collect(
            { HashMap<Class<*>, MutableSet<Any>>() },
            { map, o ->
                map.computeIfAbsent(o.javaClass) { HashSet() }.add(o)
            },
            { map1, map2 ->
                map2.forEach { (key, value) ->
                    map1.computeIfAbsent(key) { HashSet() }.addAll(value)
                }
            }
        )
    }
}