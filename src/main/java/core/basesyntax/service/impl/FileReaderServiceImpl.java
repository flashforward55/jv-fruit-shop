package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements ReaderService {

    @Override
    public List<String> read(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        try {
            Path path = Path.of(filePath);
            List<String> lines = Files.readAllLines(path);

            if (lines.isEmpty()) {
                throw new RuntimeException("File is empty: " + filePath);
            }

            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + filePath, e);
        }
    }
}
