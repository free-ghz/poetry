package model;

import java.util.HashMap;

public class KernelSet {
    private final HashMap<Kernel, Long> map = new HashMap<>();
    private final int kernelSize;

    public KernelSet(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public void put(Kernel key) {
        if (map.containsKey(key)) {
            Long previousValue = map.get(key);
            map.replace(key, previousValue + 1);
        } else {
            map.put(key, 1L);
        }
    }

    public Long get(Kernel key) {
        return map.get(key);
    }

    public int size() {
        return map.size();
    }
}
