import io.FolderReader;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String rootFolder = "/Users/mushra/nextcloud/alltext";
        FolderReader folderReader = new FolderReader(rootFolder, false);
        List<String> strings = folderReader.readContents();
        System.out.println(strings.size());
        strings.forEach(string -> {
            System.out.println((string.length() > 30 ? string.substring(0, 30) : string).replace('\n', ' '));
        });
    }
}
