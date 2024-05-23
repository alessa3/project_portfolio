package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CenterLabel {
    /**
     * Centers a label within an anchor pane.
     *
     * @param anchorPane The anchor pane in which the label should be centered.
     * @param tobeCentered The label to be centered.
     */
    public static void centerLabel(AnchorPane anchorPane, Label tobeCentered) {
        tobeCentered.setPrefWidth(anchorPane.getPrefWidth());
        tobeCentered.setPrefHeight(anchorPane.getPrefHeight());
        tobeCentered.setMouseTransparent(true);
        tobeCentered.setAlignment(Pos.CENTER);
    }
}
