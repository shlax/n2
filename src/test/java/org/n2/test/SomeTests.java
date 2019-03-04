package org.n2.test;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.rtree.MVRTreeMap;
import org.h2.mvstore.rtree.SpatialKey;
import org.h2.mvstore.tx.VersionedValueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.n2.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

public class SomeTests {

    public enum SomeEnum {
        P1, P2, P3;
    }

    private static N2Database n2Database(String path){
        MVStore store = new MVStore.Builder().autoCommitDisabled().fileName(path).open();
        N2Config.BUilder b = new N2Config.BUilder();
        N2Config conf = b.configure("rTree").generator(false).mapSupplier(() -> new MVRTreeMap.Builder()
                        .valueType(b.versionedSerializer())
                        .dimensions(1))
            .configure("enum").mapSupplier(() -> new MVMap.Builder()
                .keyType(N2IdDataType.INSTANCE)
                .valueType(new VersionedValueType(new N2ObjectDataType<>(SomeEnum.class, b.typeSerializer())))
            ).build();
        return new N2Database(store, conf);
    }

    @Test
    public void test01(){
        new File("test/data/test").delete();

        try(N2Database db = n2Database("test/data/test")){
            N2Store some = db.store("some");

            N2Id id;

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(42).id();
                ses.commit();
            }

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                Iterator<Map.Entry<Comparable, Object>> i = st.iterator(null, null);
                while (i.hasNext()){
                    Map.Entry<Comparable, Object> e = i.next();

                    Assertions.assertEquals(e.getValue(), 42);
                }

                Object v = st.get(id);
                Assertions.assertEquals(v, 42);

                ses.commit();
            }
        }

    }

    @Test
    public void test02(){
        new File("test/data/testMap").delete();

        N2Id id;

        HashMap<String, BigDecimal> data = new HashMap<>();
        data.put("a", new BigDecimal(1));
        data.put("b", new BigDecimal(2));

        try(N2Database db = n2Database("test/data/testMap")) {
            N2Store some = db.store("some");

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(data).id();
                ses.commit();
            }
        }

        try(N2Database db = n2Database("test/data/testMap")){
            N2Store some = db.store("some");

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                Map v = (Map)st.get(id);
                Assertions.assertEquals(data, v);

                ses.commit();
            }
        }
    }

    @Test
    public void test03(){
        new File("test/data/testList").delete();

        N2Id id;

        ArrayList<Integer> data = new ArrayList<>();
        data.add(42);
        data.add(13);

        try(N2Database db = n2Database("test/data/testList")) {
            N2Store some = db.store("some");

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(data).id();
                ses.commit();
            }
        }

        try(N2Database db = n2Database("test/data/testList")){
            N2Store some = db.store("some");

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                List v = (List)st.get(id);
                Assertions.assertEquals(data, v);

                ses.commit();
            }
        }
    }

    @Test
    public void test04(){
        new File("test/data/testSet").delete();

        N2Id id;

        HashSet<Integer> data = new HashSet<>();
        data.add(8);
        data.add(7);

        try(N2Database db = n2Database("test/data/testSet")) {
            N2Store some = db.store("some");

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(data).id();
                ses.commit();
            }
        }

        try(N2Database db = n2Database("test/data/testSet")){
            N2Store some = db.store("some");

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                Set v = (Set)st.get(id);
                Assertions.assertEquals(data, v);

                ses.commit();
            }
        }
    }

    @Test
    public void test05(){
        new File("test/data/testSetTree").delete();

        try(N2Database db = n2Database("test/data/testSetTree")) {
            N2Store some = db.store("rTree");

            HashSet<Integer> data = new HashSet<>();
            data.add(8);
            data.add(7);

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                st.put(new SpatialKey(0, 1f, 3f), data);
                ses.commit();
            }
        }

    }

    @Test
    public void test06(){
        new File("test/data/someEnum").delete();

        N2Id id;

        N2EnumObject<SomeEnum, Object> obj = new N2EnumObject<>(SomeEnum.class);
        obj.put(SomeEnum.P2, 3.5);

        try(N2Database db = n2Database("test/data/someEnum")) {
            N2Store some = db.store("some");

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(obj).id();
                ses.commit();
            }
        }

        try(N2Database db = n2Database("test/data/someEnum")){
            N2Store some = db.store("some");

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                N2Object v = (N2Object)st.get(id);
                Assertions.assertEquals(obj, v);

                ses.commit();
            }
        }
    }

    @Test
    public void test07(){
        new File("test/data/someEnumTpe").delete();

        N2Id id;

        N2EnumObject<SomeEnum, Object> obj = new N2EnumObject<>(SomeEnum.class);
        obj.put(SomeEnum.P2, 2.7);

        try(N2Database db = n2Database("test/data/someEnumTpe")) {
            N2Store some = db.store("enum");

            try (N2Session ses = db.session()) {
                N2SessionStore st = ses.store(some);

                id = (N2Id) st.add(obj).id();
                ses.commit();
            }
        }

        try(N2Database db = n2Database("test/data/someEnumTpe")){
            N2Store some = db.store("enum");

            try(N2Session ses = db.session()){
                N2SessionStore st = ses.store(some);

                N2Object v = (N2Object)st.get(id);
                Assertions.assertEquals(obj, v);

                ses.commit();
            }
        }
    }


}
