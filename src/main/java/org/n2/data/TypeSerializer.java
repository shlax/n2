package org.n2.data;

import org.h2.mvstore.WriteBuffer;
import org.n2.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;

public class TypeSerializer implements Serializer{

    private final BooleanSerializer boolSerializer = BooleanSerializer.INSTANCE;
    private final CharSerializer charSerializer = CharSerializer.INSTANCE;

    private final StringSerializer stringSerializer = StringSerializer.INSTANCE;

    private final ByteSerializer byteSerializer = ByteSerializer.INSTANCE;
    private final ShortSerializer shortSerializer = ShortSerializer.INSTANCE;
    private final IntSerializer intSerializer = IntSerializer.INSTANCE;
    private final LongSerializer longSerializer = LongSerializer.INSTANCE;

    private final FloatSerializer floatSerializer = FloatSerializer.INSTANCE;
    private final DoubleSerializer doubleSerializer = DoubleSerializer.INSTANCE;

    private final BigIntegerSerializer bigIntegerSerializer = BigIntegerSerializer.INSTANCE;
    private final BigDecimalSerializer bigDecimalSerializer = BigDecimalSerializer.INSTANCE;

    private final ListSerializer listSerializer = new ListSerializer(this);
    private final SetSerializer setSerializer = new SetSerializer(this);

    private final MapSerializer mapSerializer = new MapSerializer(this);

    private final DateSerializer dateSerializer = DateSerializer.INSTANCE;

    private final N2IdDataType n2IdSerializer = N2IdDataType.INSTANCE;
    private final N2RefIdSerializer n2RefIdSerializer = new N2RefIdSerializer(this);
    private final N2ObjectSerializer n2ObjectSerializer = new N2ObjectSerializer(this);

    private final Serializer[] serializers = new Serializer[]{
            boolSerializer, charSerializer, stringSerializer,
            byteSerializer, shortSerializer, intSerializer, longSerializer,
            floatSerializer, doubleSerializer,
            bigIntegerSerializer, bigDecimalSerializer,
            listSerializer, setSerializer, mapSerializer,
            dateSerializer,
            n2IdSerializer, n2RefIdSerializer, n2ObjectSerializer
    };

    @Override
    public void write(WriteBuffer buff, Object obj) {
        if(obj == null){
            buff.put((byte)-1);

        }else if(obj instanceof Boolean){
            buff.put((byte)0);
            boolSerializer.write(buff, obj);
        }else if(obj instanceof Character) {
            buff.put((byte) 1);
            charSerializer.write(buff, obj);

        }else if(obj instanceof String) {
            buff.put((byte) 2);
            stringSerializer.write(buff, obj);

        }else if(obj instanceof Number){
            if(obj instanceof Byte) {
                buff.put((byte) 3);
                byteSerializer.write(buff, obj);
            }else if(obj instanceof Short){
                buff.put((byte) 4);
                shortSerializer.write(buff, obj);
            }else if(obj instanceof Integer){
                buff.put((byte) 5);
                intSerializer.write(buff, obj);
            }else if(obj instanceof Long){
                buff.put((byte) 6);
                longSerializer.write(buff, obj);

            }else if(obj instanceof Float){
                buff.put((byte) 7);
                floatSerializer.write(buff, obj);
            }else if(obj instanceof Double){
                buff.put((byte) 8);
                doubleSerializer.write(buff, obj);

            }else if(obj instanceof BigInteger){
                buff.put((byte) 9);
                bigIntegerSerializer.write(buff, obj);
            }else if(obj instanceof BigDecimal){
                buff.put((byte) 10);
                bigDecimalSerializer.write(buff, obj);

            }else{
                throw new IllegalArgumentException(obj.getClass()+" is unsupported Number");
            }
        }else if(obj instanceof Collection){
            if(obj instanceof List) {
                buff.put((byte) 11);
                listSerializer.write(buff, obj);
            }else if(obj instanceof Set) {
                buff.put((byte) 12);
                setSerializer.write(buff, obj);
            }else{
                throw new IllegalArgumentException(obj.getClass()+" is unsupported Collection");
            }
        }else if(obj instanceof Map){
            buff.put((byte) 13);
            mapSerializer.write(buff, obj);

        }else if(obj instanceof Date){
            buff.put((byte) 14);
            dateSerializer.write(buff, obj);

        }else if(obj instanceof N2Id){
            buff.put((byte) 15);
            n2IdSerializer.write(buff, obj);
        }else if(obj instanceof N2Link){
            buff.put((byte) 16);
            n2RefIdSerializer.write(buff, obj);
        }else if(obj instanceof N2Object){
            buff.put((byte) 17);
            n2ObjectSerializer.write(buff, obj);

        }else{
            throw new IllegalArgumentException(obj.getClass()+" is unsupported");
        }
    }

    @Override
    public Object read(ByteBuffer buff) {
        int b = buff.get();
        if(b == -1) return null;
        return serializers[b].read(buff);
    }

}
