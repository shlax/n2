package org.n2;

import java.util.Objects;

public class N2Id implements Comparable<N2Id>{

    final long time;
    final int count;

    public N2Id(String id) {
        String[] p =id.split("-");
        time = Long.parseLong(p[0], Character.MAX_RADIX);
        count = Integer.parseInt(p[1], Character.MAX_RADIX);
    }

    N2Id(long time, int count) {
        this.time = time;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        N2Id n2Id = (N2Id) o;
        return time == n2Id.time &&
                count == n2Id.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, count);
    }

    @Override
    public int compareTo(N2Id o) {
        int c = Long.compare(time, o.time);
        if(c != 0) return c;
        return Integer.compare(count, o.count);
    }

    @Override
    public String toString() {
        return Long.toString(time, Character.MAX_RADIX)+"-"+Integer.toString(count, Character.MAX_RADIX);
    }
}
