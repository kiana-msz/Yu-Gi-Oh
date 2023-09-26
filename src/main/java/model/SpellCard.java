package model;

import controller.DuelController;
import javafx.scene.image.Image;
import view.DuelView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SpellCard implements Card {


    MONSTER_REBORN(Icon.NORMAL, "Target 1 monster in either GY; Special Summon it.", Status.LIMITED, 2000, "/images/Cards/MonsterReborn.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                DuelView.printText("enter \"My\" or \"Rival\" to select graveyard");
                String graveyard = DuelView.scan();
                while (!graveyard.equals("cancel") && !graveyard.equals("My") && !graveyard.equals("Rival")) {
                    DuelView.printText("enter \"My\" or \"Rival\" to select graveyard");
                    graveyard = DuelView.scan();
                }
                switch (graveyard) {
                    case "My":
                        return SpellAction.getInstance().actionMonsterReborn(duelController,targetNumber,duelController.getPlayer());
                    case "Rival":
                        return SpellAction.getInstance().actionMonsterReborn(duelController,targetNumber,duelController.getRival());
                    default:
                        return false;
                }
            } else return true;
        }
    },

    TERRAFORMING(Icon.NORMAL, "Add 1 Field Spell from your Deck to your hand.", Status.LIMITED, 2500, "/images/Cards/Terraforming.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                boolean hasFieldSpell = false;
                for (Card card : duelController.getPlayer().getGameDeck().getMainDeck()) {
                    if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals(Icon.FIELD)) {
                        hasFieldSpell = true;
                        break;
                    }
                }
                if (!hasFieldSpell) {
                    DuelView.printText("You have no field spell card in your deck!!");
                    return false;
                }
                DuelView.printText("select one of these Field Spell cards from your deck to add to your hand:");
                int cardCounter = 1;
                for (Card card : duelController.getPlayer().getGameDeck().getMainDeck()) {
                    if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals(Icon.FIELD)) {
                        DuelView.printText(cardCounter + ": " + card.getName() + ": " + card.getDescription());
                        cardCounter++;
                    }
                }
                String choice = DuelView.scan();
                if (choice.equals("cancel")) return false;
                while (!choice.matches("\\d+") || Integer.parseInt(choice) < 1
                        || Integer.parseInt(choice) > cardCounter - 1) {
                    DuelView.printText("please Enter a valid Number!");
                    choice = DuelView.scan();
                    if (choice.equals("cancel")) return false;
                }
                cardCounter = 1;
                int i;
                for (i = 0; i < duelController.getPlayer().getGameDeck().getMainDeck().size(); i++) {
                    Card card = duelController.getPlayer().getGameDeck().getMainDeck().get(i);
                    if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals(Icon.FIELD)) {
                        if (cardCounter == Integer.parseInt(choice)) {
                            System.out.println(cardCounter + " " + i);
                            break;
                        } else
                            cardCounter++;
                    }
                }
                Card card = duelController.getPlayer().getGameDeck().getMainDeck().get(i);
                duelController.getPlayer().getBoard().getCardsInHand().add(card);
                duelController.getPlayer().getGameDeck().getMainDeck().remove(i);
                DuelView.printText("card added to hand successfully");
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    POT_OF_GREED(Icon.NORMAL, "Draw 2 cards.", Status.LIMITED, 2500, "/images/Cards/PotOfGreed.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                List<Card> deck = duelController.getPlayer().getGameDeck().getMainDeck();
                Card card1 = deck.get(deck.size() - 1);
                Card card2 = deck.get(deck.size() - 2);
                duelController.getPlayer().getBoard().getCardsInHand().add(card1);
                duelController.getPlayer().getBoard().getCardsInHand().add(card2);
                deck.remove(card1);
                deck.remove(card2);
                DuelView.printText("Spell activated successfully");
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    RAIGEKI(Icon.NORMAL, "Destroy all monsters your opponent controls.", Status.LIMITED, 2500, "/images/Cards/Raigeki.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                for (int i = 0; i < 5; i++) {
                    if (duelController.getPlayer().getBoard().getMonsterByNumber(i) != null) {
                        duelController.getPlayer().getBoard().getMonsterByNumber(i).takeAction(duelController, TakeActionCase.REMOVE_FROM_MONSTERZONE, duelController.getRival(), i);
                        duelController.getPlayer().getBoard().removeMonster(i, duelController, duelController.getPlayer());
                        duelController.setMonsterAttackRival(i, null);
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    CHANGE_OF_HEART(Icon.NORMAL, "Target 1 monster your opponent controls; take control of it until the End Phase.",
            Status.LIMITED, 2500, "/images/Cards/ChangeOfHeart.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    HARPIES_FEATHER_DUST(Icon.NORMAL, "Destroy all Spells and Traps your opponent controls.", Status.LIMITED, 2500,
            "/images/Cards/HarpiesFeatherDuster.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                for (int i = 0; i < 5; i++) {
                    if (duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i) != null) {
                        if (duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i) instanceof SpellCard)
                            ((SpellCard) duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i)).takeAction(duelController, TakeActionCase.REMOVE_FROM_SPELLTRAPZONE, duelController.getRival(), i);
                        duelController.getPlayer().getBoard().removeSpellOrTrap(i);
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    SWORD_OF_REVEALING_LIGHT(Icon.NORMAL, "After this card's activation, it remains on the field, but destroy " +
            "it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls" +
            " a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your" +
            " opponent's monsters cannot declare an attack.", Status.UNLIMITED, 2500, "/images/Cards/SwordOfRevealingLight.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    DARK_HOLE(Icon.NORMAL, "Destroy all monsters on the field.", Status.UNLIMITED, 2500, "/images/Cards/DarkHole.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                for (int i = 0; i < 5; i++) {
                    if (duelController.getPlayer().getBoard().getMonsterByNumber(i) != null) {
                        duelController.getPlayer().getBoard().getMonsterByNumber(i).takeAction(duelController, TakeActionCase.REMOVE_FROM_MONSTERZONE, duelController.getRival(), i);
                        duelController.getPlayer().getBoard().removeMonster(i, duelController, duelController.getPlayer());
                    }
                    if (duelController.getRival().getBoard().getMonsterByNumber(i) != null) {
                        duelController.getRival().getBoard().getMonsterByNumber(i).takeAction(duelController, TakeActionCase.REMOVE_FROM_MONSTERZONE, duelController.getRival(), i);
                        duelController.getRival().getBoard().removeMonster(i, duelController, duelController.getRival());
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    SUPPLY_SQUAD(Icon.CONTINUOUS, "Once per turn, if a monster(s) you control is destroyed by battle or card" +
            " effect: Draw 1 card.", Status.UNLIMITED, 4000, "/images/Cards/SupplySquad.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    SPELL_ABSORPTION(Icon.CONTINUOUS, "Each time a Spell Card is activated, gain 500 Life Points immediately " +
            "after it resolves.", Status.UNLIMITED, 4000, "/images/Cards/SpellAbsorption.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.ANY_SPELL_ACTIVATED)) {
                if (owner.equals(duelController.getPlayer()))
                    duelController.getPlayer().increaseLifePoint(500);
                else
                    duelController.getRival().increaseLifePoint(500);
            }
            return true;
        }

    },

    MESSENGER_OF_PEACE(Icon.CONTINUOUS, "Monsters with 1500 or more ATK cannot declare an attack. Once per turn," +
            " during your Standby Phase, pay 100 LP or destroy this card.", Status.UNLIMITED, 4000, "/images/Cards/MessengerOfPeace.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    TWIN_TWISTERS(Icon.QUICK_PLAY, "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them."
            , Status.UNLIMITED, 3500, "/images/Cards/TwinTwisters.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                int numberOfCardsInHand = duelController.getPlayer().getBoard().getCardsInHand().size();
                if (numberOfCardsInHand == 0) {
                    DuelView.printText("you don't have enough card in your hand to use this spell");
                    return false;
                } else {
                    numberOfCardsInHand -= 1;
                    DuelView.printText("please enter a number between 1 and " + (numberOfCardsInHand + 1) + " to choose" +
                            " a card from your hand to be removed.");
                    String cardNumber = DuelView.scan();
                    if (cardNumber.equals("cancel")) return false;
                    while (!cardNumber.matches("\\d+") || Integer.parseInt(cardNumber) < 1
                            || Integer.parseInt(cardNumber) > numberOfCardsInHand + 1) {
                        DuelView.printText("please Enter a valid Number!");
                        cardNumber = DuelView.scan();
                        if (cardNumber.equals("cancel")) return false;
                    }
                    int address = Integer.parseInt(cardNumber) - 1;
                    Card toBeRemoved = duelController.getPlayer().getBoard().getCardInHandByNumber(address);
                    duelController.getPlayer().getBoard().removeCardFromHand(toBeRemoved);
                    DuelView.printText("the card was successfully removed from your hand. Now please enter \"My\" or \"Rival\" to select field");
                    String field = DuelView.scan();
                    while (!field.equals("cancel") && !field.equals("My") && !field.equals("Rival")) {
                        DuelView.printText("enter \"My\" or \"Rival\" to select field");
                        field = DuelView.scan();
                    }
                    if (field.equals("cancel")) return false;
                    SpellAction.getInstance().destroySpellOrTrap(duelController, field);
                    DuelView.printText("now, if you want to destroy another spell or trap, type the word \"My\" or \"Rival\", otherwise type the word continue");
                    field = DuelView.scan();
                    while (!field.equals("continue") && !field.equals("My") && !field.equals("Rival")) {
                        DuelView.printText("enter \"My\" or \"Rival\" to select field, or enter continue");
                        field = DuelView.scan();
                    }
                    if (field.equals("My") || field.equals("Rival")) {
                        SpellAction.getInstance().destroySpellOrTrap(duelController, field);
                    }
                    SpellAction.getInstance().enableSpellAbsorptions(duelController);
                    duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
                }
            }
            return true;
        }
    },

    MYSTICAL_SPACE_TYPHOON(Icon.QUICK_PLAY, "Target 1 Spell/Trap on the field; destroy that target.",
            Status.UNLIMITED, 3500, "/images/Cards/MysticalSpaceTyphoon.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                DuelView.printText("please type the word \"My\" or \"Rival\" to choose a field to destroy the trap or spell");
                String field = DuelView.scan();
                while (!field.equals("cancel") && !field.equals("My") && !field.equals("Rival")) {
                    DuelView.printText("enter \"My\" or \"Rival\" to select field");
                    field = DuelView.scan();
                }
                if (field.equals("cancel")) return false;
                SpellAction.getInstance().destroySpellOrTrap(duelController, field);
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
                duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
            }
            return true;
        }
    },

    RING_OF_DEFENSE(Icon.QUICK_PLAY, "When a Trap effect that inflicts damage is activated: Make that effect " +
            "damage 0.", Status.UNLIMITED, 3500, "/images/Cards/RingOfDefense.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    YAMI(Icon.FIELD, "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters" +
            " on the field lose 200 ATK/DEF.", Status.UNLIMITED, 4300, "/images/Cards/Yami.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.FIEND) || playerMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changePlayerAttackPoint(i, 200);
                            duelController.changePlayerDefencePoint(i, 200);
                        } else if (playerMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changePlayerAttackPoint(i, -200);
                            duelController.changePlayerDefencePoint(i, -200);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.FIEND) || rivalMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changeRivalAttackPoint(i, 200);
                            duelController.changeRivalDefencePoint(i, 200);
                        } else if (rivalMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changeRivalAttackPoint(i, -200);
                            duelController.changeRivalDefencePoint(i, -200);
                        }
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            } else if (takeActionCase.equals(TakeActionCase.REMOVE_FROM_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.FIEND) || playerMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changePlayerAttackPoint(i, -200);
                            duelController.changePlayerDefencePoint(i, -200);
                        } else if (playerMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changePlayerAttackPoint(i, 200);
                            duelController.changePlayerDefencePoint(i, 200);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.FIEND) || rivalMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changeRivalAttackPoint(i, -200);
                            duelController.changeRivalDefencePoint(i, -200);
                        } else if (rivalMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changeRivalAttackPoint(i, 200);
                            duelController.changeRivalDefencePoint(i, 200);
                        }
                    }
                }
            } else if (takeActionCase.equals(TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE)) {
                if (owner.getUsername().equals(duelController.getPlayer().getUsername())) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(targetNumber);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.FIEND) || playerMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changePlayerAttackPoint(targetNumber, 200);
                            duelController.changePlayerDefencePoint(targetNumber, 200);
                        } else if (playerMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changePlayerAttackPoint(targetNumber, -200);
                            duelController.changePlayerDefencePoint(targetNumber, -200);
                        }
                    }
                } else {
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(targetNumber);
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.FIEND) || rivalMonster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                            duelController.changeRivalAttackPoint(targetNumber, 200);
                            duelController.changeRivalDefencePoint(targetNumber, 200);
                        } else if (rivalMonster.getMonsterType().equals(MonsterType.FAIRY)) {
                            duelController.changeRivalAttackPoint(targetNumber, -200);
                            duelController.changeRivalDefencePoint(targetNumber, -200);
                        }
                    }
                }
            }
            return true;
        }
    },

    FOREST(Icon.FIELD, "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
            Status.UNLIMITED, 4300, "/images/Cards/Forest.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.INSECT) || playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(i, 200);
                            duelController.changePlayerDefencePoint(i, 200);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.INSECT) || rivalMonster.getMonsterType().equals(MonsterType.BEAST) || rivalMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changeRivalAttackPoint(i, 200);
                            duelController.changeRivalDefencePoint(i, 200);
                        }
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            } else if (takeActionCase.equals(TakeActionCase.REMOVE_FROM_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.INSECT) || playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(i, -200);
                            duelController.changePlayerDefencePoint(i, -200);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.INSECT) || rivalMonster.getMonsterType().equals(MonsterType.BEAST) || rivalMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changeRivalAttackPoint(i, -200);
                            duelController.changeRivalDefencePoint(i, -200);
                        }
                    }
                }
            } else if (takeActionCase.equals(TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE)) {
                if (owner.getUsername().equals(duelController.getPlayer().getUsername())) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(targetNumber);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.INSECT) || playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(targetNumber, 200);
                            duelController.changePlayerDefencePoint(targetNumber, 200);
                        }
                    }
                } else {
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(targetNumber);
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.INSECT) || rivalMonster.getMonsterType().equals(MonsterType.BEAST) || rivalMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changeRivalAttackPoint(targetNumber, 200);
                            duelController.changeRivalDefencePoint(targetNumber, 200);
                        }
                    }
                }
            }
            return true;
        }

    },

    CLOSED_FOREST(Icon.FIELD, "All Beast-Type monsters you control gain 100 ATK for each monster in your " +
            "Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn " +
            "this card is destroyed.", Status.UNLIMITED, 4300, "/images/Cards/ClosedForest.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            int amount = 0;
            for (Card card : duelController.getPlayer().getBoard().getCardsInGraveyard()) {
                if (card instanceof MonsterCard) {
                    amount++;
                }
            }
            amount = amount * 100;
            if (takeActionCase.equals(TakeActionCase.PUT_IN_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(i, amount);
                        }
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            } else if (takeActionCase.equals(TakeActionCase.REMOVE_FROM_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(i, -1 * amount);
                        }
                    }
                }
            } else if (takeActionCase.equals(TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE)) {
                if (owner.getUsername().equals(duelController.getPlayer().getUsername())) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(targetNumber);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.BEAST) || playerMonster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                            duelController.changePlayerAttackPoint(targetNumber, amount);
                        }
                    }
                }
            }
            return true;
        }

    },

    UMIIRUKA(Icon.FIELD, "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 " +
            "points.", Status.UNLIMITED, 4300, "/images/Cards/Umiiruka.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changePlayerAttackPoint(i, 500);
                            duelController.changePlayerDefencePoint(i, -400);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changeRivalAttackPoint(i, 500);
                            duelController.changeRivalDefencePoint(i, -400);
                        }
                    }
                }
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            } else if (takeActionCase.equals(TakeActionCase.REMOVE_FROM_FIELDZONE_FACE_UP)) {
                for (int i = 0; i < 5; i++) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(i);
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(i);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changePlayerAttackPoint(i, -500);
                            duelController.changePlayerDefencePoint(i, 400);
                        }
                    }
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changeRivalAttackPoint(i, -500);
                            duelController.changeRivalDefencePoint(i, 400);
                        }
                    }
                }
            } else if (takeActionCase.equals(TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE)) {
                if (owner.getUsername().equals(duelController.getPlayer().getUsername())) {
                    MonsterCard playerMonster = duelController.getPlayer().getBoard().getMonsterByNumber(targetNumber);
                    if (playerMonster != null) {
                        if (playerMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changePlayerAttackPoint(targetNumber, 500);
                            duelController.changePlayerDefencePoint(targetNumber, -400);
                        }
                    }
                } else {
                    MonsterCard rivalMonster = duelController.getRival().getBoard().getMonsterByNumber(targetNumber);
                    if (rivalMonster != null) {
                        if (rivalMonster.getMonsterType().equals(MonsterType.AQUA)) {
                            duelController.changeRivalAttackPoint(targetNumber, 500);
                            duelController.changeRivalDefencePoint(targetNumber, -400);
                        }
                    }
                }
            }
            return true;
        }

    },

    SWORD_OF_DARK_DESTRUCTION(Icon.EQUIP, "A DARK monster equipped with this card increases its ATK by 400 " +
            "points and decreases its DEF by 200 points.", Status.UNLIMITED, 4300, "/images/Cards/SwordOfDarkDestruction.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    BLACK_PENDANT(Icon.EQUIP, "The equipped monster gains 500 ATK. When this card is sent from the field to the" +
            " Graveyard: Inflict 500 damage to your opponent.", Status.UNLIMITED, 4300, "/images/Cards/BlackPendant.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    UNITED_WE_STAND(Icon.EQUIP, "The equipped monster gains 800 ATK/DEF for each face-up monster you control.",
            Status.UNLIMITED, 4300, "/images/Cards/UnitedWeStand.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_FIELDZONE_FACE_UP)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    MAGNUM_SHIELD(Icon.EQUIP, "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position.\n" +
            "Attack Position: It gains ATK equal to its original DEF.\n" +
            "Defense Position: It gains DEF equal to its original ATK.", Status.UNLIMITED, 4300, "/images/Cards/MagnumShield.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    },

    ADVANCED_RITUAL_ART(Icon.RITUAL, "This card can be used to Ritual Summon any 1 Ritual Monster. You must " +
            "also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual" +
            " Monster.", Status.UNLIMITED, 3000, "/images/Cards/AdvancedRitualArt.jpg") {
        public boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber) {
            if (takeActionCase.equals(TakeActionCase.PUT_IN_SPELLTRAPZONE)) {
                SpellAction.getInstance().enableSpellAbsorptions(duelController);
            }
            return true;
        }
    };


    private final Icon icon;

    private final Status status;
    private final String description;
    private final int price;
    private Image image;

    SpellCard(Icon icon, String description, Status status, int price,String image) {
        this.icon = icon;
        this.status = status;
        this.description = description;
        this.price = price;
        this.image = new Image(image);
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public Image getImage() {
        return image;
    }

    public abstract boolean takeAction(DuelController duelController, TakeActionCase takeActionCase, User owner, int targetNumber);

    public String getNamePascalCase() {
        String name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        Pattern pattern = Pattern.compile("_([a-z])[a-z]+");
        Matcher matcher = pattern.matcher(name);
        while (matcher.find())
            name = name.replace("_" + matcher.group(1), "_" + matcher.group(1).toUpperCase());
        name = name.replaceAll("_", " ");
        return name;
    }

    @Override
    public String toString() {
        String name = this.getNamePascalCase();
        String toReturn = "Name: " + name + "\n" +
                "Spell" + "\n";
        String type = this.icon.getNamePascalCase();
        toReturn = toReturn + "Type: " + type + "\n" +
                "Description: " + this.description;
        return toReturn;
    }



}
