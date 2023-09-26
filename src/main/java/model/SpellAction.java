package model;


import controller.DuelController;
import view.DuelView;

import java.util.ArrayList;

public class SpellAction {
    private static SpellAction instance;

    public static SpellAction getInstance() {
        if (instance == null) instance = new SpellAction();
        return instance;
    }

    public void enableSpellAbsorptions(DuelController duelController) {
        for (int i = 0; i < 5; i++) {
            if (duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i) != null
                    && duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i) instanceof SpellCard
                    && duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i).equals(SpellCard.SPELL_ABSORPTION)) {
                SpellCard.SPELL_ABSORPTION.takeAction(duelController, TakeActionCase.ANY_SPELL_ACTIVATED, duelController.getPlayer(), i);
            }
            if (duelController.getRival().getBoard().getSpellAndTrapByNumber(i) != null
                    && duelController.getRival().getBoard().getSpellAndTrapByNumber(i) instanceof SpellCard
                    && duelController.getRival().getBoard().getSpellAndTrapByNumber(i).equals(SpellCard.SPELL_ABSORPTION)) {
                SpellCard.SPELL_ABSORPTION.takeAction(duelController, TakeActionCase.ANY_SPELL_ACTIVATED, duelController.getRival(), i);
            }
        }
    }

    public void destroySpellOrTrap(DuelController duelController, String field) {
        ArrayList<Integer> updatedOnBoardSpellOrTrap = new ArrayList<>();
        if (field.equals("My")) {
            for (int i = 0; i < 5; i++) {
                Card notNullSpellOrTrap = duelController.getPlayer().getBoard().getSpellAndTrapByNumber(i);
                if (notNullSpellOrTrap != null)
                    updatedOnBoardSpellOrTrap.add(i);
            }
            DuelView.printText("please type the spell or trap's address from spell zone to be destroyed");
            String input = DuelView.scan();
            while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                DuelView.printText("please type the spell or trap's address from spell zone to be destroyed, between 1 and 5");
                input = DuelView.scan();
            }
            int myAddress = DuelController.getPlayerGroundNumbers()[Integer.parseInt(input) - 1] - 1;
            while (!(updatedOnBoardSpellOrTrap.contains(myAddress))) {
                DuelView.printText("the chosen address is empty");
                input = DuelView.scan();
                while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                    DuelView.printText("please type the spell or trap's address from spell zone to be destroyed");
                    input = DuelView.scan();
                }
                myAddress = DuelController.getPlayerGroundNumbers()[Integer.parseInt(input) - 1] - 1;
            }
            duelController.getPlayer().getBoard().removeSpellOrTrap(myAddress);
        } else {
            for (int i = 0; i < 5; i++) {
                Card notNullSpellOrTrap = duelController.getRival().getBoard().getSpellAndTrapByNumber(i);
                if (notNullSpellOrTrap != null)
                    updatedOnBoardSpellOrTrap.add(i);
            }
            DuelView.printText("please type the spell or trap's address from spell zone to be destroyed");
            String input = DuelView.scan();
            while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                DuelView.printText("please type the spell or trap's address from spell zone to be destroyed, between 1 and 5");
                input = DuelView.scan();
            }
            int myAddress = DuelController.getPlayerGroundNumbers()[Integer.parseInt(input) - 1] - 1;
            while (!(updatedOnBoardSpellOrTrap.contains(myAddress))) {
                DuelView.printText("the chosen address is empty");
                input = DuelView.scan();
                while (!input.matches("\\d+") || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                    DuelView.printText("please type the spell or trap's address from spell zone to be destroyed");
                    input = DuelView.scan();
                }
                myAddress = DuelController.getPlayerGroundNumbers()[Integer.parseInt(input) - 1] - 1;
            }
            duelController.getRival().getBoard().removeSpellOrTrap(myAddress);
        }
        DuelView.printText("the chosen spell or trap was destroyed");
    }

    public boolean actionMonsterReborn(DuelController duelController,int targetNumber,User user){
        boolean hasMonster = false;
        for (int i = 0; i < user.getBoard().getCardsInGraveyard().size(); i++) {
            Card card = user.getBoard().getCardsInGraveyard().get(i);
            if (card instanceof MonsterCard) {
                hasMonster = true;
                break;
            }
        }
        if (!hasMonster) {
            DuelView.printText("Graveyard is empty!");
            return false;
        }
        DuelView.printText("select one these cards by number:");
        int monsterCounter = 1;
        for (int i = 0; i < user.getBoard().getCardsInGraveyard().size(); i++) {
            Card card = user.getBoard().getCardsInGraveyard().get(i);
            if (card instanceof MonsterCard) {
                DuelView.printText(monsterCounter + ": " + card.getNamePascalCase() + ": " + card.getDescription());
                monsterCounter++;
                break;
            }
        }
        String cardNumber = DuelView.scan();
        if (cardNumber.equals("cancel")) return false;
        while (!cardNumber.matches("\\d+") || Integer.parseInt(cardNumber) < 1
                || Integer.parseInt(cardNumber) > monsterCounter - 1) {
            DuelView.printText("please Enter a valid Number!");
            cardNumber = DuelView.scan();
            if (cardNumber.equals("cancel")) return false;
        }
        monsterCounter = 1;
        int i;
        for (i = 0; i < user.getBoard().getCardsInGraveyard().size(); i++) {
            Card card = user.getBoard().getCardsInGraveyard().get(i);
            if (card instanceof MonsterCard) {
                if (monsterCounter == Integer.parseInt(cardNumber))
                    break;
                else
                    monsterCounter++;
            }
        }
        Card card = user.getBoard().getCardsInGraveyard().get(i);
        DuelView.printText("enter the position you want to summon monster in(attack or defence)");
        String position = DuelView.scan();
        if (position.equals("cancel")) return false;
        while (!(position.equals("attack") || position.equals("defence"))) {
            DuelView.printText("please enter a valid position!");
            position = DuelView.scan();
            if (position.equals("cancel")) return false;
        }
        switch (position) {
            case "attack":
                position = "OO";
                break;
            case "defence":
                position = "DO";
                break;
        }
        int targetPlace = duelController.getPlayer().getBoard().putMonster((MonsterCard) card, position);
        ((MonsterCard) card).takeAction(duelController, TakeActionCase.SUMMONED, duelController.getPlayer(), targetPlace);
        user.getBoard().getCardsInGraveyard().remove(i);
        DuelView.printText("special summoned successfully");
        SpellAction.getInstance().enableSpellAbsorptions(duelController);
        duelController.getPlayer().getBoard().removeSpellOrTrap(targetNumber);
        return true;
    }

}
