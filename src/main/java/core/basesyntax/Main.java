package core.basesyntax;

import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.CsvParserServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.handlers.BalanceHandler;
import core.basesyntax.service.impl.handlers.PurchaseHandler;
import core.basesyntax.service.impl.handlers.ReturnHandler;
import core.basesyntax.service.impl.handlers.SupplyHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FruitBalance balance = new FruitBalance();

        Map<OperationType, OperationHandler> handlers = new HashMap<>();
        handlers.put(OperationType.BALANCE, new BalanceHandler(balance));
        handlers.put(OperationType.SUPPLY, new SupplyHandler(balance));
        handlers.put(OperationType.PURCHASE, new PurchaseHandler(balance));
        handlers.put(OperationType.RETURN, new ReturnHandler(balance));

        OperationStrategy strategy = new OperationStrategy(handlers);

        ReaderService reader = new FileReaderServiceImpl();
        ParserService parser = new CsvParserServiceImpl();
        ReportService reportService = new ReportServiceImpl();

        List<String> lines = reader.read(INPUT_FILE);
        List<Transaction> transactions = parser.parse(lines);

        for (Transaction transaction : transactions) {
            strategy.getHandler(transaction.operation()).handle(transaction);
        }

        reportService.write(balance.getAll(), OUTPUT_FILE);
    }
}
