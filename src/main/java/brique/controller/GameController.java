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

    public int waitingTurn() {
        return waitingTurn;
    }

    public void reshapeBoard(int dim){
        board = new Board(dim);
    }

    public void reshapeBoard(int row, int col){
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
            if (waitingTurn != 2) throw new UnadmissibleMove("Can't excecute pie roule in turn " + waitingTurn );
            if (board.positionIsOf(move.toNum()) != previusPlayer()){
                throw new UnadmissibleMove("Can't play pie move in position "+ move.toNum().toString());
            }
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
        // use this function to determinate if the player have win
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
                return this.isGoalBoard(new_board, new int[] {i,0}, player);
            }
        }
    return false;
    }

    private boolean isGoalBoard(Board board, int[] pos, Player player){

        // BE CAREFUL: this function modify the board

        //here we use an int[] array instead of Move because we are looking if the position is a goal position for the player
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
                    return isGoalBoard(board, new int[] {row, column}, player);
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

    public void escortMove (Move last_move) { //to see if the last move had trigger the escort-rule
        int[] move = last_move.toNum();
        int last_row = move[0];
        int last_col = move[1];
        try {
            if (board.positionIsOf(new int[]{last_row + 1, last_col - 1}) == last_move.player()) { //look to the down-left cell
                if ((last_row + last_col) % 2 == 0) { //white cell
                    board.setCellColor(new Move(last_row, last_col - 1, last_move.player()));
                } else { //black cell
                    board.setCellColor(new Move(last_row + 1, last_col, last_move.player()));
                }
            }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (board.positionIsOf(new int[]{last_row - 1, last_col + 1}) == last_move.player()) { //look to the up-right cell
                if ((last_row + last_col) % 2 == 0) { //white cell
                    board.setCellColor(new Move(last_row - 1, last_col, last_move.player()));
                } else { //black cell
                    board.setCellColor(new Move(last_row, last_col + 1, last_move.player()));
                }
            }
        } catch (IndexOutOfBoundsException e) {}
    }

    public void escortGrid() { //to escort the entire grid
        for (int col = 0; col < board.getSize()[0]; col++){ //white cell
            for (int row = col%2; row < board.getSize()[1]; row+=2){
                if(board.positionIsOf(new int[] {row, col}) == board.positionIsOf(new int[] {row+1, col-1})){
                    board.setCellColor(new Move( row,  col-1 , board.positionIsOf(new int[] {row, col})));
                }
            }
        }
        for (int col = 0; col < board.getSize()[0]; col++){ //white cell
            for (int row = (col+1)%2; row < board.getSize()[1]; row+=2){
                if(board.positionIsOf(new int[] {row, col}) == board.positionIsOf(new int[] {row+1, col-1})){
                    board.setCellColor(new Move (row+1, col , board.positionIsOf(new int[] {row, col})));
                }
            }
        }
    }
}