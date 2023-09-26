package controller.exeption;

public class CanNotAttack extends Exception {
    public CanNotAttack() {
        super("you can’t attack with this card");
    }
}
