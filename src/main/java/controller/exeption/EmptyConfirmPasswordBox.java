package controller.exeption;

public class EmptyConfirmPasswordBox extends Exception {
    public EmptyConfirmPasswordBox() {
        super("confirm password box is empty!Please fill it!");
    }
}
