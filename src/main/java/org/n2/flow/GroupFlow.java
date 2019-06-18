package org.n2.flow;

import java.util.Objects;

public class GroupFlow <T, K, V> extends AbstractFlow<T, V>{

    final GroupBy<T, K, V> groupBy;

    public GroupFlow(Source<T> source, GroupBy<T, K, V> groupBy) {
        super(source);
        this.groupBy = groupBy;
    }

    @Override
    public boolean next() {
        boolean n = super.next();
        if(!n && key != null){
            groupBy.add(key, this::next);
            key = null;
        }
        return n;
    }

    K key = null;

    @Override
    public void accept(T u) {
        K k = groupBy.key(u);
        if(k == null) throw new NullPointerException("key is null");

        if(Objects.equals(k, key)){
            groupBy.update(u);
        }else{
            if(key != null) groupBy.add(key, this::next);
            groupBy.reset(u);
            key = k;
        }
    }
}
