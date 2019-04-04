package org.n2;

import org.h2.mvstore.WriteBuffer;
import org.n2.data.TypeSerializer;

import java.nio.ByteBuffer;

public class N2DataType implements AbstractDataType{
    final TypeSerializer typeSerializer;

    public N2DataType(int memory, TypeSerializer typeSerializer) {
        this.typeSerializer = typeSerializer;
        this.memory = memory;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        ByteBuffer bb = buff.getBuffer();
        int p1 = bb.position();
        buff.put(new byte[4]);

        typeSerializer.write(buff, obj);
        int p2 = bb.position();

        bb.position(p1);
        bb.putInt(p2 - (p1 + 4));
        bb.position(p2);
    }

    @Override
    public Object read(ByteBuffer buff) {
        int size = buff.getInt();
        byte[] data = new byte[size];
        buff.get(data);
        return typeSerializer.read(ByteBuffer.wrap(data).asReadOnlyBuffer());
    }

    final int memory;

    @Override
    public int getMemory(Object obj) {
        return memory;
    }

    @Override
    public int compare(Object a, Object b) {
        return 0;
    }
}
