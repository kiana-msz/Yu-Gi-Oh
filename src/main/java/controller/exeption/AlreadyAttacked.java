package controller.exeption;

public class AlreadyAttacked extends Exception {
    public AlreadyAttacked() {
        super("this card already attacked");
    }
}
