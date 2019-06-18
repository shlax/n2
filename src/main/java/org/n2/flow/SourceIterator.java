package org.n2.flow;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class SourceIterator<T> implements Iterator<T>, Consumer<T> {

    private final Source<T> source;

    public SourceIterator(Source<T> source) {
        this.source = source;
        this.source.to(this);
    }

    private final LinkedList<T> stack = new LinkedList<>();

    @Override
    public boolean hasNext() {
        while (stack.isEmpty() && source.next()){}
        return !stack.isEmpty();
    }

    @Override
    public void accept(T t) {
        stack.add(t);
    }

    @Override
    public T next() {
        return stack.pollFirst();
    }
}
