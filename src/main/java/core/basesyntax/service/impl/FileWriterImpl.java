package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String report, String filePath) {
        try {
            Files.write(Path.of(filePath), List.of(report.split("\\n")));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + filePath, e);
        }
    }
}
