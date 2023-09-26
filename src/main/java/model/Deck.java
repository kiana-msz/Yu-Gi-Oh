package model;

import java.io.Serializable;
import java.util.*;

public class Deck implements Serializable {
    private String deckName;
    private List<Card> mainDeck;
    private List<Card> sideDeck;

    public Deck(String deckName) {
        this.deckName = deckName;
        this.mainDeck = new ArrayList<>();
        this.sideDeck = new ArrayList<>();
    }


    public String getDeckName() {
        return this.deckName;
    }

    public List<Card> getMainDeck() {
        return this.mainDeck;
    }

    public List<Card> getSideDeck() {
        return this.sideDeck;
    }

    public int getSideSize() {
        return this.sideDeck.size();
    }

    public int getMainSize() {
        return this.mainDeck.size();
    }

    public void setDeck(ArrayList<Card> mainCards, ArrayList<Card> sideCards) {
        this.mainDeck = mainCards;
        this.sideDeck = sideCards;
    }

    public void addCardToMainDeck(Card card) {
        this.mainDeck.add(card);
    }

    public void addCardToSideDeck(Card card) {
        this.sideDeck.add(card);
    }

    public void removeCardFromMainDeck(Card card) {
        this.mainDeck.remove(card);
    }

    public void removeCardFromSideDeck(Card card) {
        this.sideDeck.remove(card);
    }

    public int numberOfWantedCard(Card wantedCard) {
        int number = 0;
        for (Card eachCard : this.sideDeck) {
            if (eachCard.equals(wantedCard))
                number++;
        }
        for (Card eachCard : this.mainDeck) {
            if (eachCard.equals(wantedCard))
                number++;
        }
        return number;
    }

    public boolean isValid() {
        return (this.mainDeck.size() >= 40);
    }

    public boolean cardExistsInDeck(Card card, boolean isSide) {
        if (isSide) {
            return this.sideDeck.contains(card);
        } else {
            return this.mainDeck.contains(card);
        }
    }

    @Override
    public String toString() {
        return this.deckName + ": main deck " + this.mainDeck.size() + ", side deck " + this.sideDeck.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(this.deckName, deck.deckName);
    }

}
