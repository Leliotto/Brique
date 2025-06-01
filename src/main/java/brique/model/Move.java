package brique.model;

import javax.swing.*;

public class Move {
    private Player player;
    private int row;
    private int column;
    private boolean pieMove;

    public Move(int row, int column, Player player) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.pieMove = false;
    }
    public Move(int row, int column, Player player, boolean pieMove) {
        this.row = row;
        this.column = column;
        this.player = player;
        this.pieMove = pieMove;
    }

    @Override
    public String toString() {
        if(pieMove) {
            return player.name() + "Pie Move";
        }
        return "(" + row + ", " + column + ")" + player.name();
    }

    public int[] toNum(){
        return new int[] {this.row, this.column};
    }

    public boolean isPieMove(){
        return pieMove;
    }
    public Player player(){
        return player;
    }
}
