package client;

import controller.DeckController;
import controller.ImportExportUserController;
import controller.SoundController;
import model.Deck;
import model.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Main extends Application {

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static ObjectInputStream objectInputStream;
    public static DataOutputStream dataOutputStream;

    public static void main(String[] args) {
        ImportExportUserController importExportUserController = ImportExportUserController.getInstance();
        importExportUserController.importAllUsers();
        importExportUserController.importProfileNumber();
        importExportUserController.importAchievements();
        importExportUserController.importAllCards();
        importExportUserController.importAllDecks();
        importExportUserController.importActiveDeck();
        Deck deck = DeckController.getInstance(User.getUserByUsername("@AI@")).createRandomDeckForAI();
        User.getUserByUsername("@AI@").setActiveDeck(deck);







        initializeNetwork();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SoundController.getInstance().playWhenStart();
        SignUpViewGraphic.getInstance().start(primaryStage);
    }

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7777);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

}