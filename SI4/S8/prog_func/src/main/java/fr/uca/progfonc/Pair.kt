package fr.uca.progfonc

data class Pair<T1, T2>(val first: T1, val second: T2) {
    fun key(): T1 {
        return first
    }

    fun value(): T2 {
        return second
    }
}