package backend;

import game.menschaergerdichnicht_gruppewiraergernuns.FXMLController;

import java.util.ArrayList;

public class Player {
    private final Color color;    // color of game pieces of a player
    private final GamePiece[] gamePieces; // an array containing all game pieces that belong to a player
    private final int baseStart;  // first field of base for player's color
    private final int firstField; // first in-game field for player's color
    private final int lastField;  // last in-game field before home for player's color
    private final int homeStart;  // first field of home for player's color
    private boolean sameTurn; // important for the method rollTheDice, so the game knows if how often the person is allowed to roll the dice
    private final String textColor; // small visual addition to the game

    /* Constructor for player, receives a color from enum Color */
    public Player(Color color) {
        this.color = color;
        this.sameTurn = false;    // false is default at object creation

        if (color == Color.RED) {   // if color is red
            baseStart = 40;
            firstField = 30;
            lastField = firstField-1;
            homeStart = 56;
            textColor = TextColor.RED;
        } else if (color == Color.GREEN) { // if color is green
            baseStart = 44;
            firstField = 20;
            lastField = firstField-1;
            homeStart = 60;
            textColor = TextColor.GREEN;
        } else if (color == Color.YELLOW) { // if color is yellow
            baseStart = 48;
            firstField = 10;
            lastField = firstField-1;
            homeStart = 64;
            textColor = TextColor.YELLOW;
        } else {    // if color is blue
            baseStart = 52;
            firstField = 0;
            lastField = 39;
            homeStart = 68;
            textColor = TextColor.BLUE;
        }

        GamePiece gamePiece1 = new GamePiece(baseStart, color, this, 1);
        GamePiece gamePiece2 = new GamePiece(baseStart+1, color, this, 2);
        GamePiece gamePiece3 = new GamePiece(baseStart+2, color, this, 3);
        GamePiece gamePiece4 = new GamePiece(baseStart+3, color, this, 4);

        gamePieces = new GamePiece[]{gamePiece1, gamePiece2, gamePiece3, gamePiece4};
    }

    public String getTextColor() {
        return textColor;
    }

    public int getBaseStart() {
        return baseStart;
    }

    public int getHomeStart() {
        return homeStart;
    }

    public int getFirstField() {
        return firstField;
    }

    public int getLastField() {
        return lastField;
    }

    /* Getter for attribute color */
    public Color getColor() {
        return this.color;
    }
    public GamePiece[] getGamePieces() {
        return gamePieces;
    }
    public void setSameTurn(boolean b){
        this.sameTurn = b;
    }
    public boolean getSameTurn(){
        return this.sameTurn;
    }

    @Override
    public String toString() {
        return "Player " + color;
    }


