package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.security.Key;
import java.util.LinkedList;
import java.util.List;

/**@Author Dennis Messmer, Philipp Fiesel
 * Represents a button for displaying rules.
 * The button is associated with an AnchorPane and positioned accordingly.
 * The button can be clicked to open/close a rules window and display corresponding labels.
 */
public class RulesButton {
    /**
     * The AnchorPane associated with the button.
     */
    AnchorPane anchorPane;
    /**
     * Flag indicating whether the rules window is open or closed.
     */
    private boolean open = false;
    /**
     * The screen resolution used for positioning the button.
     */
    ScreenResolution screenResolution = new ScreenResolution();
    /**
     * List of labels to be displayed in the rules window.
     */
    List<Label> labels = new LinkedList<>();
    /**
     * The position of the button ("left" or "right") within the AnchorPane.
     */
    String position;
    /**
     * The circle shape representing the button.
     */
    Circle circle;
    /**
     * The rectangle shape representing the rules window.
     */
    Rectangle rulesWindow;

    Label label;

    /**
     * Constructs a RulesButton object.
     * @param anchorPane The AnchorPane to which the button is associated.
     * @param position The position of the button ("left" or "right") within the AnchorPane.
     */
    public RulesButton(AnchorPane anchorPane, String position) {
        this.anchorPane = anchorPane;
        this.position = position;
        // Position the button within the AnchorPane
        alignButton();
        // Add labels to the rules window
        addLabels();
    }
    /**
     * Aligns the labels within the rules window based on visibility.
     * @param visible Indicates whether the labels should be visible or hidden.
     */
    private void alignLabels(boolean visible) {

        int spacing = 0;
        double initialY = 65;

        for (Label label : labels) {
            if (!visible) {
                // Hide the label
                label.setOpacity(0);
                // Enable the button click event
                circle.setOnMousePressed(this::openAnimation);
            }
            else {
                // Animate the label to make it visible
                Timeline timeline = new Timeline();
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.15), new KeyValue(label.opacityProperty(), 1, Interpolator.EASE_BOTH));
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();

                timeline.setOnFinished(event -> {
                    // Enable the button click event
                    circle.setOnMousePressed(this::openAnimation);
                });
            }

            label.setLayoutX(33);
            if (position.equals("right"))
                label.setLayoutX(screenResolution.getScreenWidth() - 300 - 13);
            label.setLayoutY(initialY + spacing);
            initialY += label.getHeight();
            spacing+=5;
        }
    }
    /**
     * Adds labels to the AnchorPane for display within the rules window.
     */
    private void addLabels() {
        labels.add(new Label("You can roll the dice 3 times if you can´t move out of\n your base or if you can´t move with your game pieces"));
        labels.add(new Label("- The game piece can be moved as far as the number\n   on the dice."));
        labels.add(new Label("- If you roll a 6 you can move out of your base with\n   one game piece."));
        labels.add(new Label("- As soon as the game piece is out of the base, it must\n   be moved directly away from the starting point"));
        labels.add(new Label("- If you roll a 6, you can roll again."));
        labels.add(new Label("- Two game pieces cannot stand on one field."));
        labels.add(new Label("- If you come to a field that is already occupied, the\n   game piece is kicked out and his piece lands back\n   in the base"));
        labels.add(new Label("- Game pieces who are already in the house cannot be\n   jumped over."));
        labels.add(new Label("- You win when all the game pieces are in the house.\n"));
        labels.add(new Label("Now it´s time to play. Have fun :)"));

        for (Label label : labels) {
            label.setMouseTransparent(true);
            label.setOpacity(0); // Hide the label
            label.setFont(Font.font("Segoe UI", 11.5));
            label.toFront();
            // Add the label to the AnchorPane
            anchorPane.getChildren().add(label);
        }
    }
    /**
     * Aligns the button within the AnchorPane based on its position.
     */
    private void alignButton() {
        label = new Label("?");
        label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD , 30));
        label.setPrefWidth(35);
        label.setPrefHeight(35);
        label.setAlignment(Pos.CENTER);
        label.setMouseTransparent(true);
        label.setTextFill(Color.LIGHTGRAY);

        circle = new Circle(17.5, Color.GRAY);
        circle.setCursor(Cursor.HAND);
        rulesWindow = new Rectangle(34, 34, Color.GRAY);

        if (position.equals("left")) {
            // Position the button on the left side
            circle.setLayoutX(37.5);
            circle.setLayoutY(37.5);

            rulesWindow.setLayoutX(20);
            rulesWindow.setLayoutY(20);

            label.setLayoutX(20);
            label.setLayoutY(20);
        } else if (position.equals("right")) {
            // Position the button on the right side
            circle.setLayoutX(screenResolution.getScreenWidth() - 37.5);
            circle.setLayoutY(37.5);

            rulesWindow.setLayoutX(screenResolution.getScreenWidth() - (17.5 + 37.5));
            rulesWindow.setLayoutY(20);

            label.setLayoutX(screenResolution.getScreenWidth() - 55);
            label.setLayoutY(20);
        }

        rulesWindow.setArcWidth(35);
        rulesWindow.setArcHeight(35);
        rulesWindow.toBack();

        // Enable the button click event
        circle.setOnMousePressed(this::openAnimation);
        // Add the rules window shape to the AnchorPane
        anchorPane.getChildren().add(rulesWindow);
        // Add the button shape to the AnchorPane
        anchorPane.getChildren().add(circle);
        anchorPane.getChildren().add(label);
    }
    /**
     * Performs the open/close animation for the rules window when the button is clicked.
     * @param clicked The MouseEvent representing the button click.
     */
    private void openAnimation(MouseEvent clicked) {
        int width, height, adjust, rotate = 0;
        Color color;
        // Disable the button click event temporarily
        circle.setOnMousePressed(null);

        if (!open) {
            width = 300;
            height = 360;
            adjust = width - 35;
            open = true;
            color = Color.LIGHTGRAY;
            rotate = 360;

        } else {
            width = 34;
            height = 34;
            open = false;
            adjust = -265;
            color = Color.GRAY;
            rotate = 0;
        }

        // Animation between the Circle and the RectangleWindow
        Timeline openWindow = new Timeline();
        KeyFrame keyFrame;
        KeyValue increaseWidth = new KeyValue(rulesWindow.widthProperty(), width, Interpolator.EASE_BOTH);
        KeyValue increaseHeight = new KeyValue(rulesWindow.heightProperty(), height, Interpolator.EASE_BOTH);
        KeyValue fade = new KeyValue(rulesWindow.fillProperty(), color, Interpolator.EASE_BOTH);
        KeyValue rotateQuestionMark = new KeyValue(label.rotateProperty(), rotate, Interpolator.EASE_BOTH);
        if (position.equals("right")) {
            KeyValue move = new KeyValue(rulesWindow.layoutXProperty(), rulesWindow.getLayoutX() - adjust, Interpolator.EASE_BOTH);
            keyFrame = new KeyFrame(Duration.seconds(0.3), increaseHeight, increaseWidth, move, fade, rotateQuestionMark);
        }
        else {
            keyFrame = new KeyFrame(Duration.seconds(0.3), increaseHeight, increaseWidth, fade, rotateQuestionMark);
        }


        openWindow.getKeyFrames().add(keyFrame);
        openWindow.play();


        if (open) {
            openWindow.setOnFinished(finishedEvent -> {
                // Align and show the labels within the rules window
                alignLabels(true);
            });
        }
        else {
            // Hide the labels within the rules window
            alignLabels(false);
        }
    }
}
