package controller.exeption;

public class InsufficientForTribute extends Exception {
    public InsufficientForTribute() {
        super("there are not enough cards for tribute");
    }
}
