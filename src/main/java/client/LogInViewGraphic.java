package client;

import controller.DeckController;
import controller.ImportExportUserController;
import controller.LogInControllerGraphic;
import controller.SoundController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Deck;
import model.User;

import java.net.URL;
import java.util.Objects;

public class LogInViewGraphic extends Application {
    private static Stage stage;
    static LogInViewGraphic instance = null;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public static LogInViewGraphic getInstance() {
        if (instance == null) instance = new LogInViewGraphic();
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        if ((LogInViewGraphic.instance == null) && (SignUpViewGraphic.instance == null)) {
            SoundController.getInstance().playWhenStart();
            ImportExportUserController importExportUserController = ImportExportUserController.getInstance();
            importExportUserController.importAllUsers();
            importExportUserController.importProfileNumber();
            importExportUserController.importAchievements();
            importExportUserController.importAllCards();
            importExportUserController.importAllDecks();
            importExportUserController.importActiveDeck();
            Deck AIDeck = Objects.requireNonNull(User.getUserByUsername("@AI@")).getDeckByName("DeckForAI");
            if (AIDeck == null) {
                Deck deck = DeckController.getInstance(User.getUserByUsername("@AI@")).createRandomDeckForAI();
                Objects.requireNonNull(User.getUserByUsername("@AI@")).setActiveDeck(deck);
            }
            else Objects.requireNonNull(User.getUserByUsername("@AI@")).setActiveDeck(AIDeck);
            Main.initializeNetwork();
        }
        LogInViewGraphic.stage = stage;
        URL url = getClass().getResource("/Login.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/icon.png"))));
        stage.setTitle("YU GI OH");
        stage.show();
    }

    public void signup() throws Exception {
        LogInControllerGraphic.signup(stage);
    }

    public void login() {
        try {
            LogInControllerGraphic.login(username.getText(), password.getText(), stage);
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }
}
