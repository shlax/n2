package org.n2.query;

import org.n2.N2Link;
import org.n2.N2Session;

public class N2Join {

    final N2Session session;

    public N2Join(N2Session session) {
        this.session = session;
    }

    public <T> T load(Object o){
        if(!(o instanceof N2Link)) return null;
        N2Link l = (N2Link)o;

        return (T)session.store(l.label()).get(l.id());
    }

}
