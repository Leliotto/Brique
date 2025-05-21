package brique.tests;

import brique.model.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void newBoard_isEmpty() {
        Board board = new Board(3);
        assertNull(board.getStone(0, 0));
    }
}
