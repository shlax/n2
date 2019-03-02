package org.n2;

public interface N2Object<T extends Enum<T>, V> {
    Class<T> enumClass();
    T[] keys();

    V get(T key);
}
