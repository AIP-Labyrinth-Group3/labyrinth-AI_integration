package com.mci;


import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

static GameBoard gameBoard;
static Coordinates end;
static Coordinates start;

static int lowestDifference = 1000;
static Coordinates BestFit;
static Direction[] bestDirection;


    static void main(String[] args) {
        BoardSize boardSize = new BoardSize(10, 10);
        start=new Coordinates(0,0);
        end=new Coordinates(9,9);
        gameBoard = new DemoBoardGenerator().createDemoBoard(boardSize);
        //gameBoard =  generateBoard(10,10);
        //String boardString="╔╣╦║╦╝╦═╦╗═╝═╣╝╔╝╠╗╝╠╝╦║╦╚╦╝╦╗╗║╝║╔╣╗═╝╚╠╝╩╗╣╝╩═╩╔╗╗╝╝╝╝║╦╚╣╠╗╠╝╠╔╩╝╣╗╝╝╗═╩╚═╦╗╝╠╝╦╣╣═╣╩╩╗╚╚║╗╠╔╗╚╠╝";
        //gameBoard=  generateBoardFomString(10,10,boardString);
        Tile spareTile = new Tile();
        spareTile.setEntrances(new Direction[]{Direction.UP, Direction.LEFT, Direction.RIGHT});
        //oneShot(spareTile, gameBoard);
        pushPreparation(spareTile, gameBoard);
    }



