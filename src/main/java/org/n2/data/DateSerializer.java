package org.n2.data;

import org.h2.mvstore.WriteBuffer;

import java.nio.ByteBuffer;
import java.util.Date;

public class DateSerializer implements Serializer {
    public static final DateSerializer INSTANCE = new DateSerializer();

    @Override
    public void write(WriteBuffer buff, Object obj) {
        Date d = (Date)obj;
        buff.putLong(d.getTime());
    }

    @Override
    public Date read(ByteBuffer buff) {
        return new Date(buff.getLong());
    }
}
