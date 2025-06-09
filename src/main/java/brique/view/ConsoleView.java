package brique.view;

import brique.controller.GameController;
import brique.model.Move;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleView {

    private GameController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(GameController controller) {
        this.controller = controller;
    }
    public void start() throws IOException, InterruptedException {
        // name input
        System.out.println("Name of player 1");
        String play1 = scanner.nextLine().trim();
        System.out.println("Name of player 2");
        String play2 = scanner.nextLine().trim();
        controller = new GameController(play1, play2);

        //handling of non standar dimension of the board
        System.out.println("Will play in a non standard board (15*15)?(y/n)");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")){
            boolean correct_dimensions = true;
            String[] dim = null;
            int col = 0;
            int row = 0;
            do {
                System.out.println("Dimension/s");
                dim = scanner.nextLine().trim().replaceAll("[^a-zA-Z0-9\\s]", " ").split(" ");
                try {
                    row = Integer.parseInt(dim[0]);
                    if (dim.length > 1) {col = Integer.parseInt(dim[1]);}
                    correct_dimensions = false;
                } catch (NumberFormatException e) {
                    System.out.println("Error: non valid dimension");
                }
            } while (correct_dimensions);
            if (dim.length > 1) {controller.reschapeBoard(row, col);}
            else {controller.reschapeBoard(col);}
        }

        // Start the game
        boolean incorrect_move = true;
        do {
            Move move = null;
            do {
                System.out.println("In witch cell player" + controller.currentPlayer().toString() + "will play? " +
                        "\n 'pie' for a pie move" +
                        "\n'quit()' to terminate the game");
                String input = scanner.nextLine().trim();
                input = input.replaceAll("[^a-zA-Z0-9]", " ").trim();
                if (input.equalsIgnoreCase("pie")){
                    move = new Move(0, 0, controller.currentPlayer(), true);
                    incorrect_move = false;
                } else if (input.equalsIgnoreCase("quit()")) {
                    throw new InterruptedException();
                } else {
                    String[] coordinate = input.split(" ");
                    if (coordinate.length > 1){ //play move
                        try{
                            int row = Integer.parseInt(coordinate[0]);
                            int col = Integer.parseInt(coordinate[1]);
                            move = new Move(row, col, controller.currentPlayer(), false);
                            incorrect_move = false;
                        } catch (NumberFormatException e) {
                            incorrect_move = true;
                        }
                    }
                    else { System.out.println("Error: invalid move");}
                }
            }while (incorrect_move);

            controller.makeMove(move);
            System.out.println(controller.board());

        } while (!controller.winBoard());
        System.out.println("Game over!");
    }
}