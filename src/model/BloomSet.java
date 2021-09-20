package model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class BloomSet {

    private static final int MAX_BITS = 2494090;
    private static final BigInteger ROOF = BigInteger.TWO.multiply(BigInteger.valueOf(MAX_BITS)); // wowee

    private final List<HashAlg> hashAlgorithms = List.of(
            num -> num,
            num -> num.shiftLeft(3).mod(BigInteger.TWO),
            num -> num.pow(10),
            num -> num.multiply(BigInteger.valueOf(1300)).divide(BigInteger.valueOf(2)),
            num -> num.pow(19243)
    );

    private BigInteger bits = BigInteger.ZERO;

    public BloomSet() {

    }


    public int size() {
        return 0;
    }


    public boolean isEmpty() {
        return false;
    }


    public boolean contains(Kernel kernel) {
        BigInteger bitCopy = bits.multiply(BigInteger.ONE);
        var ref = new Object() {
            BigInteger accumulator = BigInteger.ZERO;
        };
        hashAlgorithms.forEach(hashAlg -> {
            BigInteger hashcode = hashAlg.hash(BigInteger.valueOf(kernel.hashCode()));
            hashcode = hashcode.mod(ROOF);
            ref.accumulator = ref.accumulator.or(hashcode);
        });
        return bits.and(ref.accumulator).equals(ref.accumulator);
    }


    public void add(Kernel kernel) {
        hashAlgorithms.forEach(hashAlg -> {
            BigInteger hashcode = hashAlg.hash(BigInteger.valueOf(kernel.hashCode()));
            hashcode = hashcode.mod(ROOF);
            bits = bits.or(hashcode);
        });
    }


    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends Kernel> c) {
        return false;
    }

    private interface HashAlg {
        BigInteger hash(BigInteger value);
    }
}
