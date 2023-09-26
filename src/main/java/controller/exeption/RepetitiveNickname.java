package controller.exeption;

public class RepetitiveNickname extends Exception {
    public RepetitiveNickname(String nickname) {
        super("user with nickname " + nickname + " already exists");
    }
}