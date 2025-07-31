package core.basesyntax.service.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParserServiceImpl implements ParserService {
    private static final String COMMA = ",";
    private static final int HEADER_LINE_COUNT = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<Transaction> parse(List<String> lines) {
        return lines.stream()
                .skip(HEADER_LINE_COUNT)
                .map(line -> {
                    String[] parts = line.split(COMMA);
                    return new Transaction(
                            parts[FRUIT_INDEX],
                            OperationType.fromCode(parts[OPERATION_INDEX]),
                            Integer.parseInt(parts[QUANTITY_INDEX])
                    );
                })
                .collect(Collectors.toList());
    }
}
