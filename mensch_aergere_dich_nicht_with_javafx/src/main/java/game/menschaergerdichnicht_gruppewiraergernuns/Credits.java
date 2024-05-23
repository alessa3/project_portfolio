package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @Author Philipp Fiesel
 */
public class Credits {

    @FXML
    private AnchorPane creditsAnchorPane;

    /**
     * This method is used to call the {@link SceneSwitch} for switching the Scene to get back to the {@link WinnerScene}
     * @throws IOException
     */
    public void getBack() throws IOException {
        new SceneSwitch(creditsAnchorPane, "WinnerScene.fxml");
    }

}