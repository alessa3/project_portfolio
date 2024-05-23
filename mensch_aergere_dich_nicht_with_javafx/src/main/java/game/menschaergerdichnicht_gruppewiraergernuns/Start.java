package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author Philipp Fiesel
 */
public class Start implements Initializable {

    @FXML
    private AnchorPane StartAnchorPane;

    @FXML
    Circle startButton;

    Label startText;

    /**
     * This method is used to call the {@link SceneSwitch} for switching the Scene to PlayerSelection
     * @throws IOException
     */
    @FXML
    void switchToPlayerSelection() {
        try {
            new SceneSwitch(StartAnchorPane, "PlayerSelection.fxml");
        }
        catch (IOException ignored) {}
    }

    /**
     *  Initializes the StartAnchorPane by adding necessary components and configuring their properties.
     *  This method is automatically called when the Start class is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new QuitButton(StartAnchorPane);
        startButton.setCursor(Cursor.HAND);
        startText = new Label("START");
        CenterLabel.centerLabel(StartAnchorPane, startText);
        startText.setFont(Font.font("",FontWeight.EXTRA_BOLD,16));
        StartAnchorPane.getChildren().add(startText);
        new RulesButton(StartAnchorPane, "left");
    }

    /**
     * This method is called when the mouse hovers over the startButton.
     * The animation increase the radius of the start Button and the size of the startText
     */
    public void hoverStartButton() {
        Timeline animation = new Timeline();
        KeyValue keyValue = new KeyValue(startButton.radiusProperty(), 40, Interpolator.EASE_BOTH);
        KeyValue scaleX = new KeyValue(startText.scaleXProperty(), 1.125, Interpolator.EASE_BOTH);
        KeyValue scaleY = new KeyValue(startText.scaleYProperty(), 1.125, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue, scaleX, scaleY);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * This method is called when the mouse leaves the startButton.
     * It animates the button and the startText to restore the original size.
     */
    public void leaveStartButton() {
        Timeline animation = new Timeline();
        KeyValue keyValue = new KeyValue(startButton.radiusProperty(), 35, Interpolator.EASE_BOTH);
        KeyValue scaleX = new KeyValue(startText.scaleXProperty(), 1, Interpolator.EASE_BOTH);
        KeyValue scaleY = new KeyValue(startText.scaleYProperty(), 1, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), keyValue, scaleX, scaleY);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * This method is called when the startButton is pressed.
     * It triggers the scene transition to PlayerSelection.
     */
    public void pressedStartButton() {
        switchToPlayerSelection();
    }

}
