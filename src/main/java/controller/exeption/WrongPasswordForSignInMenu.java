package controller.exeption;

public class WrongPasswordForSignInMenu extends Exception{
    public WrongPasswordForSignInMenu() {
        super("username and password do not match!");
    }
}
