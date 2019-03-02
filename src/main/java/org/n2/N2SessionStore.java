package org.n2;

import org.h2.mvstore.tx.TransactionMap;

import java.util.Iterator;
import java.util.Map;

public class N2SessionStore<K> {
    final N2Store<K> label;
    final TransactionMap<K, Object> store;

    N2SessionStore(N2Store<K> label, TransactionMap<K, Object> store) {
        this.label = label;
        this.store = store;
    }

    public N2RefId<K> add(Object data){
        K id = label.generateId();
        store.put(id, data);
        return new N2RefId<>(label.name, id);
    }

    public Object get(K id){
        return store.get(id);
    }

    public Object put(K id, Object data){
        return store.put(id, data);
    }

    public Object remove(K id){
        return store.remove(id);
    }

    public Iterator<K> keyIterator(K from, K to){
        return store.keyIterator(from, to, false);
    }

    public Iterator<Map.Entry<K, Object>> iterator(K from, K to){
        return store.entryIterator(from, to);
    }

}
