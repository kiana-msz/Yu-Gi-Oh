package controller.exeption;

public class sameAddresses extends Exception {
    public sameAddresses() {
        super("you should select two different cards");
    }
}
