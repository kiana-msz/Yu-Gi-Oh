package controller.exeption;

public class CardNotFoundForController extends Exception {
    public CardNotFoundForController() {
        super("there is no card with this name");
    }
}
