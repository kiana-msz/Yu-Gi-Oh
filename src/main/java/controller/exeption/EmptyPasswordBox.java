package controller.exeption;

public class EmptyPasswordBox extends Exception {
    public EmptyPasswordBox() {
        super("password box is empty!Please fill it!");
    }
}
