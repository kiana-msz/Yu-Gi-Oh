package controller.exeption;

public class ImproperPhase extends Exception {
    public ImproperPhase() {
        super("you can’t do this action in this phase");
    }
}
