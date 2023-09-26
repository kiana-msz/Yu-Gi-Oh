package controller.exeption;

public class AlreadySummoned extends Exception {
    public AlreadySummoned() {
        super("you already summoned/set on this turn");
    }
}
