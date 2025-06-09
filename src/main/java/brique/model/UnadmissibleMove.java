package brique.model;

public class UnadmissibleMove extends RuntimeException {
    public UnadmissibleMove(String message) {
        super(message);
    }
}
