package org.n2;

public class N2IdGenerator implements IdGenerator<N2Id>{

    long time = System.currentTimeMillis();
    int count = 0;

    @Override
    public N2Id generate(){
        long t; int c;
        synchronized (this){
            t = time; c = ++count;
            if(c == Integer.MAX_VALUE){
                time++;
                c = count = 1;
                t = time;
            }
        }
        return new N2Id(t, c);
    }

}
