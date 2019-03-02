package org.n2;

import java.util.Objects;

public class N2RefId<T> {
    final String label;
    final T id;

    public N2RefId(String label, T id) {
        this.label = label;
        this.id = id;
    }

    public String label(){
        return label;
    }

    public T id(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        N2RefId<?> n2RefId = (N2RefId<?>) o;
        return label.equals(n2RefId.label) &&
                id.equals(n2RefId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, id);
    }
}
