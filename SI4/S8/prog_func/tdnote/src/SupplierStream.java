import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

public class SupplierStream<T> implements BasicStream<T> {
    final Supplier<Optional<T>> s;

    public SupplierStream(Supplier<Optional<T>> f) {
        this.s = f;
    }

    public void forEach(Consumer<T> action) {
        s.get().ifPresent((x) -> { action.accept(x); forEach(action); });

        // Autre possibilité, moins "fonctionnelle"
        // Optional<T> o = s.get();
        // while (o.isPresent()) {
        //   action.accept(x);
        //   o = s.get();
        // }
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return s.get().map((x) -> 
            reduce(accumulator).map((a) -> accumulator.apply(x, a)).orElse(x));
        

        // Autre possibilité, moins "fonctionnelle"
        // Optional<T> r = s.get() ;
        // if (r.isEmpty()) return r;        
        // T acc = r.get();
        // Optional<T> o = s.get();
        // while (o.isPresent()) {
        //     acc = accumulator.apply(acc, o.get());
        //     o = s.get();
        // }
        // return Optional.of(acc);
    }

    private Optional<T> getNext(Predicate<T> predicate) {
        return s.get().flatMap((x) -> 
            predicate.test(x) ? Optional.of(x) : getNext(predicate));
    }

    @Override
    public SupplierStream<T> filter(Predicate<T> predicate) {
        return new SupplierStream<>(() -> getNext(predicate));  

        // Autre possibilité, moins "fonctionnelle"
        // return new SupplierStream<>( () -> {
        //     Optional<T> o = s.get();
        //     while (o.isPresent() && !predicate.test(o.get())) {
        //         o = s.get();
        //     }
        //     return o;
        // });
    }

    @Override
    public SupplierStream<T> limit(long maxSize) {
        AtomicInteger c = new AtomicInteger(0);
        return new SupplierStream<>(() ->
                s.get().filter(x -> c.getAndIncrement() < maxSize));
    }

    @Override
    public <R> SupplierStream<R> map(Function<T, R> mapper) {
        return new SupplierStream<>(() -> s.get().map(mapper));
    }

    public static <T> SupplierStream<T> concat(SupplierStream<T> s1, SupplierStream<T> s2) {
        return new SupplierStream<>(() -> s1.s.get().or(s2.s));
    }

    public static <T> SupplierStream<T> iterate(T seed, UnaryOperator<T> f) {
        return new SupplierStream<>(new Supplier<>() {
            T t = seed;

            @Override
            public Optional<T> get() {
                T result = t;
                t = f.apply(t);
                return Optional.of(result);
            }
        });
    }

    public SupplierStream<T> parallel() {
        return new ParallelSupplierStream<>(this);
    }

    private static class ParallelSupplierStream<T> extends SupplierStream<T> {
        private final ForkJoinPool forkJoinPool;

        public ParallelSupplierStream(SupplierStream<T> s) {
            super(s.s);
            this.forkJoinPool = new ForkJoinPool();
        }

        @Override
        public void forEach(Consumer<T> action) {
            forkJoinPool.invoke(new ForEachTask<>(this, action));
        }

        @Override
        public Optional<T> reduce(BinaryOperator<T> accumulator) {
            return forkJoinPool.invoke(new ReduceTask<>(this, accumulator));
        }

        @Override
        public ParallelSupplierStream<T> filter(Predicate<T> predicate) {
            return new ParallelSupplierStream<>(super.filter(predicate));
        }

        @Override
        public ParallelSupplierStream<T> limit(long maxSize) {
            return new ParallelSupplierStream<>(super.limit(maxSize));
        }

        @Override
        public <R> ParallelSupplierStream<R> map(Function<T, R> mapper) {
            return new ParallelSupplierStream<>(super.map(mapper));
        }
    }

    private static class ForEachTask<T> extends RecursiveTask<Void> {
        private final SupplierStream<T> stream;
        private final Consumer<T> action;

        public ForEachTask(SupplierStream<T> stream, Consumer<T> action) {
            this.stream = stream;
            this.action = action;
        }

        @Override
        protected Void compute() {
            Optional<T> element = stream.s.get();
            if (element.isPresent()) {
                action.accept(element.get());
                ForEachTask<T> nextTask = new ForEachTask<>(stream, action);
                nextTask.fork();
                nextTask.join();
            }
            return null;
        }
    }

    private static class ReduceTask<T> extends RecursiveTask<Optional<T>> {
        private final SupplierStream<T> stream;
        private final BinaryOperator<T> accumulator;

        public ReduceTask(SupplierStream<T> stream, BinaryOperator<T> accumulator) {
            this.stream = stream;
            this.accumulator = accumulator;
        }

        @Override
        protected Optional<T> compute() {
            Optional<T> element = stream.s.get();
            if (element.isPresent()) {
                ReduceTask<T> nextTask = new ReduceTask<>(stream, accumulator);
                nextTask.fork();
                return element.map(x -> accumulator.apply(x, nextTask.join().orElse(x)));
            }
            return Optional.empty();
        }
    }
}

