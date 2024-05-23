package game.menschaergerdichnicht_gruppewiraergernuns;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.awt.*;

public class ScreenResolution {
    Rectangle2D visualBounds;
    private final double screenWidth;
    private final double screenHeight;

    /**
     * Constructs a new ScreenResolution object.
     * Gets the visual bounds of the screen for the given operating system and initializes the screenWidth and screenHeight variables.
     */
    public ScreenResolution() {
        Screen screen = Screen.getPrimary();
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            visualBounds = screen.getVisualBounds();
        }
        else {
            visualBounds = screen.getBounds();
        }
        screenWidth = visualBounds.getWidth();
        screenHeight = visualBounds.getHeight();
    }

    /**
     * Returns the width of the screen.
     *
     * @return The width of the screen.
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the height of the screen.
     *
     * @return The height of the screen.
     */
    public double getScreenHeight() {
        return screenHeight;
    }
}
