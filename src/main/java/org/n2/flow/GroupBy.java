package org.n2.flow;

import java.util.function.Consumer;

public interface GroupBy<T, K, V> {

    K key(T t);

    void reset(T t);

    void update(T t);

    void add(K key, Consumer<V> s);

}
