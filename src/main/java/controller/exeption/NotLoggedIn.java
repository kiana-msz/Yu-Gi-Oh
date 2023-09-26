package controller.exeption;

public class NotLoggedIn extends Exception{
    public NotLoggedIn(){super("please login first");}
}
