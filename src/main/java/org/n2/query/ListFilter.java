package org.n2.query;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListFilter<T> extends IterableFilter<T, List<T>> {

    public ListFilter(List<T> list) {
        super(list);
    }

    public <R> R get(int ind, Function<? super T, R> fn){
        if(obj == null || ind >= obj.size()) return null;

        T v = obj.get(ind);
        if(v == null) return null;

        return fn.apply(v);
    }

    public boolean test(int ind, Predicate<? super T> predicate){
        if(obj == null || ind >= obj.size()) return false;

        T v = obj.get(ind);
        if(v == null) return false;

        return predicate.test(v);
    }

}
