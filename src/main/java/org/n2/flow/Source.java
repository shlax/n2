package org.n2.flow;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Source<T> extends Iterable<T>, Runnable{

    boolean next();

    Source<T> to(Consumer<? super T> s);

    static <X> Source<X> from(Iterator<X> i){
        return new IteratorSource<>(i);
    }

    static <X> Source<X> from(Iterable<X> i){
        return new IteratorSource<>(i.iterator());
    }

    static <X> Source<X> from(Stream<X> i){
        return new IteratorSource<>(i.iterator());
    }

    default  Source<T> filter(Predicate<T> p){
        FlatMapFlow<T, T> f = new FlatMapFlow<>(this, (o, s) -> {
            if(p.test(o)) s.accept(o);
        });
        to(f);
        return f;
    }

    default <R> Source<R> flatMap(FlatMap<T, R> fn){
        FlatMapFlow<T, R> f = new FlatMapFlow<>(this, fn);
        to(f);
        return f;
    }

    default Source<T> flatten(){
        FlattenSource<T> f = new FlattenSource<>(this);
        to(f);
        return f;
    }

    default <R> Source<R> map(Function<T, R> fn){
        FlatMapFlow<T, R> f = new FlatMapFlow<>(this, (o, s) -> s.accept(fn.apply(o)));
        to(f);
        return f;
    }

    default <K, V> Source<V> groupBy(Function<T, K> toKey, V zero, BiFunction<V, T, V> fold){
        return groupBy(new FnGroupBy<>(toKey, zero, fold));
    }

    default <K, V> Source<V> groupBy(GroupBy<T, K, V> fn){
        GroupFlow<T, K, V> f = new GroupFlow<>(this, fn);
        to(f);
        return f;
    }

    default <V, K extends Comparable<K>, R> Source<R> join(Source<V> v, Function<T, K> keyU, Function<V, K> keyV, BiFunction<T, V, R> combine){
        return join(v, new FnJoin<>(keyU, keyV, combine));
    }

    default <V, K extends Comparable<K>, R> Source<R> join(Source<V> v, Join<T, V, K, R> j){
        return new JoinFlow<>(this, v, j);
    }

    @Override
    default Iterator<T> iterator() {
        return new SourceIterator<>(this);
    }

    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    default void run(){
        while (next()){}
    }
}
