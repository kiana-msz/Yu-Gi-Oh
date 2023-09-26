package controller;

import controller.exeption.*;
import javafx.stage.Stage;
import model.User;
import client.LogInViewGraphic;
import view.MainViewGraphic;
import client.SignUpViewGraphic;

import java.util.List;

public class SignUpControllerGraphic {

    public static void login(Stage stage) throws Exception {
        LogInViewGraphic.getInstance().start(stage);
    }

    public static void createAccount(String username,String nickname, String password, String checkPassword, Stage stage) throws Exception {
        if (username.equals("")) {
            throw new EmptyUsernameBox();
        }
        if (nickname.equals("")) {
            throw new EmptyNicknameBox();
        }
        if (password.equals("")) {
            throw new EmptyPasswordBox();
        }
        if (checkPassword.equals("")) {
            throw new EmptyConfirmPasswordBox();
        }
        if (!passwordsAreEqual(password,checkPassword)) {
            throw new DifferentPasswords();
        }
        List<User> allUsers = User.getAllUsers();
        if (allUsers != null) {
            for (User eachUser : allUsers) {
                if (eachUser.getUsername().equals(username)) {
                    throw new RepetitiveUsername(username);
                } else if (eachUser.getNickname().equals(nickname)) {
                    throw new RepetitiveNickname(nickname);
                }
            }
        }

        User user = new User(username, nickname, password,true);
        int profileNumber = user.getRandomProfileNumber();
        user.setProfileNumber(profileNumber);
        ImportExportUserController importExportUserController = ImportExportUserController.getInstance();
        importExportUserController.exportProfileNumber(username, profileNumber);
        importExportUserController.exportNewUser(User.getUserByUsername(username));
        importExportUserController.exportAllUsers(User.getAllUsers());
        SignUpViewGraphic.getInstance().showAccountCreatedPopUp();
        MainViewGraphic.getInstance().setCurrentUser(user);
        MainViewGraphic.getInstance().start(stage);
    }

    public static boolean passwordsAreEqual(String firstPassword,String secondPassword) {
        return firstPassword.equals(secondPassword);
    }
}
