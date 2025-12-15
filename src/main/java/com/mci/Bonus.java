package com.mci;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bonus {

    private Integer id;
    private BonusType type;

    private Map<String, Object> additionalProperties = new HashMap<>();

    public Bonus() {
        // Default constructor for JSON frameworks
    }
    // ⭐ FULL DEEP COPY CONSTRUCTOR
    public Bonus(Bonus other) {
        if (other == null) {
            this.additionalProperties = new HashMap<>();
            return;
        }

        this.id = other.id;        // Integer → safe copy
        this.type = other.type;    // enum BonusType → safe copy

        this.additionalProperties = other.additionalProperties != null
                ? new HashMap<>(other.additionalProperties)
                : new HashMap<>();
    }

    private Bonus copyBonus(Bonus newData) {
        return new Bonus(newData);
    }


    public Bonus(Integer id, BonusType type) {
        this.id = id;
        this.type = type;
    }

    // ---------- Getter & Setter ----------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BonusType getType() {
        return type;
    }

    public void setType(BonusType type) {
        this.type = type;
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
        if (!(o instanceof Bonus that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(type, that.type)
                && Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, additionalProperties);
    }

    // ---------- toString ----------

    @Override
    public String toString() {
        return "Bonus {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
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

