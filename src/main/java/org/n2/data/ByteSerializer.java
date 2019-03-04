package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class ByteSerializer implements AbstractDataType, Serializer{
    public static final ByteSerializer INSTANCE = new ByteSerializer();

    @Override
    public int compare(Object a, Object b) {
        Byte x = (Byte)a;
        Byte y = (Byte)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 1;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        byte b = (Byte)obj;
        buff.put(b);
    }

    @Override
    public Byte read(ByteBuffer buff) {
        return buff.get();
    }
}
