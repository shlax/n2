package org.n2.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractSource<T> implements Source<T>{

    protected void next(T t){
        for(Consumer<? super T> s : sinks) s.accept(t);
    }

    private final List<Consumer<? super T>> sinks = new ArrayList<>(1);

    @Override
    public Source<T> to(Consumer<? super T> s) {
        sinks.add(s);
        return this;
    }
}
