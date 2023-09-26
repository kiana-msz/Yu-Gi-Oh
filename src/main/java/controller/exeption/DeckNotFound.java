package controller.exeption;

public class DeckNotFound extends Exception {
    public DeckNotFound(String deckName) {
        super("deck with name " + deckName + " does not exist");
    }
}
