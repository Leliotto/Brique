package brique.model;
import java.util.Arrays;


public class Board implements Cloneable {

    private int size1; //height
    private int size2; //length
    private Player default_player = new Player();
    private Player[][] grid;

    public Board(int size1, int size2) {
        this.size1 = size1;
        this.size2 = size2;
        this.grid = createGrid(size1, size2);
    }

    public Board(int size) {
        this.size1 = size;
        this.size2 = size;
        this.grid = createGrid(size1, size2);

    }

    public Board() {
        this.size1 = 15;
        this.size2 = 15;
        this.grid = createGrid(size1, size2);
    }

    private Player[][] createGrid(int size1, int size2) { //per creare la griglia di default
        Player[][] matrix = new Player[size1][size2];
        for (Player[] row : matrix)
            Arrays.fill(row, default_player);
        return matrix;
    }

    public Player positionIsOf(int[] position) {
        if (position[0] < this.size1 && position[0] >= 0
                && position[1] < this.size2 && position[1] >= 0
        ) return this.grid[position[0]][position[1]];
        throw new IndexOutOfBoundsException("index out of board");
    }

    public boolean positionIsFree(int[] position) {
        return this.positionIsOf(position) == default_player;
    }

    public void setCellColor(Move move) {
        this.grid[move.toNum()[0]][move.toNum()[1]] = move.player();
    }

    public int[] getSize() {
        return new int[]{size1, size2};
    }

    public Player getDefaultPlayer() {
        return this.default_player;
    }

    public Player[][] getGrid() { //torna una copia della griglia della board
        Player[][] new_grid = new Player[size1][size2];
        for (int i = 0; i < size1; i++) {
            new_grid[i] = grid[i].clone();
        }
        return new_grid;
    }

    public void rotateGrid() { //Ruota di 90° la griglia della board
        Player[][] new_grid = new Player[size2][size1];
        for (int i = 0; i < size2; i++) {
            for (int j = 0; j < size1; j++) {
                new_grid[i][j] = grid[j][i];
            }
        }
        this.grid = new_grid;
        int tmp = size1;
        size1 = size2;
        size2 = tmp;
    }

    @Override
    public String toString() {
        String string = "";
        for (Player[] row : grid) {
            string += Arrays.toString(row) + "\n";
        }
        return string;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board copy = (Board) super.clone();
        copy.default_player = this.default_player;
        copy.grid = this.getGrid();
        return copy;
    }
}