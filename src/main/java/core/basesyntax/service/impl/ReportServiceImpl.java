package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    @Override
    public void write(Map<String, Integer> report, String filePath) {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        report.forEach((fruit, quantity) -> lines.add(fruit + "," + quantity));
        try {
            Files.write(Path.of(filePath), lines);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file: " + filePath, e);
        }
    }
}
