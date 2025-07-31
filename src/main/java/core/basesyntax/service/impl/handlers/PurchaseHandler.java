package core.basesyntax.service.impl.handlers;

import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import java.util.Objects;

public class PurchaseHandler implements OperationHandler {
    private final FruitBalance balance;

    public PurchaseHandler(FruitBalance balance) {
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
    }

    @Override
    public void handle(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        Objects.requireNonNull(transaction.fruit(), "Fruit name cannot be null");

        if (transaction.quantity() <= 0) {
            throw new IllegalArgumentException("Purchase quantity must be positive: "
                    + transaction.quantity());
        }

        String fruit = transaction.fruit();
        int purchaseQuantity = transaction.quantity();
        int currentBalance = balance.getAll().getOrDefault(fruit, 0);

        if (currentBalance < purchaseQuantity) {
            throw new IllegalStateException(
                    String.format("Insufficient balance for %s. Available: %d, requested: %d",
                            fruit, currentBalance, purchaseQuantity));
        }

        balance.add(fruit, -purchaseQuantity);
    }
}
