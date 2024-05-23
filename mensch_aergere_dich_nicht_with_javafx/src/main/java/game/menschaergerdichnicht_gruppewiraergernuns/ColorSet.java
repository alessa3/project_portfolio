package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.scene.paint.Color;

public class ColorSet {
    javafx.scene.paint.Color gamePieceBlue;
    javafx.scene.paint.Color gamePieceRed;
    javafx.scene.paint.Color gamePieceYellow;
    javafx.scene.paint.Color gamePieceGreen;
    javafx.scene.paint.Color fieldsBlue;
    javafx.scene.paint.Color fieldsRed;
    javafx.scene.paint.Color fieldsYellow;
    javafx.scene.paint.Color fieldsGreen;


    // blue, red, yellow, green
    public ColorSet(String colorSetName) {
        if (colorSetName.equals("standard")) {
            gamePieceBlue = Color.BLUE;
            gamePieceRed = Color.DARKRED;
            gamePieceYellow = Color.ORANGE;
            gamePieceGreen = Color.GREEN;

            fieldsBlue = Color.DODGERBLUE;
            fieldsRed = Color.web("0xda5e5e");
            fieldsYellow = Color.web("0xfeff59");
            fieldsGreen = Color.web("0x75c95d");
        } else if (colorSetName.equals("altOne")) {

        }
    }
}
