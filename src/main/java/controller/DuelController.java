package controller;

import controller.exeption.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.*;
import view.DuelView;
import view.GameViewGraphic;

import java.net.URL;
import java.util.*;

public class DuelController {

    public static final int[] playerGroundNumbers = {3, 4, 2, 5, 1};
    private GameViewGraphic gameViewGraphic;
    private User player;
    private User rival;
    private final Round[] rounds;
    private final int roundNumber;
    public SelectedCard selectedCard;
    private int roundCounter;
    private Phase phase;
    public boolean hasSummonedOrSetInThisTurn;
    public ArrayList<ArrayList<ActionsDoneInTurn>> actionsOnThisCardPlayer = new ArrayList<>(5);
    private final ArrayList<ArrayList<ActionsDoneInTurn>> actionsOnThisCardRival = new ArrayList<>(5);
    private Integer[] playerAttackPoints = new Integer[5];
    private Integer[] rivalAttackPoints = new Integer[5];
    public Integer[] playerDefencePoints = new Integer[5];
    public Integer[] rivalDefencePoints = new Integer[5];
    public boolean isStartTurn;
    public boolean hasUsedSpellOrTrap = false;
    private int numberOfChangedTurns = 0;


    public DuelController(User player, User rival, int roundNumber, GameViewGraphic gameViewGraphic) {
        this.player = player;
        this.rival = rival;
        this.gameViewGraphic = gameViewGraphic;
        setGameDeck(this.player);
        setGameDeck(this.rival);
        this.rounds = new Round[roundNumber];
        this.roundNumber = roundNumber;
        this.roundCounter = 0;
        startNewGame(null);
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public ArrayList<ActionsDoneInTurn> getActionsOnThisCardPlayer(int i) {
        return actionsOnThisCardPlayer.get(i);
    }

    public ArrayList<ActionsDoneInTurn> getActionsOnThisCardRival(int i) {
        return actionsOnThisCardRival.get(i);
    }

    public void setDuelView(GameViewGraphic gameViewGraphic) {
        this.gameViewGraphic = gameViewGraphic;
    }

    private void setGameDeck(User user) {
        Deck deck;
        if (user.getDeckByName("@" + user.getActiveDeck().getDeckName()) == null) {
            deck = new Deck("@" + user.getActiveDeck().getDeckName());
        } else {
            deck = user.getDeckByName("@" + user.getActiveDeck().getDeckName());
            user.removeDeck(deck);
        }
        ArrayList<Card> mainCards = new ArrayList<>(user.getActiveDeck().getMainDeck());
        ArrayList<Card> sideCards = new ArrayList<>(user.getActiveDeck().getSideDeck());
        deck.setDeck(mainCards, sideCards);
        user.addDeck(deck);
        user.setGameDeck(deck);
    }

    public User getPlayer() {
        return this.player;
    }

    public User getRival() {
        return this.rival;
    }

    public int getNumberOfChangedTurns() {
        return numberOfChangedTurns;
    }

    public boolean getHasEnabledSuijin(int i) {
        return actionsOnThisCardRival.get(i).contains(ActionsDoneInTurn.ENABLE_SUIJIN);
    }

    public void setHasEnabledSuijinTrue(int i) {
        actionsOnThisCardRival.get(i).add(ActionsDoneInTurn.ENABLE_SUIJIN);
    }

    public void setMonsterAttackPlayer(int i, Integer number) {
        this.playerAttackPoints[i] = number;
    }

    public void setMonsterAttackRival(int i, Integer number) {
        this.rivalAttackPoints[i] = number;
    }

    public void setMonsterDefencePlayer(int i, Integer number) {
        this.playerDefencePoints[i] = number;
    }

    public void setMonsterDefenceRival(int i, Integer number) {
        this.rivalDefencePoints[i] = number;
    }

    public SelectedCard getSelectedCard() {
        return this.selectedCard;
    }

    public static int[] getPlayerGroundNumbers() {
        return playerGroundNumbers;
    }

    public void setSelectedCard(SelectedCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    private void newActionsOnThisCardPlayer() {
        ArrayList<ActionsDoneInTurn> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.actionsOnThisCardPlayer.add(arrayList);
        }
    }

    private void newActionsOnThisCardRival() {
        ArrayList<ActionsDoneInTurn> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.actionsOnThisCardRival.add(arrayList);
        }
    }

    public void changeAllAttackPoints(int increaseOrDecrease, int amount) {
        for (int i = 0; i < 5; i++) {
            if (this.playerAttackPoints[i] != null)
                this.playerAttackPoints[i] = this.playerAttackPoints[i] + increaseOrDecrease * amount;
            if (this.rivalAttackPoints[i] != null)
                this.rivalAttackPoints[i] = this.rivalAttackPoints[i] + increaseOrDecrease * amount;
        }
    }

    public void changePlayerAttackPoint(int address, int amount) {
        this.playerAttackPoints[address] += amount;
    }

    public void changeRivalAttackPoint(int address, int amount) {
        this.rivalAttackPoints[address] += amount;
    }

    public void changePlayerDefencePoint(int address, int amount) {
        this.playerDefencePoints[address] += amount;
    }

    public void changeRivalDefencePoint(int address, int amount) {
        this.rivalDefencePoints[address] += amount;
    }

    public void startNewGame(User winner) {
        if (winner != null) {
            User loser;
            if (winner.equals(this.player)) {
                loser = this.rival;
            } else {
                loser = this.player;
            }
            this.player = loser;
            this.rival = winner;
        } else this.isStartTurn = true;
        this.player.setLifePoint(8000);
        this.rival.setLifePoint(8000);
        this.player.setNewBoard();
        this.rival.setNewBoard();
        clearLastTurn();
        startDrawPhase(true);
    }

