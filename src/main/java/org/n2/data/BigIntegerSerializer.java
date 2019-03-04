package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigIntegerSerializer implements AbstractDataType, Serializer {
    public static final BigIntegerSerializer INSTANCE = new BigIntegerSerializer();

    @Override
    public int compare(Object a, Object b) {
        BigInteger x = (BigInteger)a;
        BigInteger y = (BigInteger)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        return 4;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        BigInteger i = (BigInteger)obj;
        byte[] b = i.toByteArray();
        buff.putInt(b.length);
        buff.put(b);
    }

    @Override
    public BigInteger read(ByteBuffer buff) {
        int l = buff.getInt();
        byte[] b = new byte[l];
        buff.get(b);
        return new BigInteger(b);
    }
}
