module game.menschaergerdichnicht_gruppewiraergernuns {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                                requires com.almasb.fxgl.all;
    requires javafx.media;
    requires java.desktop;

    opens game.menschaergerdichnicht_gruppewiraergernuns to javafx.fxml;
    exports game.menschaergerdichnicht_gruppewiraergernuns;
}