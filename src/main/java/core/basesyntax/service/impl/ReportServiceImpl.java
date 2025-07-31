package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public void write(Map<String, Integer> report, String filePath) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        report.forEach((fruit, quantity) -> lines.add(fruit + COMMA + quantity));
        try {
            Files.write(Path.of(filePath), lines);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file: " + filePath, e);
        }
    }
}

