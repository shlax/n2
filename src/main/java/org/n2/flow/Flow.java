package org.n2.flow;

import java.util.function.Consumer;

public interface Flow<U, V> extends Source<V>, Consumer<U> {
}
