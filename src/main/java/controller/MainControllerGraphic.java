package controller;

import client.LogInViewGraphic;
import client.Main;
import controller.exeption.InvalidDeck;
import controller.exeption.NoActiveDeck;
import javafx.stage.Stage;
import model.User;
import view.*;

import java.io.IOException;

public class MainControllerGraphic {

    public static void logout(User user,Stage stage) throws Exception {
        sendLogoutToServer(user);
        LogInViewGraphic.getInstance().start(stage);
    }

    public static void showScoreBoard(User user, Stage stage) throws Exception {
        ScoreBoardViewGraphic.getInstance().setCurrentUser(user);
        ScoreBoardViewGraphic.getInstance().start(stage);
    }

    public static void showProfileMenu(User user, Stage stage) throws Exception {
        ProfileViewForGraphic.getInstance().setCurrentUser(user);
        ProfileViewForGraphic.getInstance().start(stage);
    }

    public static void showShopMenu(User user, Stage stage) throws Exception {
        ShopViewGraphic.getInstance().setCurrentUser(user);
        ShopViewGraphic.getInstance().start(stage);
    }

    public static void showDeckMenu(User user, Stage stage) throws Exception {
        AllDecksViewGraphic.getInstance().setCurrentUser(user);
        AllDecksViewGraphic.getInstance().start(stage);
    }

    public static void showNewDuelMenu(User user, Stage stage,Thread thread) throws Exception {
        if (user.getActiveDeck() == null)
            throw new NoActiveDeck(user.getUsername());
        if (!user.getActiveDeck().isValid())
            throw new InvalidDeck(user.getUsername());
        if(thread!=null)
            thread.stop();
        ChooseSecondPlayerViewGraphic.getInstance().setCurrentUser(user);
        ChooseSecondPlayerViewGraphic.getInstance().start(stage);
    }

    public static void sendLogoutToServer(User user) {
        try {
            Main.dataOutputStream.writeUTF("logout " + user.getUsername());
            Main.dataOutputStream.flush();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
