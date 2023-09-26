package controller.exeption;

public class NoMonsterHere extends Exception {
    public NoMonsterHere() {
        super("there no monsters one this address");
    }
}
