package model;

import java.util.*;

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

    // _unique_ keys rather than total keys
    public int size() {
        return map.size();
    }

    public KernelSet copy() {
        KernelSet copy = new KernelSet(kernelSize);
        for (Kernel k : map.keySet()) {
            Long localCount = get(k);
            copy.overrideCount(k, localCount);
        }
        return copy;
    }

    private void overrideCount(Kernel kernel, Long count) {
        map.put(kernel, count);
    }

    public void cull(String shouldContain, int pos) {
        new HashSet<>(map.keySet()).stream()
                .filter(kernel -> !kernel.getValue().startsWith(shouldContain, pos))
                .forEach(this::remove);
    }

    public void remove(Kernel kernel) {
        map.remove(kernel);
    }

    public Kernel crystallize() {
        List<Kernel> allWithDoubles = new ArrayList<>();
        map.keySet().forEach(key -> {
            for (int i = 0; i < map.get(key); i++) {
                allWithDoubles.add(key);
            }
        });
        if (allWithDoubles.isEmpty()) return null;
        Random random = new Random();
        int index = random.nextInt(allWithDoubles.size());
        return allWithDoubles.get(index);
    }
}
