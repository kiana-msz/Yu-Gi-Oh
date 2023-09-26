package controller.exeption;

public class FullSideDeck extends Exception {
    public FullSideDeck() {
        super("side deck is full");
    }
}
