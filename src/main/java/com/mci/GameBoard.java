package com.mci;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mci.GameBoardAlt.getTileSymbol;

public class GameBoard {

    private Integer rows;
    private Integer cols;
    private Tile[][] tiles;
    private PushActionInfo lastPush;

    private Map<String, Object> additionalProperties = new HashMap<>();

    public GameBoard() {

    }
    public GameBoard(GameBoard other) {
        if (other == null) {
            return;
        }

        this.rows = other.rows;
        this.cols = other.cols;

        // --- Tiles tief kopieren ---
        if (other.tiles != null) {
            this.tiles = new Tile[other.tiles.length][];

            for (int r = 0; r < other.tiles.length; r++) {
                Tile[] row = other.tiles[r];
                if (row != null) {
                    this.tiles[r] = new Tile[row.length];
                    for (int c = 0; c < row.length; c++) {
                        Tile oldTile = row[c];
                        this.tiles[r][c] = (oldTile != null) ? new Tile(oldTile) : null;
                    }
                }
            }
        }

        // --- lastPush kopieren ---
        if (other.lastPush != null) {
            // Falls PushActionInfo mutable ist → Copy-Konstruktor dort bauen
            this.lastPush = new PushActionInfo(other.lastPush);
            // wenn PushActionInfo eher immutable ist, könnte auch:
            // this.lastPush = other.lastPush;
        }

        // --- additionalProperties kopieren ---
        this.additionalProperties = other.additionalProperties != null
                ? new HashMap<>(other.additionalProperties)
                : new HashMap<>();
    }




    public GameBoard(Integer rows, Integer cols, Tile[][] tiles) {
        this.rows = rows;
        this.cols = cols;
        this.tiles = tiles;
    }

    public GameBoard deepCopy() {
        return new GameBoard(this);
    }

    // ---------- Getter & Setter ----------

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    public PushActionInfo getLastPush() {
        return lastPush;
    }

    public void setLastPush(PushActionInfo lastPush) {
        this.lastPush = lastPush;
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
        if (!(o instanceof GameBoard other)) return false;

        return Objects.equals(rows, other.rows)
                && Objects.equals(cols, other.cols)
                && Arrays.deepEquals(tiles, other.tiles)
                && Objects.equals(lastPush, other.lastPush)
                && Objects.equals(additionalProperties, other.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                rows,
                cols,
                Arrays.deepHashCode(tiles),
                lastPush,
                additionalProperties
        );
    }

    public  void printBoard() {
        System.out.println("=== GAME BOARD VISUALIZATION ===");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = tiles[row][col];
                if (tile == null) {
                    System.out.print(" - ");
                } else {
                    System.out.printf("%-3s", getTileSymbol(tile));
                }
            }
            System.out.println();
        }
    }



    // ---------- toString ----------

    @Override
    public String toString() {
        return "GameBoard {\n" +
                "    rows: " + toIndentedString(rows) + "\n" +
                "    cols: " + toIndentedString(cols) + "\n" +
                "    tiles: " + toIndentedString(Arrays.deepToString(tiles)) + "\n" +
                "    lastPush: " + toIndentedString(lastPush) + "\n" +
                "    additionalProperties: " + toIndentedString(additionalProperties) + "\n" +
                "}";
    }

    private String toIndentedString(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString().replace("\n", "\n    ");
    }







}
