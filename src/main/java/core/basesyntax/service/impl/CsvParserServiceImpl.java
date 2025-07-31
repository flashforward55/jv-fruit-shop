package core.basesyntax.service.impl;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParserServiceImpl implements ParserService {

    @Override
    public List<Transaction> parse(List<String> lines) {
        return lines.stream()
                .skip(1) // пропускаем заголовок
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Transaction(
                            parts[1],
                            OperationType.fromCode(parts[0]),
                            Integer.parseInt(parts[2])
                    );
                })
                .collect(Collectors.toList());
    }
}
