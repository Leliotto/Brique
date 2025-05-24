package brique.model;

import java.util.Arrays;


public class Board {

    private final int size;
    private final int[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new int[size][size];
    }

    public Board() {
        this.size = 15;
        this.grid = new int[size][size];
        for (int i = 0 ; i < size ; i++) { //TODO: improve this
            for (int j = 0 ; j < size ; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public int positionIsOf(int[] position) {
        if (position[0]<this.size && position[0]>=0
                && position[1]<this.size && position[1]>=0
            ) return this.grid[position[0]][position[1]];
        return -1;
    }

    public boolean positionIsFree(int[] position) {
        return this.positionIsOf(position) == 0;
    }

    public void setCellColor (int[] position, int color) {
        this.grid[position[0]][position[1]] = color;
    }

    public int getSize() {
        return size;
    }

    public int[][] getGrid() { return grid;}

    public boolean winBoard(Move move){
        int player = move.color;
        int[][] matrix = new int[size][size];
        // first. create a copy of the grid with the player's side on the left
        switch (player){
            case 2: for (int i = 0; i < size; i++) {
                matrix[i] = grid[i].clone();
                }
            case 1:
                for (int i = 0; i < size; i++) {
                    for(int j = 0; j < size; j++){
                        matrix[i][j] = grid[j][i];
                    }
            }
        }
        for (int i = 0; i < size; i++){
            if(matrix[i][0] == player){
                return this.isGoalCell(matrix, new int[] {i,0}, player);
            }
        }
    return false;
    }

    private boolean isGoalCell(int[][] matrix, int[] pos, int player){
        boolean up = false, down = false, left = false, right = false;
        if(matrix[pos[0]] [pos[1]+1] == -1) return true;

        if(matrix[pos[0]+1] [pos[1]] == player){
            matrix[pos[0]+1] [pos[1]] = 3;
            down = this.isGoalCell(matrix, new int[] {pos[0]+1, pos[1]}, player);}
        if(matrix[pos[0]-1] [pos[1]] == player){
            matrix[pos[0]-1] [pos[1]] = 3;
            up = this.isGoalCell(matrix, new int[] {pos[0]-1, pos[1]}, player);}
        if(matrix[pos[0]] [pos[1]+1] == player){
            matrix[pos[0]] [pos[1]+1] = 3;
            right = this.isGoalCell(matrix, new int[] {pos[0], pos[1]+1}, player);}
        if(matrix[pos[0]+1] [pos[1]-1] == player){
            matrix[pos[0]] [pos[1]-1] = 3;
            left = this.isGoalCell(matrix, new int[] {pos[0], pos[1]-1}, player);}
        return (up || down || left || right);
    }

    @Override
    public String toString() {
        String string = "";
        for (int[] row : grid) {
            string += Arrays.toString(row) + "\n";
        }
        return string;
    }
}
