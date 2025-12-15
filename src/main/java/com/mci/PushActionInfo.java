package com.mci;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PushActionInfo {

    private Integer rowOrColIndex;
    private Direction direction;

    private Map<String, Object> additionalProperties = new HashMap<>();

    public PushActionInfo() {
        // Default constructor
    }

    public PushActionInfo(Integer rowOrColIndex, Direction direction) {
        this.rowOrColIndex = rowOrColIndex;
        this.direction = direction;
    }

    // ⭐ FULL COPY CONSTRUCTOR — this is what Tile(Tile other) calls
    public PushActionInfo(PushActionInfo other) {
        if (other == null) {
            this.additionalProperties = new HashMap<>();
            return;
        }

        this.rowOrColIndex = other.rowOrColIndex;
        this.direction = other.direction;


        // Map copy (shallow value copy is OK unless values are mutable objects)
        this.additionalProperties = other.additionalProperties != null
                ? new HashMap<>(other.additionalProperties)
                : new HashMap<>();
    }


    // ---------- Getter & Setter ----------

    public Integer getRowOrColIndex() {
        return rowOrColIndex;
    }

    public void setRowOrColIndex(Integer rowOrColIndex) {
        this.rowOrColIndex = rowOrColIndex;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties =
                additionalProperties != null ? additionalProperties : new HashMap<>();
    }

    // ---------- equals & hashCode ----------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PushActionInfo other)) return false;

        return Objects.equals(rowOrColIndex, other.rowOrColIndex)
                && Objects.equals(direction, other.direction)
                && Objects.equals(additionalProperties, other.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowOrColIndex, direction, additionalProperties);
    }

    // ---------- toString ----------

    @Override
    public String toString() {
        return "PushActionInfo {\n" +
                "    rowOrColIndex: " + toIndentedString(rowOrColIndex) + "\n" +
                "    direction: " + toIndentedString(direction) + "\n" +
                "    additionalProperties: " + toIndentedString(additionalProperties) + "\n" +
                "}";
    }

    private String toIndentedString(Object value) {
        if (value == null) return "null";
        return value.toString().replace("\n", "\n    ");
    }
}
