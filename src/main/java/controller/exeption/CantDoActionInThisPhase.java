package controller.exeption;

public class CantDoActionInThisPhase extends Exception{
    public CantDoActionInThisPhase(){super("you can’t do this action in this phase");}
}
