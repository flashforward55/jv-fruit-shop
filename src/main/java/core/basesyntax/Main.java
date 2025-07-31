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
    private static final String INPUT_FILE = "src/main/java/core/basesyntax/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        try {
            FruitBalance balance = new FruitBalance();

            Map<OperationType, OperationHandler> handlers = createHandlers(balance);
            OperationStrategy strategy = new OperationStrategy(handlers);

            ReaderService reader = new FileReaderServiceImpl();
            ParserService parser = new CsvParserServiceImpl();

            System.out.println("Reading file: " + INPUT_FILE);
            List<String> lines = reader.read(INPUT_FILE);

            System.out.println("Parsing transactions...");
            List<Transaction> transactions = parser.parse(lines);
            System.out.println("Found " + transactions.size() + " transactions");

            System.out.println("Processing transactions...");
            for (Transaction transaction : transactions) {
                try {
                    strategy.getHandler(transaction.operation()).handle(transaction);
                    System.out.println("Processed: " + transaction);
                } catch (Exception e) {
                    System.err.println("Error processing transaction "
                            + transaction + ": " + e.getMessage());
                    throw e;
                }
            }

            System.out.println("Generating report...");
            Map<String, Integer> finalBalance = balance.getAll();

            ReportService reportService = new ReportServiceImpl();
            reportService.write(finalBalance, OUTPUT_FILE);

            System.out.println("Report generated successfully: " + OUTPUT_FILE);
            System.out.println("Final balance:");
            finalBalance.forEach((fruit, quantity) ->
                    System.out.println("  " + fruit + ": " + quantity));

        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Map<OperationType, OperationHandler> createHandlers(FruitBalance balance) {
        Map<OperationType, OperationHandler> handlers = new HashMap<>();
        handlers.put(OperationType.BALANCE, new BalanceHandler(balance));
        handlers.put(OperationType.SUPPLY, new SupplyHandler(balance));
        handlers.put(OperationType.PURCHASE, new PurchaseHandler(balance));
        handlers.put(OperationType.RETURN, new ReturnHandler(balance));
        return handlers;
    }
}
