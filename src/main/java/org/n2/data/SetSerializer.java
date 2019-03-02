package org.n2.data;

import org.n2.data.collections.ByteBufferSet;

import java.nio.ByteBuffer;

public class SetSerializer extends CollectionSerializer {

    public SetSerializer(Serializer typeSerializer) {
        super(typeSerializer);
    }

    @Override
    ByteBufferSet create(ByteBuffer buff, int off) {
        return new ByteBufferSet(typeSerializer, buff, off);
    }
}
