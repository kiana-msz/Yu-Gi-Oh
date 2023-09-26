package controller;

import javafx.stage.Stage;
import view.ChooseSecondPlayerViewGraphic;

public class ChooseDuelControllerGraphic {
    public static void goBack(Stage stage) throws Exception {
        ChooseSecondPlayerViewGraphic.getInstance().start(stage);
    }
}
