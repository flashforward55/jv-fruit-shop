package core.basesyntax.service.impl.handlers;

import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import java.util.Objects;

public class ReturnHandler implements OperationHandler {
    private final FruitBalance balance;

    public ReturnHandler(FruitBalance balance) {
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
    }

    @Override
    public void handle(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        Objects.requireNonNull(transaction.fruit(), "Fruit name cannot be null");

        if (transaction.quantity() <= 0) {
            throw new IllegalArgumentException("Return quantity must be positive: "
                    + transaction.quantity());
        }

        balance.add(transaction.fruit(), transaction.quantity());
    }
}
