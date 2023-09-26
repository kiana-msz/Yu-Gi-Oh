package controller.exeption;

public class EnterNumber extends Exception {
    public EnterNumber() {
        super("please enter a valid number");
    }
}
