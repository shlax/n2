package org.n2;

import org.h2.mvstore.tx.TransactionMap;

import java.util.Iterator;
import java.util.Map;

public class N2SessionStore<K, V> {
    final N2Store<K> label;
    final TransactionMap<K, V> store;

    N2SessionStore(N2Store<K> label, TransactionMap<K, V> store) {
        this.label = label;
        this.store = store;
    }

    public N2Link<K> add(V data){
        K id = label.generateId();
        store.put(id, data);
        return new N2Link<>(label.name, id);
    }

    public V get(K id){
        return store.get(id);
    }

    public V put(K id, V data){
        return store.put(id, data);
    }

    public V remove(K id){
        return store.remove(id);
    }

    public Iterator<K> keyIterator(){
        return keyIterator(null, null);
    }

    public Iterator<K> keyIterator(K from, K to){
        return store.keyIterator(from, to, false);
    }

    public Iterator<Map.Entry<K, V>> iterator(){
        return iterator(null, null);
    }

    public Iterator<Map.Entry<K, V>> iterator(K from, K to){
        return store.entryIterator(from, to);
    }

}
