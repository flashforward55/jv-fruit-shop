package core.basesyntax.model;

import java.util.Arrays;

public enum OperationType {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private final String code;

    OperationType(String code) {
        this.code = code;
    }

    public static OperationType fromCode(String code) {
        return Arrays.stream(values())
            .filter(op -> op.code.equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown operation code: " + code));
    }
}
