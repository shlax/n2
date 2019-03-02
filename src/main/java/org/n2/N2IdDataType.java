package org.n2;

import org.h2.mvstore.WriteBuffer;
import org.n2.data.Serializer;

import java.nio.ByteBuffer;

public class N2IdDataType extends AbstractDataType implements Serializer {
    public static final N2IdDataType INSTANCE = new N2IdDataType();

    @Override
    public int compare(Object aObj, Object bObj) {
        N2Id a = (N2Id) aObj;
        N2Id b = (N2Id) bObj;
        return a.compareTo(b);
    }

    @Override
    public int getMemory(Object obj) {
        return 12;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        N2Id id = (N2Id)obj;
        buff.putLong(id.time);
        buff.putInt(id.count);
    }

    @Override
    public Object read(ByteBuffer buff) {
        return new N2Id(buff.getLong(), buff.getInt());
    }



}
