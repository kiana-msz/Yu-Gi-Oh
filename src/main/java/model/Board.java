package model;

import controller.DuelController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private static final int[] playerGroundNumbers = {2,3,1,4,0};
    private List<Card> cardsInHand;
    private List<Card> cardsInGraveyard;
    private MonsterCard[] monsters;
    private String[] monstersCondition;
    private Card[] spellsAndTraps;
    private String[] spellsAndTrapsCondition;
    private Card fieldZone;

    public Board() {
        this.cardsInHand = new ArrayList<>();
        this.cardsInGraveyard = new ArrayList<>();
        this.monsters = new MonsterCard[5];
        this.monstersCondition = new String[5];
        this.spellsAndTraps = new Card[5];
        this.spellsAndTrapsCondition = new String[5];
        this.fieldZone = null;
    }

    public List<Card> getCardsInHand() {
        return this.cardsInHand;
    }

    public Card getFieldZone() {
        if (this.fieldZone==null) {
            return null;
        } else return this.fieldZone;
    }

    public MonsterCard[] getMonsters() {
        return this.monsters;
    }

    public Card[] getSpellsAndTraps() {
        return this.spellsAndTraps;
    }

    public MonsterCard getMonsterByNumber(int number) {
        return this.monsters[number];
    }

    public Card getSpellAndTrapByNumber(int number) {
        return this.spellsAndTraps[number];
    }

    public String getMonsterConditionByNumber(int number) {
        return this.monstersCondition[number];
    }

    public String getSpellAndTrapConditionByNumber(int number) {
        return this.spellsAndTrapsCondition[number];
    }

    public List<Card> getCardsInGraveyard() {
        return this.cardsInGraveyard;
    }

    public Card getCardInHandByNumber(int number) {
        return this.cardsInHand.get(number);
    }

    public void removeCardFromHand(Card card) {
        this.cardsInHand.remove(card);
    }

    public void addCardToHand(Card card) {
        this.cardsInHand.add(card);
    }

    public int putMonster(MonsterCard monsterCard,String condition){
        for(int i=0;i<5;i++){
            if(this.monsters[playerGroundNumbers[i]] == null){
                this.monsters[playerGroundNumbers[i]] =monsterCard;
                this.monstersCondition[playerGroundNumbers[i]] =condition;
                return playerGroundNumbers[i];
            }
        }
        return 0;
    }

    public void removeMonster(int number, DuelController duelController,User owner) {
        this.monsters[number] = null;
        this.monstersCondition[number] = null;
        if(owner.getUsername().equals(duelController.getPlayer().getUsername())){
            duelController.setMonsterAttackPlayer(number,null);
            duelController.setMonsterDefencePlayer(number,null);
            duelController.getActionsOnThisCardPlayer(number).clear();
        }
        else {
            duelController.setMonsterAttackRival(number,null);
            duelController.setMonsterDefenceRival(number,null);
            duelController.getActionsOnThisCardRival(number).clear();
        }
    }

    public int putSpellOrTrap(Card card, String condition) {
        for(int i=0;i<5;i++){
            if(this.spellsAndTraps[playerGroundNumbers[i]] == null){
                this.spellsAndTraps[playerGroundNumbers[i]] =card;
                this.spellsAndTrapsCondition[playerGroundNumbers[i]] =condition;
                return playerGroundNumbers[i];
            }
        }
        return 0;
    }

    public void removeSpellOrTrap(int number) {
        this.spellsAndTraps[number] = null;
        this.spellsAndTrapsCondition[number] = null;
    }

    public void changeMonsterPosition(int index,String target){
        this.monstersCondition[index] = target;
    }

    public void changeSpellAndTrapPosition(int index,String target){
        this.spellsAndTrapsCondition[index] = target;
    }

    public boolean isFullSpellAndTrapZone(){
        for (int i = 0; i < 5; i++){
            if (this.spellsAndTraps[i] == null) return false;
        }
        return true;
    }

    public boolean isFullMonsterZone() {
        for (int i = 0; i < 5; i++) {
            if (this.monsters[i] == null) return false;
        }
        return true;
    }

    public void putInFieldZone(Card card) {
        this.fieldZone = card;
    }

    public void removeFromFieldZone() {
        this.fieldZone = null;
    }

    public void putInGraveYard(Card card) {
        this.cardsInGraveyard.add(card);
    }

}