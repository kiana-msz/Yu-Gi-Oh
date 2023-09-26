package controller.exeption;

public class SameNewUsername extends Exception{
    public SameNewUsername(){
        super("the new username is the same as the old one!");
    }
}
