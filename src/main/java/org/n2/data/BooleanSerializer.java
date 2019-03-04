package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class BooleanSerializer implements AbstractDataType, Serializer {
    public static final BooleanSerializer INSTANCE = new BooleanSerializer();

    @Override
    public int compare(Object a, Object b) {
        Boolean x = (Boolean)a;
        Boolean y = (Boolean)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 1;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        boolean b = (Boolean)obj;
        buff.put(b ? (byte)1 : (byte)0);
    }

    @Override
    public Boolean read(ByteBuffer buff) {
        byte b = buff.get();
        return b == (byte) 1;
    }
}
