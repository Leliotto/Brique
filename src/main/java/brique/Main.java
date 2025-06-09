package brique;

import brique.controller.GameController;
import brique.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController( "Black", "White");
        ConsoleView view = new ConsoleView(controller);
        //view.start();
    }
}
