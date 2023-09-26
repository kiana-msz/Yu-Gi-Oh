package controller;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LightController extends Transition {
    private final ImageView imageView;

    public LightController(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(500));
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 12);
        try {
            imageView.setImage(new Image("/images/light/" + frame + ".png"));
        } catch (Exception ignored) {

        }
    }
}
