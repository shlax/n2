package org.n2;

import java.util.Objects;

public class N2Link<T> {
    final String label;
    final T id;

    public N2Link(String label, T id) {
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
        N2Link<?> n2IdLink = (N2Link<?>) o;
        return label.equals(n2IdLink.label) &&
                id.equals(n2IdLink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, id);
    }
}
