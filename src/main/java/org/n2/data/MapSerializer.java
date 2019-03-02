package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.data.collections.ByteBufferMap;

import java.nio.ByteBuffer;
import java.util.Map;

public class MapSerializer implements Serializer{
    final Serializer typeSerializer;

    public MapSerializer(Serializer typeSerializer) {
        this.typeSerializer = typeSerializer;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        Map<?, ?> m = (Map)obj;
        ByteBuffer bb = buff.getBuffer();

        int p0 = buff.position();
        buff.put(new byte[4]);

        int size = m.size();
        buff.putInt(m.size());

        int p1 = bb.position();
        buff.put(new byte[size * 8]);

        int i = 0;
        int[] pos = new int[size * 2];

        for(Map.Entry o : m.entrySet()){
            pos[i*2] = bb.position() - p1;
            typeSerializer.write(buff, o.getKey());

            pos[i*2 + 1] = bb.position() - p1;
            typeSerializer.write(buff, o.getValue());

            i++;
        }

        int p2 = bb.position();

        bb.position(p0);
        bb.putInt(p2 - p0);

        bb.position(p1);
        for(int o : pos) bb.putInt(o);
        bb.position(p2);
    }

    @Override
    public ByteBufferMap read(ByteBuffer buff) {
        int off = buff.position();
        int skip = buff.getInt();
        ByteBufferMap m = new ByteBufferMap(typeSerializer, buff, off+4);
        buff.position(off + skip);
        return m;
    }
}
