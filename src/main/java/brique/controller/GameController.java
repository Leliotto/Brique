package brique.controller;

import brique.model.*;

import java.util.Objects;

/*
Orchestrates turns, validates moves and applies game rules (escorts, pie, victory).
 */
public class GameController {

    private Board board = new Board();
    private Player firstPlayer = new Player();
    private Player secondPlayer = new Player(); // Black starts
    private int waitingTurn = 1; // I'm waiting the move of the n° turn

    public GameController(){} // creation without set players' names

    public GameController(String name1, String name2) {
        this.firstPlayer = new Player(name1);
        this.secondPlayer = new Player(name2);
    }

    public void reschapeBoard(int dim){
        board = new Board(dim);
    }

    public void reschapeBoard(int row, int col){
        board = new Board(row, col);
    }

    public Board board() { return board; }

    public Player currentPlayer() {
        if (waitingTurn%2 == 0) return secondPlayer;
        return firstPlayer;
    }

    public Player previusPlayer(){
        if (waitingTurn%2 == 1) return secondPlayer;
        return firstPlayer;
    }

    public boolean makeMove (Move move) throws UnadmissibleMove {
        if (move.isPieMove()){
            if (waitingTurn != 2) throw new UnadmissibleMove("Can't excecuter pie roule int turn " + waitingTurn );
            board.setCellColor(move);
            waitingTurn +=1;
            return true;
        }
        if (move.player() == currentPlayer() && positionIsFree(move)){
            board.setCellColor(move);
            waitingTurn +=1;
            return true;
        }
        throw new UnadmissibleMove("Player " + (2-waitingTurn%2) + ": "+ move.player().toString() +
                " can't play in position " + move.toNum() + " in turn " + waitingTurn);
    }

    public boolean positionIsFree(Move move) {return board.positionIsFree(move.toNum());}

    public boolean winBoard(){
        return winBoard(this.previusPlayer());
    }

    public boolean winBoard(Player player) {
        Board new_board = null;
        try { new_board = (Board) this.board.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); }
        
        if (player == firstPlayer){
            //il primo giocatore nero vince se si collega il lato superiore con quello inferiore
            //ruotando la griglia torniamo a voler collegare il lato di destra con quello di sinistra
            new_board.rotateGrid();
        }
        
        for (int i = 0; i < new_board.getSize()[0]; i++){
            if(new_board.positionIsOf(new int[] {i,0}) == player){
                return this.isGoalCell(new_board, new int[] {i,0}, player);
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