    public int roll(Dice d){

        return d.roll();
    }
    /* returns a number between 1 and 6 */
    public int rollTheDice(Dice d) {
        int eyeCount = 0;

        if(canMove() || sameTurn == true ) { // If one game piece is on the field, you're only allowed to roll the dice once or the person is having the second rolling phase in one game phase

            eyeCount = d.roll();

        }
        else if(sameTurn == false) { // if a player rolls the dice
            // Roll the dice 3 times if no game piece is on the field or until the person rolls a 6
            for(int i = 0; i < 3; i++) {

                // Times rolled dice in dependency of button pressed -> rollingButton.onAction -> timesRolledDice+=1
                eyeCount = d.roll();
                if(eyeCount == 6){
                    break;
                }

            }
        }
        return eyeCount;
    }
    public boolean canMove(){
        int counter = 0;
        for(int i = 0; i < 4; i++){
            if(gamePieces[i].getState() == GamePieceState.IN_BASE || gamePieces[i].getState() == GamePieceState.IN_HOME)
                counter += 1;
        }
        if(counter == 4) // all of them are in base or home, -> roll 3 times
            return false;
        else             //  someone is on the field -> roll once
            return true;
    }
    /* checks if a game piece is movable or not */
    private boolean isMovable(int eyeCount, GamePiece gamePiece, GamePiece[] gameBoard, int thereIsSomethingInTheWay) {

        /* cases */
        if (gamePiece.getState() == GamePieceState.IN_BASE && eyeCount != 6) {   // if piece is in base it cannot be moved if player didn't roll a 6
            return false;
        } else if (gamePiece.getThrowDestination() >= thereIsSomethingInTheWay && gamePiece.getPosition() < thereIsSomethingInTheWay ) {    // if piece would exceed home or skip over other pieces in home
            return false;
        } else if(gamePiece.getThrowDestination() > this.homeStart + 3){  // catching all special cases (skip over other pieces in home)
            return false;
        }else if(gamePiece.getPosition() == this.homeStart && gameBoard[homeStart+1] != null){ // (skip over other pieces in home)
            return false;
        }else if(gamePiece.getPosition() == this.homeStart && gameBoard[homeStart+2] != null && eyeCount > 1){ // (skip over other pieces in home)
            return false;
        }else if(gamePiece.getPosition() == this.homeStart + 1 && gameBoard[homeStart+2] != null) { //(skip over other pieces in home)
            return false;
        }else if (gameBoard[gamePiece.getThrowDestination()] != null && gameBoard[gamePiece.getThrowDestination()].getColor() == this.color) {    // if piece of same color occupies field
            return false;
        }

        return true;
    }
    /* checks which game pieces of a player are currently movable and returns them in an array */
    public ArrayList<GamePiece> checkMovablePieces(int eyeCount, GamePiece[] gameBoard) {
        ArrayList<GamePiece> movablePieces = new ArrayList<>();

        for (GamePiece g : this.gamePieces) {
            g.setThrowDestination(eyeCount);    // update throwDestination attribute for game piece g
        }

        boolean isFirstFieldOccupied = gameBoard[this.firstField] != null && gameBoard[this.firstField].getColor() == this.color;    // true if first field is occupied by own color

        /* if roll was 6 this HAS to be moved if possible: */
        if (eyeCount == 6 && isFirstFieldOccupied != true) {    // if a 6 was rolled a game piece from the base has to be moved, unless it is blocked
            for (GamePiece g : this.gamePieces) {
                if (g.getState() == GamePieceState.IN_BASE) {    // first game piece that is in the base is added to movablePieces
                    movablePieces.add(g);
                    return movablePieces;   // this piece has to be moved so there will be only one option for the player
                }
            }
        }
        int thereIsSomethingInTheWay = homeStart+4;   // position that exceeds home ("end of home + 1"), map ends here
        /* for checking if pieces would be skipped inside home */
        for (int i = homeStart; i <= homeStart+3; i++) {
            if (gameBoard[i] != null) {
                thereIsSomethingInTheWay = i; // index of field in home which cannot be exceeded
                break;
            }
        }
        /* this piece HAS to be moved if possible: */
        if (isFirstFieldOccupied && isMovable(eyeCount, gameBoard[this.firstField], gameBoard, thereIsSomethingInTheWay)) {  // if player has a piece on their first walkable field
            movablePieces.add(gameBoard[this.firstField]);
            return movablePieces;   // this piece has to be moved
        }

        /* if no piece HAS to be moved, each movable piece is added to movablePieces */
        for (GamePiece g : gamePieces) {
            if (isMovable(eyeCount, g, gameBoard, thereIsSomethingInTheWay)) {
                movablePieces.add(g);
            }
        }

        return movablePieces;
    }
    public GamePiece moveGamePiece(GameBoard gameBoard, GamePiece gamePiece) { // moves the chosen game piece
        GamePiece toReturn = null;
        int newPosition = gamePiece.getThrowDestination();
        int oldPosition = gamePiece.getPosition();

        if(gameBoard.getGameBoard()[newPosition] != null) { //if a kick happens
            GamePiece toKick = gameBoard.getGameBoard()[newPosition];
            gamePiece.kick(gameBoard, toKick);
            toReturn = toKick; // kicked Game Piece needs to be visually placed too
        }
        gameBoard.placeGamePiece(this, gamePiece, newPosition); // places the game piece to new spot
        gameBoard.placeGamePiece(this, null, oldPosition); // resets old position to null

        return toReturn;
    }

    /* checks whether all game pieces of a player are in the home, causing player to win */
    public boolean hasWon() {

        for (GamePiece g : gamePieces) {    // for all game pieces of player
            if (!(g.getPosition() >= homeStart && g.getPosition() <= homeStart+3)) {  // if position of game piece is not within the indexes of the home
                return false;
            }
        }

        return true;
    }



}