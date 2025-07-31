package core.basesyntax.service.impl.handlers;

import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;

public class ReturnHandler implements OperationHandler {
    private final FruitBalance balance;

    public ReturnHandler(FruitBalance balance) {
        this.balance = balance;
    }

    @Override
    public void handle(Transaction transaction) {
        balance.add(transaction.fruit(), transaction.quantity());
    }
}

