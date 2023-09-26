package controller.exeption;

public class CanNotFlipSummon extends Exception {
    public CanNotFlipSummon() {
        super("you can’t flip summon this card");
    }
}
