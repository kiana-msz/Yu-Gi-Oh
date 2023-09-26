package controller.exeption;

public class WrongPassword extends Exception {
    public WrongPassword() {
        super("current password is invalid");
    }
}
