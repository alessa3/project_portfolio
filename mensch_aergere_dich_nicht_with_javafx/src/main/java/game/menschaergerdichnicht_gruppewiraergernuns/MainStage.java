package game.menschaergerdichnicht_gruppewiraergernuns;

import backend.GameLoopController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainStage extends Application {
    private GameLoopController gameLoopController;
    private Stage stage;
    private static MainStage mainStage;

    public static MainStage getMainStage() {
        return mainStage;
    }
    public Stage getStage(){return stage;}
    @Override
    public void start(Stage stage) throws Exception {
        this.gameLoopController = new GameLoopController();
        AnchorPane root =  FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Start.fxml"));
        MainStage.mainStage = this;
        this.stage = stage;
        Scene scene = new Scene(root);


        stage.setResizable(false);
        stage.setTitle("Mensch ärgere dich nicht");
        stage.getIcons().add(new Image("IconGame.png"));
        stage.setScene(scene);

        stage.show(); // applications shows up
    }
    public void newGame() throws IOException {
        this.gameLoopController = new GameLoopController();
        AnchorPane root =  FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Start.fxml"));
        MainStage.mainStage = this;
        this.stage = stage;
        Scene scene = new Scene(root);
        stage.setTitle("Mensch ärgere dich nicht");
        stage.setScene(scene);
        stage.show(); // applications shows up
        }
    public void gaming() throws IOException {
        TransferValues.createTransferValues(); // creates the transfer values, now the frontend and backend work with each other
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Gaming.fxml"));
        Parent parent = loader.load(); // getting the fxml file <-> the anchor pane, here stored

        FXMLController controller = loader.getController(); // setting the controllers up and giving them an interface
        controller.setGameLoopController(gameLoopController);
        gameLoopController.setFxmlController(controller);
        gameLoopController.setPlayersAndTheirColorsFrontend();

        Scene scene = new Scene(parent);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setScene(scene); // SCENE change
        stage.setFullScreen(true); // now the game starts and we go into full screen
    }
    public void showConfetti(Scene scene){
        Image gifImage = new Image(getClass().getResourceAsStream("confetti.gif"));
        ImageView imageView = new ImageView(gifImage);

        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(imageView);
        //keine neue Scene sondern die Scene von Gaming aber wie? (aufruf in gaming geht nicht da sonst die ganze Zeit konfetti da ist)
    }
    /**
     * @Author Anna Baur
     */
    public void winnerScene(){
        try{
            FXMLLoader winnerSceneLoader = new FXMLLoader(getClass().getClassLoader().getResource("WinnerScene.fxml"));
            Parent root = winnerSceneLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.err.println(e);
        }
    }
    public static void main(String[] args) {
        launch(args); // launches application
    }
    }