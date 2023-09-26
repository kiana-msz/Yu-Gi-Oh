package controller.exeption;

public class EmptyNicknameBox extends Exception{
    public EmptyNicknameBox() {
        super("nickname box is empty!Please fill it!");
    }
}
