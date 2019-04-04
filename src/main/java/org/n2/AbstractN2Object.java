package org.n2;

import java.util.Objects;

public abstract class AbstractN2Object<T extends Enum<T>, V> implements N2Object<T, V> {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof N2Object)) return false;

        N2Object that = (N2Object) o;

        if(!enumClass().equals(that.enumClass())) return false;
        for(T k : keys()) if(!Objects.equals(get(k), that.get(k))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(enumClass());
        for (T k : keys()) {
            V o = get(k);
            if(o != null) result = 31 * o.hashCode();
        }
        return result;
    }
}
