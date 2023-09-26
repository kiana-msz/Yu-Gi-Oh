package controller;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CrownController extends Transition {
    private final ImageView imageView;

    public CrownController(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(500));
    }


    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 4);
        try {
            imageView.setImage(new Image("/images/crown/" + frame + ".png"));
        } catch (Exception ignored) {

        }
    }
}
