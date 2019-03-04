package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class LongSerializer implements AbstractDataType, Serializer {
    public static final LongSerializer INSTANCE = new LongSerializer();

    @Override
    public int compare(Object a, Object b) {
        Long x = (Long)a;
        Long y = (Long)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 8;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        long l = (Long)obj;
        buff.putLong(l);
    }

    @Override
    public Long read(ByteBuffer buff) {
        return buff.getLong();
    }
}
