package core.basesyntax.strategy;

import core.basesyntax.model.OperationType;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import java.util.Objects;

public class OperationStrategy {
    private final Map<OperationType, OperationHandler> strategy;

    public OperationStrategy(Map<OperationType, OperationHandler> strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy map cannot be null");
        validateStrategy();
    }

    public OperationHandler getHandler(OperationType type) {
        Objects.requireNonNull(type, "Operation type cannot be null");

        OperationHandler handler = strategy.get(type);
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for operation type: " + type);
        }
        return handler;
    }

    private void validateStrategy() {
        for (OperationType operationType : OperationType.values()) {
            if (!strategy.containsKey(operationType)) {
                throw new IllegalArgumentException("Missing handler for operation type: "
                        + operationType);
            }
            if (strategy.get(operationType) == null) {
                throw new IllegalArgumentException("Handler cannot be null for operation type: "
                        + operationType);
            }
        }
    }
}
