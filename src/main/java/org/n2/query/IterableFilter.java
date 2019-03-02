package org.n2.query;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class IterableFilter<T, C extends Iterable<T>> extends NotNullFilter<C> {

    public IterableFilter(C coll){
        super(coll);
    }

    public T find(Predicate<? super T> predicate){
        if(obj == null) return null;

        for(T e : obj) if(predicate.test(e)) return e;
        return null;
    }

    public List<T> findAll(Predicate<? super T> predicate){
        ArrayList<T> res = new ArrayList<>();
        if(obj == null) return res;

        for(T e : obj) if(predicate.test(e)) res.add(e);
        return res;
    }

    public boolean exists(Predicate<? super T> predicate){
        if(obj == null) return false;

        for(T e : obj) if(predicate.test(e)) return true;
        return false;
    }

    public boolean forAll(Predicate<? super T> predicate){
        if(obj == null) return false;

        for(T e : obj) if(!predicate.test(e)) return false;
        return true;
    }

}
