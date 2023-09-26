package controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.SoundCondition;

import java.net.URISyntaxException;

public class SoundController {
    static SoundCondition currentCondition;
    static MediaPlayer mediaPlayerForMain;
    static SoundController instance;

    public static SoundController getInstance() {
        if (instance == null) instance = new SoundController();
        return instance;
    }

    public static void muteAndUnmute() {
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForMain.pause();
            currentCondition = SoundCondition.PAUSE;
        } else if (currentCondition.equals(SoundCondition.PAUSE)) {
            mediaPlayerForMain.play();
            currentCondition = SoundCondition.PLAY;
        }
    }

    public void playWhenStart(){
        Media media = null;
        try {
            media = new Media(getClass().getResource("/TheHours.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mediaPlayerForMain = new MediaPlayer(media);
        mediaPlayerForMain.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayerForMain.play();
        currentCondition = SoundCondition.PLAY;
    }

    public void playWhenEnterNextPhase() {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/next.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayerForEatsDot = new MediaPlayer(media);
        mediaPlayerForEatsDot.setCycleCount(1);
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForEatsDot.play();
        }
    }

    public void playWhenAttacks() {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/swords.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayerForEatsDot = new MediaPlayer(media);
        mediaPlayerForEatsDot.setCycleCount(1);
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForEatsDot.play();
        }
    }

    public void playWhenSummons() {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/summon.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayerForEatsDot = new MediaPlayer(media);
        mediaPlayerForEatsDot.setCycleCount(1);
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForEatsDot.play();
        }
    }

    public void playWhenBuys() {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/coin.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayerForEatsDot = new MediaPlayer(media);
        mediaPlayerForEatsDot.setCycleCount(1);
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForEatsDot.play();
        }
    }

    public void playWhenMovesCard() {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/card.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayerForEatsDot = new MediaPlayer(media);
        mediaPlayerForEatsDot.setCycleCount(1);
        if (currentCondition.equals(SoundCondition.PLAY)) {
            mediaPlayerForEatsDot.play();
        }
    }


}
