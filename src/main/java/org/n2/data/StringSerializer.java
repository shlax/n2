package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.AbstractDataType;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringSerializer extends AbstractDataType implements Serializer{
    public static final StringSerializer INSTANCE = new StringSerializer();

    @Override
    public int compare(Object a, Object b) {
        String x = (String)a;
        String y = (String)b;
        return x.compareTo(y);
    }

    @Override
    public int getMemory(Object obj) {
        String s = (String)obj;
        return 4 + s.length() * 2;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        String s = (String)obj;
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        buff.putInt(b.length);
        buff.put(b);
    }

    @Override
    public String read(ByteBuffer buff) {
        int len = buff.getInt();
        byte[] b = new byte[len];
        buff.get(b);
        return new String(b, StandardCharsets.UTF_8);
    }
}
