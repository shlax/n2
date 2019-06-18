package org.n2.flow;

public class FlatMapFlow<U, V> extends AbstractFlow<U, V>{

    private final FlatMap<U, V> fn;

    public FlatMapFlow(Source<U> source, FlatMap<U, V> fn) {
        super(source);
        this.fn = fn;
    }

    @Override
    public void accept(U u) {
        fn.apply(u, this::next);
    }

}
