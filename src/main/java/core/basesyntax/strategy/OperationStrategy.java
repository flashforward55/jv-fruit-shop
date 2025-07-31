package core.basesyntax.strategy;

import core.basesyntax.model.OperationType;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class OperationStrategy {
    private final Map<OperationType, OperationHandler> strategy;

    public OperationStrategy(Map<OperationType, OperationHandler> strategy) {
        this.strategy = strategy;
    }

    public OperationHandler getHandler(OperationType type) {
        return strategy.get(type);
    }
}
