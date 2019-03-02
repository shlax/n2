package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;

public class DoubleSerializer implements Serializer{
    public static final DoubleSerializer INSTANCE = new DoubleSerializer();

    @Override
    public void write(WriteBuffer buff, Object obj) {
        Double d = (Double)obj;
        buff.putDouble(d);
    }

    @Override
    public Double read(ByteBuffer buff) {
        return buff.getDouble();
    }
}
