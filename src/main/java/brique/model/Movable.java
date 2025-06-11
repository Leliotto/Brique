package brique.model;

@FunctionalInterface
public interface Movable {
    boolean cellOfPlayer(int row, int col, String direction);
}
// I need this Functional interface to create the lambda in GameControlle.isGoalCell
// not very usable interface