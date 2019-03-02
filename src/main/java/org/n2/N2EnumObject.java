package org.n2;

public class N2EnumObject<T extends Enum<T>, V> extends AbstractN2Object<T, V> {
    final Class<T> enumClass;

    final T[] keys;
    final Object[] values;

    public N2EnumObject(N2Object<T, V> o) {
        enumClass = o.enumClass();
        keys = o.keys();
        values = new Object[keys.length];
        for(int i = 0; i < keys.length; i++){
            values[i] = o.get(keys[i]);
        }
    }

    public N2EnumObject(Class<T> enumClass) {
        this.enumClass = enumClass;
        this.keys = enumClass.getEnumConstants();
        this.values = new Object[keys.length];
    }

    @Override
    public Class<T> enumClass() {
        return enumClass;
    }

    @Override
    public T[] keys() {
        return keys;
    }

    @Override
    public V get(T key) {
        assert enumClass().equals(key.getClass());

        return (V)values[key.ordinal()];
    }

    public V put(T k, V value){
        assert enumClass().equals(k.getClass());

        int i = k.ordinal();
        V old = (V)values[i];
        values[i] = value;
        return old;
    }

}
