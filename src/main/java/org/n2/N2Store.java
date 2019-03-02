package org.n2;

import org.h2.mvstore.MVMap;
import org.h2.value.VersionedValue;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        N2Store n2Store = (N2Store) o;
        return name.equals(n2Store.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
