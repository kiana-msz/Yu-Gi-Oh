package controller.exeption;

public class RepetitiveUsername extends Exception{
public RepetitiveUsername(String username){super("user with username " + username + " already exists");}
}
