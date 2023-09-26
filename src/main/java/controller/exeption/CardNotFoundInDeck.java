package controller.exeption;

public class CardNotFoundInDeck extends Exception {
    public CardNotFoundInDeck(String cardName, String sideOrMain) {
        super("card with name " + cardName + " does not exist in " + sideOrMain + " deck");
    }
}
