package org.n2.query;

import org.n2.N2Object;

import java.util.function.Function;
import java.util.function.Predicate;

public class N2ObjectFilter<T extends Enum<T>, V> extends NotNullFilter<N2Object<T, V>> {

    public N2ObjectFilter(N2Object<T, V> obj) {
        super(obj);
    }

    public <R> R get(T key, Function<? super V, R> fn){
        if(obj == null) return null;

        if(!obj.enumClass().equals(key.getClass())) return null;

        V v = obj.get(key);
        if(v == null) return null;

        return fn.apply(v);
    }

    public boolean test(Enum key, Predicate<? super V> predicate){
        if(obj == null) return false;

        if(!obj.enumClass().equals(key.getClass())) return false;

        V v = obj.get((T)key);
        if(v == null) return false;

        return predicate.test(v);
    }

}
