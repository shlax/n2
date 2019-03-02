package org.n2.query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Compare {

    public static Integer compare(Object a, Object b){
        if(a instanceof Comparable && b instanceof Comparable){
            Integer res = comparable((Comparable)a, (Comparable)b);
            if(res != null) return res;
        }

        if(Objects.equals(a, b)) return 0;
        return null;
    }

    private static Integer comparable(Comparable a, Comparable b){
        if(a.getClass().equals(b.getClass())) return a.compareTo(b);

        if(a instanceof Number && b instanceof Number) return (number((Number)a, (Number)b));

        return null;
    }

    private static Integer number(Number a, Number b){
        if(a instanceof BigDecimal){
            if(b instanceof BigDecimal) return ((BigDecimal) a).compareTo((BigDecimal)b);
            if(b instanceof BigInteger) return ((BigDecimal) a).compareTo(new BigDecimal((BigInteger)b));
            if(b instanceof Long) return ((BigDecimal) a).compareTo(BigDecimal.valueOf((Long)b));
            if(b instanceof Integer) return ((BigDecimal) a).compareTo(BigDecimal.valueOf((Integer)b));
            if(b instanceof Short) return ((BigDecimal) a).compareTo(BigDecimal.valueOf((Short)b));
            if(b instanceof Byte) return ((BigDecimal) a).compareTo(BigDecimal.valueOf((Byte)b));

            return null;
        }

        if(b instanceof BigDecimal){
            if(a instanceof BigInteger) return new BigDecimal((BigInteger)a).compareTo((BigDecimal)b);
            if(a instanceof Long) return BigDecimal.valueOf((Long)a).compareTo((BigDecimal)b);
            if(a instanceof Integer) return BigDecimal.valueOf((Integer)a).compareTo((BigDecimal)b);
            if(a instanceof Short) return BigDecimal.valueOf((Short)a).compareTo((BigDecimal)b);
            if(a instanceof Byte) return BigDecimal.valueOf((Byte)a).compareTo((BigDecimal)b);

            return null;
        }

        if(a instanceof BigInteger){
            if(b instanceof BigInteger) return ((BigInteger) a).compareTo((BigInteger)b);
            if(b instanceof Long) return ((BigInteger) a).compareTo(BigInteger.valueOf((Long)b));
            if(b instanceof Integer) return ((BigInteger) a).compareTo(BigInteger.valueOf((Integer)b));
            if(b instanceof Short) return ((BigInteger) a).compareTo(BigInteger.valueOf((Short)b));
            if(b instanceof Byte) return ((BigInteger) a).compareTo(BigInteger.valueOf((Byte)b));

            return null;
        }

        if(b instanceof BigInteger){
            if(a instanceof Long) return BigInteger.valueOf((Long)a).compareTo((BigInteger)b);
            if(a instanceof Integer) return BigInteger.valueOf((Integer)a).compareTo((BigInteger)b);
            if(a instanceof Short) return BigInteger.valueOf((Short)a).compareTo((BigInteger)b);
            if(a instanceof Byte) return BigInteger.valueOf((Byte)a).compareTo((BigInteger)b);

            return null;
        }

        if(a instanceof Long){
            if(b instanceof Long) return ((Long) a).compareTo((Long)b);
            if(b instanceof Integer) return ((Long) a).compareTo(Long.valueOf((Integer)b));
            if(b instanceof Short) return ((Long) a).compareTo(Long.valueOf((Short)b));
            if(b instanceof Byte) return ((Long) a).compareTo(Long.valueOf((Byte)b));

            return null;
        }

        if(b instanceof Long){
            if(a instanceof Integer) return Long.valueOf((Integer)a).compareTo((Long)b);
            if(a instanceof Short) return Long.valueOf((Short)a).compareTo((Long)b);
            if(a instanceof Byte) return Long.valueOf((Byte)a).compareTo((Long)b);

            return null;
        }

        if(a instanceof Integer){
            if(b instanceof Integer) return ((Integer) a).compareTo((Integer)b);
            if(b instanceof Short) return ((Integer) a).compareTo(Integer.valueOf((Short)b));
            if(b instanceof Byte) return ((Integer) a).compareTo(Integer.valueOf((Byte)b));

            return null;
        }

        if(b instanceof Integer){
            if(a instanceof Short) return Integer.valueOf((Short)a).compareTo((Integer)b);
            if(a instanceof Byte) return Integer.valueOf((Byte)a).compareTo((Integer)b);

            return null;
        }

        if(a instanceof Short){
            if(b instanceof Short) return ((Short) a).compareTo((Short)b);
            if(b instanceof Byte) return ((Short) a).compareTo(Short.valueOf((Byte)b));

            return null;
        }

        if(b instanceof Short){
            if(a instanceof Byte) return Short.valueOf((Byte)a).compareTo((Short)b);

            return null;
        }

        if(b instanceof Byte){
            if(a instanceof Byte) return ((Byte)a).compareTo((Byte)b);

            return null;
        }

        if(a instanceof Float && b instanceof Float){
            return ((Float) a).compareTo((Float)b);
        }

        if(a instanceof Double && b instanceof Double){
            return ((Double) a).compareTo((Double)b);
        }

        return null;
    }

}
