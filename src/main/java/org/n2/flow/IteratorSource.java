package org.n2.flow;

import java.util.Iterator;

public class IteratorSource<T> extends AbstractSource<T> {

    final Iterator<T> source;

    public IteratorSource(Iterator<T> source) {
        this.source = source;
    }

    boolean n = true;

    @Override
    public boolean next() {
        if(!n) return false;

        n = source.hasNext();
        if(n) next(source.next());
        return n;
    }

}
