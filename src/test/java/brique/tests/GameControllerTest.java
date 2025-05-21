package brique.tests;

import brique.controller.GameController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @Test
    void blackStartsFirst() {
        GameController gc = new GameController();
        assertEquals("BLACK", gc.currentPlayer().name());
    }
}
