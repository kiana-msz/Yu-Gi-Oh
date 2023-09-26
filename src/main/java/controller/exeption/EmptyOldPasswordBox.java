package controller.exeption;

public class EmptyOldPasswordBox extends Exception {
    public EmptyOldPasswordBox(){
        super("old password box is empty!Please fill it!");
    }
}
