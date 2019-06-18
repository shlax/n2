package org.n2.flow;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FnJoin<U, V, K extends Comparable<K>, R> implements Join<U, V, K, R>{

    private final Function<U, K> keyU;
    private final Function<V, K> keyV;

    private final BiFunction<U, V, R> combine;

    public FnJoin(Function<U, K> keyU, Function<V, K> keyV, BiFunction<U, V, R> combine) {
        this.keyU = keyU;
        this.keyV = keyV;
        this.combine = combine;
    }

    @Override
    public K keyU(U u) {
        return keyU.apply(u);
    }

    @Override
    public K keyV(V v) {
        return keyV.apply(v);
    }

    @Override
    public void add(U u, V n, Consumer<R> s) {
        s.accept(combine.apply(u, n));
    }

}
