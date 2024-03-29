package fr.uca.progfonc

import java.util.*

data class LSTO<T>(val car: T, val cdr: Optional<LSTO<T>>) {
    fun car(): T {
        return car
    }

    fun cdr(): Optional<LSTO<T>> {
        return cdr
    }
}