package org.n2;

import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;

import java.nio.ByteBuffer;

public abstract class AbstractDataType implements DataType {

    @Override
    public void read(ByteBuffer buff, Object[] obj, int len, boolean key) {
        for (int i = 0; i < len; i++) {
            obj[i] = read(buff);
        }
    }

    @Override
    public void write(WriteBuffer buff, Object[] obj, int len, boolean key) {
        for (int i = 0; i < len; i++) {
            write(buff, obj[i]);
        }
    }

}
