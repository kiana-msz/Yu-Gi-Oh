package controller;

import controller.exeption.*;
import javafx.stage.Stage;
import model.User;
import view.ChangePasswordViewGraphic;
import view.ProfileViewForGraphic;

public class ChangePasswordControllerGraphic {

    public static void changePassword(User user, String oldPassword, String newPassword,Stage stage) throws Exception {
        if (oldPassword.equals("")) {
            throw new EmptyOldPasswordBox();
        }
        if (newPassword.equals("")) {
            throw new EmptyNewPasswordBox();
        }
        if(!user.getPassword().equals(oldPassword))
            throw new WrongPassword();
        if(user.getPassword().equals(newPassword))
            throw new SamePassword();
        user.setPassword(newPassword);
        ChangePasswordViewGraphic.showPasswordChanged(user);
        ProfileViewForGraphic.getInstance().start(stage);
    }


    public static void goBack(Stage stage) throws Exception {
        ProfileViewForGraphic.getInstance().start(stage);
    }
}
