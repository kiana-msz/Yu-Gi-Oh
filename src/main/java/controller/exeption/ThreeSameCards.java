package controller.exeption;

public class ThreeSameCards extends Exception {
    public ThreeSameCards(String cardName, String deckName) {
        super("there are already three cards with name " + cardName + " in deck " + deckName);
    }
}
