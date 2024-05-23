package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {
    /**
     * Receives current scene and the fxml file which should load.
     *  The current scene is removed and the new one is set.
     * @param currentAnchorPane
     * @param fxml
     * @throws IOException
     */

    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainStage.class.getClassLoader().getResource(fxml)));
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), nextAnchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        currentAnchorPane.setStyle("-fx-background-color: #FFFFFF;");
        currentAnchorPane.getChildren().removeAll();
        if(fxml.equals("Gaming.fxml")){
            MainStage ms = MainStage.getMainStage();
            ms.gaming();
            return;
        }
        fadeTransition.play();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }


}
