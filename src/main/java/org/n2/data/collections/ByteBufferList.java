package org.n2.data.collections;

import org.n2.data.Serializer;

import java.nio.ByteBuffer;
import java.util.AbstractList;

public class ByteBufferList extends AbstractList {

    final Serializer typeSerializer;
    final ByteBuffer buff;
    final int off;

    public ByteBufferList(Serializer typeSerializer, ByteBuffer buff, int off) {
        this.typeSerializer = typeSerializer;
        this.buff = buff;
        this.off = off;
    }

    Object[] values = null;
    boolean[] init = null;

    @Override
    public Object get(int index) {
        if(init == null){
            int s = size();
            init = new boolean[s];
            values = new Object[s];
        }

        if(!init[index]){
            init[index] = true;

            int off4 = off + 4;

            buff.position(off4 + (4 * index));
            int pos = buff.getInt();

            buff.position(off4 + pos);
            values[index] = typeSerializer.read(buff);
        }

        return values[index];
    }

    int size = -1;

    @Override
    public int size() {
        if(size == -1){
            size = buff.position(off).getInt();
        }

        return size;
    }

}
