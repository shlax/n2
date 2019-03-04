package org.n2;

import org.h2.mvstore.MVStore;
import org.h2.mvstore.tx.TransactionStore;
import java.util.HashMap;

public class N2Database implements AutoCloseable {

    final MVStore mvStore;
    final TransactionStore transactionStore;

    final N2Config config;

    public N2Database(MVStore mvStore){
        this(mvStore, new N2Config.BUilder().build());
    }

    public N2Database(MVStore mvStore, N2Config config){
        this.mvStore = mvStore;
        this.config = config;
        transactionStore = new TransactionStore(this.mvStore);
    }

    final HashMap<String, N2Store> stores = new HashMap<>();

    public N2Store store(String label){
        N2Store l;
        synchronized (stores){
            l = stores.get(label);
            if(l == null){
                N2Config.LabelConfig conf = config.config(label);
                l = new N2Store(label, conf.generator,  mvStore.openMap(label, conf.mapSupplier.get()));
                stores.put(label, l);
            }
        }
        return l;
    }

    public N2Session session(){
        return new N2Session(this, transactionStore.begin());
    }

    @Override
    public void close(){
        try {
            transactionStore.close();
        }finally {
            mvStore.close();
        }
    }
}
