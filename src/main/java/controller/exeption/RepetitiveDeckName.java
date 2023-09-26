package controller.exeption;

public class RepetitiveDeckName extends Exception{
public RepetitiveDeckName(String deckName){super("deck with name " + deckName + " already exists");}
}
