package brique.view;

import brique.controller.GameController;

import java.util.Scanner;

/**
 * Very simple text‑based UI for early testing.
 */
public class ConsoleView {

    private final GameController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(GameController controller) {
        this.controller = controller;
    }

    public void start() {
        while (!controller.isGameOver()) {
            System.out.println(controller.board());
            System.out.printf("%s to move. Enter row col: ", controller.currentPlayer());
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            // controller.play(new Coordinate(row, col));
        }
        System.out.println("Game over!");
    }
}
