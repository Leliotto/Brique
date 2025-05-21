package brique.model;

import java.util.Arrays;

/**
 * Immutable board representation for the game Brique.
 * Size is currently fixed to a square board (e.g. 15×15) but can be made configurable later.
 */
public class Board {

    public static final int DEFAULT_SIZE = 15;
    private final int size;
    private final StoneColor[][] grid;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.size = size;
        this.grid = new StoneColor[size][size];
    }

    public int getSize() {
        return size;
    }

    public StoneColor getStone(int row, int col) {
        return grid[row][col];
    }

    public Board withMove(Move move) {
        // TODO: Apply move, escorts rule and return a NEW Board instance (functional style)
        return this;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(grid);
    }
}
