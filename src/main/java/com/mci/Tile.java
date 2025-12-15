package com.mci;
import java.util.*;

/**
 * Represents a single tile on the game board.
 */
public class Tile {

    private Direction[] entrances;
    private Treasure treasure;
    private Bonus bonus;
    private Boolean isFixed;

    private Map<String, Object> additionalProperties;

    /* -------------------- Getters & Setters -------------------- */
   public Tile() {
        this.entrances=new Direction[]{};
        this.treasure=new Treasure();
        this.bonus=new Bonus();
        this.isFixed=false;
        this.additionalProperties=new HashMap<>();
    }

    public Tile(Tile other) {
        // copy entrances array
        this.entrances = other.entrances != null
                ? other.entrances.clone()
                : null;

        // deep copy or safe copy of treasure
        if (other.treasure != null) {
            // OPTION A: if Treasure has a copy constructor:
            this.treasure = new Treasure(other.treasure);

            // OPTION B (if Treasure is immutable or enum-like):
            // this.treasure = other.treasure;
        } else {
            this.treasure = null;
        }

        // deep copy or safe copy of bonus
        if (other.bonus != null) {
            // OPTION A: if Bonus has a copy constructor:
            this.bonus = new Bonus(other.bonus);

            // OPTION B (if Bonus is immutable):
            // this.bonus = other.bonus;
        } else {
            this.bonus = null;
        }

        // Boolean is immutable, can copy directly
        this.isFixed = other.isFixed;

        // copy map (shallow copy of values)
        this.additionalProperties = other.additionalProperties != null
                ? new HashMap<>(other.additionalProperties)
                : new HashMap<>();
    }



    public Direction[] getEntrances() {
        return entrances;
    }

    public void setEntrances(Direction[] entrances) {
        this.entrances = entrances;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }


    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties =
                additionalProperties != null ? additionalProperties : new HashMap<>();
    }



    public boolean contains(String value) {
        List<Direction> list = Arrays.asList(entrances);
        for (Direction d : list) {
            if (d.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

















    /* -------------------- Object Overrides -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tile)) {
            return false;
        }

        Tile other = (Tile) o;
        return Arrays.equals(entrances, other.entrances)
                && Objects.equals(treasure, other.treasure)
                && Objects.equals(bonus, other.bonus)
                && Objects.equals(isFixed, other.isFixed)
                && Objects.equals(additionalProperties, other.additionalProperties);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(entrances);
        result = 31 * result + Objects.hash(treasure, bonus, isFixed, additionalProperties);
        return result;
    }

    @Override
    public String toString() {
        return "Tile {\n"
                + "    entrances: " + Arrays.toString(entrances) + "\n"
                + "    treasure: " + treasure + "\n"
                + "    bonus: " + bonus + "\n"
                + "    isFixed: " + isFixed + "\n"
                + "    additionalProperties: " + additionalProperties + "\n"
                + "}";
    }
}
