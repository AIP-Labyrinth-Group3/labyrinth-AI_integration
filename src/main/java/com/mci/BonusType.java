package com.mci;

public enum BonusType {

    BEAM("BEAM"),
    PUSH_FIXED("PUSH_FIXED"),
    SWAP("SWAP"),
    PUSH_TWICE("PUSH_TWICE");

    private final String value;

    BonusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BonusType fromValue(String value) {
        for (BonusType type : BonusType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @Override
    public String toString() {
        return value;
    }
}
