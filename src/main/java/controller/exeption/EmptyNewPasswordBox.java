package controller.exeption;

public class EmptyNewPasswordBox extends Exception{
    public EmptyNewPasswordBox(){
        super("new password box is empty!Please fill it!");
    }
}
