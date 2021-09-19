import io.FolderReader;
import io.TextNormalizer;
import model.KernelSet;
import model.Sentence;
import text.InputProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int KERNEL_SIZE = 7;

    public static void main(String[] args) throws IOException {
        // String rootFolder = "/Users/mushra/temp/text";
        String rootFolder = "/Users/mushra/nextcloud/alltext";
        FolderReader folderReader = new FolderReader(rootFolder, false);
        List<String> strings = folderReader.readContents();
        System.out.println("Strings: " + strings.size());

        TextNormalizer textNormalizer = new TextNormalizer();
        List<String> normalizedStrings = textNormalizer.transform(strings);
        System.out.println("Normalized strings: " + normalizedStrings.size());

        InputProcessor inputProcessor = new InputProcessor(KERNEL_SIZE);
        KernelSet kernelSet = inputProcessor.toKernelSet(normalizedStrings);

        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Sentence sentence = new Sentence(40, KERNEL_SIZE, kernelSet);
            sentences.add(sentence.generate());
            // sentence.printKernels();
        }
        for (String sentence : sentences) {
            System.err.println(sentence);
        }
    }
}
