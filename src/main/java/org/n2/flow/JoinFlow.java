package org.n2.flow;

public class JoinFlow<U, V, K extends Comparable<K>, R> extends AbstractSource<R> {

    private final Join<U, V, K, R> join;

    private final Source<U> su;
    private final Source<V> sv;

    public JoinFlow(Source<U> su, Source<V> sv, Join<U, V, K, R> join) {
        this.join = join;
        this.su = su;
        this.sv = sv;

        su.to(this::setU);
        sv.to(this::setV);
    }

    private K ku = null;
    private K kv = null;

    private U du = null;
    private V dv = null;

    private boolean au = false;
    private boolean av = false;

    private void setU(U u){
        if(au) throw new IllegalStateException();
        ku = join.keyU(u);
        if(ku == null) throw new NullPointerException("key U is null");
        au = true;
        du = u;
    }

    private void setV(V v){
        if(av) throw new IllegalStateException();
        kv = join.keyV(v);
        if(kv == null) throw new NullPointerException("key V is null");
        av =  true;
        dv = v;
    }

    private boolean nextU = true;
    private boolean nextV = true;

    @Override
    public boolean next() {
        if(!nextU && !nextV) return false;

        while(nextU && !au) nextU = su.next();
        while(nextV && !av) nextV = sv.next();

        if(!nextU) ku = null;
        if(!nextV) kv = null;

        if(ku != null && kv != null) {
            int cmp = ku.compareTo(kv);
            if (cmp == 0) {
                join.add(du, dv, this::next);
                av = au = false;
                du = null;
                dv = null;
            } else if (cmp < 0) {
                join.add(du, null, this::next);
                au = false;
                du = null;
            } else {
                join.add(null, dv, this::next);
                av = false;
                dv = null;
            }
        }else if(ku != null){
            join.add(du, null, this::next);
            au = false;
            du = null;
        }else if(kv != null){
            join.add(null, dv, this::next);
            av = false;
            dv = null;
        }

        return nextU || nextV;
    }
}
