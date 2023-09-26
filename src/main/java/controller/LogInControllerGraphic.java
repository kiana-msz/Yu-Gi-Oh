package controller;

import client.Main;
import client.SignUpViewGraphic;
import controller.exeption.*;
import javafx.stage.Stage;
import model.User;
import view.*;

import java.io.IOException;

public class LogInControllerGraphic {

    public static void signup(Stage stage) throws Exception {
        SignUpViewGraphic.getInstance().start(stage);
    }

    public static void login(String username, String password, Stage stage) throws Exception {
        if (username.equals("")) {
            throw new EmptyUsernameBox();
        }
        if (password.equals("")) {
            throw new EmptyPasswordBox();
        }
        User user = User.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFound();
        } else if (!user.getPassword().equals(password)) {
            throw new UsernameNotFound();
        } else {
            sendToServer(user);
            MainViewGraphic.getInstance().setCurrentUser(user);
            MainViewGraphic.getInstance().start(stage);
        }
    }

    public static void sendToServer(User user) {
        try {
            Main.dataOutputStream.writeUTF("login " + user.getUsername());
            Main.dataOutputStream.flush();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
