package core.basesyntax.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FruitBalance {
    private final Map<String, Integer> balance = new HashMap<>();

    public void add(String fruit, int amount) {
        Objects.requireNonNull(fruit, "Fruit name cannot be null");

        if (fruit.trim().isEmpty()) {
            throw new IllegalArgumentException("Fruit name cannot be empty");
        }

        balance.merge(fruit, amount, Integer::sum);

        int newBalance = balance.get(fruit);
        if (newBalance < 0) {
            balance.put(fruit, newBalance - amount);
            throw new IllegalStateException(
                    String.format("Operation would result in negative balance for %s: %d",
                            fruit, newBalance));
        }
    }

    public Map<String, Integer> getAll() {
        return new HashMap<>(balance);
    }

    public int getBalance(String fruit) {
        Objects.requireNonNull(fruit, "Fruit name cannot be null");
        return balance.getOrDefault(fruit, 0);
    }
}
