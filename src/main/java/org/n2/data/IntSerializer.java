package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class IntSerializer extends AbstractDataType implements Serializer {
    public static final IntSerializer INSTANCE = new IntSerializer();

    @Override
    public int compare(Object a, Object b) {
        Integer x = (Integer)a;
        Integer y = (Integer)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 4;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        int i = (Integer)obj;
        buff.putInt(i);
    }

    @Override
    public Integer read(ByteBuffer buff) {
        return buff.getInt();
    }
}
