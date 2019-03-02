package org.n2;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.tx.VersionedValueType;
import org.h2.value.VersionedValue;
import org.n2.data.TypeSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class N2Config {

    static class LabelConfig{
        final IdGenerator generator;
        final Supplier<MVMap.Builder<Comparable, VersionedValue>> mapSupplier;

        LabelConfig(IdGenerator generator, Supplier<MVMap.Builder<Comparable, VersionedValue>> mapSupplier) {
            this.generator = generator;
            this.mapSupplier = mapSupplier;
        }
    }

    final HashMap<String, LabelConfig> configs;
    final LabelConfig def;

    N2Config(HashMap<String, LabelConfig> configs, LabelConfig def) {
        this.def = def;
        this.configs = configs;
    }

    LabelConfig config(String label){
        LabelConfig c = configs.get(label);
        if(c == null) return def;
        return c;
    }

    public static class BUilder {

        final HashMap<String, StoreConfig> configs = new HashMap<>();

        public class StoreConfig<K extends Comparable> {
            IdGenerator generator;

            public StoreConfig<K> generator(IdGenerator<K> gen) {
                this.generator = gen;
                return this;
            }

            Supplier mapSupplier;

            public StoreConfig<K> mapSupplier(Supplier<MVMap.BasicBuilder> s) {
                this.mapSupplier = s;
                return this;
            }

            public <K extends Comparable> StoreConfig<K> configure(String label) {
                return BUilder.this.configure(label);
            }

            public N2Config build(){
                return BUilder.this.build();
            }

            boolean useGenerator = true;

            public StoreConfig<K> generator(boolean gen) {
                useGenerator = gen;
                return this;
            }

            LabelConfig labelConfig(){
                if(generator == null && useGenerator) generator = n2IdGenerator();
                if(mapSupplier == null) mapSupplier = n2IdSupplier();
                return new LabelConfig(generator, mapSupplier);
            }

        }

        public <K extends Comparable> StoreConfig<K> configure(String label) {
            StoreConfig x = configs.get(label);
            if (x == null) {
                x = new StoreConfig();
                configs.put(label, x);
            }
            return x;
        }

        IdGenerator generator;

        public BUilder generator(IdGenerator gen) {
            this.generator = gen;
            return this;
        }

        Supplier mapSupplier;

        public BUilder mapSupplier(Supplier<MVMap.Builder<Comparable, VersionedValue>> s) {
            this.mapSupplier = s;
            return this;
        }

        boolean useGenerator = true;

        public BUilder generator(boolean gen) {
            this.useGenerator = gen;
            return this;
        }

        public N2Config build(){
            HashMap<String, LabelConfig> m = new HashMap<>(configs.size());
            for(Map.Entry<String, StoreConfig> e : configs.entrySet()) m.put(e.getKey(), e.getValue().labelConfig());

            if(generator == null && useGenerator) generator = n2IdGenerator();
            if(mapSupplier == null) mapSupplier = n2IdSupplier();
            return new N2Config(m, new LabelConfig(generator, mapSupplier));
        }

        int memory = 1024;
        public BUilder memory(int mem) {
            this.memory = mem;
            return this;
        }

        TypeSerializer typeSerializer;
        public TypeSerializer typeSerializer(){
            if(typeSerializer == null) typeSerializer = new TypeSerializer();
            return typeSerializer;
        }

        N2DataType serializer;
        public N2DataType serializer(){
            if(serializer == null) serializer = new N2DataType(memory, typeSerializer());
            return serializer;
        }

        VersionedValueType versionedSerializer;
        public VersionedValueType versionedSerializer(){
            if(versionedSerializer == null) versionedSerializer = new VersionedValueType(serializer());
            return versionedSerializer;
        }

        N2IdGenerator n2IdGenerator;
        public N2IdGenerator n2IdGenerator(){
            if(n2IdGenerator == null) n2IdGenerator = new N2IdGenerator();
            return n2IdGenerator;
        }

        Supplier<MVMap.Builder<N2Id, VersionedValue>> n2IdSupplier;
        Supplier<MVMap.Builder<N2Id, VersionedValue>> n2IdSupplier(){
            if(n2IdSupplier == null) n2IdSupplier = () -> new MVMap.Builder<N2Id, VersionedValue>()
                    .keyType(N2IdDataType.INSTANCE)
                    .valueType(versionedSerializer());
            return n2IdSupplier;
        }

    }

}
