package brique.utils;
// pierules : prendi la board e la mossa appena fatta e modificala con la regola del pie
// criterio di vincita

import brique.model.Move;
import brique.model.Board;



public final class ValidationUtil {
    private ValidationUtil() {}

    public static boolean validMove (Move move, Board board) {
        int[] valid_move = move.toNum();
        if(move.isPieMove()) return true;
        return board.positionIsFree(valid_move); //position free
    }

    public static void escortMove (Board board, Move last_move) { //for see if the last move had trigger the escort-rule
        int[] move = last_move.toNum();
        int last_row = move[0];
        int last_col = move[1];

        if (board.positionIsOf(new int[] {last_row+1, last_col-1}) == last_move.color){ //look to the down-left cell
            if ((last_row + last_col)%2 == 0) { //white cell
                board.setCellColor(new int[] {last_row, last_col-1}, last_move.color);
            } else { //black cell
                board.setCellColor(new int[] {last_row + 1, last_col}, last_move.color);
            }
        }

        if (board.positionIsOf(new int[] {last_row-1, last_col+1}) == last_move.color){ //look to the up-right cell
            if ((last_row + last_col)%2 == 0) { //white cell
                board.setCellColor(new int[] {last_row-1, last_col}, last_move.color);
            } else { //black cell
                board.setCellColor(new int[] {last_row , last_col+1}, last_move.color);
            }
        }
    }

    public static void escortGrid(Board board) { //for escort the entire grid
        for (int col = 0; col < board.getSize(); col++){ //white cell
            for (int row = col%2; row < board.getSize(); row+=2){
                if(board.positionIsOf(new int[] {row, col}) == board.positionIsOf(new int[] {row+1, col-1})){
                    board.setCellColor(new int[] {row, col-1}, board.positionIsOf(new int[] {row, col}));
                }
            }
        }

        for (int col = 0; col < board.getSize(); col++){ //white cell
            for (int row = (col+1)%2; row < board.getSize(); row+=2){
                if(board.positionIsOf(new int[] {row, col}) == board.positionIsOf(new int[] {row+1, col-1})){
                    board.setCellColor(new int[] {row+1, col}, board.positionIsOf(new int[] {row, col}));
                }
            }
        }
    }
/* TODO:
    public static void pieRoles (Board board, Move move) {
        if (//move stack have one element && move.isPieMove())
            board.setCellColor(//first_move, //different color);
    }*/


}
