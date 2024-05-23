package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;

public class WinnerScene {

    static ImageView imageview;

    @FXML
    private AnchorPane winnerSceneAnchorPane;

    @FXML
    public void newGame() throws IOException {
        MainStage.getMainStage().newGame();
    }

    public static void confetti(){
        Image i = new Image(new File("confetti.gif").toURI().toString());
        imageview.setImage(i);
    }


    /**
     * This method is used to call the {@link SceneSwitch} for switching the Scene to Credits.fxml
     * @throws IOException
     */
    public void switchToCredits() throws IOException {
        new SceneSwitch(winnerSceneAnchorPane, "Credits.fxml");
    }
}
