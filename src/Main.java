import io.FolderReader;
import io.TextNormalizer;
import model.KernelSet;
import text.InputProcessor;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final int KERNEL_SIZE = 5;

    public static void main(String[] args) throws IOException {
        String rootFolder = "/Users/mushra/nextcloud/alltext";
        FolderReader folderReader = new FolderReader(rootFolder, false);
        List<String> strings = folderReader.readContents();
        System.out.println("Strings: " + strings.size());

        TextNormalizer textNormalizer = new TextNormalizer();
        List<String> normalizedStrings = textNormalizer.transform(strings);
        System.out.println("Normalized strings: " + normalizedStrings.size());

        InputProcessor inputProcessor = new InputProcessor(KERNEL_SIZE);
        KernelSet kernelSet = inputProcessor.toKernelSet(normalizedStrings);

    }
}
