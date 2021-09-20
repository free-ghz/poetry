package model;

import java.util.LinkedList;

// not really a stream LOL
public class Stream {
    private final KernelSet allKernels;
    private final int kernelSize;
    private final int visibleBufferSize;
    private final int totalBufferSize;
    private final LinkedList<Letter> buffer = new LinkedList<>();


    public Stream(KernelSet allKernels, int kernelSize) {
        this.allKernels = allKernels;
        this.kernelSize = kernelSize;
        this.visibleBufferSize = kernelSize;
        this.totalBufferSize = kernelSize * 2;

        for (int i = 0; i < totalBufferSize; i++) {
            buffer.add(new Letter(allKernels.copy(), kernelSize));
        }
    }

    public String produce() {
        do {
            int chosenIndex = chooseIndex();
            crystallize(chosenIndex);
        } while (!canAdvance());
        return advance();
    }

    private int chooseIndex() {
        long bestPossibilities = Integer.MAX_VALUE;
        int bestIndex = 0;
        for (int i = 0; i < visibleBufferSize; i++) {
            Letter scrutiny = buffer.get(i);
            if (scrutiny.isCrystallized()) continue;
            if (scrutiny.isLocked()) continue;
            if (scrutiny.getPossibleKernelsSize() < bestPossibilities) {
                bestPossibilities = scrutiny.getPossibleKernelsSize();
                bestIndex = i;
            }
        }
        // index 0 shouldnt be crystallized or locked here, in theory.
        // it should automatically be the chosen one if they are all equivalently good
        // maybe we want to formalize that?
        return bestIndex;
    }

    private void crystallize(int index) {
        Letter chosen = buffer.get(index);
        chosen.crystallize();

        if (chosen.isLocked()) {
            // sometimes they dont survive the procedure. not so much to do about that.
            return;
        }

        // reflect in our surroundings what has changed in us
        int half = kernelSize / 2;
        for (int i = 0; i < kernelSize; i++) {
            int relativeIndex = -half + i;
            if (relativeIndex == 0) continue;
            int absoluteIndex = index + relativeIndex;
            if (absoluteIndex < 0) continue;
            if (absoluteIndex >= totalBufferSize) continue;

            Letter neighbour = buffer.get(absoluteIndex);
            if (neighbour.isLocked() || neighbour.isCrystallized()) continue;

            neighbour.setNeighbour(-relativeIndex, chosen.getKernel());
        }
    }

    private boolean canAdvance() {
        Letter first = buffer.get(0);
        return (first.isLocked() || first.isCrystallized());
    }

    private String advance() {
        int howMuchToCull = 1; // min 1
        for (int i = howMuchToCull; i < visibleBufferSize; i++) {
            Letter scrutiny = buffer.get(i);
            if (scrutiny.isCrystallized() || scrutiny.isLocked()) {
                howMuchToCull += 1;
                continue;
            }
            break;
        }

        StringBuilder cull = new StringBuilder();
        for (int i = 0; i < howMuchToCull; i++) {
            Letter subject = buffer.get(i);
            if (subject.isLocked()) {
                cull.append("█");
            } else {
                cull.append(subject.getLetter());
            }
        }

        // kill old, add fresh
        for (int i = 0; i < howMuchToCull; i++) {
            buffer.removeFirst();
            buffer.add(new Letter(allKernels.copy(), kernelSize));
        }

        return cull.toString();
    }


}
