package com.mci;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameBoardAlt {

    private static final String TILE_TYPE_CORNER = "CORNER";
    private static final String TILE_TYPE_STRAIGHT = "STRAIGHT";
    private static final String TILE_TYPE_CROSS = "CROSS";

    private static final int BASE_ROWS = 7;
    private static final int BASE_COLS = 7;
    private static final int BASE_CORNER_COUNT = 20;
    private static final int BASE_CROSS_COUNT = 18;
    private static final int BASE_STRAIGHT_COUNT = 12;

    private final BoardSize size;
    private final Tile[][] tiles;

    private GameBoardAlt(BoardSize size) {
        this.size = size;
        this.tiles = new Tile[size.getRows()][size.getCols()];
    }

    public static GameBoardAlt generateBoard(BoardSize size) {
        GameBoardAlt board = new GameBoardAlt(size);
        int rows = size.getRows();
        int cols = size.getCols();

        // corners
        Direction[] corner1 = {Direction.RIGHT, Direction.DOWN};
        Tile tile = new Tile();
        tile.setEntrances(corner1);
        board.setTile(0, 0, tile);

        Direction[] corner2 = {Direction.LEFT, Direction.DOWN};
        tile = new Tile();
        tile.setEntrances(corner2);
        board.setTile(0, cols - 1, tile);

        Direction[] corner3 = {Direction.UP, Direction.RIGHT};
        tile = new Tile();
        tile.setEntrances(corner3);
        board.setTile(rows - 1, 0, tile);

        Direction[] corner4 = {Direction.UP, Direction.LEFT};
        tile = new Tile();
        tile.setEntrances(corner4);
        board.setTile(rows - 1, cols - 1, tile);

        // edge crosses
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    boolean isEdge = row == 0 || col == 0 || row == rows - 1 || col == cols - 1;
                    if (isEdge && board.getTiles()[row][col] == null) {
                        Direction[] tileDirections = generateEdgeCrossEntrances(row, col, rows, cols);
                        tile = new Tile();
                        tile.setEntrances(tileDirections);
                        board.setTile(row, col, tile);
                    }
                }
            }
        }

        // inner crosses
        for (int row = 2; row < rows - 1; row += 2) {
            for (int col = 2; col < cols - 1; col += 2) {
                if (board.getTiles()[row][col] == null) {
                    Direction[] tileDirections = generateEntrancesForTypeWithRandomRotation(TILE_TYPE_CROSS);
                    tile = new Tile();
                    tile.setEntrances(tileDirections);
                    board.setTile(row, col, tile);
                }
            }
        }

        // fill remaining tiles randomly
        fillRandomTiles(board);

        try {
            System.out.println("Working dir: " + System.getProperty("user.dir"));
            String treasurePath = System.getProperty("user.dir") + "/src/main/resources/treasure/treasure.txt";
            List<String> treasureNames = Files.readAllLines(Paths.get(treasurePath));

            // If you want treasures:
            // setTreasures(board, treasureNames);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return board;
    }

    private static String getTileType(Tile tile) {
        Direction[] entrances = tile.getEntrances();
        if (entrances == null || entrances.length == 0) {
            return "";
        }

        boolean containsUp = Arrays.asList(entrances).contains(Direction.UP);
        boolean containsDown = Arrays.asList(entrances).contains(Direction.DOWN);
        boolean containsLeft = Arrays.asList(entrances).contains(Direction.LEFT);
        boolean containsRight = Arrays.asList(entrances).contains(Direction.RIGHT);

        if (entrances.length == 2) {
            if ((containsUp && containsRight)
                    || (containsRight && containsDown)
                    || (containsDown && containsLeft)
                    || (containsLeft && containsUp)) {
                return TILE_TYPE_CORNER;
            }
            return TILE_TYPE_STRAIGHT;
        }

        if (entrances.length == 3) {
            return TILE_TYPE_CROSS;
        }

        return "";
    }

    private static void fillRandomTiles(GameBoardAlt board) {
        int rows = board.getSize().getRows();
        int cols = board.getSize().getCols();
        int totalTiles = rows * cols;
        int totalCards = totalTiles + 1; // +1 spare tile

        int cornerCount = totalCards * BASE_CORNER_COUNT / (BASE_ROWS * BASE_COLS + 1);
        int crossCount = totalCards * BASE_CROSS_COUNT / (BASE_ROWS * BASE_COLS + 1);
        int straightCount = totalCards * BASE_STRAIGHT_COUNT / (BASE_ROWS * BASE_COLS + 1);

        int sum = cornerCount + crossCount + straightCount;
        int diff = totalCards - sum;
        if (diff > 0) {
            straightCount += diff;
        }

        // subtract already placed tiles
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = board.getTiles()[row][col];
                if (tile != null) {
                    String type = getTileType(tile);
                    switch (type) {
                        case TILE_TYPE_CORNER -> cornerCount--;
                        case TILE_TYPE_CROSS -> crossCount--;
                        case TILE_TYPE_STRAIGHT -> straightCount--;
                        default -> {
                        }
                    }
                }
            }
        }

        List<String> remainingTiles = new ArrayList<>();
        for (int i = 0; i < cornerCount; i++) {
            remainingTiles.add(TILE_TYPE_CORNER);
        }
        for (int i = 0; i < crossCount; i++) {
            remainingTiles.add(TILE_TYPE_CROSS);
        }
        for (int i = 0; i < straightCount; i++) {
            remainingTiles.add(TILE_TYPE_STRAIGHT);
        }

        Collections.shuffle(remainingTiles);

        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board.getTiles()[row][col] == null && index < remainingTiles.size()) {
                    String type = remainingTiles.get(index++);
                    Tile tile = new Tile();
                    Direction[] directions = generateEntrancesForTypeWithRandomRotation(type);
                    tile.setEntrances(directions);
                    board.setTile(row, col, tile);
                }
            }
        }
    }

    private static Direction[] generateEdgeCrossEntrances(int row, int col, int rows, int cols) {
        boolean topEdge = row == 0;
        boolean bottomEdge = row == rows - 1;
        boolean leftEdge = col == 0;
        boolean rightEdge = col == cols - 1;

        if (topEdge) {
            return new Direction[]{Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        }
        if (bottomEdge) {
            return new Direction[]{Direction.UP, Direction.LEFT, Direction.RIGHT};
        }
        if (leftEdge) {
            return new Direction[]{Direction.UP, Direction.DOWN, Direction.RIGHT};
        }
        if (rightEdge) {
            return new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT};
        }

        return new Direction[0];
    }

    private static Direction[] generateEntrancesForTypeWithRandomRotation(String type) {
        Random random = new Random();

        switch (type) {
            case TILE_TYPE_CORNER -> {
                Direction[][] corners = {
                        {Direction.UP, Direction.RIGHT},
                        {Direction.RIGHT, Direction.DOWN},
                        {Direction.DOWN, Direction.LEFT},
                        {Direction.LEFT, Direction.UP}
                };
                return corners[random.nextInt(corners.length)];
            }
            case TILE_TYPE_STRAIGHT -> {
                Direction[][] straights = {
                        {Direction.UP, Direction.DOWN},
                        {Direction.LEFT, Direction.RIGHT},
                        {Direction.DOWN, Direction.LEFT},
                        {Direction.LEFT, Direction.UP}
                };
                return straights[random.nextInt(straights.length)];
            }
            case TILE_TYPE_CROSS -> {
                Direction[][] crosses = {
                        {Direction.UP, Direction.RIGHT, Direction.DOWN},
                        {Direction.RIGHT, Direction.DOWN, Direction.LEFT},
                        {Direction.DOWN, Direction.LEFT, Direction.UP},
                        {Direction.LEFT, Direction.UP, Direction.RIGHT}
                };
                return crosses[random.nextInt(crosses.length)];
            }
            default -> {
                return new Direction[0];
            }
        }
    }

    private static void setTreasures(GameBoardAlt board, List<String> treasureNames) {

        int numberOfFields = board.size.getCols() * board.size.getRows();
        Integer[] occupiedPositions = new Integer[numberOfFields];
        Arrays.fill(occupiedPositions, 0);

        int index = 0;
        int treasureCount = treasureNames.size();
        Treasure[] treasures = new Treasure[treasureCount];

        for (String name : treasureNames) {
            int id = 1; // TODO: probably should not be reset each loop – left as in original logic
            Treasure treasure = new Treasure();
            treasure.setId(id);
            treasure.setName(name);
            treasures[id] = treasure;
            occupiedPositions[index++] = id++;
        }

        List<Integer> shuffledPositions = Arrays.asList(occupiedPositions);
        Collections.shuffle(shuffledPositions);

        for (int i = 0; i < numberOfFields; i++) {
            int row = i / board.getSize().getCols();
            int col = i % board.getSize().getCols();

            if (shuffledPositions.get(i) != 1) {
                Tile tile = board.getTiles()[row][col];
                if (tile != null) {
                    tile.setTreasure(treasures[shuffledPositions.get(i)]);
                    board.setTile(row, col, tile);
                }
            }
        }
    }

    // DEMO
    public static void printBoard(GameBoardAlt board) {
        Tile[][] tiles = board.getTiles();
        int rows = board.getSize().getRows();
        int cols = board.getSize().getCols();

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

    public static String getTileSymbol(Tile tile) {
        Direction[] directions = tile.getEntrances();
        if (directions == null || directions.length == 0) {
            return " ? ";
        }

        boolean containsUp = Arrays.asList(directions).contains(Direction.UP);
        boolean containsRight = Arrays.asList(directions).contains(Direction.RIGHT);
        boolean containsDown = Arrays.asList(directions).contains(Direction.DOWN);
        boolean containsLeft = Arrays.asList(directions).contains(Direction.LEFT);

        // corners
        if (directions.length == 2 && containsUp && containsRight) return "╚";
        if (directions.length == 2 && containsRight && containsDown) return "╔";
        if (directions.length == 2 && containsDown && containsLeft) return "╗";
        if (directions.length == 2 && containsUp && containsLeft) return "╝";

        // straight
        if (directions.length == 2 && containsRight && containsLeft) return "═";
        if (directions.length == 2 && containsUp && containsDown) return "║";

        // T-crossings
        if (directions.length == 3) {
            if (!containsUp) return "╦";
            if (!containsRight) return "╣";
            if (!containsDown) return "╩";
            if (!containsLeft) return "╠";
        }

        return " ? ";
    }

    public static String getTileImageName(Tile tile) {
        Direction[] directions = tile.getEntrances();
        if (directions == null || directions.length == 0) {
            return " ? ";
        }

        boolean containsUp = Arrays.asList(directions).contains(Direction.UP);
        boolean containsRight = Arrays.asList(directions).contains(Direction.RIGHT);
        boolean containsDown = Arrays.asList(directions).contains(Direction.DOWN);
        boolean containsLeft = Arrays.asList(directions).contains(Direction.LEFT);

        // corners
        if (directions.length == 2 && containsUp && containsRight) return "ecke_AB.png";
        if (directions.length == 2 && containsRight && containsDown) return "ecke_BC.png";
        if (directions.length == 2 && containsDown && containsLeft) return "ecke_CD.png";
        if (directions.length == 2 && containsUp && containsLeft) return "ecke_AD.png";

        // straight
        if (directions.length == 2 && containsRight && containsLeft) return "gerade_BD.png";
        if (directions.length == 2 && containsUp && containsDown) return "gerade_AC.png";

        // T-crossings
        if (directions.length == 3) {
            if (!containsUp) return "kreuzung_BCD.png";
            if (!containsRight) return "kreuzung_ACD.png";
            if (!containsDown) return "kreuzung_ABD.png";
            if (!containsLeft) return "kreuzung_ABC.png";
        }

        return " ? ";
    }

    public BoardSize getSize() {
        return size;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTile(int row, int col, Tile tile) {
        this.tiles[row][col] = tile;
    }
}
