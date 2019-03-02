package org.n2.data.collections;

import org.n2.data.Serializer;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class ByteBufferMap extends AbstractMap {

    final Serializer typeSerializer;
    final ByteBuffer buff;
    final int off;

    public ByteBufferMap(Serializer typeSerializer, ByteBuffer buff, int off) {
        this.typeSerializer = typeSerializer;
        this.buff = buff;
        this.off = off;
    }

    ByteBufferEntry[] values = null;

    class ByteBufferEntry implements Entry{
        final int index;

        ByteBufferEntry(int index) {
            this.index = index;
        }

        boolean initKey = false;
        Object key = null;

        @Override
        public Object getKey() {
            if(!initKey){
                initKey = true;

                int off4 = off + 4;

                buff.position(off4 + (8 * index));
                int pos = buff.getInt();

                buff.position(off4 + pos);
                key = typeSerializer.read(buff);
            }
            return key;
        }

        boolean initValue = false;
        Object value = null;

        @Override
        public Object getValue() {
            if(!initValue){
                initValue = true;

                int off4 = off + 4;

                buff.position(off4 + (8 * index + 4));
                int pos = buff.getInt();

                buff.position(off4 + pos);
                value = typeSerializer.read(buff);
            }
            return value;
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }
    }

    ByteBufferEntry get(int index) {
        if(values == null){
            values = new ByteBufferEntry[size()];
        }

        if(values[index] == null){
            values[index] = new ByteBufferEntry(index);
        }

        return values[index];
    }

    ByteBufferEntrySet entrySet;

    class ByteBufferEntrySet extends AbstractSet<ByteBufferEntry>{

        @Override
        public Iterator<ByteBufferEntry> iterator() {
            final int s = size();
            return new Iterator<>() {
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < s;
                }

                @Override
                public ByteBufferEntry next() {
                    return  get(i++);
                }
            };
        }

        @Override
        public int size() {
            return ByteBufferMap.this.size();
        }
    }

    @Override
    public Set<ByteBufferEntry> entrySet() {
        if(entrySet == null) entrySet = new ByteBufferEntrySet();
        return entrySet;
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
