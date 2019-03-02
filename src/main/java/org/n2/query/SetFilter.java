package org.n2.query;

import java.util.Set;

public class SetFilter<T> extends IterableFilter<T, Set<T>> {

    public SetFilter(Set<T> set) {
        super(set);
    }
}
