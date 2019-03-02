package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;

public class FloatSerializer implements Serializer{
    public static final FloatSerializer INSTANCE = new FloatSerializer();

    @Override
    public void write(WriteBuffer buff, Object obj) {
        Float f = (Float)obj;
        buff.putFloat(f);
    }

    @Override
    public Float read(ByteBuffer buff) {
        return buff.getFloat();
    }
}
