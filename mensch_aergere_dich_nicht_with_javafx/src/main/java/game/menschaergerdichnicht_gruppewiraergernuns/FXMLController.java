package game.menschaergerdichnicht_gruppewiraergernuns;

import backend.Color;
import backend.Dice;
import backend.GameLoopController;
import backend.GamePiece;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.EventHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FXMLController implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public AnchorPane anchorPane;
    public Circle whoseTurnCircle;
    @FXML
    public Button rollingButton;
    @FXML
    public Button movingButton1,movingButton2, movingButton3, movingButton4;
    @FXML
    private ImageView quitButton;
    @FXML
    private Label rollingLabel, whoseTurnLabel;
    private Random random = new Random();
    private GameLoopController gameLoopController;
    @FXML
    AnchorPane rollingCup;
    @FXML
    ImageView toBeDragged;
    @FXML
    private ImageView diceImage;
    @FXML
    private Circle landingCircle1, landingCircle2, landingCircle3, landingCircle4;
    @FXML
    private ImageView kickSymbol1, kickSymbol2, kickSymbol3, kickSymbol4;
    private static FXMLController fxmlController;
    private static Set<MediaPlayer> soundPlayers;   // for keeping references from getting deleted by garbage collection
    private ScreenResolution screenResolution = new ScreenResolution();

    public static FXMLController getFxmlController() {
        return fxmlController;
    }

    public void setGameLoopController(GameLoopController gameLoopController){
        this.gameLoopController = gameLoopController;
    }
    private void setRollingLabel(String text){
        rollingLabel.setText(text);
    }

    /**
     * @Author Dennis Messmer
     */
    private void centerGridPane() {
        double gridSize = 600;

        double x = screenResolution.getScreenWidth() / 2 - gridSize/2;
        double y = screenResolution.getScreenHeight() / 2 - gridSize/2;

        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
    }

    /**
     * @Author Alessa Hackh
     */
    /* places a pane with a game piece in it into a grid */
    public void placeVisualGamePiece (VisualGamePiece v) {
        int columnValue = TransferValues.transferValues.get(v.getMyPartner().getPosition())[0];
        int rowValue = TransferValues.transferValues.get(v.getMyPartner().getPosition())[1];

        v.setVisuals();
        gridPane.add(v.getVisuals(), columnValue, rowValue);
    }
    /**
     * @Author Lennart Raach
     */
    private void removeVisualsOfVisualGamePiece(VisualGamePiece visualGamePiece){
        ObservableList<Node> childrens = gridPane.getChildren(); // code iterates a list and removes the pane of the viusal game piece

        for(Node node : childrens) {
            if(node instanceof Pane && node == visualGamePiece.getVisuals()){
                Pane toRemove=(Pane) node; // downcast since Pane is a subclass of Node
                gridPane.getChildren().remove(toRemove);
                visualGamePiece.removeVisuals();
                break;  // after finding the correct one it breaks the loop
            }
        }
    }
    /**
     * @Author Alessa Hackh
     */
    @FXML
    private void visualMove(VisualGamePiece visualGamePiece){

        removeVisualsOfVisualGamePiece(visualGamePiece); // removes visual game piece at old spot
        placeVisualGamePiece(visualGamePiece); // places visual game piece at new spot

    }

    /**
     * @Author Alessa Hackh
     */
    @FXML
    public void visualMoveButKickVersion(VisualGamePiece visualGamePiece){
        createKickSound();

        removeVisualsOfVisualGamePiece(visualGamePiece); // removes visual game piece at old spot
        placeVisualGamePiece(visualGamePiece); // places visual game piece at new spot

    }
    /**
     * @Author Dennis Messmer, Philipp Fiesel, Alessa Hackh, Anna Baur, Lennart Raach
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            anchorPane.setPrefWidth(screenResolution.getScreenWidth());
            anchorPane.setPrefHeight(screenResolution.getScreenHeight());
            FXMLController.fxmlController = this; // when the controller is initialized it references itself, so we can use it in later steps
            getDraggableDice();
            centerGridPane();
            setColorsOfFields();
            soundPlayers = new HashSet<>(); // initialize HashSet for saving MediaPlayer references
            new RulesButton(anchorPane, "right");
            new QuitButton(anchorPane);
    }

    /**
     * @Author Alessa Hackh
     */
    private void createDiceSound() {
        Media diceSound = new Media(new File("dicecup_sound.wav").toURI().toString());
        MediaPlayer diceSoundPlayer = new MediaPlayer(diceSound);
        soundPlayers.add(diceSoundPlayer);
        diceSoundPlayer.play();
    }

    /**
     * @Author Alessa Hackh
     */
    /* creates sound that plays when a player game piece gets kicked */
    private void createKickSound() {
        Media kickSound = new Media(new File("kick_sound.wav").toURI().toString());
        MediaPlayer kickSoundPlayer = new MediaPlayer(kickSound);
        soundPlayers.add(kickSoundPlayer);
        kickSoundPlayer.play();
    }
    /**
     * @Author Lennart Raach
     */
    public void createWhoseTurnCircle(){ // Simply creates a circle in the top left corner which shows the current color of player
        this.whoseTurnCircle = new Circle(50);
        anchorPane.getChildren().add(whoseTurnCircle);
        AnchorPane.setTopAnchor(whoseTurnCircle,20.0);
        AnchorPane.setBottomAnchor(whoseTurnCircle,20.0);
        AnchorPane.setLeftAnchor(whoseTurnCircle,20.0);
        AnchorPane.setRightAnchor(whoseTurnCircle,20.0);

    }

    /**
     * @Author Alessa Hackh, Dennis Messmer
     */
    public void setColorsOfFields() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Circle) {
                if (((Circle) node).getFill().equals(javafx.scene.paint.Color.web("#feff59"))) {
                    ((Circle) node).setFill(ColorTransfer.colors[0]);
                }
                else if (((Circle) node).getFill().equals(javafx.scene.paint.Color.web("#da5e5e"))) {
                    ((Circle) node).setFill(ColorTransfer.colors[1]);
                }
                else if (((Circle) node).getFill().equals(javafx.scene.paint.Color.web("#607D8B"))) {
                    ((Circle) node).setFill(ColorTransfer.colors[2]);
                }
                else if (((Circle) node).getFill().equals(javafx.scene.paint.Color.web("#75c95d"))) {
                    ((Circle) node).setFill(ColorTransfer.colors[3]);
                }
            }
        }
    }

    /**
     * @Author Lennart Raach
     */
    @FXML
    public void whoseTurn(Color colorOfCurrentPlayer) { // changes color depending on the current player

        if(colorOfCurrentPlayer == Color.RED)
            this.whoseTurnCircle.setFill(ColorTransfer.colors[1]);
        if(colorOfCurrentPlayer == Color.BLUE)
            this.whoseTurnCircle.setFill(ColorTransfer.colors[2]);
        if(colorOfCurrentPlayer == Color.YELLOW)
            this.whoseTurnCircle.setFill(ColorTransfer.colors[0]);
        if(colorOfCurrentPlayer == Color.GREEN)
            this.whoseTurnCircle.setFill(ColorTransfer.colors[3]);

    }
    /**
     * @Author Anna Baur
     */
    @FXML
    void roll(ActionEvent event) throws InterruptedException {

        rollingButton.setDisable(true);

        boolean stillRolling = this.gameLoopController.rollingPhase(); // after the button is pressed, the method of the game loop controller starts
        Thread thread = new Thread(){ // inner class thread, which is needed for cool small animation
            @Override
            public void run(){

                try {
                    for (int i = 0; i < 10; i++) {
                        File file = new File("src/main/java/dice/dice" + (random.nextInt(6)+1)+".png"); // changes pictures fast so it looks like rolling a dice
                        diceImage.setImage(new Image(file.toURI().toString()));
                        Thread.sleep(50);
                        }
                    File file;
                    if(Dice.getEyeCount() == 1){
                      file= new File("src/main/java/dice/dice1.png");
                      diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    if(Dice.getEyeCount() == 2){
                        file= new File("src/main/java/dice/dice2.png");
                        diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    if(Dice.getEyeCount() == 3){
                        file= new File("src/main/java/dice/dice3.png");
                        diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    if(Dice.getEyeCount() == 4){
                        file= new File("src/main/java/dice/dice4.png");
                        diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    if(Dice.getEyeCount() == 5){
                        file= new File("src/main/java/dice/dice5.png");
                        diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    if(Dice.getEyeCount() == 6){
                        file= new File("src/main/java/dice/dice6.png");
                        diceImage.setImage(new Image(file.toURI().toString()));
                    }
                    rollingButton.setDisable(false);//true setzen im Spiel das man nur einmal würfeln kann -> erneuter methoden aufruf wenn 6 gewürfelt wird


                } catch (InterruptedException e) {                  //Exception falls Thread unterbrochen wird
                    e.printStackTrace();
                }

            }
        };

        thread.start(); // starts the defined thread


        if(!stillRolling){ // when the rolling phase is over
            this.disableRollButton();   // no rolling for the user
            this.gameLoopController.movingPrep();   // moving buttons are created
        }
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void getDraggableDice(){
        // TEST *** TEST *** TEST *** TEST

        //Circle toBeDragged = new Circle(10);

        Image dice = new Image(new File("src/main/java/dice/DraggableDice.png").toURI().toString());
        toBeDragged = new ImageView(dice);
        toBeDragged.setFitHeight(80); // size of image
        toBeDragged.setFitWidth(80);

        anchorPane.getChildren().add(toBeDragged);
        AnchorPane.setTopAnchor(toBeDragged,100.0);
        AnchorPane.setBottomAnchor(toBeDragged,150.0);
        AnchorPane.setLeftAnchor(toBeDragged,150.0);
        AnchorPane.setRightAnchor(toBeDragged,150.0); // just positioning the dice in the layout

        toBeDragged.setTranslateX(-50);
        toBeDragged.setTranslateY(600);

        toBeDragged.setOnDragDetected(event->{
            toBeDragged.startFullDrag();
        });


        toBeDragged.setOnMouseDragged(event->{
            toBeDragged.setManaged(false);
            toBeDragged.setTranslateX(event.getX() + toBeDragged.getTranslateX() - 50); // now you can move the dice around with your mouse  -> changes position in dependency of mouse
            toBeDragged.setTranslateY(event.getY() + toBeDragged.getTranslateY() - 50);
            event.consume();

        });


        toBeDragged.setOnMousePressed(event ->{ // so you can drop the dice on something and it doesnt detect your mouse as a potential target
            // toBeDragged.setRadius(25);
            toBeDragged.setMouseTransparent(true);
        });

        toBeDragged.setOnMouseReleased(event ->{ // so u can pick the dice up again
            // toBeDragged.setRadius(10);
            toBeDragged.setMouseTransparent(false);
        });

        rollingCup.setOnMouseDragReleased(event->{ // after dice is dropped down on the rolling cup


            createDiceSound();

            makeDraggableDiceInvisible();

            toBeDragged.setManaged(true);   // sets position of dice
            toBeDragged.setTranslateX(-50);
            toBeDragged.setTranslateY(600);

                shake();                                        //rollingCup starts to shake


        });
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    private void makeDraggableDiceVisible(){

        toBeDragged.setVisible(true);

    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    private void makeDraggableDiceInvisible(){

        toBeDragged.setVisible(false);

    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void enableRollButton(){
        //if condition is fulfilled and its rolling phase
        whoseTurn(gameLoopController.getCurrentPlayer().getColor());
        makeDraggableDiceVisible();
        this.rollingButton.setVisible(true); // now you see the button again ayyy
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void disableRollButton(){
        //after successful roll, when its moving phase
        this.rollingButton.setVisible(false);
        makeDraggableDiceInvisible();
    }

    /**
     * @Author Anna Baur
     */
    public void shake(){

        Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            int y = 0;
            @Override
            public void handle(ActionEvent t) {                             //changes y-axis values to create shaking effect
                if(y ==0) {
                    rollingCup.setLayoutY(rollingCup.getLayoutY() + 10);
                    y =1;
                }else {
                    rollingCup.setLayoutY(rollingCup.getLayoutY() - 10);
                    y =0;
                }
            }
        }));
        timelineY.setCycleCount(6);
        timelineY.setAutoReverse(false);
        timelineY.play();

        timelineY.setOnFinished(e ->{ // after animation is finished

            boolean stillRolling = this.gameLoopController.rollingPhase();

            diceImage.setImage(new Image(new File("src/main/java/dice/dice"+ Dice.getEyeCount() + ".png").toURI().toString()));

            if(!stillRolling){ // when the rolling phase is over
                this.disableRollButton();   // no rolling for the user
                this.gameLoopController.movingPrep();   // moving buttons are created
            }
            else enableRollButton();
        });

    }


    /**
     * @Author Lennart Raach
     */
    @FXML
    public void createMovingButtonsV2(ArrayList<GamePiece> gamePieces) {
        int[] coordinates;
        for (GamePiece gp : gamePieces) {
            coordinates = TransferValues.transferValues.get(gp.getPosition()); // gives the needed position on frontend board

            Circle landingCircle = new Circle(15,javafx.scene.paint.Color.PEACHPUFF);
            Image toReplace = new Image(new File("src/main/java/dice/Kick_Symbol.png").toURI().toString());
            ImageView kickSymbol = new ImageView(toReplace);

            if(gameLoopController.kickingPossibility(gp))
                {
                    kickSymbol.setFitWidth(40);
                    kickSymbol.setFitHeight(40);
                    gridPane.add(kickSymbol,TransferValues.transferValues.get(gp.getThrowDestination())[0],TransferValues.transferValues.get(gp.getThrowDestination())[1]);
                    GridPane.setHalignment(kickSymbol, HPos.CENTER);
                    kickSymbol.setOpacity(0);
                }
                else {
                    gridPane.add(landingCircle, TransferValues.transferValues.get(gp.getThrowDestination())[0], TransferValues.transferValues.get(gp.getThrowDestination())[1]);
                    GridPane.setHalignment(landingCircle, HPos.CENTER);
                    landingCircle.setOpacity(0);
                }
                Button movingButton = new Button();
                movingButton.setText("placeholder");
                GridPane.setHalignment(movingButton, HPos.CENTER); // centers button
                movingButton.prefHeightProperty().bind(gridPane.heightProperty()); // now the button fills the whole pane inside the grid
                movingButton.prefWidthProperty().bind(gridPane.widthProperty());
                gridPane.add(movingButton, coordinates[0], coordinates[1]);    // adds the button to the pane
                movingButton.setOpacity(0); // makes the button transparent but it stays usable

                movingButton.setOnAction((event) -> { // if pressed makes the whole moving stuff



                    try {
                        gameLoopController.move(gp);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    visualMove(gp.getVisualGamePiece());
                    this.clearMovingButtons();

                }); // move gamepiece to new spot, remove button, remove old game piece
            if(movingButton1 == null){      // after the button is created it gets an ID =)
                movingButton1 = movingButton;
                if(gameLoopController.kickingPossibility(gp))
                    kickSymbol1 = kickSymbol;
                else
                landingCircle1 = landingCircle;
                setButtonHover(movingButton1);
            }else if(movingButton2 == null){
                movingButton2 = movingButton;
                if(gameLoopController.kickingPossibility(gp))
                    kickSymbol2 = kickSymbol;
                else
                landingCircle2 = landingCircle;
                setButtonHover(movingButton2);
            }else if(movingButton3 == null){
                movingButton3 = movingButton;
                if(gameLoopController.kickingPossibility(gp))
                    kickSymbol3 = kickSymbol;
                else
                landingCircle3 = landingCircle;
                setButtonHover(movingButton3);
            }else if(movingButton4 == null){
                movingButton4 = movingButton;
                if(gameLoopController.kickingPossibility(gp))
                    kickSymbol4 = kickSymbol;
                else
                landingCircle4 = landingCircle;
                setButtonHover(movingButton4);
            }

        }
    }
    /**
     * @Author Lennart Raach
     */
    private void setButtonHover(Button button){
        if(button == null){
            return;
        }
        if(movingButton1 == button) {
            if(kickSymbol1 == null ) {
                button.setOnMouseEntered((event) -> landingCircle1.setOpacity(100));
                button.setOnMouseExited((event) -> landingCircle1.setOpacity(0));
            }else{
                button.setOnMouseEntered((event) -> kickSymbol1.setOpacity(100));
                button.setOnMouseExited((event) -> kickSymbol1.setOpacity(0));
            }
        }
        if(movingButton2 == button) {
            if(kickSymbol2 == null ) {
                button.setOnMouseEntered((event) -> landingCircle2.setOpacity(100));
                button.setOnMouseExited((event) -> landingCircle2.setOpacity(0));
            }else{
                button.setOnMouseEntered((event) -> kickSymbol2.setOpacity(100));
                button.setOnMouseExited((event) -> kickSymbol2.setOpacity(0));
            }
        }
        if(movingButton3 == button) {
            if(kickSymbol3 == null ) {
                button.setOnMouseEntered((event) -> landingCircle3.setOpacity(100));
                button.setOnMouseExited((event) -> landingCircle3.setOpacity(0));
            }else{
                button.setOnMouseEntered((event) -> kickSymbol3.setOpacity(100));
                button.setOnMouseExited((event) -> kickSymbol3.setOpacity(0));
            }
        }
        if(movingButton4 == button){
            if(kickSymbol4 == null ) {
                button.setOnMouseEntered((event) -> landingCircle4.setOpacity(100));
                button.setOnMouseExited((event) -> landingCircle4.setOpacity(0));
            }else{
                button.setOnMouseEntered((event) -> kickSymbol4.setOpacity(100));
                button.setOnMouseExited((event) -> kickSymbol4.setOpacity(0));
            }
        }

    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void removeMovingButton(Button movingButton){
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof Button && ( node == movingButton1 || node == movingButton2 || node == movingButton3 || node == movingButton4)){
                Button toRemove = (Button) movingButton; //
                gridPane.getChildren().remove(toRemove);
                break;
            }
        }
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void removeLandingCircle(Circle landingCircle){
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof Circle && ( node == landingCircle1 || node == landingCircle2 || node == landingCircle3 || node == landingCircle4)){
                Circle toRemove = (Circle) node; //
                gridPane.getChildren().remove(toRemove);
                break;
            }
        }
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    private void removeKickSymbol(ImageView kickSymbol){
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof ImageView && ( node == kickSymbol1 || node == kickSymbol2 || node == kickSymbol3 || node == kickSymbol4)){
                ImageView toRemove = (ImageView) node; //
                gridPane.getChildren().remove(toRemove);
                break;
            }
        }
    }
    /**
     * @Author Lennart Raach
     */
    @FXML
    public void clearMovingButtons(){ // it just removes all moving buttons, so there won't be any bugs
        if(movingButton1 != null) {
            removeMovingButton(movingButton1);
            movingButton1 = null;
            if(landingCircle1 != null)
                removeLandingCircle(landingCircle1);
            else
                removeKickSymbol(kickSymbol1);
            landingCircle1 = null;
            kickSymbol1 = null;
        }
        if(movingButton2 != null) {
            removeMovingButton(movingButton2);
            movingButton2 = null;
            if(landingCircle2 != null)
                removeLandingCircle(landingCircle2);
            else
                removeKickSymbol(kickSymbol2);
            landingCircle2 = null;
            kickSymbol2 = null;
        }
        if(movingButton3 != null) {
            removeMovingButton(movingButton3);
            movingButton3 = null;
            if(landingCircle3 != null)
                removeLandingCircle(landingCircle3);
            else
                removeKickSymbol(kickSymbol3);
            landingCircle3= null;
            kickSymbol3 = null;
        }
        if(movingButton4 != null){
            removeMovingButton(movingButton4);
            movingButton4 = null;
            if(landingCircle4 != null)
                removeLandingCircle(landingCircle4);
            else
                removeKickSymbol(kickSymbol4);
            landingCircle4 = null;
            kickSymbol4 = null;
        }
    }

    /**
     * @Author Anna Baur
     */
    @FXML
    public void winningConfetti(){ // if someone wins
        Image gifImage = new Image("confetti.gif");
        ImageView gifConfetti = new ImageView(gifImage);
        gifConfetti.setFitWidth(2000);
        gifConfetti.setFitHeight(2000);
        anchorPane.getChildren().add(gifConfetti);  // now shows the gif

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), t -> {}));

        timeline.play();

        timeline.setOnFinished(e ->{ // after animation is finished, (5sec), it goes into the winning scene
            MainStage.getMainStage().winnerScene();
        });

        }

}
