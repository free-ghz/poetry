package model;

public class Kernel {
    private final String value;
    private final int length;

    public Kernel(String value) {
        this.value = value;
        this.length = value.length();
    }
}
