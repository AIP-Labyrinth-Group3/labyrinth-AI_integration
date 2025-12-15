package com.uni.gameclient.socket.model.items;

public enum AchievementType {

    RUNNER("RUNNER"),
    PUSHER("PUSHER"),
    BLOCKER("BLOCKER"),
    TIME_WASTER("TIME_WASTER"),
    HATTRICK("HATTRICK");

    private final String value;

    AchievementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AchievementType fromValue(String value) {
        for (AchievementType t : AchievementType.values()) {
            if (t.value.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @Override
    public String toString() {
        return value;
    }
}
