package io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FolderReader {

    private final Path root;
    private final boolean recursive;

    private final String[] encodings = {
            "UTF-8",
            "Windows-1252",
            "ISO-8859-1"
    };

    public FolderReader(String path, boolean recursive) throws IOException {
        root = Path.of(path);
        if (!Files.exists(root) || !Files.isReadable(root)) { // what about execute?
            throw new IOException("Can't read " + path);
        }
        this.recursive = recursive;
    }

    public List<String> readContents() {
        List<Path> paths;
        if (Files.isDirectory(root)) {
            paths = enumerateFiles();
        } else {
            paths = List.of(root);
        }

        List<String> contents = new ArrayList<>();
        paths.forEach(path -> {
            for (String encoding : encodings) {
                try {
                    String content = Files.readString(path, Charset.forName(encoding));
                    contents.add(content);
                    break;
                } catch (MalformedInputException ignored) {
                } catch (IOException e) {
                    System.out.println("Couldn't read " + path + ": " + e.getMessage());
                    break;
                }
            }
        });

        return contents;
    }

    private List<Path> enumerateFiles() {
        List<Path> files = new ArrayList<>();
        enumerateFilesRecursive(root, files);
        return files;
    }

    private void enumerateFilesRecursive(Path currentFolder, List<Path> paths) {
        try {
            Files.list(currentFolder)
                    .filter(path -> !path.getFileName().toString().startsWith("."))
                    .filter(path -> !path.getFileName().toString().toLowerCase().endsWith(".png"))
                    .filter(Files::exists)
                    .filter(Files::isReadable) // what about execute?
                    .forEach(path -> {
                        if (Files.isDirectory(path) && recursive) {
                            enumerateFilesRecursive(path, paths);
                        } else if (Files.isRegularFile(path)) {
                            paths.add(path);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Couldn't list " + currentFolder + ": " + e.getMessage());
        }
    }
}
