package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.N2Object;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class N2ObjectSerializer implements Serializer {
    final Serializer typeSerializer;

    public N2ObjectSerializer(Serializer typeSerializer) {
        this.typeSerializer = typeSerializer;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        N2Object o = (N2Object)obj;
        ByteBuffer bb = buff.getBuffer();

        int p0 = bb.position();
        buff.put(new byte[4]);

        byte[] nm = o.enumClass().getName().getBytes(StandardCharsets.UTF_8);
        buff.putInt(nm.length);
        buff.put(nm);

        Enum[] keys = o.keys();
        int size = keys.length;

        int p1 = bb.position();
        buff.put(new byte[size * 4]);
        int[] pos = new int[size];

        for(int i = 0; i < size; i++){
            Enum k = keys[i];
            Object x = o.get(k);
            if(x != null){
                pos[i] = bb.position() - p1;
                typeSerializer.write(k, buff, x);
            }else{
                pos[i] = -1;
            }
        }

        int p2 = bb.position();

        bb.position(p0);
        bb.putInt(p2 - p0);

        bb.position(p1);
        for(int k : pos) bb.putInt(k);
        bb.position(p2);
    }

    @Override
    public N2BufferObject read(ByteBuffer buff) {
        int off = buff.position();
        int skip = buff.getInt();
        N2BufferObject c = new N2BufferObject(typeSerializer,buff, off + 4);
        buff.position(off + skip);
        return c;
    }
}
