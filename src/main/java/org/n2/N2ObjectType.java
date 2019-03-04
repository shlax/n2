package org.n2;

import org.n2.data.Serializer;
import java.nio.ByteBuffer;

class N2ObjectType <T extends Enum<T>, V> extends AbstractN2Object<T, V> {
    final Class<T> enumClass;
    final Serializer typeSerializer;

    final ByteBuffer buff;
    final int off;

    N2ObjectType(Class<T> enumClass, ByteBuffer buffer, int off, Serializer typeSerializer) {
        this.enumClass = enumClass;
        this.typeSerializer = typeSerializer;
        this.buff = buffer;
        this.off = off;
    }

    @Override
    public Class<T> enumClass() {
        return enumClass;
    }

    T[] keys;

    @Override
    public T[] keys() {
        if(keys == null){
            keys = enumClass.getEnumConstants();
        }
        return keys;
    }

    boolean[] init;
    Object[] values;

    @Override
    public V get(T key) {
        assert enumClass().equals(key.getClass());

        if(init == null){
            int len = keys().length;
            init = new boolean[len];
            values = new Object[len];
        }

        int index = key.ordinal();
        if(!init[index]){
            init[index] = true;

            buff.position(off + 4 * index);
            int pos = buff.getInt();

            if(pos != -1){
                buff.position(pos);
                values[index] = typeSerializer.read(key, buff);
            }
        }

        return (V)values[index];
    }

}
