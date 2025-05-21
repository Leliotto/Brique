package brique;

import brique.controller.GameController;
import brique.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController();
        ConsoleView view = new ConsoleView(controller);
        view.start();
    }
}
