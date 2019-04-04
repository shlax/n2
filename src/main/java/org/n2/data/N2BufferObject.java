package org.n2.data;

import org.n2.AbstractN2Object;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

class N2BufferObject<T extends Enum<T>, V> extends AbstractN2Object<T, V> {
    private static final ConcurrentHashMap<String, Class> enums = new ConcurrentHashMap<>();

    final Serializer typeSerializer;

    final ByteBuffer buff;
    final int off;

    N2BufferObject(Serializer typeSerializer, ByteBuffer buff, int off) {
        this.typeSerializer = typeSerializer;
        this.buff = buff;
        this.off = off;
    }

    Class<T> enumClass;
    int off4;

    @Override
    public Class<T> enumClass() {
        if(enumClass == null){
            int len = buff.position(off).getInt();
            off4 = off + len + 4;
            byte[] nm = new byte[len];
            buff.get(nm);
            String cNm = new String(nm, StandardCharsets.UTF_8);
            enumClass = enums.get(cNm);
            if(enumClass == null){
                try {
                    enumClass = (Class)Class.forName(cNm);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
                enums.put(cNm, enumClass);
            }

        }
        return enumClass;
    }

    T[] keys;

    @Override
    public T[] keys() {
        if(keys == null){
            keys = enumClass().getEnumConstants();
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

            buff.position(off4 + (4 * index));
            int pos = buff.getInt();

            if(pos != -1) {
                buff.position(off4 + pos);
                values[index] = typeSerializer.read(key, buff);
            }
        }

        return (V)values[index];
    }
}
