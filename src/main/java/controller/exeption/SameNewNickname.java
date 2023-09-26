package controller.exeption;

public class SameNewNickname extends Exception{
    public SameNewNickname(){super("the new nickname is the same as the old one!");}
}
