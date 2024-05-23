package game.menschaergerdichnicht_gruppewiraergernuns;

import backend.Color;
import backend.GameLoopController;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** @Author Dennis Messmer
 * This class is used to define buttons, functions and effects for the color selection phase.
 */
public class ColorSelection {

    /**
     * Layout container to position buttons.
     */
    @FXML
    private AnchorPane ColorSelectionAnchorPane;

    /*------------------------------------------------------------------------*/

    /**
     * Text that shows which players turn it is
     */
    @FXML
    private Label currentPlayerLabel;

    /*------------------------------------------------------------------------*/

    @FXML
    Group menuContainer;

    /**
     * Button that opens the {@link #colorSetMenu}
     */
    @FXML
    Circle colorSetButton;

    /**
     * Icon inside the {@link #colorSetButton}
     */
    @FXML
    Group colorSetButtonIcon;

    /**
     * Container for all the different <b>colorSets</b>
     */
    @FXML
    private Rectangle colorSetMenu;

    /**
     * Coordinates whether the {@link #colorSetMenu} is open or not
     */
    private boolean colorSetMenuOpen = false;

    @FXML
    private Group colorSet1;

    @FXML
    private Group colorSet2;

    @FXML
    private Group colorSet3;

    @FXML
    private Group colorSet4;

    /**
     * Hit box for {@link #colorSet1}
     */
    @FXML
    Circle hitbox1;

    /**
     * Hit box for {@link #colorSet2}
     */
    @FXML
    Circle hitbox2;

    /**
     * Hit box for {@link #colorSet3}
     */
    @FXML
    Circle hitbox3;

    /**
     * Hit box for {@link #colorSet4}
     */
    @FXML
    Circle hitbox4;

    /*------------------------------------------------------------------------*/

    /**
     * Dynamic color array, that will transfer the current visual colors
     * to the playing field. These colors can change depending on the
     * chosen color set ex. {@link #colorSet2}. This transfer is
     * done in the {@link #chooseColor(MouseEvent)} method via the
     * {@link ColorTransfer} class.
     */
    javafx.scene.paint.Color[] colors = {
            javafx.scene.paint.Color.web("#FEFF59"), // yellow (initial)
            javafx.scene.paint.Color.web("#DA5E5E"), // red    (initial)
            javafx.scene.paint.Color.web("#1E90FF"), // green  (initial)
            javafx.scene.paint.Color.web("#75C95D")  // blue   (initial)
    };

    /*------------------------------------------------------------------------*/

    /**
     * (<b>colorButton1</b>) Yellow button in shape of a circle used for the
     * player selection. The player pressing this button
     * will be signed to the color <b>yellow</b>.
     */
    @FXML
    private Circle yellowButton;
    private boolean yellowPressed = false;

    /**
     * (<b>colorButton2</b>) Red button in shape of a circle used for the
     * player selection. The player pressing this button
     * will be signed to the color <b>red</b>.
     */
    @FXML
    private Circle redButton;
    private boolean redPressed = false;

    /**
     * (<b>colorButton3</b>) Blue button in shape of a circle used for the
     * player selection. The player pressing this button
     * will be signed to the color <b>blue</b>.
     */
    @FXML
    private Circle blueButton;
    private boolean bluePressed;

    /**
     * (<b>colorButton4</b>) Green button in shape of a circle used for the
     * player selection. The player pressing this button
     * will be signed to the color <b>green</b>.
     */
    @FXML
    private Circle greenButton;
    private boolean greenPressed;

    /**
     * Highlight transition Object for the <b>colorButtons</b>
     */
    private Timeline hoverTransition;

    /*------------------------------------------------------------------------*/
    
    @FXML
    Group twoPlayerIcon;
    
    @FXML
    Group threePlayerIcon;
    
    @FXML
    Group fourPlayerIcon;

    /*------------------------------------------------------------------------*/

    /**
     * Number of players in the game.
     */
    private final int amountPlayers;

