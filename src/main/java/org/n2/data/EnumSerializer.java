package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;

public class EnumSerializer<T extends Enum & SerializableEnum> implements Serializer {

    @Override
    public void write(Enum key, WriteBuffer buff, Object obj) {
        SerializableEnum t = (SerializableEnum)key;
        t.serializer().write(key, buff, obj);
    }

    @Override
    public Object read(Enum key, ByteBuffer buff) {
        SerializableEnum t = (SerializableEnum)key;
        return t.serializer().read(key, buff);
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object read(ByteBuffer buff) {
        throw new UnsupportedOperationException();
    }
}
