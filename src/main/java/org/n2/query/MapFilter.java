package org.n2.query;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class MapFilter<K, V> extends NotNullFilter<Map<K, V>> {

    public MapFilter(Map<K, V> map) {
        super(map);
    }

    public <R> R get(K key, Function<? super V, R> fn){
        if(obj == null) return null;

        V v = obj.get(key);
        if(v == null) return null;

        return fn.apply(v);
    }

    public boolean test(Object key, Predicate<? super V> predicate){
        if(obj == null) return false;

        V v = obj.get(key);
        if(v == null) return false;

        return predicate.test(v);
    }

    public SetFilter<K> keys(){
        return new SetFilter<>(obj == null ? null : obj.keySet());
    }

    public CollectionFilter<V> values(){
        return new CollectionFilter<>(obj == null ? null : obj.values());
    }

    public SetFilter<Map.Entry<K, V>> entrySet(){
        return new SetFilter<>(obj == null ? null : obj.entrySet());
    }

}
