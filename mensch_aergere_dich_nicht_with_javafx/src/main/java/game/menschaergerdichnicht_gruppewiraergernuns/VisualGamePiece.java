package game.menschaergerdichnicht_gruppewiraergernuns;

import backend.Color;
import backend.GamePiece;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class VisualGamePiece {

        private GamePiece myPartner; // for the connection between both of them

        private Pane visuals;
        private javafx.scene.paint.Color visualColor;

        public VisualGamePiece(GamePiece myPartner){

            this.myPartner = myPartner;
            Color color = myPartner.getColor();
            if(color == Color.BLUE) // depending on color has different color
                this.visualColor = ColorTransfer.colors[2];
            else if(color == Color.RED)
                this.visualColor = ColorTransfer.colors[1];
            else if(color == Color.YELLOW)
                this.visualColor = ColorTransfer.colors[0];
            else
                this.visualColor = ColorTransfer.colors[3];

            setVisuals();
        }


    public void setVisuals(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.30);
        // creation of visuals, game piece consists of a polygon (a triangle) and a circle
        Polygon polygon = new Polygon(-11.5, 40.0, 7.449604034423828, 40.000003814697266, -1.9999942779541016, 17.0);
        polygon.setFill(this.visualColor);
        polygon.setEffect(colorAdjust);
        polygon.setStroke(this.visualColor);
        polygon.setStrokeType(StrokeType.INSIDE);
        polygon.setLayoutX(25.0);
        polygon.setLayoutY(-2.0);

        Circle circle = new Circle();
        circle.setFill(visualColor);
        circle.setEffect(colorAdjust);
        circle.setLayoutX(23.0);
        circle.setLayoutY(15.0);
        circle.setRadius(7.0);
        circle.setStroke(visualColor);
        circle.setStrokeType(StrokeType.INSIDE);

        /* game piece shape is added to a pane, so the shapes can be moved around together */

        this.visuals = new Pane();
        this.visuals.getChildren().addAll(circle, polygon);
    }
    public void removeVisuals(){
            this.visuals = null;     // deletes reference, is used for a cursed grid pane add, that got us crying
    }

    public Pane getVisuals(){
            return this.visuals;
        }

    public GamePiece getMyPartner() {
        return myPartner;
    }

}
