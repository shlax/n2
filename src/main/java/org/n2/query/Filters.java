package org.n2.query;

import org.n2.N2Object;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.n2.query.Compare.compare;

public class Filters {

    public static boolean eq(Object a, Object b){
        Integer r = compare(a, b);
        if(r == null) return false;
        return r == 0;
    }

    public static boolean ne(Object a, Object b){
        return !eq(a, b);
    }

    public static boolean lt(Object a, Object b){
        Integer r = compare(a, b);
        if(r == null) return false;
        return r < 0;
    }

    public static boolean lte(Object a, Object b){
        Integer r = compare(a, b);
        if(r == null) return false;
        return r <= 0;
    }

    public static boolean gt(Object a, Object b){
        Integer r = compare(a, b);
        if(r == null) return false;
        return r > 0;
    }

    public static boolean gte(Object a, Object b){
        Integer r = compare(a, b);
        if(r == null) return false;
        return r >= 0;
    }

    // less npe

    public static <T> NotNullFilter<T> notNull(Object x){
        return new NotNullFilter(x);
    }

    public static <T> CollectionFilter<T, Collection<T>> collection(Object x){
        return new CollectionFilter(x instanceof Collection ? (Collection)x : null);
    }

    public static <T> ListFilter<T> list(Object x){
        return new ListFilter(x instanceof List ? (List)x : null);
    }

    public static <T> SetFilter<T> set(Object x){
        return new SetFilter(x instanceof Set ? (Set)x : null);
    }

    public static <K, V> MapFilter<K, V> map(Object x){
        return new MapFilter(x instanceof Map ? (Map)x : null);
    }

    public static <T extends Enum<T>, V> N2ObjectFilter<T, V> n2Object(Object x){
        return new N2ObjectFilter(x instanceof N2Object ? (N2Object)x : null);
    }

}
