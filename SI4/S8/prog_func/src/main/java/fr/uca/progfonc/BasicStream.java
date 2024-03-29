package fr.uca.progfonc;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BasicStream<T> {
    // Performs an action for each element of this stream.
    void forEach(Consumer<T> action);

    // Performs a reduction on the elements of this stream, using an associative accumulation function, and returns an Optional describing the reduced value, if any.
    Optional<T> reduce(BinaryOperator<T> accumulator);

    // Returns a stream consisting of the elements of this stream that match the given predicate.
    BasicStream<T> filter(Predicate<T> predicate);

    // Returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in length.
    BasicStream<T> limit(long maxSize);

    // Returns a stream consisting of the results of applying the given function to the elements of this stream.
    <R> BasicStream<R> map(Function<T,R> mapper);
}
