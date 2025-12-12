package com.mci;
import static com.mci.GameBoardAlt.printBoard;

public class DemoBoardGenerator {

    public static GameBoard createDemoBoard(BoardSize size) {

        GameBoardAlt alternative = GameBoardAlt.generateBoard(size);

        GameBoard board = new GameBoard();
        board.setCols(size.getCols());
        board.setRows(size.getRows());
        board.setTiles(alternative.getTiles());

        printBoard(alternative);

        return board;
    }
}