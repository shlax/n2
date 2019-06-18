package org.n2.flow;

import java.util.LinkedList;

public class FlattenSource<T> extends AbstractFlow<T, T> {

    public FlattenSource(Source<T> source) {
        super(source);
    }

    private final LinkedList<T> stack = new LinkedList<>();

    @Override
    public boolean next() {
        while (stack.isEmpty() && super.next()){}
        if(stack.isEmpty()) return false;
        next(stack.pollFirst());
        return true;
    }

    @Override
    public void accept(T t) {
        stack.add(t);
    }

}
