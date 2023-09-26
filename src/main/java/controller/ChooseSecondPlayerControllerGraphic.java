package controller;

import client.Main;
import controller.exeption.InvalidDeck;
import controller.exeption.NoActiveDeck;
import javafx.stage.Stage;
import model.User;
import view.ChooseDuelView;
import java.io.IOException;

public class ChooseSecondPlayerControllerGraphic {

    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public static void twoPlayerGame(User user, Stage stage) throws Exception {
        if (user.getActiveDeck() == null)
            throw new NoActiveDeck(user.getUsername());
        if (!user.getActiveDeck().isValid())
            throw new InvalidDeck(user.getUsername());
        ChooseDuelView.getInstance().setCurrentUser(user);
        ChooseDuelView.getInstance().setPlayerTwoName("");
        ChooseDuelView.getInstance().start(stage);
    }

    public static void sendInvitation(User sender, User receiver){
        try {
            Main.dataOutputStream.writeUTF("setRequest!@#"+sender.getUsername()+"!@#"+receiver.getUsername());
            Main.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
