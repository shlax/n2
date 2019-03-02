package org.n2;

import org.h2.mvstore.tx.Transaction;
import java.util.HashMap;

public class N2Session implements AutoCloseable {

    final N2Database database;
    final Transaction transaction;

    N2Session(N2Database database, Transaction transaction) {
        this.database = database;
        this.transaction = transaction;
    }

    final HashMap<N2Store, N2SessionStore> maps = new HashMap<>();

    public N2SessionStore store(N2Store label){
        N2SessionStore v = maps.get(label);
        if(v == null){
            v = new N2SessionStore(label, transaction.openMap(label.store));
            maps.put(label, v);
        }
        return v;
    }

    boolean committed = false;

    public void commit(){
        committed = true;
    }

    @Override
    public void close(){
        if(committed) transaction.commit();
        else transaction.rollback();
    }
}