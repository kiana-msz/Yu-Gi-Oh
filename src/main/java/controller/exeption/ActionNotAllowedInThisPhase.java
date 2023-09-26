package controller.exeption;

public class ActionNotAllowedInThisPhase extends Exception {
    public ActionNotAllowedInThisPhase() {
        super("action not allowed in this phase");
    }
}
