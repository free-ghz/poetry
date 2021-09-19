package model;

import java.util.Objects;

public class Kernel {
    private final String value;
    private final int length;

    public Kernel(String value) {
        this.value = value;
        this.length = value.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kernel kernel = (Kernel) o;
        return Objects.equals(value, kernel.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return value;
    }
}
