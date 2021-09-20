package model;

public class Letter {
    private final KernelSet possibleKernels;
    private final int kernelSize;
    private boolean isLocked = false;
    private boolean isCrystallized = false;
    private Kernel kernel;

    public Letter(KernelSet possibleKernels, int kernelSize) {
        this.possibleKernels = possibleKernels;
        this.kernelSize = kernelSize;
    }

    public void setNeighbour(int distance, Kernel neighbour) {
        if (isCrystallized) return;
        int overlapLength = kernelSize - Math.abs(distance);
        int neighbourStart = 0 - distance;
        if (neighbourStart < 0) neighbourStart = 0;
        int localStart = distance;
        if (localStart < 0) localStart = 0;
        String overlap = neighbour.getValue().substring(neighbourStart, neighbourStart + overlapLength);
        possibleKernels.cull(overlap, localStart);
    }

    public long getPossibleKernelsSize() {
        return possibleKernels.size();
    }

    public void crystallize() {
        isCrystallized = true;
        if (getPossibleKernelsSize() == 0) { // should speed things up perhaps
            isLocked = true;
            return;
        }
        kernel = possibleKernels.crystallize();
        if (kernel == null) isLocked = true;
    }

    public Kernel getKernel() {
        return kernel;
    }

    public String getLetter() {
        if (kernel == null) return null;
        return kernel.getValue().substring(kernelSize / 2, (kernelSize / 2) + 1);
    }

    public boolean isCrystallized() {
        return isCrystallized;
    }

    public boolean isLocked() {
        return isLocked;
    }

}
/*
        +---+---+---+          +---+---+---+---+---+
        |   |   |   |          | 5 | 4 | 3 | 2 | 1 |
    +---+---+---+---+  +---+---+---+---+---+---+---+
    |   |   |   |      |-2 |-1 | 0 | 1 | 2 |          frozenSlice = kernelSize - distance
    +---+---+---+      +---+---+---+---+---+

    length = 5 - 2 = 3
    frozenSliceStart = 0
    localstart = 5 - 2 - 1 = 2

    lemngth = 5 - 1 = 4
    fss = 0 - 1 => 0
    localStart = 5 - 1 - 1 = 3


 */