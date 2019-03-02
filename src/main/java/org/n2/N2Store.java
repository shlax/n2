package org.n2;

import org.h2.mvstore.MVMap;
import org.h2.value.VersionedValue;

public final class N2Store<K> {

    final String name;

    final MVMap<K, VersionedValue> store;

    final IdGenerator<K> generator;

    N2Store(String name, IdGenerator<K> generator, MVMap<K, VersionedValue> store) {
        this.generator = generator;
        this.name = name;
        this.store = store;
    }

    public String name(){
        return name;
    }

    K generateId(){
        if(generator == null) throw new IllegalStateException("undefined typeSerializer");
        return generator.generate();
    }

}
