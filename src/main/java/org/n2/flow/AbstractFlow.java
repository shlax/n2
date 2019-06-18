package org.n2.flow;

public abstract class AbstractFlow<U, V> extends AbstractSource<V> implements Flow<U, V>{

    private final Source<U> source;

    public AbstractFlow(Source<U> source) {
        this.source = source;
    }

    @Override
    public boolean next() {
        return source.next();
    }
}
