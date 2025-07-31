package core.basesyntax.model;

import java.util.HashMap;
import java.util.Map;

public class FruitBalance {
    private static final Map<String, Integer> storage = new HashMap<>();

    public void add(String fruit, int amount) {
        storage.merge(fruit, amount, Integer::sum);
    }

    public Map<String, Integer> getAll() {
        return storage;
    }
}
