package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
/**@Author Philipp Fiesel, Dennis Messmer
 * Represents a Quit Button that implements the AdjustableButton interface.
 * This button allows the user to quit the game.
 */
public class QuitButton implements AdjustableButton {
    /**
     * The group that contains the visual elements of the quit button.
     */
    private Group quitButton;
    /**
     * The outer red circle of the quit button.
     */
    private Circle outerRed;
    /**
     * The inner white circle of the quit button.
     */
    private Circle innerWhite;
    /**
     * The inner red circle of the quit button.
     */
    private Circle innerRed;
    /**
     * The default color of the quit button.
     */
    private Color defaultColor = Color.web("#FF4848");
    /**
     * The color of the quit button when hovered.
     */
    private Color hoveredColor = Color.web("#B92E2E");

    /**
     * Constructs a QuitButton and aligns it to the bottom right corner of the specified AnchorPane.
     *
     * @param anchorPane The AnchorPane to which the button will be aligned.
     */
    public QuitButton(AnchorPane anchorPane) {
        alignButton(anchorPane, anchorPane.getPrefWidth() - 40, anchorPane.getPrefHeight() - 40);
    }

    /**
     * Constructs a QuitButton and aligns it to the specified position on the specified AnchorPane.
     *
     * @param anchorPane The AnchorPane to which the button will be aligned.
     * @param x          The x-coordinate of the button's position.
     * @param y          The y-coordinate of the button's position.
     */
    public QuitButton(AnchorPane anchorPane, double x, double y) {
        alignButton(anchorPane, x, y);
    }

    /**
     * Constructs a QuitButton with custom colors and aligns it to the specified position on the specified AnchorPane.
     *
     * @param anchorPane    The AnchorPane to which the button will be aligned.
     * @param x             The x-coordinate of the button's position.
     * @param y             The y-coordinate of the button's position.
     * @param defaultColor  The default color of the button.
     * @param hoveredColor  The color of the button when hovered.
     */
    public QuitButton(AnchorPane anchorPane, double x, double y, Color defaultColor, Color hoveredColor) {
        this.defaultColor = defaultColor;
        this.hoveredColor = hoveredColor;
        alignButton(anchorPane, x, y);
    }

    /**
     * Aligns the quit button to the specified position on the specified AnchorPane.
     * Creates the visual elements of the button and adds event handlers for mouse interactions.
     *
     * @param anchorPane The AnchorPane to which the button will be aligned.
     * @param x          The x-coordinate of the button's position.
     * @param y          The y-coordinate of the button's position.
     */
    @Override
    public void alignButton(AnchorPane anchorPane, double x, double y) {
        quitButton = new Group();

        outerRed = new Circle(17.5, defaultColor);
        innerWhite = new Circle(10.5, Color.WHITE);
        innerRed = new Circle(6.5, defaultColor);
        Rectangle whiteLine = new Rectangle(4, 10, Color.WHITE);

        // Set the layout of the whiteLine element
        whiteLine.setLayoutX(-2);
        whiteLine.setLayoutY(-13);

        // Add the visual elements to the quitButton group
        quitButton.getChildren().add(outerRed);
        quitButton.getChildren().add(innerWhite);
        quitButton.getChildren().add(innerRed);
        quitButton.getChildren().add(whiteLine);

        // Set the cursor to indicate it's clickable
        quitButton.setCursor(Cursor.HAND);


        // In this animation, the red circle increases in radius and changes the color
        quitButton.setOnMouseEntered(event -> {
            Timeline expand = new Timeline();
            KeyValue keyValue = new KeyValue(outerRed.radiusProperty(), 20, Interpolator.EASE_BOTH);
            KeyValue keyValue1 = new KeyValue(outerRed.fillProperty(), hoveredColor, Interpolator.EASE_BOTH);
            KeyValue keyValue2 = new KeyValue(innerRed.fillProperty(), hoveredColor, Interpolator.EASE_BOTH);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), keyValue,keyValue1,keyValue2);
            expand.getKeyFrames().add(keyFrame);
            expand.play();
        });

        // In this animation, the outer red circle is reduced back to the original size and color
        quitButton.setOnMouseExited(event -> {
            Timeline expand = new Timeline();
            KeyValue keyValue = new KeyValue(outerRed.radiusProperty(), 17.5, Interpolator.EASE_BOTH);
            KeyValue keyValue1 = new KeyValue(outerRed.fillProperty(), defaultColor, Interpolator.EASE_BOTH);
            KeyValue keyValue2 = new KeyValue(innerRed.fillProperty(), defaultColor, Interpolator.EASE_BOTH);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), keyValue,keyValue1,keyValue2);
            expand.getKeyFrames().add(keyFrame);
            expand.play();
        });

        // A pop-up window will be created. It contains two more buttons where you can choose if you really want to close it or if you want to stay.
        quitButton.setOnMousePressed(event -> {
            Stage stage = new Stage();
            stage.setResizable(false);
            AnchorPane anchorPane1 = new AnchorPane();
            anchorPane1.setPrefWidth(250);
            anchorPane1.setPrefHeight(150);
            double centerX = anchorPane1.getPrefWidth()/2;
            double centerY = anchorPane1.getPrefHeight()/2;
            Scene scene = new Scene(anchorPane1);

            Label label = new Label("Do you really want to quit the game?");
            label.setPrefWidth(anchorPane1.getPrefWidth());
            label.setAlignment(Pos.CENTER);
            label.setLayoutX(anchorPane1.getPrefWidth()/2 - label.getPrefWidth()/2);
            label.setLayoutY(25);

            anchorPane1.getChildren().add(label);

            Label quitLabel = new Label("quit");
            quitLabel.setPrefWidth(25);
            quitLabel.setLayoutX(centerX - 40 - quitLabel.getPrefWidth()/2);
            quitLabel.setLayoutY(centerY + 40 + 5);
            quitLabel.setAlignment(Pos.CENTER);
            QuitButton quit = new QuitButton(anchorPane1, centerX - 40, centerY + 20);
            Label stayLabel = new Label("stay");
            stayLabel.setPrefWidth(25);
            stayLabel.setLayoutX(centerX + 40 - stayLabel.getPrefWidth()/2);
            stayLabel.setLayoutY(centerY + 40 + 5);
            stayLabel.setAlignment(Pos.CENTER);
            QuitButton stay = new QuitButton(anchorPane1, centerX + 40, centerY + 20, Color.web("12DC01"), Color.web("0D9F00"));

            anchorPane1.getChildren().add(quitLabel);
            anchorPane1.getChildren().add(stayLabel);

            // When the quit button in the popup is pressed, the system closed
            quit.quitButton.setOnMousePressed(exitEvent -> {
                System.exit(0);
            });
            // When the stay button in the popup is pressed, you will stay to play
            stay.quitButton.setOnMousePressed(stayEvent -> {
                stage.close();
            });

            stage.setTitle("Quit Game?");
            stage.setScene(scene);

            // Set the window position relative to the main stage
            Stage currentStage = MainStage.getMainStage().getStage();
            stage.initOwner(currentStage);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        });

        // Set the position of the quit button
        quitButton.setLayoutX(x);
        quitButton.setLayoutY(y);

        // Add the quit button to the AnchorPane
        anchorPane.getChildren().add(quitButton);
    }

}