    public void selectCardPlayerMonsterZone(int address) {
        MonsterCard[] monsters = this.player.getBoard().getMonsters();
        if ((address > 5) || (address < 1)) {
            return;
        }
        address = playerGroundNumbers[address - 1] - 1;
        if (monsters[address] != null) {
            this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(address), BoardZone.MONSTERZONE, address, this.player);
        }
    }

    public void selectCardOpponentMonsterZone(int address) {
        MonsterCard[] monsters = this.rival.getBoard().getMonsters();
        if ((address > 5) || (address < 1)) {
            return;
        }
        address = playerGroundNumbers[address - 1] - 1;
        if (monsters[address] != null) {
            this.selectedCard = new SelectedCard(this.rival.getBoard().getMonsterByNumber(address), BoardZone.MONSTERZONE, address, this.rival);
        }
    }

    public void selectCardPlayerTrapAndSpellZone(int address) {
        Card[] spellAndTrap = this.player.getBoard().getSpellsAndTraps();
        if ((address > 5) || (address < 1)) {
            return;
        }
        address = playerGroundNumbers[address - 1] - 1;
        if (spellAndTrap[address] != null) {
            this.selectedCard = new SelectedCard(this.player.getBoard().getSpellAndTrapByNumber(address), BoardZone.SPELLANDTRAPZONE, address, this.player);
        }
    }

    public void selectCardOpponentTrapAndSpellZone(int address) {
        Card[] spellAndTrap = this.rival.getBoard().getSpellsAndTraps();
        if ((address > 5) || (address < 1)) {
            return;
        }
        address = playerGroundNumbers[address - 1] - 1;
        if (spellAndTrap[address] != null) {
            this.selectedCard = new SelectedCard(this.rival.getBoard().getSpellAndTrapByNumber(address), BoardZone.SPELLANDTRAPZONE, address, this.rival);
        }
    }

    public void selectCardPlayerFieldZone(int number) {
        Card fieldZone = this.player.getBoard().getFieldZone();
        if (fieldZone != null) {
            this.selectedCard = new SelectedCard(this.player.getBoard().getFieldZone(), BoardZone.FIELDZONE, 1, this.player);
        }
    }

    public void selectCardOpponentFieldZone(int number) {
        Card fieldZone = this.rival.getBoard().getFieldZone();
        if (fieldZone != null) {
            this.selectedCard = new SelectedCard(this.rival.getBoard().getFieldZone(), BoardZone.FIELDZONE, 1, this.rival);
        }
    }

    public void selectCardPlayerHand(int address) {
        List<Card> cardsInHand = this.player.getBoard().getCardsInHand();
        if (!((address > cardsInHand.size()) || (address < 1)) && cardsInHand.get(address - 1) != null) {
            this.selectedCard = new SelectedCard(this.player.getBoard().getCardInHandByNumber(address - 1), BoardZone.HAND, address - 1, this.player);
        }
    }

    public void unselectCard() {
        if (this.selectedCard != null) {
            this.selectedCard = null;
        }
    }


    public int getCountOfMonsterCardsInGround(User user) {
        MonsterCard[] monsterCards = user.getBoard().getMonsters();
        int countOfMonsterCardsInGround = 0;
        for (int i = 0; i < 5; i++) {
            if (monsterCards[i] != null) {
                countOfMonsterCardsInGround++;
            }
        }
        return countOfMonsterCardsInGround;
    }

    public void addToAttackDefenceOfPutCard(int place, User owner) {
        for (int i = 0; i < 5; i++) {
            if (this.player.getBoard().getMonsterByNumber(i) != null)
                this.player.getBoard().getMonsterByNumber(i).takeAction(this, TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE, owner, place);
            if (this.rival.getBoard().getMonsterByNumber(i) != null)
                this.rival.getBoard().getMonsterByNumber(i).takeAction(this, TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE, owner, place);
        }
        if (this.player.getBoard().getFieldZone() != null)
            ((SpellCard) this.player.getBoard().getFieldZone()).takeAction(this, TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE, owner, place);
        if (this.rival.getBoard().getFieldZone() != null)
            ((SpellCard) this.rival.getBoard().getFieldZone()).takeAction(this, TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE, owner, place);
    }

    public void summonMonster() throws Exception {
        if (this.selectedCard == null) {
            return;
        }
        if (!(this.selectedCard.getCard() instanceof MonsterCard && this.selectedCard.getBoardZone().equals(BoardZone.HAND) && !((MonsterCard) this.selectedCard.getCard()).getCardType().equals(CardType.RITUAL))) {
            return;
        }
        if (!(phase.equals(Phase.MAIN_PHASE1) || (phase.equals(Phase.MAIN_PHASE2)))) {
            return;
        }
        if (this.player.getBoard().isFullMonsterZone()) {
            throw new FullMonsterZone();
        }
        if (((MonsterCard) this.selectedCard.getCard()).getCanBeNormalSummoned()) {
            if (hasSummonedOrSetInThisTurn) {
                throw new AlreadySummoned();
            }
            MonsterCard monsterCard = (MonsterCard) selectedCard.getCard();
            if (monsterCard.getLevel() <= 4) {
                int place = this.player.getBoard().putMonster(monsterCard, "OO");
                setMonsterAttackPlayer(place, monsterCard.getAttack());
                this.playerDefencePoints[place] = monsterCard.getAttack();
                monsterCard.takeAction(this, TakeActionCase.SUMMONED, this.player, place);
                addToAttackDefenceOfPutCard(place, this.player);
                gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, 0, 0);
                SoundController.getInstance().playWhenSummons();
                return;
            }
            if (monsterCard.getLevel() < 7) {
                if (getCountOfMonsterCardsInGround(this.player) < 1) {
                    throw new InsufficientForTribute();
                } else {
                    tributeOneMonsterForSummon();
                }
            } else {
                if (getCountOfMonsterCardsInGround(this.player) < 2) {
                    throw new InsufficientForTribute();
                } else {
                    tributeTwoMonstersForSummon();
                }
            }
        } else if (!((MonsterCard) this.selectedCard.getCard()).getCardType().equals(CardType.RITUAL)) {
            specialSummonNormal();
        }
    }

    private ImageView getHandByNumber(int number) {
        if (number == 0) return GameViewGraphic.imageView1hand1;
        if (number == 1) return GameViewGraphic.imageView1hand2;
        if (number == 2) return GameViewGraphic.imageView1hand3;
        if (number == 3) return GameViewGraphic.imageView1hand4;
        if (number == 4) return GameViewGraphic.imageView1hand5;
        if (number == 5) return GameViewGraphic.imageView1hand6;
        return null;
    }

    private void tributeOneMonsterForSummon() throws Exception {
        DuelView.printText("select a monster by number to tribute");
        String input = DuelView.scan();
        if (input.equals("cancel")) return;
        while (!input.matches("[\\d]+") && Integer.parseInt(input) < 6) {
            DuelView.printText("please enter a valid number!");
            input = DuelView.scan();
            if (input.equals("cancel")) return;
        }
        int address = Integer.parseInt(input);
        address = playerGroundNumbers[address - 1] - 1;
        if (this.player.getBoard().getMonsterByNumber(address) == null) throw new NoMonsterHere();
        this.player.getBoard().removeMonster(address, this, player);
        removeMonsterPlayer(address);
        int place = this.player.getBoard().putMonster((MonsterCard) selectedCard.getCard(), "OO");
        setMonsterAttackPlayer(place, ((MonsterCard) selectedCard.getCard()).getAttack());
        this.playerDefencePoints[place] = ((MonsterCard) selectedCard.getCard()).getAttack();
        ((MonsterCard) selectedCard.getCard()).takeAction(this, TakeActionCase.SUMMONED, this.player, place);
        addToAttackDefenceOfPutCard(place, this.player);
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, 0, 0);
        SoundController.getInstance().playWhenSummons();
    }

    private void tributeTwoMonstersForSummon() throws Exception {
        DuelView.printText("select two monsters by number to tribute");
        String input1 = DuelView.scan();
        if (input1.equals("cancel")) return;
        while (!input1.matches("[\\d]+") && Integer.parseInt(input1) < 6) {
            DuelView.printText("please enter a valid number!");
            input1 = DuelView.scan();
            if (input1.equals("cancel")) return;
        }
        int address1 = Integer.parseInt(input1) - 1;
        address1 = playerGroundNumbers[address1] - 1;
        if (this.player.getBoard().getMonsterByNumber(address1) == null) throw new NoMonsterHere();
        String input2 = DuelView.scan();
        if (input2.equals("cancel")) return;
        while (!input2.matches("[\\d]+") && Integer.parseInt(input2) < 6) {
            DuelView.printText("please enter a valid number!");
            input2 = DuelView.scan();
            if (input2.equals("cancel")) return;
        }
        int address2 = Integer.parseInt(input2) - 1;
        address2 = playerGroundNumbers[address2] - 1;
        if (this.player.getBoard().getMonsterByNumber(address2) == null) throw new NoMonsterHere();
        if (address1 == address2) throw new sameAddresses();
        this.player.getBoard().removeMonster(address1, this, player);
        this.player.getBoard().removeMonster(address2, this, player);
        removeMonsterPlayer(address1);
        removeMonsterPlayer(address2);
        int place = this.player.getBoard().putMonster((MonsterCard) selectedCard.getCard(), "OO");
        setMonsterAttackPlayer(place, ((MonsterCard) selectedCard.getCard()).getAttack());
        this.playerDefencePoints[place] = ((MonsterCard) selectedCard.getCard()).getAttack();
        ((MonsterCard) selectedCard.getCard()).takeAction(this, TakeActionCase.SUMMONED, this.player, place);
        addToAttackDefenceOfPutCard(place, this.player);
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, 0, 0);
        SoundController.getInstance().playWhenSummons();
    }

    public void specialSummonNormal() {
        ((MonsterCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.SPECIAL_SUMMONED, this.player, this.selectedCard.getNumber());
    }

    public void preSet() throws Exception {
        if (this.selectedCard == null) {
            return;
        }
        if (!(this.selectedCard.getBoardZone().equals(BoardZone.HAND))) {
            return;
        }
        if (this.selectedCard.getCard() instanceof MonsterCard) setMonster();
        else if (this.selectedCard.getCard() instanceof SpellCard) setSpell();
        else if (this.selectedCard.getCard() instanceof TrapCard) setTrap();
    }

    public void setMonster() throws Exception {
        if (!(phase.equals(Phase.MAIN_PHASE1) || (phase.equals(Phase.MAIN_PHASE2)))) {
            return;
        }
        if (this.player.getBoard().isFullMonsterZone()) {
            throw new FullMonsterZone();
        }
        if (hasSummonedOrSetInThisTurn) {
            throw new AlreadySummoned();
        }
        MonsterCard monsterCard = (MonsterCard) this.selectedCard.getCard();
        if (monsterCard.getCanBeNormalSummoned()) {
            if (monsterCard.getLevel() <= 4) {
                int place = this.player.getBoard().putMonster(monsterCard, "DH");
                setMonsterAttackPlayer(place, ((MonsterCard) selectedCard.getCard()).getAttack());
                this.playerDefencePoints[place] = ((MonsterCard) selectedCard.getCard()).getAttack();
                addToAttackDefenceOfPutCard(place, this.player);
                this.actionsOnThisCardPlayer.get(place).add(ActionsDoneInTurn.SET);
                gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, 0, 0);
            } else if (monsterCard.getLevel() < 7) {
                if (getCountOfMonsterCardsInGround(this.player) < 1) {
                    throw new InsufficientForTribute();
                } else {
                    tributeOneMonsterForSet();
                }
            } else {
                if (getCountOfMonsterCardsInGround(this.player) < 2) {
                    throw new InsufficientForTribute();
                } else {
                    tributeTwoMonstersForSet();
                }
            }
        } else if (!((MonsterCard) this.selectedCard.getCard()).getCardType().equals(CardType.RITUAL)) {
            specialSummonNormal();
        }
    }

    private void tributeOneMonsterForSet() throws Exception {
        DuelView.printText("select a monster by number to tribute");
        String input = DuelView.scan();
        if (input.equals("cancel")) return;
        while (!(input.matches("[\\d]+") && Integer.parseInt(input) < 6)) {
            DuelView.printText("please enter a valid number!");
            input = DuelView.scan();
            if (input.equals("cancel")) return;
        }
        int address = Integer.parseInt(input);
        address = playerGroundNumbers[address - 1] - 1;
        if (this.player.getBoard().getMonsterByNumber(address) == null) throw new NoMonsterHere();
        this.player.getBoard().removeMonster(address, this, player);
        removeMonsterPlayer(address);
        int place = this.player.getBoard().putMonster((MonsterCard) selectedCard.getCard(), "DH");
        setMonsterAttackPlayer(place, ((MonsterCard) selectedCard.getCard()).getAttack());
        this.playerDefencePoints[place] = ((MonsterCard) selectedCard.getCard()).getAttack();
        addToAttackDefenceOfPutCard(place, this.player);
        this.actionsOnThisCardPlayer.get(place).add(ActionsDoneInTurn.SET);
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, 0, 0);
    }

    private void tributeTwoMonstersForSet() throws Exception {
        DuelView.printText("select two monsters by number to tribute");
        String input1 = DuelView.scan();
        if (input1.equals("cancel")) return;
        while (!input1.matches("[\\d]+")) {
            DuelView.printText("please enter a valid number!");
            input1 = DuelView.scan();
            if (input1.equals("cancel")) return;
        }
        int address1 = Integer.parseInt(input1);
        address1 = playerGroundNumbers[address1 - 1] - 1;
        if (this.player.getBoard().getMonsterByNumber(address1) == null) throw new NoMonsterHere();
        String input2 = DuelView.scan();
        if (input2.equals("cancel")) return;
        while (!input2.matches("[\\d]+")) {
            DuelView.printText("please enter a valid number!");
            input2 = DuelView.scan();
            if (input2.equals("cancel")) return;
        }
        int address2 = Integer.parseInt(input2);
        address2 = playerGroundNumbers[address2 - 1] - 1;
        if (this.player.getBoard().getMonsterByNumber(address2) == null) throw new NoMonsterHere();
        if (address1 == address2) throw new sameAddresses();
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 1, address1, address2);
    }

    private void setSpell() throws Exception {
        if (!(phase.equals(Phase.MAIN_PHASE1) || (phase.equals(Phase.MAIN_PHASE2))))
            throw new ImproperPhase();
        SpellCard spellCard = (SpellCard) this.selectedCard.getCard();
        if (!spellCard.getIcon().equals(Icon.FIELD)) {
            if (this.player.getBoard().isFullSpellAndTrapZone())
                throw new FullSpellZone();
            this.player.getBoard().putSpellOrTrap(spellCard, "H");
            hasUsedSpellOrTrap = true;
        } else {
            if (this.player.getBoard().getFieldZone() != null) {
                this.player.getBoard().putInGraveYard(this.player.getBoard().getFieldZone());
                this.player.getBoard().removeFromFieldZone();
            }
            changeBackground(spellCard);
            this.player.getBoard().putInFieldZone(spellCard);
            hasUsedSpellOrTrap = true;
        }
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 3, 0, 0);
    }

    private void setTrap() throws Exception {
        if (!(phase.equals(Phase.MAIN_PHASE1) || (phase.equals(Phase.MAIN_PHASE2)))) {
            return;
        }
        TrapCard trapCard = (TrapCard) this.selectedCard.getCard();
        if (!trapCard.getIcon().equals(Icon.FIELD)) {
            if (this.player.getBoard().isFullSpellAndTrapZone()) {
                throw new FullSpellZone();
            }
            this.player.getBoard().putSpellOrTrap(trapCard, "H");
            hasUsedSpellOrTrap = true;

        } else {
            if (this.player.getBoard().getFieldZone() != null) {
                this.player.getBoard().putInGraveYard(this.player.getBoard().getFieldZone());
                this.player.getBoard().removeFromFieldZone();
            }
            this.player.getBoard().putInFieldZone(trapCard);
            hasUsedSpellOrTrap = true;
        }
        gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 3, 0, 0);
    }

    public void changePosition() throws Exception {
        String targetPositionInShort = "";
        switch (this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber())) {
            case "DO":
                targetPositionInShort = "OO";
                break;
            case "OO":
                targetPositionInShort = "DO";
                break;
        }
        if (this.selectedCard == null) {
            return;
        }
        if (!this.selectedCard.getOwner().getUsername().equals(player.getUsername()) || !this.selectedCard.getBoardZone().equals(BoardZone.MONSTERZONE) || this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals("DH")) {
            return;
        }
        if (!(this.phase.equals(Phase.MAIN_PHASE1) || (this.phase.equals(Phase.MAIN_PHASE2)))) {
            return;
        }
        if (this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals(targetPositionInShort) || this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals("DH")) {
            return;
        }
        if (actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.CHANGE_POSITION))
            throw new AlreadyChangedPosition();
        if (actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.ATTACK) && this.phase.equals(Phase.MAIN_PHASE2))
            throw new HasAttackedInBattle();
        this.player.getBoard().changeMonsterPosition(this.selectedCard.getNumber(), targetPositionInShort);
        actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.CHANGE_POSITION);
    }

    public void flipSummon() throws Exception {
        if (this.selectedCard == null) {
            return;
        }
        if (!this.selectedCard.getBoardZone().equals(BoardZone.MONSTERZONE) || !this.selectedCard.getOwner().getUsername().equals(player.getUsername())) {
            return;
        }
        if (!(this.phase.equals(Phase.MAIN_PHASE1) || this.phase.equals(Phase.MAIN_PHASE2))) {
            return;
        }
        if (!this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals("DH") || actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.SET))
            throw new CanNotFlipSummon();
        this.player.getBoard().changeMonsterPosition(this.selectedCard.getNumber(), "OO");
        ((MonsterCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.FLIP_SUMMONED, this.player, this.selectedCard.getNumber());
        gameViewGraphic.lightAnimation(getImageViewByNumberForExplode(this.selectedCard.getNumber() + 5));
        unselectCard();
    }

    public void attackMonster(int monsterNumber) throws Exception {
        monsterNumber = playerGroundNumbers[monsterNumber - 1] - 1;
        if (this.selectedCard == null) {
            return;
        }
        if (!(this.selectedCard.getOwner().getUsername().equals(player.getUsername()) && this.selectedCard.getBoardZone().equals(BoardZone.MONSTERZONE) && (this.selectedCard.getCard() instanceof MonsterCard) && (this.player.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals("OO")))) {
            return;
        }
        if (!(this.phase.equals(Phase.BATTLE_PHASE)))
            throw new CantDoActionInThisPhase();
        if (actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.ATTACK))
            throw new AlreadyAttacked();
        if (getCountOfMonsterCardsInGround(this.rival) == 0) {
            return;
        }
        if (rival.getBoard().getMonsterByNumber(monsterNumber) == null) {
            return;
        }
        findWayAttack(monsterNumber);
        manageEndGame();
    }

    private void findWayAttack(int monsterNumber) throws Exception {
        String targetPosition = this.rival.getBoard().getMonsterConditionByNumber(monsterNumber);
        if (this.rival.getBoard().getMonsterByNumber(monsterNumber).canBeAttacked(this, monsterNumber)) {
            this.rival.getBoard().getMonsterByNumber(monsterNumber).takeAction(this, TakeActionCase.ATTACKED, this.rival, monsterNumber);
            switch (targetPosition) {
                case "OO":
                    attackMonsterOO(monsterNumber);
                    break;
                case "DO":
                    attackMonsterDO(monsterNumber);
                    break;
                case "DH":
                    attackMonsterDH(monsterNumber);
                    break;
            }
        } else throw new CanNotAttackThisCard();
    }

    private ImageView getImageViewByNumberForExplode(int number) {
        if (number == 0) return GameViewGraphic.imageView2Monster5;
        if (number == 1) return GameViewGraphic.imageView2Monster4;
        if (number == 2) return GameViewGraphic.imageView2Monster3;
        if (number == 3) return GameViewGraphic.imageView2Monster2;
        if (number == 4) return GameViewGraphic.imageView2Monster1;
        if (number == 5) return GameViewGraphic.imageView1Monster1;
        if (number == 6) return GameViewGraphic.imageView1Monster2;
        if (number == 7) return GameViewGraphic.imageView1Monster3;
        if (number == 8) return GameViewGraphic.imageView1Monster4;
        if (number == 9) return GameViewGraphic.imageView1Monster5;
        else return null;
    }

    private void attackMonsterOO(int monsterNumber) {
        int attackerAttack = this.playerAttackPoints[this.selectedCard.getNumber()];
        int targetAttack = this.rivalAttackPoints[monsterNumber];
        if (attackerAttack > targetAttack) {
            int damage = attackerAttack - targetAttack;
            if (!this.rival.getBoard().getMonsterByNumber(monsterNumber).equals(MonsterCard.EXPLODER_DRAGON))
                this.rival.decreaseLifePoint(damage);
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(monsterNumber);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            this.rival.getBoard().putInGraveYard(this.rival.getBoard().getMonsterByNumber(monsterNumber));
            this.rival.getBoard().getMonsterByNumber(monsterNumber).takeAction(this, TakeActionCase.DIED_BY_BEING_ATTACKED, this.rival, monsterNumber);
            this.rival.getBoard().removeMonster(monsterNumber, this, rival);
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else if (attackerAttack == targetAttack) {
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(monsterNumber);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(this.selectedCard.getNumber() + 5);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            this.rival.getBoard().putInGraveYard(this.rival.getBoard().getMonsterByNumber(monsterNumber));
            this.rival.getBoard().getMonsterByNumber(monsterNumber).takeAction(this, TakeActionCase.DIED_BY_BEING_ATTACKED, this.rival, monsterNumber);
            this.rival.getBoard().removeMonster(monsterNumber, this, rival);
            if (this.player.getBoard().getMonsterByNumber(this.selectedCard.getNumber()) != null) {
                this.player.getBoard().putInGraveYard(this.selectedCard.getCard());
                this.player.getBoard().removeMonster(this.selectedCard.getNumber(), this, player);
                removeMonsterPlayer(this.selectedCard.getNumber());
            }
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else {
            int damage = targetAttack - attackerAttack;
            this.player.decreaseLifePoint(damage);
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(this.selectedCard.getNumber() + 5);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            ((MonsterCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.REMOVE_FROM_MONSTERZONE, this.player, this.selectedCard.getNumber());
            this.player.getBoard().putInGraveYard(this.selectedCard.getCard());
            this.player.getBoard().removeMonster(this.selectedCard.getNumber(), this, player);
            removeMonsterPlayer(this.selectedCard.getNumber());
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        }
        SoundController.getInstance().playWhenAttacks();
        unselectCard();
        gameViewGraphic.resetSelectedCard();
        gameViewGraphic.setLifePoints();
    }

    private void attackMonsterDO(int monsterNumber) {
        MonsterCard target = this.rival.getBoard().getMonsterByNumber(monsterNumber);
        int attackerAttack = this.playerAttackPoints[this.selectedCard.getNumber()];
        if (attackerAttack > target.getDefence()) {
            this.rival.getBoard().getMonsterByNumber(monsterNumber).takeAction(this, TakeActionCase.DIED_BY_BEING_ATTACKED, this.rival, monsterNumber);
            this.rival.getBoard().removeMonster(monsterNumber, this, rival);
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(monsterNumber);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            this.rival.getBoard().putInGraveYard(target);
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else if (attackerAttack == target.getDefence()) {
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else {
            int damage = this.rivalDefencePoints[monsterNumber] - attackerAttack;
            this.player.decreaseLifePoint(damage);
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        }
        SoundController.getInstance().playWhenAttacks();
        unselectCard();
        gameViewGraphic.resetSelectedCard();
        gameViewGraphic.setLifePoints();
    }

    private void attackMonsterDH(int monsterNumber) {
        MonsterCard target = this.rival.getBoard().getMonsterByNumber(monsterNumber);
        int attackerAttack = this.playerAttackPoints[this.selectedCard.getNumber()];
        this.rival.getBoard().changeMonsterPosition(monsterNumber, "DO");
        ((MonsterCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.FLIP_SUMMONED, this.rival, this.selectedCard.getNumber());
        if (attackerAttack > target.getDefence()) {
            gameViewGraphic.cardToExplode = getImageViewByNumberForExplode(monsterNumber);
            gameViewGraphic.explode(gameViewGraphic.cardToExplode);
            this.rival.getBoard().putInGraveYard(target);
            this.rival.getBoard().getMonsterByNumber(monsterNumber).takeAction(this, TakeActionCase.DIED_BY_BEING_ATTACKED, this.rival, monsterNumber);
            this.rival.getBoard().removeMonster(monsterNumber, this, rival);
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else if (attackerAttack == target.getDefence()) {
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        } else {
            int damage = this.rivalDefencePoints[monsterNumber] - attackerAttack;
            this.player.decreaseLifePoint(damage);
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
        }
        SoundController.getInstance().playWhenAttacks();
        unselectCard();
        gameViewGraphic.resetSelectedCard();
        gameViewGraphic.setLifePoints();
    }

    public void directAttack(int number) throws Exception {
        if (this.selectedCard == null) {
            return;
        }
        if (!this.selectedCard.getBoardZone().equals(BoardZone.MONSTERZONE)) {
            return;
        }
        if (!this.phase.equals(Phase.BATTLE_PHASE)) throw new ImproperPhase();
        if (actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.ATTACK))
            throw new AlreadyAttacked();
        int countOfMonsterCardsInGround = getCountOfMonsterCardsInGround(this.rival);
        if (countOfMonsterCardsInGround == 0) {
            rival.decreaseLifePoint(((MonsterCard) this.selectedCard.getCard()).getAttack());
            actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).add(ActionsDoneInTurn.ATTACK);
            unselectCard();
            gameViewGraphic.explode(GameViewGraphic.directAttackArea);
            gameViewGraphic.setLifePoints();
            manageEndGame();
        }

    }

    public void activateSpell() throws Exception {
        if (this.selectedCard == null) {
            return;
        }
        if (!(this.selectedCard.getCard() instanceof SpellCard)) {
            return;
        }
        if (!((this.phase.equals(Phase.MAIN_PHASE1)) || (this.phase.equals(Phase.MAIN_PHASE2)))) {
            return;
        }
        if (!this.selectedCard.getBoardZone().equals(BoardZone.HAND) && this.player.getBoard().getSpellAndTrapConditionByNumber(this.selectedCard.getNumber()).equals("O")) {
            return;
        }
        SpellCard spellCard = (SpellCard) this.selectedCard.getCard();
        if (this.player.getBoard().isFullSpellAndTrapZone() && !spellCard.getIcon().equals(Icon.FIELD) && selectedCard.getBoardZone().equals(BoardZone.HAND))
            throw new FullSpellZone();
        if ((((SpellCard) this.selectedCard.getCard()).getIcon().equals(Icon.QUICK_PLAY)
                && actionsOnThisCardPlayer.get(this.selectedCard.getNumber()).contains(ActionsDoneInTurn.SET)))
            throw new UndonePreparationOfSpell();
        if (spellCard.getIcon().equals(Icon.FIELD)) {
            if (this.player.getBoard().getFieldZone() != null) {
                if (((SpellCard) this.player.getBoard().getFieldZone()).takeAction(this, TakeActionCase.REMOVE_FROM_FIELDZONE_FACE_UP, this.player, 1)) {
                    this.player.getBoard().putInGraveYard(this.player.getBoard().getFieldZone());
                    this.player.getBoard().removeFromFieldZone();
                }
            }
            if (((SpellCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.PUT_IN_FIELDZONE_FACE_UP, this.player, 1)) {
                {
                    this.player.getBoard().putInFieldZone(spellCard);
                    changeBackground(spellCard);
                }
                gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 3, 0, 0);
            }
        } else {
            if (selectedCard.getBoardZone().equals(BoardZone.SPELLANDTRAPZONE)) {
                this.player.getBoard().changeSpellAndTrapPosition(selectedCard.getNumber(), "O");
                boolean wasSuccessful = ((SpellCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.PUT_IN_SPELLTRAPZONE, this.player, selectedCard.getNumber());
                if (!wasSuccessful) {
                    this.player.getBoard().removeSpellOrTrap(selectedCard.getNumber());
                }
            } else if (selectedCard.getBoardZone().equals(BoardZone.HAND)) {
                int number = this.player.getBoard().putSpellOrTrap(selectedCard.getCard(), "O");
                boolean wasSuccessful = ((SpellCard) this.selectedCard.getCard()).takeAction(this, TakeActionCase.PUT_IN_SPELLTRAPZONE, this.player, number);
                if (!wasSuccessful) {
                    this.player.getBoard().removeSpellOrTrap(selectedCard.getNumber());
                }
                gameViewGraphic.lightningAnimation(getHandByNumber(this.selectedCard.getNumber()), 3, 0, 0);
            }
        }
    }

    public void cheatLifePoint(String target, int lifePoint) {
        if (lifePoint < 2000) {
            if (target.equals("player"))
                this.player.increaseLifePoint(lifePoint);
            if (target.equals("opponent"))
                this.rival.decreaseLifePoint(lifePoint);
            GameViewGraphic.printInformation("shame on you for cheating!!!!");
        }
    }

    public void cheatToWinGame() {
        GameViewGraphic.printInformation("this is not a good way to win the game, but ok. Shame on you!");
        endGame(this.rival);
    }

    public void surrender() {
        endGame(this.player);
    }

    public void manageEndGame() {
        List<Card> playersCardInHand = player.getBoard().getCardsInHand();
        List<Card> rivalsCardInHand = rival.getBoard().getCardsInHand();
        if (player.getLifePoint() <= 0) {
            endGame(player);
        } else if (rival.getLifePoint() <= 0) {
            endGame(rival);
        } else if (playersCardInHand.isEmpty()) {
            endGame(player);
        } else if (rivalsCardInHand.isEmpty()) {
            endGame(rival);
        }
    }

    private void endGame(User loser) {
        User winner;
        if (loser.equals(rival)) {
            gameViewGraphic.crownAnimation(gameViewGraphic.imageViewForCrown);
            winner = player;
            player.addWonGamesInARow();
            player.setLostGamesInARow(0);
            rival.addToLostGames();
            if (player.getLifePoint() >= 6000) {
                player.addGift();
                playerWonGift();
            }
            if (player.getLifePoint() >= 7000)
                rival.addSadAfarin();

            if (!hasUsedSpellOrTrap){
                player.addNumberOfWonGamesWithoutMonster();
            }
            else player.setNumberOfWonGamesWithoutMonsterToZero();
            if (player.getNumberOfWonGamesWithoutMonster() == 5) {
                player.addNumberOfTrophy();
                playerWonWithoutMonster();
                player.setNumberOfWonGamesWithoutMonsterToZero();
            }
            rival.setWonGamesInARowToZero();
            rival.setNumberOfWonGamesWithoutMonsterToZero();
            if (player.getWonGamesInARow() == 5) {
                playerWon5Games();
                player.addNumberOfBronze();
            }
            if (player.getWonGamesInARow() == 10){
                playerWon10Games();
                player.addNumberOfSilver();
            }
            if (player.getWonGamesInARow() == 15){
                playerWon15Games();
                player.addNumberOfGold();
                player.setWonGamesInARowToZero();
            }
            if (rival.getLostGamesInARow() == 3)
                rival.addThreeLosesInARow();
            if (rival.getLostGamesInARow() == 7)
                rival.addSevenLosesInARow();
            if (rival.getNumberOfTenLosesInARow() == 10)
                rival.addTenLosesInARow();
            if (this.numberOfChangedTurns < 10) {
                player.addNumberOfFlowers();
                rival.addNumberOfRassberry();
            }
        } else {
            winner = rival;
            rival.addWonGamesInARow();
            player.addToLostGames();
            rival.setLostGamesInARow(0);
            if (rival.getLifePoint() >= 6000) {
                rival.addGift();
                playerWonGift();
            }

            if (rival.getLifePoint() >= 7000)
                player.addSadAfarin();

            if (!hasUsedSpellOrTrap){
                rival.addNumberOfWonGamesWithoutMonster();
            }
            else rival.setNumberOfWonGamesWithoutMonsterToZero();

            if (rival.getNumberOfWonGamesWithoutMonster() == 5) {
                rival.addNumberOfTrophy();
                playerWonWithoutMonster();
                rival.setNumberOfWonGamesWithoutMonsterToZero();
            }
            player.setWonGamesInARowToZero();
            player.setNumberOfWonGamesWithoutMonsterToZero();
            if (rival.getWonGamesInARow() == 5){
                playerWon5Games();
                rival.addNumberOfBronze();
            }
            if (rival.getWonGamesInARow() == 10){
                playerWon10Games();
                rival.addNumberOfSilver();
            }
            if (rival.getWonGamesInARow() == 15){
                playerWon15Games();
                rival.addNumberOfGold();
                rival.setWonGamesInARowToZero();
            }

            if (player.getLostGamesInARow() == 3) {
                player.addThreeLosesInARow();
                playerLost3games();
            }
            if (player.getLostGamesInARow() == 7) {
                player.addSevenLosesInARow();
                playerLost7Games();
            }
            if (player.getNumberOfTenLosesInARow() == 10) {
                player.addTenLosesInARow();
                playerLost10Games();
            }
            if (this.numberOfChangedTurns < 10) {
                rival.addNumberOfFlowers();
                player.addNumberOfRassberry();
            }
        }
        ImportExportUserController.getInstance().exportAchievements(User.getAllUsers());
        if (player.getLifePoint() < 0) player.setLifePoint(0);
        if (rival.getLifePoint() < 0) rival.setLifePoint(0);
        roundCounter++;
        if (roundNumber == 1) {
            winner.increaseScore(1000);
            winner.increaseMoney(1000 + winner.getLifePoint());
            loser.increaseMoney(100);
            gameViewGraphic.endGamePopup(winner.getUsername() + " won the game and the score is: " + winner.getScore() + "-" + loser.getScore());
        }
        if (roundNumber == 3) {
            this.rounds[this.roundCounter - 1] = new Round(winner, loser, winner.getLifePoint(), loser.getLifePoint());
            if (this.roundCounter == 2 && this.rounds[0].getWinner().equals(this.rounds[1].getWinner())) {
                int winnerLP1 = this.rounds[0].getLifePointByUser(winner);
                int winnerLP2 = this.rounds[1].getLifePointByUser(winner);
                int maxLP = Math.max(winnerLP1, winnerLP2);
                winner.increaseScore(3000);
                winner.increaseMoney(3000 + (3 * maxLP));
                loser.increaseMoney(300);
                GameViewGraphic.printInformation(winner.getUsername() + " won the game and the score is: " + winner.getScore() + "-" + loser.getScore());
                gameViewGraphic.endGamePopup(winner.getUsername() + " won the whole match with score: " + winner.getScore() + "-" + loser.getScore());
            } else if (roundCounter == 3) {
                int winnerLP1 = this.rounds[0].getLifePointByUser(winner);
                int winnerLP2 = this.rounds[1].getLifePointByUser(winner);
                int winnerLP3 = this.rounds[2].getLifePointByUser(winner);
                int maxLP = Math.max(winnerLP1, winnerLP2);
                maxLP = Math.max(maxLP, winnerLP3);
                winner.increaseScore(3000);
                winner.increaseMoney(3000 + (3 * maxLP));
                loser.increaseMoney(300);
                GameViewGraphic.printInformation(winner.getUsername() + " won the game and the score is: " + winner.getScore() + "-" + loser.getScore());
                gameViewGraphic.endGamePopup(winner.getUsername() + " won the whole match with score: " + winner.getScore() + "-" + loser.getScore());
            } else {
                GameViewGraphic.printInformation(winner.getUsername() + " won the game and the score is: " + winner.getScore() + "-" + loser.getScore());
                try {
                    gameViewGraphic.start(GameViewGraphic.stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                exchangeCardBetweenMainAndSide(this.player);
                exchangeCardBetweenMainAndSide(this.rival);
                startNewGame(winner);
            }
        }
    }

    private void exchangeCardBetweenMainAndSide(User user) {
        DuelView.printText("Do you want to exchange a card between main and side deck " + user.getNickname() + "?");
        String answer = DuelView.scan().toLowerCase();
        if (answer.equals("yes")) {
            DuelView.printText("enter card name from main and side");
            String main = DuelView.scan();
            Card mainCard = user.getCardByName(main);
            while (mainCard == null || !user.getGameDeck().getMainDeck().contains(mainCard)) {
                DuelView.printText("Please enter a card you have in your main deck!");
                main = DuelView.scan();
                mainCard = user.getCardByName(main);
            }
            String side = DuelView.scan();
            Card sideCard = user.getCardByName(side);
            while (sideCard == null || !user.getGameDeck().getSideDeck().contains(sideCard)) {
                DuelView.printText("Please enter a card you have in your side deck!");
                side = DuelView.scan();
                sideCard = user.getCardByName(side);
            }
            user.getGameDeck().getSideDeck().remove(sideCard);
            user.getGameDeck().getMainDeck().remove(mainCard);
            user.getGameDeck().getSideDeck().add(mainCard);
            user.getGameDeck().getMainDeck().add(sideCard);
        }
    }

    public void goNextPhase() {
        if (this.phase.equals(Phase.DRAW_PHASE)) {
            this.phase = Phase.STANDBY_PHASE;
            try {
                gameViewGraphic.startMainNoCardSelected();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (phase.equals(Phase.STANDBY_PHASE)) {
            this.phase = Phase.MAIN_PHASE1;
        } else if (phase.equals(Phase.MAIN_PHASE1)) {
            if (!isStartTurn) {
                this.phase = Phase.BATTLE_PHASE;
                gameViewGraphic.imageViewForSword.toFront();
                gameViewGraphic.startBattlePhaseWithSword(gameViewGraphic.imageViewForSword);
                try {
                    gameViewGraphic.startBattleNoCardSelected();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else
                this.phase = Phase.MAIN_PHASE2;
        } else if (this.phase.equals(Phase.BATTLE_PHASE)) {
            this.phase = Phase.MAIN_PHASE2;
            try {
                gameViewGraphic.startMainNoCardSelected();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (this.phase.equals(Phase.MAIN_PHASE2)) {
            this.phase = Phase.END_PHASE;
        } else if (this.phase.equals(Phase.END_PHASE)) {
            GameViewGraphic.printInformation("its " + rival.getNickname() + "’s turn");
            changeTurn();
            startDrawPhase(false);
            if (this.player.getUsername().equals("@AI@"))
                handleAITurn();
        }
        SoundController.getInstance().playWhenEnterNextPhase();
        gameViewGraphic.setPhaseRectangleColors();
        gameViewGraphic.updateBoard();
    }

    private void startDrawPhase(boolean isFirst) {
        if (!isFirst) manageEndGame();
        this.phase = Phase.DRAW_PHASE;
        gameViewGraphic.deckShuffleAnimation(gameViewGraphic.imageViewForCardShuffle);
        ArrayList<Card> playerMainCards = (ArrayList<Card>) this.player.getGameDeck().getMainDeck();
        ArrayList<Card> rivalMainCards = (ArrayList<Card>) this.rival.getGameDeck().getMainDeck();
        if (playerMainCards.size() == 0) endGame(this.player);
        else {
            Collections.shuffle(this.player.getGameDeck().getMainDeck());
            Collections.shuffle(this.rival.getGameDeck().getMainDeck());
            if (isFirst) {
                for (int i = 0; i < 5; i++) {
                    this.player.getBoard().addCardToHand(playerMainCards.get(playerMainCards.size() - 1));
                    this.player.getGameDeck().removeCardFromMainDeck(playerMainCards.get(playerMainCards.size() - 1));
                    Collections.shuffle(this.player.getGameDeck().getMainDeck());
                    this.rival.getBoard().addCardToHand(rivalMainCards.get(rivalMainCards.size() - 1));
                    this.rival.getGameDeck().removeCardFromMainDeck(rivalMainCards.get(rivalMainCards.size() - 1));
                    Collections.shuffle(this.rival.getGameDeck().getMainDeck());
                }
            }
            if (this.player.getBoard().getCardsInHand().size() < 6) {
                this.player.getBoard().addCardToHand(playerMainCards.get(playerMainCards.size() - 1));
                this.player.getGameDeck().removeCardFromMainDeck(playerMainCards.get(playerMainCards.size() - 1));
            }
            Collections.shuffle(this.player.getGameDeck().getMainDeck());
        }
        goNextPhase();
    }

    private void changeTurn() {
        if (this.isStartTurn) this.isStartTurn = false;
        clearLastTurn();
        User temp = this.player;
        this.player = rival;
        this.rival = temp;
        addToNumberOfTurns();
        gameViewGraphic.changeTurn();
        changePlayerAndRival();
    }

    public void changePlayerAndRival() {
        Integer[] temp = this.playerAttackPoints;
        this.playerAttackPoints = this.rivalAttackPoints;
        this.rivalAttackPoints = temp;
    }

    private void clearLastTurn() {
        this.selectedCard = null;
        this.hasSummonedOrSetInThisTurn = false;
        newThoseThatResetWithTurn();
    }

    private void newThoseThatResetWithTurn() {
        newActionsOnThisCardPlayer();
        newActionsOnThisCardRival();
        for (int i = 0; i < 5; i++) {
            actionsOnThisCardPlayer.get(i).clear();
            actionsOnThisCardRival.get(i).clear();
            if (this.player.getBoard().getMonsterByNumber(i) != null && this.playerAttackPoints[i] != null && this.playerAttackPoints[i] == 0 && this.player.getBoard().getMonsterByNumber(i).getAttack() != 0) {
                this.playerAttackPoints[i] = this.player.getBoard().getMonsterByNumber(i).getAttack();
            }
        }
    }

    public ArrayList<Image> getBoard() {
        ArrayList<Image> images = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (this.player.getBoard().getMonsterConditionByNumber(i) == null) images.add(null);
            else if (!this.player.getBoard().getMonsterConditionByNumber(i).equals("DH"))
                images.add(this.player.getBoard().getMonsterByNumber(i).getImage());
            else
                images.add(GameViewGraphic.unknown);
        }
        for (int i = 4; i >= 0; i--) {
            if (this.rival.getBoard().getMonsterConditionByNumber(i) == null) images.add(null);
            else if (!this.rival.getBoard().getMonsterConditionByNumber(i).equals("DH"))
                images.add(this.rival.getBoard().getMonsterByNumber(i).getImage());
            else
                images.add(GameViewGraphic.unknown);
        }
        for (int i = 0; i < 5; i++) {
            if (this.player.getBoard().getSpellAndTrapByNumber(i) == null) images.add(null);
            else if (this.player.getBoard().getSpellAndTrapConditionByNumber(i).equals("O"))
                images.add(this.player.getBoard().getSpellAndTrapByNumber(i).getImage());
            else
                images.add(GameViewGraphic.unknown);
        }
        for (int i = 4; i >= 0; i--) {
            if (this.rival.getBoard().getSpellAndTrapByNumber(i) == null) images.add(null);
            else if (this.rival.getBoard().getSpellAndTrapConditionByNumber(i).equals("O"))
                images.add(this.rival.getBoard().getSpellAndTrapByNumber(i).getImage());
            else
                images.add(GameViewGraphic.unknown);
        }
        for (int i = 0; i < 6; i++) {
            if (this.player.getBoard().getCardsInHand().size() <= i) images.add(null);
            else images.add(this.player.getBoard().getCardsInHand().get(i).getImage());
        }
        for (int i = 5; i >= 0; i--) {
            if (this.rival.getBoard().getCardsInHand().size() <= i) images.add(null);
            else images.add(GameViewGraphic.unknown);
        }
        if (this.player.getBoard().getFieldZone() == null)
            images.add(null);
        else
            images.add(this.player.getBoard().getFieldZone().getImage());
        if (this.rival.getBoard().getFieldZone() == null)
            images.add(null);
        else
            images.add(this.rival.getBoard().getFieldZone().getImage());
        if (this.player.getBoard().getCardsInGraveyard().isEmpty())
            images.add(null);
        else
            images.add(this.player.getBoard().getCardsInGraveyard().get(this.player.getBoard().getCardsInGraveyard().size() - 1).getImage());
        if (this.rival.getBoard().getCardsInGraveyard().isEmpty())
            images.add(null);
        else
            images.add(this.rival.getBoard().getCardsInGraveyard().get(this.rival.getBoard().getCardsInGraveyard().size() - 1).getImage());
        return images;
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(player.getBoard().getMonsterByNumber(i));
        }
        for (int i = 4; i >= 0; i--) {
            cards.add(rival.getBoard().getMonsterByNumber(i));
        }
        for (int i = 0; i < 5; i++) {
            cards.add(player.getBoard().getSpellAndTrapByNumber(i));
        }
        for (int i = 4; i >= 0; i--) {
            cards.add(rival.getBoard().getSpellAndTrapByNumber(i));
        }
        for (int i = 0; i < 6; i++) {
            if (this.player.getBoard().getCardsInHand().size() <= i) cards.add(null);
            else cards.add(this.player.getBoard().getCardInHandByNumber(i));
        }
        for (int i = 5; i >= 0; i--) {
            if (this.rival.getBoard().getCardsInHand().size() <= i) cards.add(null);
            else cards.add(this.rival.getBoard().getCardInHandByNumber(i));
        }
        cards.add(this.player.getBoard().getFieldZone());
        cards.add(this.rival.getBoard().getFieldZone());
        if (this.player.getBoard().getCardsInGraveyard().isEmpty())
            cards.add(null);
        else
            cards.add(this.player.getBoard().getCardsInGraveyard().get(this.player.getBoard().getCardsInGraveyard().size() - 1));
        if (this.rival.getBoard().getCardsInGraveyard().isEmpty())
            cards.add(null);
        else
            cards.add(this.rival.getBoard().getCardsInGraveyard().get(this.rival.getBoard().getCardsInGraveyard().size() - 1));
        return cards;
    }

    public ArrayList<Boolean> getConditions() {
        ArrayList<Boolean> conditions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (player.getBoard().getMonsterConditionByNumber(i) == null) conditions.add(null);
            else conditions.add(!player.getBoard().getMonsterConditionByNumber(i).equals("OO"));
        }
        for (int i = 4; i >= 0; i--) {
            if (rival.getBoard().getMonsterConditionByNumber(i) == null) conditions.add(null);
            else conditions.add(!rival.getBoard().getMonsterConditionByNumber(i).equals("OO"));
        }
        for (int i = 0; i < 5; i++) {
            conditions.add(false);
        }
        for (int i = 4; i >= 0; i--) {
            conditions.add(false);
        }
        for (int i = 0; i < 6; i++) {
            conditions.add(false);
        }
        for (int i = 5; i >= 0; i--) {
            conditions.add(false);
        }
        conditions.add(false);
        conditions.add(false);
        conditions.add(false);
        conditions.add(false);
        return conditions;
    }

    public void showCard() throws Exception {
        if (this.selectedCard == null) {
            throw new NoCardSelected();
        } else if (!this.selectedCard.getOwner().equals(this.player)) {
            if (this.selectedCard.getBoardZone().equals(BoardZone.HAND))
                throw new InvisibleCard();
            if (this.selectedCard.getBoardZone().equals(BoardZone.MONSTERZONE) && this.rival.getBoard().getMonsterConditionByNumber(this.selectedCard.getNumber()).equals("DH"))
                throw new InvisibleCard();
            if (this.selectedCard.getBoardZone().equals(BoardZone.SPELLANDTRAPZONE) && this.rival.getBoard().getSpellAndTrapConditionByNumber(this.selectedCard.getNumber()).equals("H"))
                throw new InvisibleCard();
        }
    }

    public void showPlayerGraveyard() {
        int[] i = {0};
        AnchorPane anchorPane = new AnchorPane();
        Button previousButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button backButton = new Button("Back");
        ArrayList<Image> images = getGraveYard(player);
        gameViewGraphic.fillPopUp(i, anchorPane, previousButton, nextButton, backButton, images);
    }

    public void showRivalGraveyard() {
        int[] i = {0};
        AnchorPane anchorPane = new AnchorPane();
        Button previousButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button backButton = new Button("Back");
        ArrayList<Image> images = getGraveYard(rival);
        gameViewGraphic.fillPopUp(i, anchorPane, previousButton, nextButton, backButton, images);
    }

    public void removeMonsterPlayer(int address) {
        actionsOnThisCardPlayer.get(address).clear();
        setMonsterAttackPlayer(address, null);
        player.getBoard().removeMonster(address, this, player);
        ((MonsterCard) selectedCard.getCard()).takeAction(this, TakeActionCase.REMOVE_FROM_MONSTERZONE, this.player, this.selectedCard.getNumber());
    }

    public void handleAITurn() {
        goNextPhase();
        setMonsterCardInAI();
        setSpellOrTrapInAI();
        goNextPhase();
        attackInAI();
        goNextPhase();
        goNextPhase();
        goNextPhase();
    }

    private void setMonsterCardInAI() {
        int numberOfMonstersOnPlayerBoard = numberOfMonstersOnPlayerBoard();
        if (numberOfMonstersOnPlayerBoard > 1 && monsterCardWithTwoTributesWithMaxAttackPointInHand() != null)
            setATwoTributeMonster();
        else if (numberOfMonstersOnPlayerBoard > 0 && monsterCardWithOneTributeWithMaxAttackPointInHand() != null)
            setAOneTributeMonster();
        else if (numberOfMonstersOnPlayerBoard != 5)
            setANoTributeMonster();
        getBoard();

    }

    private void setATwoTributeMonster() {
        if (numberOfLevelSevenEightMonstersInHand() > 0) {
            this.player.getBoard().removeMonster(monsterCardWithLeaseAttackPointOnPlayerBoard(), this, player);
            this.player.getBoard().removeMonster(monsterCardWithLeaseAttackPointOnPlayerBoard(), this, player);
            int place = this.player.getBoard().putMonster(monsterCardWithTwoTributesWithMaxAttackPointInHand(), "OO");
            setMonsterAttackPlayer(place, (Objects.requireNonNull(monsterCardWithTwoTributesWithMaxAttackPointInHand()).getAttack()));
            this.playerDefencePoints[place] = (Objects.requireNonNull(monsterCardWithTwoTributesWithMaxAttackPointInHand()).getAttack());
            addToAttackDefenceOfPutCard(place, this.player);
            this.player.getBoard().removeCardFromHand(monsterCardWithTwoTributesWithMaxAttackPointInHand());
        }
    }

    private void setAOneTributeMonster() {
        if (numberOfLevelFiveSixMonstersInPlayerHand() > 0) {
            this.player.getBoard().removeMonster(monsterCardWithLeaseAttackPointOnPlayerBoard(), this, player);
            int place = this.player.getBoard().putMonster(monsterCardWithOneTributeWithMaxAttackPointInHand(), "OO");
            setMonsterAttackPlayer(place, (Objects.requireNonNull(monsterCardWithOneTributeWithMaxAttackPointInHand()).getAttack()));
            this.playerDefencePoints[place] = (Objects.requireNonNull(monsterCardWithOneTributeWithMaxAttackPointInHand()).getAttack());
            addToAttackDefenceOfPutCard(place, this.player);
            this.player.getBoard().removeCardFromHand(monsterCardWithOneTributeWithMaxAttackPointInHand());
        }
    }

    private void setANoTributeMonster() {
        if (numberOfLevelOneTwoFourMonstersInPlayerHand() > 0) {
            if (monsterCardWithLeastDefencePointOnRivalBoard() != 10) {
                if (numberOfMonstersOnPlayerBoard() == 0 && Objects.requireNonNull(monsterCardWithoutTributeWithMaxAttackPointInHand()).getAttack() < monsterCardWithLeaseAttackPointOnPlayerBoard()) {
                    int place = this.player.getBoard().putMonster(monsterCardWithMaxDefenseInHand(), "DH");
                    setMonsterAttackPlayer(place, (Objects.requireNonNull(monsterCardWithMaxDefenseInHand()).getAttack()));
                    this.playerDefencePoints[place] = (Objects.requireNonNull(monsterCardWithMaxDefenseInHand()).getAttack());
                    addToAttackDefenceOfPutCard(place, this.player);
                    this.player.getBoard().removeCardFromHand(monsterCardWithMaxDefenseInHand());
                }
            } else if (numberOfMonstersOnPlayerBoard() < 5) {
                MonsterCard wantedMonster = monsterCardWithoutTributeWithMaxAttackPointInHand();
                int place = this.player.getBoard().putMonster(wantedMonster, "OO");
                setMonsterAttackPlayer(place, (Objects.requireNonNull(wantedMonster).getAttack()));
                this.playerDefencePoints[place] = (wantedMonster.getAttack());
                addToAttackDefenceOfPutCard(place, this.player);
                this.player.getBoard().removeCardFromHand(wantedMonster);
            }
        }
    }

    private void setSpellOrTrapInAI() {
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if ((card instanceof SpellCard || card instanceof TrapCard) && (numberOfSpellsAndTrapsOnPlayerBoard() < 5)) {
                this.player.getBoard().putSpellOrTrap(card, "H");
                toBeRemoved.add(i);
            }
        }
        if (toBeRemoved.size() > 0) {
            for (int i = toBeRemoved.size() - 1; i >= 0; i--) {
                this.player.getBoard().removeCardFromHand(this.player.getBoard().getCardInHandByNumber(toBeRemoved.get(i)));
            }
        }
    }

    private void attackInAI() {
        if (numberOfMonstersOnPlayerBoard() > 0) {
            if (numberOfMonstersOnRivalBoard() == 0)
                directAttackInAI();
            else if (allCardsOnRivalAreDH())
                attackAllDHInAI(numberOfMonstersOnRivalBoard(), numberOfMonstersOnPlayerBoard());
            else if (monsterCardWithMostAttackPointOnPlayerBoard() != 10 && monsterCardWithMostAttackPointOnRivalBoard() != 10) {
                attackOOInAI();
            }
        }
    }

    private boolean allCardsOnRivalAreDH() {
        int counterOfDH = 0;
        int counterOfCards = 0;
        for (int i = 0; i < 5; i++) {
            if (this.rival.getBoard().getMonsterByNumber(i) != null) {
                counterOfCards++;
                if (this.rival.getBoard().getMonsterConditionByNumber(i).equals("DH"))
                    counterOfDH++;
            }
        }
        return counterOfDH == counterOfCards;
    }

    private void directAttackInAI() {
        for (int i = 0; i < 5; i++) {
            MonsterCard monsterCard = this.player.getBoard().getMonsterByNumber(i);
            if (monsterCard != null) {
                try {
                    this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(i), BoardZone.MONSTERZONE, i, this.player);
                    try {
                        directAttack(0);
                    } catch (Exception ignored) {

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void attackAllDHInAI(int numberOfMonstersOnRivalBoard, int numberOfMonstersOnPlayerBoard) {
        int indexOfAttacker = 10;
        ArrayList<Integer> monstersOnBoardIndex = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (this.rival.getBoard().getMonsterByNumber(i) != null) {
                monstersOnBoardIndex.add(i);
            }
        }
        if (numberOfMonstersOnPlayerBoard > numberOfMonstersOnRivalBoard) {
            for (int i = 0; i < numberOfMonstersOnRivalBoard; i++) {
                if (i == 0)
                    indexOfAttacker = monsterCardWithMostAttackPointOnPlayerBoard();
                else if (i == 1)
                    indexOfAttacker = monsterCardWithSecondMostAttackPointOnRivalBoard(monsterCardWithMostAttackPointOnPlayerBoard());
                try {
                    this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(indexOfAttacker), BoardZone.MONSTERZONE, indexOfAttacker, this.player);
                    findWayAttack(monstersOnBoardIndex.get(i));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < numberOfMonstersOnPlayerBoard; i++) {
                if (i == 0)
                    indexOfAttacker = monsterCardWithMostAttackPointOnPlayerBoard();
                if (i == 1)
                    indexOfAttacker = monsterCardWithSecondMostAttackPointOnRivalBoard(monsterCardWithMostAttackPointOnPlayerBoard());
                try {
                    this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(indexOfAttacker), BoardZone.MONSTERZONE, indexOfAttacker, this.player);
                    findWayAttack(monstersOnBoardIndex.get(i));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void attackOOInAI() {
        if (monsterCardWithMostAttackPointOnPlayerBoard() != 10 && this.rival.getBoard().getMonsterConditionByNumber(monsterCardWithMostAttackPointOnRivalBoard()).equals("OO")) {
            MonsterCard strongestRivalAttacker = this.rival.getBoard().getMonsterByNumber(monsterCardWithLeaseAttackPointOnRivalBoard());
            MonsterCard strongestPlayerAttacker = this.player.getBoard().getMonsterByNumber(monsterCardWithMostAttackPointOnPlayerBoard());
            if (strongestRivalAttacker != null && strongestPlayerAttacker != null) {
                if (strongestPlayerAttacker.getAttack() > strongestRivalAttacker.getAttack()) {
                    try {
                        this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(monsterCardWithMostAttackPointOnPlayerBoard()), BoardZone.MONSTERZONE, monsterCardWithMostAttackPointOnPlayerBoard(), this.player);
                        findWayAttack(monsterCardWithMostAttackPointOnRivalBoard());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            } else if (monsterCardWithSecondMostAttackPointOnRivalBoard(monsterCardWithMostAttackPointOnPlayerBoard()) != 10) {
                MonsterCard secondStrongestRivalAttacker = this.rival.getBoard().getMonsterByNumber(monsterCardWithSecondMostAttackPointOnRivalBoard(monsterCardWithMostAttackPointOnPlayerBoard()));
                assert strongestPlayerAttacker != null;
                if (strongestPlayerAttacker.getAttack() > secondStrongestRivalAttacker.getAttack()) {
                    try {
                        this.selectedCard = new SelectedCard(this.player.getBoard().getMonsterByNumber(monsterCardWithMostAttackPointOnPlayerBoard()), BoardZone.MONSTERZONE, monsterCardWithMostAttackPointOnPlayerBoard(), this.player);
                        findWayAttack(monsterCardWithSecondMostAttackPointOnRivalBoard(monsterCardWithLeaseAttackPointOnRivalBoard()));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    private MonsterCard monsterCardWithoutTributeWithMaxAttackPointInHand() {
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        int maxAttackPoint = 0;
        int indexOfMaxAttacker = 10;
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getLevel() < 5 && ((MonsterCard) card).getAttack() > maxAttackPoint) {
                    maxAttackPoint = ((MonsterCard) card).getAttack();
                    indexOfMaxAttacker = i;
                }
        }
        if (indexOfMaxAttacker != 10)
            return (MonsterCard) this.player.getBoard().getCardInHandByNumber(indexOfMaxAttacker);
        else return null;
    }

    private MonsterCard monsterCardWithOneTributeWithMaxAttackPointInHand() {
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        int maxAttackPoint = 0;
        int indexOfMaxAttacker = 10;
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card != null) {
                if (card instanceof MonsterCard)
                    if (((MonsterCard) card).getLevel() == 5 || ((MonsterCard) card).getLevel() == 6)
                        if (((MonsterCard) card).getAttack() > maxAttackPoint) {
                            maxAttackPoint = ((MonsterCard) card).getAttack();
                            indexOfMaxAttacker = i;
                        }
            }
        }
        if (indexOfMaxAttacker != 10)
            return (MonsterCard) this.player.getBoard().getCardInHandByNumber(indexOfMaxAttacker);
        else return null;
    }

    private MonsterCard monsterCardWithTwoTributesWithMaxAttackPointInHand() {
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        int maxAttackPoint = 0;
        int indexOfMaxAttacker = 10;
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card != null) {
                if (card instanceof MonsterCard)
                    if (((MonsterCard) card).getLevel() > 6)
                        if (((MonsterCard) card).getAttack() > maxAttackPoint) {
                            maxAttackPoint = ((MonsterCard) card).getAttack();
                            indexOfMaxAttacker = i;
                        }
            }
        }
        if (indexOfMaxAttacker != 10)
            return (MonsterCard) this.player.getBoard().getCardInHandByNumber(indexOfMaxAttacker);
        else return null;
    }

    private MonsterCard monsterCardWithMaxDefenseInHand() {
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        int maxDefencePoint = 0;
        int indexOfMaxDefender = 10;
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card != null) {
                if (card instanceof MonsterCard)
                    if (((MonsterCard) card).getDefence() > maxDefencePoint) {
                        maxDefencePoint = ((MonsterCard) card).getDefence();
                        indexOfMaxDefender = i;
                    }
            }
        }
        if (indexOfMaxDefender != 10)
            return (MonsterCard) this.player.getBoard().getCardInHandByNumber(indexOfMaxDefender);
        else return null;
    }

    private int monsterCardWithLeaseAttackPointOnRivalBoard() {
        int leastAttackPoint = 100000;
        int indexOfMinAttacker = 10;
        for (int i = 0; i < 5; i++) {
            MonsterCard card = this.rival.getBoard().getMonsterByNumber(i);
            if (card != null)
                if (this.rival.getBoard().getMonsterConditionByNumber(i).equals("OO"))
                    if (leastAttackPoint > card.getAttack()) {
                        indexOfMinAttacker = i;
                        leastAttackPoint = card.getAttack();
                    }
        }
        return indexOfMinAttacker;
    }

    private int monsterCardWithLeaseAttackPointOnPlayerBoard() {
        int leastAttackPoint = 100000;
        int indexOfMinAttacker = 10;
        for (int i = 0; i < 5; i++) {
            MonsterCard card = this.player.getBoard().getMonsterByNumber(i);
            if (card != null)
                if (this.player.getBoard().getMonsterConditionByNumber(i).equals("OO"))
                    if (leastAttackPoint > card.getAttack()) {
                        indexOfMinAttacker = i;
                        leastAttackPoint = card.getAttack();
                    }
        }
        return indexOfMinAttacker;
    }

    private int monsterCardWithSecondMostAttackPointOnRivalBoard(int indexOfStrongestAttacker) {
        int indexOfSecondStrongestAttacker = 10;
        int secondMostAttackPoint = 0;
        if (numberOfMonstersOnPlayerBoard() > 1) {
            for (int i = 0; i < 5; i++) {
                MonsterCard card = this.rival.getBoard().getMonsterByNumber(i);
                if (card != null)
                    if (i != indexOfStrongestAttacker && this.rival.getBoard().getMonsterConditionByNumber(i).equals("OO")) {
                        if (secondMostAttackPoint < card.getAttack()) {
                            indexOfSecondStrongestAttacker = i;
                            secondMostAttackPoint = card.getAttack();
                        }
                    }
            }
        }
        return indexOfSecondStrongestAttacker;
    }

    private int monsterCardWithMostAttackPointOnPlayerBoard() {
        int mostAttackPoint = 0;
        int indexOfMaxAttacker = 10;
        for (int i = 0; i < 5; i++) {
            MonsterCard card = this.player.getBoard().getMonsterByNumber(i);
            if (card != null)
                if (this.player.getBoard().getMonsterConditionByNumber(i).equals("OO"))
                    if (mostAttackPoint < card.getAttack()) {
                        indexOfMaxAttacker = i;
                        mostAttackPoint = card.getAttack();
                    }
        }
        return indexOfMaxAttacker;
    }

    private int monsterCardWithLeastDefencePointOnRivalBoard() {
        int leastDefencePoint = 100000;
        int indexOfMinDefender = 10;
        for (int i = 0; i < 5; i++) {
            MonsterCard card = this.rival.getBoard().getMonsterByNumber(i);
            if (card != null)
                if (this.rival.getBoard().getMonsterConditionByNumber(i).equals("DO"))
                    if (leastDefencePoint > card.getDefence()) {
                        leastDefencePoint = card.getDefence();
                        indexOfMinDefender = i;
                    }
        }
        return indexOfMinDefender;
    }

    private int monsterCardWithMostAttackPointOnRivalBoard() {
        int mostAttackPoint = 0;
        int indexOfMaxAttacker = 10;
        for (int i = 0; i < 5; i++) {
            MonsterCard card = this.rival.getBoard().getMonsterByNumber(i);
            if (card != null)
                if (this.rival.getBoard().getMonsterConditionByNumber(i).equals("OO"))
                    if (mostAttackPoint < card.getAttack()) {
                        mostAttackPoint = card.getAttack();
                        indexOfMaxAttacker = i;
                    }
        }
        return indexOfMaxAttacker;
    }

    private int numberOfMonstersOnPlayerBoard() {
        int numberOfMonsters = 0;
        for (int i = 0; i < 5; i++) {
            MonsterCard monsterCard = this.player.getBoard().getMonsterByNumber(i);
            if (monsterCard != null)
                numberOfMonsters++;
        }
        return numberOfMonsters;
    }

    private int numberOfMonstersOnRivalBoard() {
        int numberOfMonsters = 0;
        for (int i = 0; i < 5; i++) {
            MonsterCard monsterCard = this.rival.getBoard().getMonsterByNumber(i);
            if (monsterCard != null)
                numberOfMonsters++;
        }
        return numberOfMonsters;
    }

    private int numberOfSpellsAndTrapsOnPlayerBoard() {
        int numberOfSpellsAndTraps = 0;
        for (int i = 0; i < 5; i++) {
            Card card = this.player.getBoard().getSpellAndTrapByNumber(i);
            if (card != null)
                numberOfSpellsAndTraps++;
        }
        return numberOfSpellsAndTraps;
    }

    private int numberOfLevelOneTwoFourMonstersInPlayerHand() {
        int numberOfWantedMonsters = 0;
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getLevel() < 5)
                    numberOfWantedMonsters++;
        }
        return numberOfWantedMonsters;
    }

    private int numberOfLevelFiveSixMonstersInPlayerHand() {
        int numberOfWantedMonsters = 0;
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getLevel() == 5 || ((MonsterCard) card).getLevel() == 6)
                    numberOfWantedMonsters++;
        }
        return numberOfWantedMonsters;
    }

    private int numberOfLevelSevenEightMonstersInHand() {
        int numberOfWantedMonsters = 0;
        int numberOfCardsInHand = this.player.getBoard().getCardsInHand().size();
        for (int i = 0; i < numberOfCardsInHand; i++) {
            Card card = this.player.getBoard().getCardInHandByNumber(i);
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getLevel() > 6)
                    numberOfWantedMonsters++;
        }
        return numberOfWantedMonsters;
    }

    public void doNothing(int number) {

    }

    public void doNothing() {

    }


    public ArrayList<Image> getGraveYard(User user) {
        ArrayList<Image> graveYard = new ArrayList<>();
        for (Card card : user.getBoard().getCardsInGraveyard()) {
            graveYard.add(card.getImage());
        }
        return graveYard;
    }

    private void changeBackground(SpellCard spellCard) {
        if (spellCard.getName().equals("CLOSED_FOREST") || spellCard.getName().equals("FOREST") || spellCard.getName().equals("UMIIRUKA") || spellCard.getName().equals("YAMI")) {
            URL url;
            switch (spellCard.getName()) {
                case "CLOSED_FOREST":
                    url = getClass().getResource("/images/gameBackground/closed_forest.bmp");
                    break;
                case "FOREST":
                    url = getClass().getResource("/images/gameBackground/forrest.bmp");
                    break;
                case "UMIIRUKA":
                    url = getClass().getResource("/images/gameBackground/umiruka.bmp");
                    break;
                default:
                    url = getClass().getResource("/images/gameBackground/yami.bmp");
                    break;
            }

            Image image = new Image(String.valueOf(url));
            gameViewGraphic.gameBackground.setImage(image);
        }
    }


    public void playerWon5Games() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Congratulation!");
        info.setContentText("You just won 5 games in a row and received a bronze medal!");
        info.showAndWait();
    }

    public void playerWon10Games(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Congratulation!");
        info.setContentText("You just won 10 games in a row and received a silver medal!");
        info.showAndWait();
    }

    public void playerWon15Games(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Congratulation!");
        info.setContentText("You just won 15 games in a row and received a gold medal!");
        info.showAndWait();
    }

    public void playerWonWithoutMonster(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Congratulation!");
        info.setContentText("You just won 5 games without using any spell or trap!");
        info.showAndWait();
    }

    public void playerWonGift(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Congratulation!");
        info.setContentText("You just won with losing less than 25percent of your lifepoint!");
        info.showAndWait();
    }

    public void playerLost3games(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Dude!");
        info.setContentText("You just lost three games in a row!!!");
        info.showAndWait();
    }

    public void playerLost7Games(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Dude!");
        info.setContentText("You just lost seven games in a row!!!");
        info.showAndWait();
    }

    public void playerLost10Games(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Dude!");
        info.setContentText("You just Lost ten games in a row!!!");
        info.showAndWait();
    }

    public void addToNumberOfTurns(){
        this.numberOfChangedTurns += 1;
    }
}

