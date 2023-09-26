package controller;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DeckShuffleController extends Transition {

    private final ImageView imageView;

    public DeckShuffleController(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 6);
        try {
            imageView.setImage(new Image("/images/shuffle/" + frame + ".png"));
        } catch (Exception ignored) {

        }
    }
}