    /**
     * Color selection status for each player.
     */
    private List<Boolean> colorSelectionStatus;

    /**
     * Index of the current player.
     */
    private int currentPlayerIndex;

    ObservableList<Node> playerIcon;

    /*------------------------------------------------------------------------*/

    public ColorSelection() {
        this.amountPlayers = GameLoopController.currentGameLoopController.getAmountPlayers();
    }

    /*------------------------------------------------------------------------*/

    /**
     * This method is used to set the initial state of the {@link ColorSelection} class.
     * It creates a list with amountPlayers and the value false.
     * This list is used to save the selection state of each player.
     */
    public void initialize() {
        colorSelectionStatus = new ArrayList<>(Collections.nCopies(amountPlayers, false));

        currentPlayerIndex = 0;
        playerIcon = figuresFadeIn();

        colorSetButton.setCursor(Cursor.HAND);

        hitbox1.setCursor(Cursor.HAND);
        hitbox2.setCursor(Cursor.HAND);
        hitbox3.setCursor(Cursor.HAND);
        hitbox4.setCursor(Cursor.HAND);

        new QuitButton(ColorSelectionAnchorPane);
    }

    /**
     * @Author Philipp Fiesel
     * This method allows each player to pick a color. If every player has chosen a
     * color this window closes and opens the main field.
     * @param event this event is triggered when the button is pressed.
     */
    @FXML
    void chooseColor(MouseEvent event) {

        if (colorSetMenuOpen) {
            openColorSetMenu();
        }
        colorSetButton.setOnMousePressed(null);
        grayOutColorsOfColorSetButton();


        //Check if all players have chosen a color
        if (!allPlayersSelectedColor()) {
            Circle clickedButton = (Circle) event.getSource();
            String buttonId = clickedButton.getId();

            // Check if the selected color is available for the current player.
            if (isColorAvailableForCurrentPlayer(buttonId)) {

                disableAllButtonsTemporarily();
                disableClickedButton(clickedButton);
                disableCursor();

                // Set the selected color as the player's current color.
                Color.setCurrentColor(currentPlayerIndex,getColorFromButtonId(buttonId));
                playerIconColor(amountPlayers - currentPlayerIndex - 1, getColorFromButtonId(buttonId));
                // Update the visibility of the selected color button (make it invisible).
                updateColorButtonVisibility(clickedButton);
                // Update the status of the color selection for the current player.
                colorSelectionStatus.set(currentPlayerIndex, true);
                currentPlayerIndex++;

                // End of textFade() animation
                textFade().setOnFinished(textFadeAnimation -> {
                    currentPlayerLabel.setText("Player " + (currentPlayerIndex+1));

                    textReappear();
                    enableCursor();
                    enableAllButtons();

                    // Check that all players have selected their colors
                    if (currentPlayerIndex >= amountPlayers) {
                        // Scene change to the play field
                        if(allPlayersSelectedColor()) {
                            ColorTransfer.colors = colors; // transfer visual colors to the visual board
                            try {
                                new SceneSwitch(ColorSelectionAnchorPane, "Gaming.fxml");
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        }
    }

    private void enableCursor() {
        yellowButton.setCursor(Cursor.HAND);
        redButton.setCursor(Cursor.HAND);
        blueButton.setCursor(Cursor.HAND);
        greenButton.setCursor(Cursor.HAND);
    }

    private void disableCursor() {
        yellowButton.setCursor(Cursor.DEFAULT);
        redButton.setCursor(Cursor.DEFAULT);
        blueButton.setCursor(Cursor.DEFAULT);
        greenButton.setCursor(Cursor.DEFAULT);
    }

    /**
     * Disables the click event for the button to avoid too many consecutive clicks on the color buttons.
     * This is used to wait until the {@link #textFade()} method has ended to allow new button inputs.
     */
    private void disableClickedButton(Circle clickedButton) {
        clickedButton.setOnMousePressed(null);
        switch (clickedButton.getId()) {
            case "yellowButton" -> {
                yellowPressed = true;
            }
            case "redButton" -> {
                redPressed = true;
            }
            case "blueButton" -> {
                bluePressed = true;
            }
            case "greenButton" -> {
                greenPressed = true;
            }
        }
    }

    private void disableAllButtonsTemporarily() {
        yellowButton.setOnMousePressed(null);
        redButton.setOnMousePressed(null);
        blueButton.setOnMousePressed(null);
        greenButton.setOnMousePressed(null);
    }

    private void enableAllButtons() {
        if (!yellowPressed)
            yellowButton.setOnMousePressed(this::chooseColor);
        if (!redPressed)
            redButton.setOnMousePressed(this::chooseColor);
        if (!bluePressed)
            blueButton.setOnMousePressed(this::chooseColor);
        if (!greenPressed)
            greenButton.setOnMousePressed(this::chooseColor);
    }

    /*------------------------------------------------------------------------*/

    /**
     * Animation for the player name
     * @return the animation object to check for the <b>end of the animation</b>
     */
    private Timeline textFade() {
        Timeline animation = new Timeline();

        KeyValue moveUp     = new KeyValue(currentPlayerLabel.layoutYProperty(), currentPlayerLabel.getLayoutY() - 15, Interpolator.EASE_IN);
        KeyValue scaleDownX = new KeyValue(currentPlayerLabel.scaleXProperty(), 0.9, Interpolator.EASE_IN);
        KeyValue scaleDownY = new KeyValue(currentPlayerLabel.scaleYProperty(), 0.9, Interpolator.EASE_IN);
        KeyValue fadeOut    = new KeyValue(currentPlayerLabel.opacityProperty(), 0.0, Interpolator.EASE_IN);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), moveUp, scaleDownX, scaleDownY, fadeOut);

        animation.getKeyFrames().add(keyFrame);
        animation.play();
        return animation;
    }

    /**
     * Animation that plays after the {@link #textFade()} method to show the new player
     */
    private void textReappear() {
        currentPlayerLabel.setLayoutY(25);
        Timeline animation = new Timeline();

        KeyValue moveUp     = new KeyValue(currentPlayerLabel.layoutYProperty(), currentPlayerLabel.getLayoutY() - 5, Interpolator.EASE_IN);
        KeyValue scaleDownX = new KeyValue(currentPlayerLabel.scaleXProperty(), 1, Interpolator.EASE_IN);
        KeyValue scaleDownY = new KeyValue(currentPlayerLabel.scaleYProperty(), 1, Interpolator.EASE_IN);
        KeyValue fadeOut    = new KeyValue(currentPlayerLabel.opacityProperty(), 1, Interpolator.EASE_IN);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), moveUp, scaleDownX, scaleDownY, fadeOut);

        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     * This method creates a transition to the {@link PlayerSelection} by making the player icons ease into the
     * screen.
     * @return the {@link Group} that contains the player icons to later set their colors in {@link #playerIconColor(int, Color)}
     */
    private ObservableList<Node> figuresFadeIn() {
        Timeline animation;

        ObservableList<Node> playerIcons = null;

        switch (amountPlayers) {
            case 2 -> {
                playerIcons = twoPlayerIcon.getChildren();
            }
            case 3 -> {
                playerIcons = threePlayerIcon.getChildren();
            }
            case 4 -> {
                playerIcons = fourPlayerIcon.getChildren();
            }
        }
        double delay = 0;

        if (playerIcons != null) {
            for(Node playerIcon : playerIcons) {
                // iterates the group of player icons and sets their position to the middle
                animation = new Timeline();
                KeyValue keyValue1 = new KeyValue(playerIcon.layoutXProperty(), playerIcon.getLayoutX() + 290, Interpolator.EASE_BOTH);
                KeyValue keyValue2 = new KeyValue(playerIcon.opacityProperty(), 1, Interpolator.EASE_IN);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.8), keyValue1, keyValue2);
                animation.getKeyFrames().add(keyFrame);
                animation.setDelay(Duration.seconds(delay));
                animation.play();
                delay+= 0.1;
            }
        }
        return playerIcons;
    }

    /**
     * If a player chooses a color, the little player icons change depending
     * on the chosen color. A player Icon consists of a {@link Group} which devides
     * into a {@link Circle} (as the head) and an {@link Ellipse} (as the body) of the player icon.
     * @param index of the current player
     * @param color uses the backend color to decide which color the player will get
     */
    private void playerIconColor(int index, Color color) {
        javafx.scene.paint.Color color1 = null;

        switch (color) {
            case YELLOW -> color1 = colors[0];
            case RED    -> color1 = colors[1];
            case BLUE   -> color1 = colors[2];
            case GREEN  -> color1 = colors[3];
        }
        Group group = (Group) playerIcon.get(index); // these icons are saved inside a group

        for (Node node : group.getChildren()) {
            // iterate through the different body parts
            Timeline colorFade = new Timeline();
            KeyValue keyValue1 = null;
            KeyValue keyValue2 = null;
            
            if (node instanceof Circle) { // head
                keyValue1 = new KeyValue(((Circle) node).fillProperty(), color1, Interpolator.EASE_IN);
            }
            if (node instanceof Ellipse) { // body
                keyValue2 = new KeyValue(((Ellipse) node).fillProperty(), color1, Interpolator.EASE_IN);
            }
            
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue1, keyValue2);
            colorFade.getKeyFrames().add(keyFrame);
            colorFade.play();
        }
    }

    /*------------------------------------------------------------------------*/

    /**
     * This method gives each button a highlight effect when <b>event</b> is triggered.
     * @param event this event is triggered when the cursor hovers the button.
     */
    public void colorButton_hover(MouseEvent event) {
        Circle hoveredButton = (Circle) event.getSource();

        hoveredButton.setCursor(Cursor.HAND);

        hoverTransition = new Timeline();
        KeyValue keyValue = new KeyValue(hoveredButton.radiusProperty(), 42, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        hoverTransition.getKeyFrames().add(keyFrame);
        hoverTransition.play();
    }

    /**
     * This method removes the effects of the {@link #colorButton_hover} when <b>event</b> is triggered.
     * @param event this event is triggered when the cursor leaves the button.
     */
    public void colorButton_default(MouseEvent event) {
        Circle hoveredButton = (Circle) event.getSource();

        hoveredButton.setCursor(Cursor.HAND);

        hoverTransition = new Timeline();
        KeyValue keyValue = new KeyValue(hoveredButton.radiusProperty(), 35, Interpolator.EASE_IN);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValue);
        hoverTransition.getKeyFrames().add(keyFrame);
        hoverTransition.play();
    }


    /**
     * This method contains the functionality and animation for the
     * color set button, which is opening the color menu also known as
     * <b>colorSwitchBar</b>
     */
    public void openColorSetMenu() {
        // initial values
        double width = 34; // initial width for the menu
        double radius = 0; // initial radius value for the button icon
        javafx.scene.paint.Color color = javafx.scene.paint.Color.web("#DDDDDD"); // initial color

        // if the color set menu is not opened
        if (!colorSetMenuOpen) {
            width = 155; // when pressing the button the width of the menu will expand to this value
            radius = 180; // when pressing the button, its icon will rotate by this value
            color = javafx.scene.paint.Color.web("#616161");
            colorSetMenuOpen = true;
            moveMenuButtons("forwards");
        }
        // if the color set menu is opened
        else {
            colorSetMenuOpen = false;
            moveMenuButtons("backwards");
        }

        /*
        the animation for the entire menu. It will expand or close the menu,
        rotate the button icon and fade into another color.
        */
        Timeline animation = new Timeline();
        KeyValue expand = new KeyValue(colorSetMenu.widthProperty(), width, Interpolator.EASE_BOTH);
        KeyValue rotate = new KeyValue(colorSetButtonIcon.rotateProperty(), radius, Interpolator.EASE_BOTH);
        KeyValue colorFade = new KeyValue(colorSetMenu.fillProperty(), color, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), expand, colorFade, rotate);
        animation.getKeyFrames().add(keyFrame);
        animation.play();
    }

