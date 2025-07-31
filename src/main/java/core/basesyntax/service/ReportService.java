package core.basesyntax.service;

import java.util.Map;

public interface ReportService {
    void write(Map<String, Integer> report, String filePath);
}
