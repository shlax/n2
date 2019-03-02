package org.n2;

import org.h2.mvstore.WriteBuffer;
import org.n2.data.Serializer;

import java.nio.ByteBuffer;

public class N2ObjectDataType<T extends Enum<T>, V> extends AbstractDataType{
    final Serializer typeSerializer;
    final Class<T> enumClass;

    public N2ObjectDataType(Class<T> enumClass, Serializer typeSerializer) {
        this(enumClass, 1024, typeSerializer);
    }

    public N2ObjectDataType(Class<T> enumClass, int memory, Serializer typeSerializer) {
        this.enumClass = enumClass;
        this.typeSerializer = typeSerializer;
        this.memory = memory;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        N2Object o = (N2Object)obj;

        ByteBuffer bb = buff.getBuffer();
        int p0 = bb.position();
        buff.put(new byte[4]);

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

        bb.position(p1);
        for(int k : pos) bb.putInt(k);

        bb.position(p0);
        bb.putInt(p2 - (p0 + 4));
        bb.position(p2);
    }

    @Override
    public N2ObjectType<T, V> read(ByteBuffer buff) {
        int size = buff.getInt();
        byte[] data = new byte[size];
        buff.get(data);
        return new N2ObjectType(enumClass, ByteBuffer.wrap(data).asReadOnlyBuffer(), typeSerializer);
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
