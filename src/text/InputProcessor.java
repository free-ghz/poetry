package text;

import model.Kernel;
import model.KernelSet;

import java.util.List;

public class InputProcessor {

    private final int kernelSize;

    public InputProcessor(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public KernelSet toKernelSet(List<String> source) {
        KernelSet kernelSet = new KernelSet(kernelSize);
        for (String line : source) {
            if (line.length() < kernelSize) continue;
            if (line.length() == kernelSize) {
                Kernel wholeLine = new Kernel(line);
                kernelSet.put(wholeLine);
                continue;
            }
            for (int i = 0; i < line.length() - kernelSize; i++) {
                String slice = line.substring(i, i + kernelSize);
                Kernel kernel = new Kernel(slice);
                kernelSet.put(kernel);
            }
        }
        System.out.println("KernelSet size: " + kernelSet.size());
        return kernelSet;
    }
}
