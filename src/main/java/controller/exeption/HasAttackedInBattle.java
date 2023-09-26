package controller.exeption;

public class HasAttackedInBattle extends Exception {
    public HasAttackedInBattle() {
        super("this card has attacked in battle phase and you can not change it's position");
    }
}
