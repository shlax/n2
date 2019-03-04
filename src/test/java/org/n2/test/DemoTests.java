package org.n2.test;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.tx.VersionedValueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.n2.*;
import static org.n2.query.Filters.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DemoTests {

    @Test
    public void simple(){
        new File("test/data/simple").delete();

        Map<String, Object> data = new HashMap<>(){{
            put("id", 10);
            put("value", "File");
            put("menuItems", new ArrayList<>(){{
                add(new HashMap<>(){{
                    put("value", "New");
                    put("id", 11);
                }});
                add(new HashMap<>(){{
                    put("value", "Open");
                    put("id", 12);
                }});
                add(new HashMap<>(){{
                    put("value", "Close");
                    put("id", 13);
                }});
            }});
        }};

        MVStore.Builder sb = new MVStore.Builder().autoCommitDisabled().fileName("test/data/test");

        try(N2Database db = new N2Database(sb.open())){
            N2Store some = db.store("hello");

            try(N2Session ses = db.session()){
                N2SessionStore<N2Id, Object> st = ses.store(some);

                // save
                N2Id id = st.add(data).id();

                { // get by id
                    Object dt = st.get(id);
                    Assertions.assertEquals(dt, data);
                }

                { // search
                    Object dt = null;
                    Iterator<Map.Entry<N2Id, Object>> it = st.iterator();

                    while (it.hasNext()) {
                        Object val = it.next().getValue();
                        boolean hasOpen = map(val).test("menuItems", o ->
                                list(o).exists(o1 ->
                                        map(o1).test("value", (o2) ->
                                                eq(o2, "Open")
                                        )
                                )
                        );
                        if (hasOpen) dt = val;
                    }
                    Assertions.assertEquals(dt, data);
                }

                ses.commit();
            }
        }

    }

    public enum Window{
        title, name, width, height
    }

    @Test
    public void simpleN2Object(){
        new File("test/data/simpleN2Object").delete();
        MVStore.Builder sb = new MVStore.Builder().autoCommitDisabled().fileName("test/data/simpleN2Object");

        N2Config.BUilder b = new N2Config.BUilder();
        b.configure("hello").mapSupplier(() -> new MVMap.Builder()
                .keyType(N2IdDataType.INSTANCE)
                .valueType(new VersionedValueType(new N2ObjectDataType<>(Window.class, b.typeSerializer())))
            );

        try(N2Database db = new N2Database(sb.open(), b.build()) ) {
            N2Store some = db.store("hello");

            try (N2Session ses = db.session()) {
                N2SessionStore<N2Id, N2Object> st = ses.store(some);

                N2EnumObject<Window, Object> data = new N2EnumObject<>(Window.class);
                data.put(Window.title, "Sample Widget");
                data.put(Window.name, "main_window");
                data.put(Window.width, 500);
                data.put(Window.height, 300);

                // save
                N2Id id = st.add(data).id();

                { // get by id
                    Object dt = st.get(id);
                    Assertions.assertEquals(dt, data);
                }

                { // search
                    Object dt = null;
                    Iterator<Map.Entry<N2Id, N2Object>> it = st.iterator();

                    while (it.hasNext()) {
                        N2Object val = it.next().getValue();

                        if(eq(val.get(Window.width), 500)){
                            dt = val;
                        }
                    }

                    Assertions.assertEquals(dt, data);
                }

            }
        }

    }

}
