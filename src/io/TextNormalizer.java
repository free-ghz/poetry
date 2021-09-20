package io;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// idea here is to bring text into some kind of similar format
public class TextNormalizer {

    public List<String> transform(List<String> lines) {
        return lines.stream()
                .map(this::transform)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<String> transform(String line) {
        line = removeMagicBytes(line);
        line = line.replaceAll("[\r\n]+", "\n");
        line = line.replaceAll("[\s\t\n]+", " ");
        String[] lines = line.split("\n");
        return Arrays.stream(lines)
                .map(String::strip)
                .filter(l -> !l.isBlank())
                // .map(l -> MagicBytes.LINE_BEGIN.code + l + MagicBytes.LINE_END.code)
                .toList();
    }

    public String removeMagicBytes(String subject) {
        for (MagicBytes magicByte : MagicBytes.values()) {
            subject = subject.replace(String.valueOf(magicByte.code), "");
        }
        return subject;
    }

    public enum MagicBytes {
        FILE_BEGIN('\0'),
        FILE_END('\1'),
        LINE_BEGIN('\2'),
        LINE_END('\3');

        public final char code;

        MagicBytes(char code) {
            this.code = code;
        }
    }
}
