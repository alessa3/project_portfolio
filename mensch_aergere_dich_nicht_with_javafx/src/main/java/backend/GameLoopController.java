package backend;

import game.menschaergerdichnicht_gruppewiraergernuns.FXMLController;
import game.menschaergerdichnicht_gruppewiraergernuns.MainStage;
import game.menschaergerdichnicht_gruppewiraergernuns.WinnerScene;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;



public class GameLoopController { // controls the game loop

//    private static int amountPlayers;
    public static GameLoopController currentGameLoopController;
    private int amountPlayers;
    private final GameBoard gameBoard;
    private final Dice mainDice;
    private Player[] players;
    private int currentPlayerIndex;
    private Player currentPlayer;
    private int timesRolledDice; // it's used for recording the amount of dice rolls
    private Input input;
    private FXMLController fxmlController;

    //private MainStage mainStage;

    public GameLoopController() {
        this.gameBoard = new GameBoard(); //Creating attributes
        this.mainDice  = new Dice();
        GameLoopController.currentGameLoopController = this;
    }

    public Player[] getPlayers() {
        return players;
    }
    public Player getCurrentPlayer(){return currentPlayer;}

    private void selectBeginner() { //All players roll the dice until one threw the highest eye count, current player = the one with the highest eye count
        this.currentPlayerIndex = mainDice.allRollTheDice(amountPlayers);
        this.currentPlayer = players[currentPlayerIndex];
        this.fxmlController.createWhoseTurnCircle(); // sets up the circle that represents the current player
        this.fxmlController.whoseTurn(this.currentPlayer.getColor());

    }

    private void nextPlayer()  { // next player

        try{
            Thread.sleep(100);}// so the color circle doesn't change color too early, still does lol
        catch(InterruptedException e){
            System.err.println("ayy couldn't wait");}
        timesRolledDice = 0; // resets
        currentPlayer.setSameTurn(false); // move is over
        currentPlayerIndex = (currentPlayerIndex + 1) % amountPlayers; //Next player, after current player finishes turn
        currentPlayer = players[currentPlayerIndex];

        fxmlController.enableRollButton(); // we get the rolling button
        fxmlController.whoseTurn(this.currentPlayer.getColor()); // circle changes color to the one of the next person

    }

    public boolean rollingPhase(){
        currentPlayer.roll(this.mainDice);
        timesRolledDice++; // roll +1;

        if(Dice.getEyeCount() == 6){
            currentPlayer.setSameTurn(true); // player can only roll once after that
            return false; // moving phase no matter what
        }
        else if(currentPlayer.canMove() == true || currentPlayer.getSameTurn() == true){
            return false; //moving phase no matter what, because game pieces can theoretically move/ it's their same turn
        }
        else if(currentPlayer.canMove() == false && timesRolledDice < 3 && Dice.getEyeCount() != 6){
            return true; // rolling phase, stays
        }
        else{
            this.nextPlayer();
            return true; // rolling phase of next person
        }

    }

    public void movingPrep() {
        // moving phase
        ArrayList<GamePiece> movableGamePieces = currentPlayer.checkMovablePieces(Dice.getEyeCount(), gameBoard.getGameBoard());

        if(movableGamePieces.isEmpty() && Dice.getEyeCount() == 6){ // if u rolled a 6 and cant move -> get the rolling button my guy
            fxmlController.enableRollButton();}
        else if (movableGamePieces.isEmpty() && Dice.getEyeCount() != 6) { // if there is no character the person can move with the thrown eye count, they have to give the dice to the next person, except they had a 6
            fxmlController.enableRollButton();
            this.nextPlayer();
        }
         else if (!movableGamePieces.isEmpty()) { // if a game piece can move, moving will be made available

            fxmlController.createMovingButtonsV2(movableGamePieces); // now there are buttons at the gridpane

        }
    }

    /**
     * @Author Lennart Raach
     */
    public boolean kickingPossibility(GamePiece gamePiece){
        if(gamePiece.checkKickPossibility(gameBoard))
            return true;
        return false;
    }


        public void move(GamePiece gamePiece) throws FileNotFoundException {

            GamePiece kickedGamePiece = this.currentPlayer.moveGamePiece(gameBoard, gamePiece);

            if(kickedGamePiece != null) // if a kick happens
                fxmlController.visualMoveButKickVersion(kickedGamePiece.getVisualGamePiece());
            if(currentPlayer.hasWon()) {    // after win call it
                fxmlController.winningConfetti();

            }

        if(Dice.getEyeCount() == 6 ){ // player threw a 6 and now they are allowed to roll another time
            fxmlController.enableRollButton();
        }
        else {  // no 6, so next player
            this.nextPlayer();
        }
    }

   public void setPlayersAndTheirColorsFrontend() {
        this.amountPlayers = getAmountPlayers(); // creation of players and their colors
        this.players = new Player[amountPlayers];

        // Spielerobjekte erstellen
       for (int i = 0; i < amountPlayers; i++) {
           Color playerColor = Color.getPlayerColor(i);
           this.players[i] = new Player(playerColor);
            // Visuelle Spielfiguren platzieren
            for (int j = 0; j < 4; j++) {
               // Visually place all game pieces
               fxmlController.placeVisualGamePiece(players[i].getGamePieces()[j].getVisualGamePiece());
           }
        }        selectBeginner();
    }

   public int getAmountPlayers() {  // some getters and setters which were needed
       return amountPlayers;
   }
    public void setAmountPlayers(int amountPlayers) {
       this.amountPlayers = amountPlayers;
    }
    public void setFxmlController(FXMLController fxmlController){
        this.fxmlController = fxmlController;
    }

}