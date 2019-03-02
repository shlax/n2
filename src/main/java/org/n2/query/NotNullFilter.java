package org.n2.query;

import java.util.function.Function;
import java.util.function.Predicate;

public class NotNullFilter<T> {

    final T obj;

    public NotNullFilter(T obj) {
        this.obj = obj;
    }

    public <V> V get(Function<? super T, V> fn){
        if(obj == null) return null;

        return fn.apply(obj);
    }

    public boolean test(Predicate<? super T> predicate){
        if(obj == null) return false;

        return predicate.test(obj);
    }

}
