package com.mci;

public class PushTile {

private  int rowOrColumn;
private GameBoard board;
private GameBoard boardOriginal;
private int breite = 0;
private int hoehe = 0;
private Tile[][] karten;
private Tile plate;
private Tile plateOriginal;


public PushTile(GameBoard board, Tile tile) {


    this.boardOriginal = board;
    this.board = new GameBoard();
    this.board = boardOriginal.deepCopy();

    this.plateOriginal = tile;
    this.plate= new Tile(plateOriginal);

    this.karten=board.getTiles();
    }


    public GameBoard getOriginalBoard() {return boardOriginal;}
    public GameBoard getPushedBoard() {return board;}

    public void setPushPlate(Tile tile) {
    this.plateOriginal = tile;
    this.plate = tile;
}

    public Tile getPushPlate() {

        return this.plate;
    }


    public void setOrigin(){

        this.board = new GameBoard(boardOriginal);
        this.plate= new Tile(plateOriginal);
    }

    public void setOriginBoard(){
        this.board = new GameBoard(boardOriginal);
    }





    public Tile pushPlate(int row, int col,  int direction){
        Tile temp;
        this.karten=board.getTiles();
        this.breite = this.board.getRows();
        this.hoehe = this.board.getCols();
        switch(direction) {
            case 1: //left
                System.out.println("Push left");
                // Logik zum Schieben der Platte nach links implementieren
                temp = new Tile(karten[row][0]);
                for(int j = 0; j < breite - 1; j++) {
                    karten[row][j] = new Tile(karten[row][j + 1]);
                }
                karten[row][breite-1] = new Tile(plate);
                return temp;
            //break;
            case 2: //right
                System.out.println("Push right");
                // Logik zum Schieben der Platte nach rechts implementiere
                temp = karten[row][breite-1];
                for(int k = breite - 1; k > 0; k--){
                    karten[row][k] = karten[row][k - 1];
                }
                karten[row][0] = plate;
                return temp;
            //break;
            case 3: //up
                System.out.println("Push up");
                // Logik zum Schieben der Platte nach oben implementieren
                temp = karten[0][col];
                for(int l = 0 ; l <hoehe - 1; l++){
                    karten[l][col] = karten[l+1][col];
                }
                karten[hoehe-1][col] = plate;
                return temp;
            //break;


            case 4: //down
                System.out.println("Push down");
                // Logik zum Schieben der Platte nach unten implementieren
                temp = karten[hoehe-1][col];
                for(int l = hoehe-1 ; l>0 ; l--){
                    karten[l][col] = karten[l-1][col];
                }
                karten[0][col] = plate;
                return temp;
            //break;

            default:
                throw new IllegalArgumentException("Ungültige Richtung zum Schieben der Platte.");

        }

        //return temp;

    };

    public void rotateTileRight() {
        if (plate == null || plate.getEntrances() == null) return;

        Direction[] old = plate.getEntrances();
        Direction[] rotated = new Direction[old.length];

        for (int i = 0; i < old.length; i++) {
            rotated[i] = switch (old[i]) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
            };
        }

        plate.setEntrances(rotated);
    }

    /**
     * Dreht das Tile nach rechts (90° Rotation).
     */
    public  Tile rotateTileRightExtern(Tile tile) {
        if (tile == null || tile.getEntrances() == null) return null;

        Direction[] old = tile.getEntrances();
        Direction[] rotated = new Direction[old.length];

        for (int i = 0; i < old.length; i++) {
            rotated[i] = switch (old[i]) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
            };
        }

        tile.setEntrances(rotated);
        return tile;
    }

    public  void testBoard(GameBoard original){


// z.B.:
        Tile[][] tiles = original.getTiles();
        tiles[0][0].getTreasure().setName("Gold");
        original.setTiles(tiles);

        GameBoard copy = boardOriginal.deepCopy();
// Jetzt ein Feld im Copy ändern:
        copy.getTiles()[0][0].getTreasure().setName("Diamant");

// Vergleiche
        System.out.println("Original Treasure: " +
                original.getTiles()[0][0].getTreasure().getName());
        System.out.println("Copy Treasure    : " +
                copy.getTiles()[0][0].getTreasure().getName());
        Tile originalTile = original.getTiles()[0][0];
        Tile copiedTile   = copy.getTiles()[0][0];

        System.out.println("Tile == ? " + (originalTile == copiedTile));
        System.out.println("Treasure == ? " + (originalTile.getTreasure() == copiedTile.getTreasure()));
        System.out.println("Bonus == ? " + (originalTile.getBonus() == copiedTile.getBonus()));





    }




}
