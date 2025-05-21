package brique.model;

public enum Player {
    BLACK(StoneColor.BLACK),
    WHITE(StoneColor.WHITE);

    private final StoneColor stoneColor;

    Player(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor stone() {
        return stoneColor;
    }

    public Player opponent() {
        return this == BLACK ? WHITE : BLACK;
    }
}
