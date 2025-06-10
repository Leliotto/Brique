package brique.view;

import brique.controller.GameController;
import brique.model.Move;
import brique.model.UnadmissibleMove;

import java.util.Scanner;

public class ConsoleView {

    private GameController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleView(GameController controller) {
        this.controller = controller;
    }
    public void start() throws InterruptedException {
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
            if (dim.length > 1) {controller.reshapeBoard(row, col);}
            else {controller.reshapeBoard(row);}
        }

        // Start the game
        boolean incorrect_move = true;
        do {
            Move move = null;

            do {
                System.out.println("In witch cell player " + controller.currentPlayer().toString() + " will play in turn "+ controller.waitingTurn() +"? " +
                        "\n'pie (row, column)' for a pie move" +
                        "\n'quit' to terminate the game");
                String[] input = scanner.nextLine().replaceAll("[^a-zA-Z0-9]", " ").trim().split(" ");

                int row = -1;
                int col = -1;
                boolean pieMove = false;
                if (input[0].equalsIgnoreCase("pie")){  //pie move
                    pieMove = true;
                    if (input.length > 1){
                        try{
                            row = Integer.parseInt(input[1]);
                            col = Integer.parseInt(input[2]);
                        } catch (NumberFormatException e) {
                        }
                    }
                }

                else if (input[0].equalsIgnoreCase("quit")) {
                    throw new InterruptedException();
                }

                else {
                    if (input.length > 1){ //play move
                        try{
                            row = Integer.parseInt(input[0]);
                            col = Integer.parseInt(input[1]);
                            pieMove = false;
                        } catch (NumberFormatException | UnadmissibleMove | IndexOutOfBoundsException e) {
                            System.out.println(e);
                            incorrect_move = true;
                        }
                    }
                    else { System.out.println("Invalid move");}

                }
                try{
                    move = new Move(row-1, col-1, controller.currentPlayer(), pieMove);
                    controller.makeMove(move);
                    incorrect_move = false;
                } catch (UnadmissibleMove | IndexOutOfBoundsException e) {
                    System.out.println(e);
                    incorrect_move = true;
                }

            }while (incorrect_move);

            System.out.println(controller.board());

        } while (!controller.winBoard());
        System.out.println("Player "+ controller.previusPlayer() + " have WIN!!!");
    }
}