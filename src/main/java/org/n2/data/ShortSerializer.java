package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class ShortSerializer implements AbstractDataType, Serializer {
    public static final ShortSerializer INSTANCE = new ShortSerializer();

    @Override
    public int compare(Object a, Object b) {
        Short x = (Short)a;
        Short y = (Short)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 2;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        short s = (Short)obj;
        buff.putShort(s);
    }

    @Override
    public Short read(ByteBuffer buff) {
        return buff.getShort();
    }
}
