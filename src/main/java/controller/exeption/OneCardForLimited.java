package controller.exeption;

public class OneCardForLimited extends Exception {
    public OneCardForLimited(String cardName, String deckName) {
        super("there is already one card with name " + cardName + " in deck " + deckName);
    }
}
