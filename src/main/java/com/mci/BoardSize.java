package com.mci;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BoardSize {

    private int rows;
    private int cols;

    private Map<String, Object> additionalProperties = new HashMap<>();

    public BoardSize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public BoardSize() {
        // Default constructor for JSON frameworks
    }

    // ---------- Getter & Setter ----------

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
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
        if (!(o instanceof BoardSize that)) return false;
        return rows == that.rows
                && cols == that.cols
                && Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, additionalProperties);
    }

    // ---------- toString ----------

    @Override
    public String toString() {
        return "BoardSize {\n" +
                "    rows: " + toIndentedString(rows) + "\n" +
                "    cols: " + toIndentedString(cols) + "\n" +
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
