package controller;

import javafx.scene.image.Image;
import model.*;

import java.util.ArrayList;

public class ShopController {

    private static ShopController instance = null;
    private final static ArrayList<Image> allImages = new ArrayList<>();
    private final static ArrayList<Card> allCards = (ArrayList<Card>)DeckController.getInstance(null).getAllCardsOfGame();

    public static ShopController getInstance() {
        if (instance == null) instance = new ShopController();
        return instance;
    }

    private ShopController() {
        initImages();
    }

    private void initImages() {
        for (Card card : allCards) {
            allImages.add(card.getImage());
        }
    }

    public ArrayList<Image> getImages(int start){
        ArrayList<Image> myImages = new ArrayList<>(4);
        for (int i = start; i < start+4; i++) {
            myImages.add(allImages.get(i));
        }
        return myImages;
    }

    public ArrayList<Card> getCards(int start){
        ArrayList<Card> cards = new ArrayList<>(4);
        for (int i = start; i < start+4; i++) {
            cards.add(allCards.get(i));
        }
        return cards;
    }

    public int getTotalCardsNumber(){
        return allImages.size();
    }


}
