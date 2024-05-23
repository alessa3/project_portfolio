package backend;

import java.util.HashMap;

public class GameBoard {
    private final GamePiece[] gameBoard;
    private static HashMap<Integer,int[]> transferValues;
    VisualBoard visualBoard;


    public GameBoard(){
        this.gameBoard = new GamePiece[72];
    }

    public GamePiece[] getGameBoard() {
        return this.gameBoard;
    }
    public static int[] getTransferValue(int pos){
        return transferValues.get(pos);
    }
    public boolean checkKick(int boardpos){ // only works for moving buttons :), because the conditions are too general
        if(gameBoard[boardpos] != null){
            return true;
        }
        return false;
    }

    public void placeGamePiece(Player player, GamePiece gamePiece, int boardPosition) {
        this.gameBoard[boardPosition] = gamePiece;
        if(gamePiece != null) {
            gamePiece.setPosition(boardPosition, this);
        }
    }

}
