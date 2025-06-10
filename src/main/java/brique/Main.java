package brique;

import brique.controller.GameController;
import brique.view.ConsoleView;
import brique.model.Board;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController( "Black", "White");
        ConsoleView view = new ConsoleView(controller);
        Scanner scanner = new Scanner(System.in);
        try{view.start();}
        catch(InterruptedException e){}
        System.out.println("'Return' to end the game");
        scanner.nextLine();

    }
}
