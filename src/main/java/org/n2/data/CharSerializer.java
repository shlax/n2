package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;

public class CharSerializer extends AbstractDataType implements Serializer{
    public static final CharSerializer INSTANCE = new CharSerializer();

    @Override
    public int compare(Object a, Object b) {
        Character x = (Character)a;
        Character y = (Character)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 4;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        char c = (Character)obj;
        buff.putChar(c);
    }

    @Override
    public Character read(ByteBuffer buff) {
        return buff.getChar();
    }
}
