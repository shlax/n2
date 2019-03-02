package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;

public interface Serializer {

    default void write(Enum key, WriteBuffer buff, Object obj){
        write(buff, obj);
    }

    default Object read(Enum key, ByteBuffer buff){
        return read(buff);
    }

    void write(WriteBuffer buff, Object obj);

    Object read(ByteBuffer buff);
}
