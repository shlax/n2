package org.n2.flow;

import java.util.function.Consumer;

public interface Join<U, V, K extends Comparable<K>, R> {

    K keyU(U u);
    K keyV(V v);

    void add(U u, V n, Consumer<R> s);

}
