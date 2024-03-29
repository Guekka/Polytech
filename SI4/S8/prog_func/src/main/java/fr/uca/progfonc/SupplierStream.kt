package fr.uca.progfonc

import java.util.*
import java.util.function.*
import java.util.function.Function

class SupplierStream<T : Any>(private val supplier: Supplier<Optional<T>>) : BasicStream<T> {
    override fun forEach(action: Consumer<T>) {
        val value = supplier.get()
        if (value.isEmpty)
            return

        action.accept(value.get())

        forEach(action)
    }

    override fun reduce(accumulator: BinaryOperator<T>): Optional<T> {
        var accumulated = supplier.get()
        forEach { newElement ->
            accumulated = accumulated.map { accumulator.apply(it, newElement) }
        }
        return accumulated
    }

    override fun filter(predicate: Predicate<T>): BasicStream<T> {
        class FilteredSupplier : Supplier<Optional<T>> {
            override fun get(): Optional<T> {
                // should use recursion but since java has no tail recursion optimization, it will blow the stack
                while (true) {
                    val value = supplier.get()
                    if (value.isEmpty)
                        return Optional.empty()

                    if (predicate.test(value.get()))
                        return value

                    // skip the value
                }
            }
        }

        return SupplierStream(FilteredSupplier())
    }

    override fun limit(maxSize: Long): BasicStream<T> {
        class LimitedSupplier : Supplier<Optional<T>> {
            var count = 0L

            override fun get(): Optional<T> {
                if (count >= maxSize)
                    return Optional.empty()

                count++
                return supplier.get()
            }
        }

        return SupplierStream(LimitedSupplier())
    }

    override fun <R : Any> map(mapper: Function<T, R>): BasicStream<R> {
        class MappingSupplier : Supplier<Optional<R>> {
            override fun get(): Optional<R> {
                val value = supplier.get()
                return if (value.isEmpty) Optional.empty() else Optional.of(mapper.apply(value.get()))
            }
        }

        return SupplierStream(MappingSupplier())
    }
}