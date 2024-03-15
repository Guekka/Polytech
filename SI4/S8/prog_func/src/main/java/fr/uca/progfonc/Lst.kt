package fr.uca.progfonc

data class Lst<T>(val car: T, val cdr: Lst<T>?) {
    fun car(): T {
        return car
    }

    fun cdr(): Lst<T>? {
        return cdr
    }
}