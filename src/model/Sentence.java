package model;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

    private final List<Letter> letters;
    private final int length;
    private final int kernelSize;

    public Sentence(int length, int kernelSize, KernelSet allKernels) {
        this.kernelSize = kernelSize;
        this.length = length;
        letters = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            letters.add(new Letter(allKernels.copy(), kernelSize));
        }
    }

    public String generate() {
        System.out.println(this);

        letters.get(0).crystallize();
        System.out.println(this);

        for (int i = 0; i < kernelSize / 2; i++) {
            letters.get(i + 1).setNeighbour(-(kernelSize / 2), letters.get(0).getKernel());
        }

        // first letter is set, loop through rest
        for (int finished = 1; finished < length; finished++) {
            System.out.println(this);

            // find next to crystallize
            int chosenIndex = -1;
            long minPossibilities = Integer.MAX_VALUE;
            for (int i = 0; i < letters.size(); i++) {
                Letter scrutiny = letters.get(i);
                if (scrutiny.isCrystallized()) continue;
                if (scrutiny.isLocked()) continue;
                if (scrutiny.getPossibleKernelsSize() < minPossibilities) {
                    minPossibilities = scrutiny.getPossibleKernelsSize();
                    chosenIndex = i;
                }
            }
            if (chosenIndex == -1) break; // well, i dont know...

            Letter chosen = letters.get(chosenIndex);
            chosen.crystallize();
            System.out.println(this);
            if (chosen.isLocked()) continue;

            // update neighbours
            for (int i = 0; i < kernelSize; i++) {
                int neighbourOffset = ((kernelSize / 2) + 1) - kernelSize;
                int neighbourIndex = chosenIndex + neighbourOffset;
                if (neighbourIndex < 0) continue;
                if (neighbourIndex >= length) continue;
                Letter neighbour = letters.get(neighbourIndex);
                if (!neighbour.isLocked()) neighbour.setNeighbour(neighbourOffset, chosen.getKernel());
            }
        }
        System.out.println(this);
        return toString();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Letter l : letters) {
            if (l.isLocked()) {
                s.append("█");
            } else if (l.isCrystallized()) {
                s.append(l.getLetter());
            } else {
                s.append("_");
            }
        }
        return s.toString();
    }
}
