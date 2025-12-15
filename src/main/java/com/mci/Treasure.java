package com.mci;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a treasure that can appear on a game tile.
 */
public class Treasure {

    private int id;
    private String name;
    private Map<String, Object> additionalProperties;

    public Treasure() {
        this.additionalProperties = new HashMap<>();
    }

    // ⭐ FULL COPY CONSTRUCTOR — this is what Tile(Tile other) calls
    public Treasure(Treasure other) {
        if (other == null) {
            this.additionalProperties = new HashMap<>();
            return;
        }

        this.id = other.id;
        this.name = other.name;

        // Map copy (shallow value copy is OK unless values are mutable objects)
        this.additionalProperties = other.additionalProperties != null
                ? new HashMap<>(other.additionalProperties)
                : new HashMap<>();
    }

    /* -------------------- Getters & Setters -------------------- */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties =
                additionalProperties != null ? additionalProperties : new HashMap<>();
    }

    /* -------------------- Object Overrides -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treasure)) return false;

        Treasure other = (Treasure) o;
        return id == other.id
                && Objects.equals(name, other.name)
                && Objects.equals(additionalProperties, other.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, additionalProperties);
    }

    @Override
    public String toString() {
        return "Treasure {\n"
                + "    id: " + id + "\n"
                + "    name: " + name + "\n"
                + "    additionalProperties: " + additionalProperties + "\n"
                + "}";
    }
}