private static void oneShot(Tile spareTile, GameBoard gameBoard){
    Tile mytile = new Tile();
    GameBoard myboard = new GameBoard();
    myboard=gameBoard;
    mytile.setEntrances(spareTile.getEntrances());
    mytile.setBonus(spareTile.getBonus());
    mytile.setTreasure(spareTile.getTreasure());
    PushTile pushTile = new PushTile(myboard, mytile);
    Coordinates pushCoordinates = new Coordinates(0,0);

    pushTile.pushPlate(pushCoordinates.getRow(), pushCoordinates.getCol(), 1);
    List<Coordinates> MyUsedTileList = new ArrayList<>();
    MyUsedTileList = Ai(start, end, pushTile.getPushedBoard());
    System.out.println("------------------------------");
    System.out.println("Push at Row: " + pushCoordinates.getRow() + " Col: " + pushCoordinates.getCol() + " Rotation: " + 1);
    System.out.println("Number of reachable tiles: " + MyUsedTileList.size());

}


    private static void pushPreparation(Tile spareTile, GameBoard gameBoard) {
        Tile mytile = new Tile();
        GameBoard myboard = new GameBoard();
        myboard=gameBoard;
        mytile.setEntrances(spareTile.getEntrances());
        mytile.setBonus(spareTile.getBonus());
        mytile.setTreasure(spareTile.getTreasure());
        PushTile pushTile = new PushTile(myboard, mytile);
        Coordinates pushCoordinates = new Coordinates(0,0);

        //for (int i = 1; i < 2; i++) {
        for (int i = 0; i < gameBoard.getCols(); i++) {

            pushCoordinates.setRow(i);
            for (int j = 1; j < 5; j++) {
                pushTile.setOriginBoard();
                System.out.println("------------------------------");
                System.out.println("------------------------------");
                pushTile.pushPlate(pushCoordinates.getRow(), pushCoordinates.getCol(), 1);
                List<Coordinates> MyUsedTileList = new ArrayList<>();
                MyUsedTileList = Ai(new Coordinates(0, 0), new Coordinates(9, 9), pushTile.getPushedBoard());
                for (Coordinates c : MyUsedTileList) {
                    System.out.println("X: " + c.getCol() + " Y: " + c.getRow());
                    int diff = calcDifference(end,c);
                    if(diff<lowestDifference){
                        lowestDifference=diff;
                        BestFit=c;
                        Tile myTile= pushTile.getPushPlate();
                        Direction[] dir =myTile.getEntrances();
                        bestDirection=dir;
                    }
                }

                System.out.println("------------------------------");
                System.out.println("Push at Row: " + pushCoordinates.getRow() + " Col: " + pushCoordinates.getCol() + " Rotation: " + j);
                System.out.println("Number of reachable tiles: " + MyUsedTileList.size());
                pushTile.rotateTileRight();
            }
            pushTile.setOrigin();
        }


        System.out.println("=== BEST PUSH OPTION ===");
        System.out.println("Best reachable tile is at X: " + BestFit.getCol() + " Y: " + BestFit.getRow());
        System.out.print("With entrances at: ");
        for(Direction d : bestDirection) {
            System.out.print(d.toString() + " ");
        }

    }
    private static List<Coordinates> Ai(Coordinates start, Coordinates end, GameBoard gameBoard) {
    Tile startTile = new Tile();
    Tile endTile = new Tile();
    Tile aktiveTile = new Tile();
    startTile = gameBoard.getTile(start.getRow(), start.getCol());
    aktiveTile = gameBoard.getTile(start.getRow(), start.getCol());
    endTile = gameBoard.getTile(end.getRow(), end.getCol());
    List<Coordinates> usedCoordiantes = new ArrayList<>();
    usedCoordiantes.add(start);
    usedCoordiantes = untersuche(start,usedCoordiantes,gameBoard);
    gameBoard.printBoard();
    return usedCoordiantes;
}
    private static List<Coordinates> untersuche(Coordinates coord, List<Coordinates> list, GameBoard gameBoard) {
        Tile tile = gameBoard.getTile(coord.getRow(), coord.getCol());
        List<Coordinates> myList = new ArrayList<>();
        myList=list;
        for (Direction dir : tile.getEntrances()) {
            Coordinates neighbourCoord = getNeighbour(coord, dir,gameBoard);
            if (neighbourCoord != null
            && !myList.contains(neighbourCoord)) {
                myList.add(neighbourCoord);
                if (neighbourCoord == end) {
                    myList.add(neighbourCoord);
                    return myList;
                }
                myList=untersuche(neighbourCoord,myList, gameBoard);
            }
        }
        return myList;
    }



    private static int calcDifference(Coordinates a, Coordinates b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }
        private static Coordinates getNeighbour(Coordinates location, Direction direction, GameBoard gameBoard) {
            Coordinates myLocation = new Coordinates();
            myLocation.setCol(location.getCol());
            myLocation.setRow(location.getRow());
            switch (direction) {
                case UP: myLocation.setRow((myLocation.getRow() - 1));
                break;
                case DOWN: myLocation.setRow((myLocation.getRow() + 1));
                break;
                case LEFT: myLocation.setCol((myLocation.getCol() - 1));
                break;
                case RIGHT:  myLocation.setCol((myLocation.getCol() + 1));
            break;
            }
            if(myLocation.getRow()<0
                    || myLocation.getCol()<0
                    || myLocation.getRow()>= gameBoard.getRows()
                    || myLocation.getCol()>= gameBoard.getCols()){
                return null;
            }
            Tile tile= gameBoard.getTile(myLocation.getRow(), myLocation.getCol());
            if(tile.contains(inversDirection(direction).toString()))
            {
                return myLocation;
            }
            else{
                return null;
            }
        }
        static List<Direction[]> directions1 = new ArrayList<>();
        static List<Direction[]> directions2= new ArrayList<>();
        static List<Direction[]> directions3= new ArrayList<>();





    private static Direction[] dirFromSign(char sign) {
        return switch (sign) {
            case '╩' -> new Direction[]{Direction.UP, Direction.LEFT, Direction.RIGHT};
            case '╠' -> new Direction[]{Direction.UP, Direction.DOWN, Direction.RIGHT};
            case '╦' -> new Direction[]{Direction.DOWN, Direction.LEFT, Direction.RIGHT};
            case '╣' -> new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT};
            case '║' -> new Direction[]{Direction.UP, Direction.DOWN};
            case '═' -> new Direction[]{Direction.LEFT, Direction.RIGHT};
            case '╗' -> new Direction[]{Direction.LEFT, Direction.DOWN};
            case '╝' -> new Direction[]{Direction.UP, Direction.LEFT};
            case '╚' -> new Direction[]{Direction.UP, Direction.RIGHT};
            case '╔' -> new Direction[]{Direction.DOWN, Direction.RIGHT};
            default -> new Direction[]{};
        };
    }



    private static GameBoard generateBoardFomString(int rows, int cols, String boardString) {
        GameBoard gameBoard = new GameBoard();
        char[] signs = boardString.toCharArray();
        Direction[] entrances = new Direction[] {Direction.LEFT,Direction.RIGHT};
        Tile[][] tiles = new Tile[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = new Tile();
                tile.setEntrances(dirFromSign(signs[i * cols + j]));
                tiles[i][j] = tile;
            }
        }
        gameBoard.setRows(rows);
        gameBoard.setCols(cols);
        gameBoard.setTiles(tiles);
        gameBoard.printBoard();
        return gameBoard;
    }



    public static Direction[] rotateTileRight(Direction[] dir) {
        Direction[] rotated = new Direction[dir.length];
        for (int i = 0; i < dir.length; i++) {
            rotated[i] = switch (dir[i]) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
            };
        }


        return rotated;
    }



        private static  Direction inversDirection(Direction direction) {
            return switch (direction) {
                case UP -> Direction.DOWN;
                case DOWN -> Direction.UP;
                case LEFT -> Direction.RIGHT;
                case RIGHT -> Direction.LEFT;
            };
        }


}