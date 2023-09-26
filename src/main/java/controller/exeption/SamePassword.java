package controller.exeption;

public class SamePassword extends Exception {
    public SamePassword() {
        super("please enter a new password");
    }
}
