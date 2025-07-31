package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;

import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private final OperationStrategy strategy;

    public TransactionProcessorImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void process(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            try {
                OperationHandler handler = strategy.getHandler(transaction.operation());
                handler.handle(transaction);
                System.out.println("Processed: " + transaction);
            } catch (Exception e) {
                System.err.println("Error processing transaction "
                        + transaction + ": " + e.getMessage());
                throw e;
            }
        }
    }
}
