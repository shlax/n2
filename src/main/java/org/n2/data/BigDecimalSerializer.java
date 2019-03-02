package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigDecimalSerializer implements Serializer{
    public static final BigDecimalSerializer INSTANCE = new BigDecimalSerializer();

    @Override
    public void write(WriteBuffer buff, Object obj) {
        BigDecimal n = (BigDecimal)obj;
        buff.putInt(n.scale());
        byte[] b = n.unscaledValue().toByteArray();
        buff.putInt(b.length);
        buff.put(b);
    }

    @Override
    public BigDecimal read(ByteBuffer buff) {
        int scale = buff.getInt();
        int len = buff.getInt();
        byte[] b = new byte[len];
        buff.get(b);
        return new BigDecimal(new BigInteger(b), scale);
    }
}
