package controller;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LightningController extends Transition {

    private final ImageView imageView;

    public LightningController(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(500));
    }

    @Override
    protected void interpolate(double v) {
        int frame = ((int) Math.floor(v * 8)+1);
        imageView.setImage( new Image("/images/lightning/" + frame + ".png"));
    }
}
