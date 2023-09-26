package controller.exeption;

public class EmptyUsernameBox extends Exception {
    public EmptyUsernameBox() {
        super("Username box is empty!Please fill it!");
    }
}
