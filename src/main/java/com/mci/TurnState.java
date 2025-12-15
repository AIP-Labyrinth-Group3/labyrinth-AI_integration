package com.mci;

/**
 * Represents the current state of a player's turn.
 */
public enum TurnState {

    WAITING_FOR_PUSH("WAITING_FOR_PUSH"),
    WAITING_FOR_MOVE("WAITING_FOR_MOVE"),
    WAITING_FOR_BONUS("WAITING_FOR_BONUS");

    private final String value;

    TurnState(String value) {
        this.value = value;
    }

    public static TurnState fromValue(String value) {
        for (TurnState state : TurnState.values()) {
            if (state.value.equals(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
