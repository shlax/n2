package org.n2.flow;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FnGroupBy<T, K, V> implements GroupBy<T, K, V> {

    private final Function<T, K> toKey;
    private final V zero;
    private final BiFunction<V, T, V> fold;

    public FnGroupBy(Function<T, K> toKey, V zero, BiFunction<V, T, V> fold) {
        this.toKey = toKey;
        this.zero = zero;
        this.fold = fold;
    }

    private V v;

    @Override
    public K key(T t) {
        return toKey.apply(t);
    }

    @Override
    public void reset(T t) {
        v = fold.apply(zero, t);
    }

    @Override
    public void update(T t) {
        v = fold.apply(v, t);
    }

    @Override
    public void add(K key, Consumer<V> s) {
        s.accept(v);
    }

}
