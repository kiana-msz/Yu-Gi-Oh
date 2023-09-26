package controller;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ExplosionController extends Transition {
    private final ImageView imageView;

    public ExplosionController(ImageView imageView){
        this.imageView = imageView;
        setCycleDuration(Duration.millis(500));
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 6);
        imageView.setImage( new Image("/images/explosion/" + frame + ".png"));
    }

}
