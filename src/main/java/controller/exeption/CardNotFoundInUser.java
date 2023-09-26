package controller.exeption;

public class CardNotFoundInUser extends Exception {
    public CardNotFoundInUser(String cardName) {
        super("card with name " + cardName + " does not exist");
    }
}