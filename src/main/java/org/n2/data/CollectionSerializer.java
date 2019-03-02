package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;
import java.util.Collection;

abstract class CollectionSerializer implements Serializer {
    final Serializer typeSerializer;

    public CollectionSerializer(Serializer typeSerializer) {
        this.typeSerializer = typeSerializer;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        Collection<?> l = (Collection) obj;
        ByteBuffer bb = buff.getBuffer();

        int p0 = bb.position();
        buff.put(new byte[4]);

        int size = l.size();
        buff.putInt(size);

        int p1 = bb.position();
        buff.put(new byte[size * 4]);

        int i = 0;
        int[] pos = new int[size];

        for(Object o : l){
            pos[i] = bb.position() - p1;
            typeSerializer.write(buff, o);
            i++;
        }

        int p2 = bb.position();

        bb.position(p0);
        bb.putInt(p2 - p0);

        bb.position(p1);
        for(int o : pos) bb.putInt(o);
        bb.position(p2);
    }

    abstract Collection create(ByteBuffer buff, int off);

    @Override
    public Collection read(ByteBuffer buff) {
        int off = buff.position();
        int skip = buff.getInt();
        Collection c = create(buff, off + 4);
        buff.position(off + skip);
        return c;
    }

}
