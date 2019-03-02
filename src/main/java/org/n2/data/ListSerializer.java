package org.n2.data;

import org.n2.data.collections.ByteBufferList;

import java.nio.ByteBuffer;

public class ListSerializer extends CollectionSerializer{

    public ListSerializer(Serializer typeSerializer) {
        super(typeSerializer);
    }

    @Override
    ByteBufferList create(ByteBuffer buff, int off) {
        return new ByteBufferList(typeSerializer, buff, off);
    }
}
