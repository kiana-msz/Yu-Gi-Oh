package model;


import controller.DeckController;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

public interface Card extends Serializable {
    String getName();
    String getNamePascalCase();
    String getDescription();
    int getPrice();
    @Override
    String toString();
    Image getImage();

    static Card getCardByImage(Image image) {
        ArrayList<Card> allCards = (ArrayList<Card>)DeckController.getInstance(null).getAllCardsOfGame();
        for (Card card : allCards) {
            if(card.getImage().equals(image))
                return card;
        }
        return null;
    }

    static Card getCardByName(String cardName) {
        ArrayList<Card> allCards = (ArrayList<Card>)DeckController.getInstance(null).getAllCardsOfGame();
        for (Card card : allCards) {
            if(card.getName().equals(cardName))
                return card;
        }
        return null;
    }
}
