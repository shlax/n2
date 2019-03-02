package org.n2.query;

import java.util.Collection;

public class CollectionFilter<T> extends IterableFilter<T, Collection<T>> {

    public CollectionFilter(Collection<T> coll) {
        super(coll);
    }
}
