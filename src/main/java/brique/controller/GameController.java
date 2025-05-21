package brique.controller;

import brique.model.*;

/**
 * Orchestrates turns, validates moves and applies game rules (escorts, pie, victory).
 */
public class GameController {

    private Board board = new Board();
    private Player currentPlayer = Player.BLACK; // Black starts
    private boolean pieOffered = true;

    public Board board() { return board; }
    public Player currentPlayer() { return currentPlayer; }

    public void play(Coordinate coordinate) {
        // TODO: validate, update board, toggle player, handle pie rule
    }

    public boolean isGameOver() {
        // TODO: implement win detection
        return false;
    }
}