    /**
     *Moves the buttons and their hit boxes inside the color set menu
     * @param direction <b>forwards</b>: when the menu is opened the buttons inside the menu
     *                  and their hit boxes are moved to their desired location.
     *                  - <b>backwards</b>: when it is closed these buttons and hit boxes move
     *                  back behind the {@code colorSwitchButton}.
     */
    private void moveMenuButtons(String direction) {
        List<Group> allOptions = new LinkedList<>();
        allOptions.add(colorSet1);
        allOptions.add(colorSet2);
        allOptions.add(colorSet3);
        allOptions.add(colorSet4);

        List<Circle> hitboxes = new LinkedList<>(); //TODO kann schöner gelöst werden eventuell
        hitboxes.add(hitbox1);
        hitboxes.add(hitbox2);
        hitboxes.add(hitbox3);
        hitboxes.add(hitbox4);

        double spacing = 0;

        if (direction.equals("forwards")) {
            // iterates through all color options and moves them their desired location
            for (Group option : allOptions) {
                Timeline animation = new Timeline();
                KeyValue expand = new KeyValue(option.layoutXProperty(), 150 - spacing, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), expand);
                animation.getKeyFrames().add(keyFrame);
                animation.play();
                hitboxes.get((int)spacing / 25).setLayoutX(155 - spacing);
                spacing+= 25;
            }
        }
        else if (direction.equals("backwards")) {
            // iterates through all color options and moves them back to their initial location
            for (Group option : allOptions) {
                Timeline animation = new Timeline();
                KeyValue expand = new KeyValue(option.layoutXProperty(), 36.35, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), expand);
                animation.getKeyFrames().add(keyFrame);
                animation.play();
            }
            for (Circle hitbox : hitboxes) {
                hitbox.setLayoutX(36.35); // initial position
            }
        }

    }

    /**
     * This method changes the colors the players can choose from in the color selection.
     * this is achieved by clicking on a <b>colorSetButton</b> in the {@link #colorSetMenu}.
     * It also contains an animation for switching the colors of the color set and the color selection buttons.
     * @param clicked this event is triggered when a button of the color set
     *                menu is pressed.
     */
    public void applyColor(MouseEvent clicked) {
        Circle clickedButton = (Circle) clicked.getSource();

        Circle[] buttons = {yellowButton, redButton, blueButton, greenButton};
        int index = 0;
        clickedButton.setOnMousePressed(null); // remove clickable property of the button

        ObservableList<Node> children = getGroupById(clickedButton.getId()).getChildren();
        for (Node child : children) {
            if (child instanceof Circle) {
                double startX = child.getLayoutX(); // initial x-coordinate of the color set
                double startY = child.getLayoutY(); // initial y-coordinate of the color set

                /*
                This animation simply tells the color set circles to move to the color selection buttons.
                For that you have to calculate the button coordinate, because the color set circles are inside a group.
                A group changes how x and y coordinates work. If you change these coordinates, they will be aligned
                by the group x and y instead of the anchor pane x and y.

                To calculate this coordinate you have to subtract the coordinate of the clicked button by the
                coordinate of color set button to negate the effect of the group alignment.
                 */
                Timeline animation = new Timeline();
                KeyValue moveX = new KeyValue(child.layoutXProperty(), buttons[index].getLayoutX() - clickedButton.getLayoutX(), Interpolator.EASE_BOTH);
                KeyValue moveY = new KeyValue(child.layoutYProperty(), buttons[index].getLayoutY() - clickedButton.getLayoutY(), Interpolator.EASE_BOTH);
                KeyValue transform = new KeyValue(((Circle) child).radiusProperty(), 35, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), moveX, moveY, transform);
                animation.getKeyFrames().add(keyFrame);
                animation.play();
                int finalIndex = index; // finalIndex is the same as index, its needed because 'index' cant be used the lambda
                index++;
                animation.setOnFinished(event -> {
                    clickedButton.setOnMousePressed(this::applyColor); // re-enable the clickable property
                    child.setOpacity(0);

                    // switches out the colors of the color set and the color choose button
                    buttons[finalIndex].setFill(((Circle) child).getFill());
                    ((Circle) child).setFill(colors[finalIndex]);

                    // apply these colors for the visual game board
                    colors[finalIndex] = (javafx.scene.paint.Color) buttons[finalIndex].getFill();

                    // return the color set to its initial position and radius values
                    child.setLayoutX(startX);
                    child.setLayoutY(startY);
                    ((Circle) child).setRadius(3.65);

                    // this animation just lets the color set smoothly re-appear in the color set menu
                    Timeline transition = new Timeline();
                    KeyValue reappear = new KeyValue(child.opacityProperty(), 1, Interpolator.EASE_BOTH);
                    KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.3), reappear);
                    transition.getKeyFrames().add(keyFrame1);
                    transition.play();
                });
            }
        }
    }

    /**
     * This method grays out the colors of the icon inside the color set button
     */
    private void grayOutColorsOfColorSetButton() {
        colorSetButton.setCursor(Cursor.DEFAULT);
        for (Node child : colorSetButtonIcon.getChildren()) {
            if (child instanceof Circle) {
                ((Circle) child).setFill(javafx.scene.paint.Color.web("#616161"));
            }
        }
    }


    /**
     * This method checks if the selected color is available for the current player.
     * @param buttonId identifier of the pressed button
     */
    private boolean isColorAvailableForCurrentPlayer(String buttonId) {
        Color selectedColor = getColorFromButtonId(buttonId);

        for (int i = 0; i < currentPlayerIndex-1; i++) {
            Color playerColor = Color.getPlayerColor(i);
            // Check if the player has already selected a color and if it matches the selected color.
            if (playerColor != null && playerColor.equals(selectedColor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method assigns the current player the chosen color, using the <b>buttonId</b>.
     * @param buttonId identifier of the pressed button
     * @return player color
     */
    private Color getColorFromButtonId(String buttonId) {
        return switch (buttonId) {
            case "yellowButton" -> Color.YELLOW;
            case "redButton"    -> Color.RED;
            case "blueButton"   -> Color.BLUE;
            case "greenButton"  -> Color.GREEN;
            default -> null;
        };
    }

    /**
     * This method is used to disable the color buttons after they have been pressed.
     * This is achieved by simply using the {@link Circle#setVisible} and {@link Circle#setDisable} methods.
     * @param clickedButton identifier of the pressed button
     */
    private void updateColorButtonVisibility(Circle clickedButton) {
        clickedButton.setOnMouseEntered(null);
        clickedButton.setOnMouseExited(null);
        clickedButton.setOnMousePressed(null);
        clickedButton.setCursor(Cursor.DEFAULT);
        hoverTransition.stop();
        clickedButton.setFill(javafx.scene.paint.Color.web("#D9D9D9"));
        clickedButton.setRadius(35);
    }

    /**
     * This method allows to link the logic buttons of color sets with the actual visual color set
     * @param id of the clicked button
     * @return the color set linked to the button
     */
    private Group getGroupById(String id) {
        return switch (id) {
            case "hitbox1" -> colorSet1;
            case "hitbox2" -> colorSet2;
            case "hitbox3" -> colorSet3;
            case "hitbox4" -> colorSet4;
            default -> null;
        };
     }

    /**@Author Philipp Fiesel
     * This method checks if all players have selected their colors.
     * @return true if there are no players left in the <b>colorSelectionStatus</b> list
     * or false if there are players in the list.
     */
    private boolean allPlayersSelectedColor() {
        for (int i = 0; i < amountPlayers; i++) {
            if (!colorSelectionStatus.get(i)) {
                return false;
            }
        }
        return true;
    }
}

