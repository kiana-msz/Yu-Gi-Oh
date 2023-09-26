package controller.exeption;

public class CanNotAttackThisCard extends Exception {
    public CanNotAttackThisCard() {
        super("you can not attack this card right now");
    }
}
