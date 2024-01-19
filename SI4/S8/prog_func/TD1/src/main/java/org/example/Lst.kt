package org.example

data class Lst<T>(val car: T, val cdr: Lst<T>?)