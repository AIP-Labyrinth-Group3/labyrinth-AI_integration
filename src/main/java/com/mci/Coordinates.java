package com.mci;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Coordinates {

    private int row;
    private int col;

    private Map<String, Object> additionalProperties = new HashMap<>();

    public Coordinates() {
        // Default constructor for JSON frameworks
    }

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // ---------- Getter & Setter ----------

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;
        return row == that.row
                && col == that.col
                && Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, additionalProperties);
    }

    // ---------- toString ----------

    @Override
    public String toString() {
        return "Coordinates {\n" +
                "    row: " + toIndentedString(row) + "\n" +
                "    col: " + toIndentedString(col) + "\n" +
                "    additionalProperties: " + toIndentedString(additionalProperties) + "\n" +
                '}';
    }

    private String toIndentedString(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString().replace("\n", "\n    ");
    }
}
