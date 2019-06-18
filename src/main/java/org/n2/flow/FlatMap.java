package org.n2.flow;

import java.util.function.Consumer;

@FunctionalInterface
public interface FlatMap<U, V> {

    void apply(U o, Consumer<V> s);

}
