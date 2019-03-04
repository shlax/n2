package org.n2;

import org.n2.data.Serializer;

import java.nio.ByteBuffer;

public class N2ObjectDataType<T extends Enum<T>, V> extends N2ObjectTypeSerializer implements AbstractDataType {

    public N2ObjectDataType(Class<T> enumClass, Serializer typeSerializer) {
        this(enumClass, 1024, typeSerializer);
    }

    public N2ObjectDataType(Class<T> enumClass, int memory, Serializer typeSerializer) {
        super(enumClass, typeSerializer);
        this.memory = memory;
    }

    @Override
    public N2ObjectType<T, V> read(ByteBuffer buff) {
        int size = buff.getInt();
        byte[] data = new byte[size];
        buff.get(data);
        return new N2ObjectType(enumClass, ByteBuffer.wrap(data).asReadOnlyBuffer(), 0, typeSerializer);
    }

    final int memory;

    @Override
    public int getMemory(Object obj) {
        return memory;
    }

    @Override
    public int compare(Object a, Object b) {
        return 0;
    }

}
