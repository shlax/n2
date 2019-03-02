package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.N2Link;

import java.nio.ByteBuffer;

public class N2RefIdSerializer implements Serializer{
    final StringSerializer stringSerializer = StringSerializer.INSTANCE;
    final Serializer typeSerializer;

    public N2RefIdSerializer(Serializer typeSerializer) {
        this.typeSerializer = typeSerializer;
    }

    @Override
    public void write(WriteBuffer buff, Object obj) {
        N2Link id = (N2Link)obj;
        stringSerializer.write(buff, id.label());
        typeSerializer.write(buff, id.id());
    }

    @Override
    public N2Link read(ByteBuffer buff) {
        String l = stringSerializer.read(buff);
        Comparable c = (Comparable) typeSerializer.read(buff);
        return new N2Link(l, c);
    }
}
