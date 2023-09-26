package controller.exeption;

public class CardNotFound extends Exception {
    public CardNotFound(String cardName) {
        super("card with name " + cardName + " does not exist");
    }
}
