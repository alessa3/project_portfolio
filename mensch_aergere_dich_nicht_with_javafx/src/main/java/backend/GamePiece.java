package backend;
import game.menschaergerdichnicht_gruppewiraergernuns.TransferValues;
import game.menschaergerdichnicht_gruppewiraergernuns.VisualGamePiece;
import game.menschaergerdichnicht_gruppewiraergernuns.FXMLController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class GamePiece {

    private int position; // describes the position of the game piece on the game board
    private Color color; // the color of the game piece
    private GamePieceState state; // state of game piece to make some methods easier (in_base and so on...)
    private int throwDestination;   // calculation of field where the game piece would land after rolling the dice, to be updated after each move
    private Player owner; // describes the player who owns the game piece
    private int id; // the number of the game piece
    private VisualGamePiece visualGamePiece;



    public GamePiece(int position, Color color, Player owner, int id) {
        this.position = position;
        this.throwDestination = owner.getFirstField();
        this.color = color;
        this.state = GamePieceState.IN_BASE;
        this.owner = owner;
        this.id = id;
        this.visualGamePiece = new VisualGamePiece(this);
    }

    public VisualGamePiece getVisualGamePiece(){
        return this.visualGamePiece;
    }
    public int getId(){
        return id;
    }

    public int getThrowDestination() {
        return throwDestination;
    }

    public void setThrowDestination(int eyeCount) {

        /* calculation of destination field */
        int throwDestination;
        if(this.position >= this.owner.getHomeStart()){ // if they are in home already
            throwDestination = this.getPosition() + eyeCount;
        }
        else if ((this.position + eyeCount) > this.owner.getLastField() && this.position <= this.owner.getLastField() ||
             this.color == Color.BLUE && this.state != GamePieceState.IN_BASE && (this.position + eyeCount) > this.owner.getLastField()) {    // if piece would move into home
            throwDestination = this.owner.getHomeStart() + ((eyeCount - (this.owner.getLastField() - this.position)) - 1); // 68 + (1 - (68 - 70) -1 = 68 + (1 +3) -1 72-1 ->
            /* explanation:
             * a) lastField - g.getPosition()   -> how many fields can the game piece move forward until it reaches the end of the walkable game board
             * b) eyeCount - a                  -> calculate how many single moves are left to walk after entering the home
             * c) b - 1                         -> remainder of the rolled number after entering the house, substract 1 because that is the one move from the last field of the map to the first field of the house
             * d) homeStart + c                 -> walk the rest of eye count
             */
        }
        else if (this.getState() == GamePieceState.IN_BASE) {    // if piece moves out of base
            throwDestination = this.owner.getFirstField();
        } else {    // in every other case
            throwDestination = (this.getPosition() + eyeCount) % 40;
        }
        this.throwDestination = throwDestination;
    }


    public GamePieceState getState() {
        return state;
    }

    public void setState(GameBoard gameBoard) {
        if(this.position >= this.owner.getBaseStart() && this.position <= this.owner.getBaseStart() + 3){
            this.state = GamePieceState.IN_BASE;
        }
        else if(this.position == 71) {
            this.state = GamePieceState.IN_HOME;
        }
        else if(this.position >= this.owner.getHomeStart() && this.position <= this.owner.getHomeStart() + 3 && (gameBoard.getGameBoard()[this.position+1] != null || this.position+1 ==  this.owner.getHomeStart()+4) ){
            this.state = GamePieceState.IN_HOME;
        }
        else{
            this.state = GamePieceState.ON_FIELD;
         }
    }

    public Color getColor() {
        return color;
    }
    public String toString(){
        return "" + this.id;
    }

    public int getPosition(){
        return this.position;
    }

    public void setPosition(int position, GameBoard gameBoard) {
        this.position = position;
        setState(gameBoard);
    }
    public boolean checkKickPossibility(GameBoard gameBoard){
        if(gameBoard.checkKick(throwDestination))
            return true;
        return false;
    }


    public void kick(GameBoard gameBoard, GamePiece getsKicked){

        getsKicked.gotKickedInTheNuts(gameBoard);
    }
    public void gotKickedInTheNuts(GameBoard gameBoard){ // game piece is kicked by another game piece and returns to the base

        int baseStart = 0;

        int i = 0;

        if(this.color == Color.RED)
            baseStart = 40;

        else if(this.color == Color.YELLOW)
            baseStart = 48;

        else if(this.color == Color.GREEN)
            baseStart = 44;

        else if(this.color == Color.BLUE)
            baseStart = 52;

        while(gameBoard.getGameBoard()[baseStart] != null && i < 4){ // looks for a free spot in the base
            baseStart += 1;
            i++;
        }

        position = baseStart;
        gameBoard.placeGamePiece(owner, this, position); // places the game piece in the base

    }

}