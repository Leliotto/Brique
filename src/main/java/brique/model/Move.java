package brique.model;

import javax.swing.*;

public class Move {
    public int color; //identified by 1, 2 or 21. first player, second player or pie role.
    private int row;
    private int column;

    public Move(int row, int column, char color) {
        this.row = row;
        this.column = column;
        if (color == 1 || color == 2 || color == 21) {
            this.color = color;
        }
        else { throw new IllegalArgumentException("color invalid, have to be 1 or 2\n"); }
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")" + color;
    }

    public int[] toNum(){
        return new int[] {this.row, this.column};
    }

    public boolean isPieMove(){
        return color == 21;
    }
}
