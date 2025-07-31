package core.basesyntax.service.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvParserServiceImpl implements ParserService {
    private static final int EXPECTED_COLUMNS = 3;
    private static final String CSV_DELIMITER = ",";

    @Override
    public List<Transaction> parse(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Lines cannot be null or empty");
        }

        if (lines.size() < 2) {
            throw new IllegalArgumentException("CSV must contain at least a header "
                    + "and one data row");
        }

        return IntStream.range(1, lines.size())
                .mapToObj(i -> parseLine(lines.get(i), i + 1))
                .collect(Collectors.toList());
    }

    private Transaction parseLine(String line, int lineNumber) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Line " + lineNumber + " is empty or null");
        }

        String[] parts = line.split(CSV_DELIMITER);
        if (parts.length != EXPECTED_COLUMNS) {
            throw new IllegalArgumentException(
                    String.format("Line %d has %d columns, expected %d: %s",
                            lineNumber, parts.length, EXPECTED_COLUMNS, line));
        }

        try {
            String operationCode = parts[0].trim();
            String fruit = parts[1].trim();
            String quantityStr = parts[2].trim();

            if (operationCode.isEmpty()) {
                throw new IllegalArgumentException("Operation code is empty on line " + lineNumber);
            }
            if (fruit.isEmpty()) {
                throw new IllegalArgumentException("Fruit name is empty on line " + lineNumber);
            }
            if (quantityStr.isEmpty()) {
                throw new IllegalArgumentException("Quantity is empty on line " + lineNumber);
            }

            OperationType operation = OperationType.fromCode(operationCode);
            int quantity = parseQuantity(quantityStr, lineNumber);

            return new Transaction(fruit, operation, quantity);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid quantity format on line " + lineNumber + ": " + parts[2], e);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Unknown operation code")) {
                throw new IllegalArgumentException(
                        "Unknown operation code on line " + lineNumber + ": " + parts[0], e);
            }
            throw e;
        }
    }

    private int parseQuantity(String quantityStr, int lineNumber) {
        int quantity = Integer.parseInt(quantityStr);
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative on line " + lineNumber + ": " + quantity);
        }
        return quantity;
    }
}
