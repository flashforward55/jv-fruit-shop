package core.basesyntax.service.impl.handlers;

import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    private final FruitBalance balance;

    public BalanceHandler(FruitBalance balance) {
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
    }

    @Override
    public void handle(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        Objects.requireNonNull(transaction.fruit(), "Fruit name cannot be null");

        if (transaction.quantity() < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative: "
                    + transaction.quantity());
        }

        String fruit = transaction.fruit();
        int currentBalance = balance.getAll().getOrDefault(fruit, 0);

        if (currentBalance != 0) {
            throw new IllegalStateException(
                    String.format("Balance for %s already exists: %d. Cannot set initial balance.",
                            fruit, currentBalance));
        }

        balance.add(fruit, transaction.quantity());
    }
}
