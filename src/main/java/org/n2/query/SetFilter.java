package org.n2.query;

import java.util.Set;

public class SetFilter<T> extends CollectionFilter<T, Set<T>> {

    public SetFilter(Set<T> set) {
        super(set);
    }
}
