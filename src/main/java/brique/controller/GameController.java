package brique.controller;

import brique.model.*;

import java.util.Objects;

/**
 * Orchestrates turns, validates moves and applies game rules (escorts, pie, victory).
 */
public class GameController {

    private Board board = new Board();
    private Player firstPlayer = new Player();
    private Player secondPlayer = new Player(); // Black starts
    private boolean pieOffered = true;

    public Board board() { return board; }
    public Player currentPlayer() { return firstPlayer; }


    public boolean winBoard(Player player, Board board) {
        try {board = (Board) board.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); }
        
        if (player == firstPlayer){
            //il primo giocatore nero vince se si collega il lato superiore con quello inferiore
            //ruotando la griglia torniamo a voler collegare il lato di destra con quello di sinistra
            board.rotateGrid();
        }
        
        for (int i = 0; i < board.getSize()[0]; i++){
            if(board.positionIsOf(new int[] {i,0}) == player){
                return this.isGoalCell(board, new int[] {i,0}, player);
            }
        }
    return false;
    }

    private boolean isGoalCell(Board board, int[] pos, Player player){ 
        //here we don't use Move because we are loking if the position is a goal position for the player
        boolean up, down, left, right;

        Movable lambda = (row_ofset, column_ofset, direction) -> {
            // this lambda look the near cell and see if it can be a possible cell for a winning path
            try {
                int row = pos[0] + row_ofset;
                int column = pos[1] + column_ofset;
                if (board.positionIsOf(new int[]{row, column}) == player){
                    board.setCellColor(new Move(row, column, board.getDefaultPlayer()) );
                    //riassegno il valore della cella in maniera che chiamate successive non vadano
                    // a considerare nuovamente quella cella
                    return isGoalCell(board, new int[] {row, column}, player);
                } else {
                    return false;
                }
            }
            catch (IndexOutOfBoundsException e) { //se cado fuori dalla board andando a destra ho vinto
                return Objects.equals(direction, "right");
                }
        };

        left = lambda.cellOfPlayer(0, -1, "left");
        right = lambda.cellOfPlayer(0, +1, "right");
        up = lambda.cellOfPlayer(-1, 0, "up");
        down = lambda.cellOfPlayer(+1, 0, "down");

        return (up || down || left || right);
    }
}