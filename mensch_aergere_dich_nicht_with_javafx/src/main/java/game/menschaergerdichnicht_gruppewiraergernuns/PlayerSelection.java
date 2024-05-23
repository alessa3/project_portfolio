package game.menschaergerdichnicht_gruppewiraergernuns;

import backend.Color;
import backend.GameLoopController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.Iterator;
import java.util.ResourceBundle;
/**@Author Philipp Fiesel, Dennis Messmer
 * This class handles the player selection screen of the game.
 * It implements the Initializable interface for JavaFX initialization.
 */
public class PlayerSelection implements Initializable {

    /**
     * The AnchorPane that contains the player selection UI elements.
     */
    @FXML
    private AnchorPane playerSelectionAnchorPane;

    /*------------------------------------------------------------------------*/
    /**
     * The button for selecting two players.
     */
    @FXML
    Rectangle twoPlayerButton;
    /**
     * The icon group associated with the two-player selection button.
     */
    @FXML
    Group twoPlayerIcon;


    /**
     * The button for selecting three players.
     */
    @FXML
    Rectangle threePlayerButton;
    /**
     * The icon group associated with the three-player selection button.
     */
    @FXML
    Group threePlayerIcon;


    /**
     * The button for selecting four players.
     */
    @FXML
    Rectangle fourPlayerButton;
    /**
     * The icon group associated with the four-player selection button.
     */
    @FXML
    Group fourPlayerIcon;

    /**
     * The text label displaying the selected player amount.
     */
    @FXML
    Label playerAmountText;

    /**
     * Applies a visual effect where the background turns dark gray when the mouse cursor hovers over a button.
     *
     * @param hovered The MouseEvent triggered by hovering over a button.
     */
    public void hoveredButtonEffect(MouseEvent hovered) {
        Rectangle hoveredButton = (Rectangle) hovered.getSource();
        hoveredButton.setCursor(Cursor.HAND);
        Timeline animation = new Timeline();
        KeyValue keyValue = new KeyValue(hoveredButton.fillProperty(), javafx.scene.paint.Color.web("#BABABA"), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * Reverts the visual effect when the mouse stops hovering over a button.
     *
     * @param hovered The MouseEvent triggered when the mouse stops hovering over a button.
     */
    public void revertEffect(MouseEvent hovered) {
        Rectangle hoveredButton = (Rectangle) hovered.getSource();
        Timeline animation = new Timeline();
        KeyValue keyValue = new KeyValue(hoveredButton.fillProperty(), javafx.scene.paint.Color.web("#D9D9D9"), Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * When a player selection button is clicked, the player count display will fade to the right and fade.
     *
     * @param clickedButton The Rectangle button that was clicked.
     * @return The Timeline animation for the button click.
     */
    public Timeline clickedButton(Rectangle clickedButton) {
        ObservableList<Node> children = null;
        Timeline animation = null;
        switch (clickedButton.getId()) {
            case "twoPlayerButton" -> {
                children = twoPlayerIcon.getChildren();
            }
            case "threePlayerButton" -> {
                children = threePlayerIcon.getChildren();
            }
            case "fourPlayerButton" -> {
                children = fourPlayerIcon.getChildren();
            }
        }
        double delay = 0;

        if (children != null) {
            for (Node child : children) {
                animation = new Timeline();
                KeyValue moveRight = new KeyValue(child.layoutXProperty(), child.getLayoutY() + 300, Interpolator.EASE_BOTH);
                KeyValue fade = new KeyValue(child.opacityProperty(), 0, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.8), moveRight, fade);
                animation.setDelay(Duration.seconds(delay));
                animation.getKeyFrames().add(keyFrame);
                animation.play();
                delay += 0.1;
            }
        }
        return animation;
    }

    /**
     * Removes the player selection buttons based on the button ID, with a fade animation.
     *
     * @param buttonId The ID of the button to be removed.
     */
    private void removeButtons(String buttonId) {
        twoPlayerButton.setOnMousePressed(null);
        threePlayerButton.setOnMousePressed(null);
        fourPlayerButton.setOnMousePressed(null);

        Timeline animation = new Timeline();

        KeyValue fadeButton1 = new KeyValue(twoPlayerButton.opacityProperty(), 0, Interpolator.EASE_IN);
        KeyValue fadeButton2 = new KeyValue(threePlayerButton.opacityProperty(), 0, Interpolator.EASE_IN);
        KeyValue fadeButton3 = new KeyValue(fourPlayerButton.opacityProperty(), 0, Interpolator.EASE_IN);

        KeyValue fadeIcon1 = null;
        KeyValue fadeIcon2 = null;

        switch (buttonId) {
            case "twoPlayerButton" -> {
                fadeIcon1 = new KeyValue(threePlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
                fadeIcon2 = new KeyValue(fourPlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
            }
            case "threePlayerButton" -> {
                fadeIcon1 = new KeyValue(twoPlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
                fadeIcon2 = new KeyValue(fourPlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
            }
            case "fourPlayerButton" -> {
                fadeIcon1 = new KeyValue(twoPlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
                fadeIcon2 = new KeyValue(threePlayerIcon.opacityProperty(), 0, Interpolator.EASE_IN);
            }
        }
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), fadeButton1, fadeButton2, fadeButton3, fadeIcon1, fadeIcon2);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * Set the AmountPlayers for the {@link  GameLoopController}.
     * Switches to the color selection screen after a player selection button is clicked.
     * @param clicked The MouseEvent triggered by clicking a player selection button.
     */
    @FXML
    void switchToColorSelection(MouseEvent clicked) {
        // Get button object to get access to the ID
        Rectangle clickedButton = (Rectangle) clicked.getSource();
        String buttonId = clickedButton.getId();

        removeButtons(buttonId);
        clickedButton.setCursor(Cursor.DEFAULT);

        clickedButton(clickedButton).setOnFinished(animationFinished -> {
            switch (buttonId) {
                case "twoPlayerButton" -> {
                    GameLoopController.currentGameLoopController.setAmountPlayers(2);
                    Color.setAmountPlayers(2);
                }
                case "threePlayerButton" -> {
                    GameLoopController.currentGameLoopController.setAmountPlayers(3);
                    Color.setAmountPlayers(3);
                }
                case "fourPlayerButton" -> {
                    GameLoopController.currentGameLoopController.setAmountPlayers(4);
                    Color.setAmountPlayers(4);
                }
            }

            try {
                new SceneSwitch(playerSelectionAnchorPane, "ColorSelection.fxml");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    /**
     * Initializes the player selection screen.
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new QuitButton(playerSelectionAnchorPane);
        playerAmountText.setPrefWidth(playerSelectionAnchorPane.getPrefWidth());
        playerAmountText.setAlignment(Pos.CENTER);
        playerAmountText.setLayoutY(playerSelectionAnchorPane.getPrefHeight()/2 - 50);
    }
}