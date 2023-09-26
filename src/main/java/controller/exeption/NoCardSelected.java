package controller.exeption;

public class NoCardSelected extends Exception {
    public NoCardSelected() {
        super("no card is selected yet");
    }
}
