package model;

import controller.DuelController;

public class MonsterAction {
    private static MonsterAction instance;

    public static MonsterAction getInstance() {
        if (instance == null) instance = new MonsterAction();
        return instance;
    }

    public void addToMonsterAttackPoints(DuelController duelController, int amount, TakeActionCase takeActionCase, User owner, int targetNumber) {
        if (takeActionCase.equals(TakeActionCase.SUMMONED) || takeActionCase.equals(TakeActionCase.FLIP_SUMMONED)) {
            duelController.changeAllAttackPoints(1, amount);
        } else if (takeActionCase.equals(TakeActionCase.REMOVE_FROM_MONSTERZONE) || takeActionCase.equals(TakeActionCase.DIED_BY_BEING_ATTACKED)) {
            duelController.changeAllAttackPoints(-1, amount);
        } else if (takeActionCase.equals(TakeActionCase.ANY_MONSTER_PUT_IN_MONSTERZONE)) {
            if (owner.getUsername().equals(duelController.getPlayer().getUsername())) {
                if (!duelController.getPlayer().getBoard().getMonsterByNumber(targetNumber).equals(MonsterCard.COMMAND_KNIGHT)) {
                    duelController.changePlayerAttackPoint(targetNumber, amount);
                }
            } else duelController.changeRivalAttackPoint(targetNumber, amount);
        }
    }

    public boolean canBeAttackedCommandKnight(DuelController duelController, int monsterNumber) {
        if (!duelController.getRival().getBoard().getMonsterConditionByNumber(monsterNumber).equals("DH")) {
            for (int i = 0; i < 5; i++) {
                if (i != monsterNumber) {
                    if (duelController.getRival().getBoard().getMonsterByNumber(i) != null && !duelController.getRival().getBoard().getMonsterByNumber(i).equals(MonsterCard.COMMAND_KNIGHT))
                        return false;
                }
            }
            return true;
        }
        return true;
    }

    public void destroyAttackerYomiShip(DuelController duelController, TakeActionCase takeActionCase) {
        if (takeActionCase.equals(TakeActionCase.DIED_BY_BEING_ATTACKED)) {
            duelController.getPlayer().getBoard().putInGraveYard(duelController.getSelectedCard().getCard());
            duelController.getPlayer().getBoard().removeMonster(duelController.getSelectedCard().getNumber(), duelController, duelController.getPlayer());
            duelController.removeMonsterPlayer(duelController.getSelectedCard().getNumber());
        }
    }

    public void makeAttackerAttackPoint0Suijin(DuelController duelController, TakeActionCase takeActionCase, int targetNumber) {
        if (takeActionCase.equals(TakeActionCase.ATTACKED)) {
            if (!(duelController.getRival().getBoard().getMonsterConditionByNumber(targetNumber).equals("DH") || duelController.getActionsOnThisCardRival(targetNumber).contains(ActionsDoneInTurn.ENABLE_SUIJIN))) {
                int place = duelController.getSelectedCard().getNumber();
                if (!duelController.getHasEnabledSuijin(place)) {
                    duelController.setMonsterAttackPlayer(targetNumber, 0);
                    duelController.setHasEnabledSuijinTrue(place);
                }
            }
        }
    }
}
