package backend;

import java.util.ArrayList; // imports the needed packages
import java.util.Scanner;

public class Input { // everything that has to do with an input is in here

    // Attributes
    private boolean start_available; // all availabilities describe the current usable aspects, so the help method can give out the current needed command
    private boolean roll_available;
    private boolean move_available;
    private boolean amount_available;

    private boolean red_available;
    private boolean green_available;
    private boolean yellow_available;
    private boolean blue_available;

    private int amountPlayers;
    private Color currentColor;
    private int id;
    private Scanner s; // scans input
    private String input;

    // Constructor
    public Input() {
        this.s = new Scanner(System.in);
        this.start_available = true;
    }

    // Getters
    public int getAmountPlayers() {
        return amountPlayers;
    }
    public int getId() {
        return id;
    }
    public Color getCurrentColor(){
        return currentColor;
    }

    // Setters
    public void setInputToGamePhase() { // preparation for starting input
        red_available = false;
        green_available = false;
        yellow_available = false;
        blue_available = false;
    }
    // Methods
    public void startingInput() { // the start input phase (amount of players and colors)

        System.out.print("\t\t\t\t> ");
        this.input = s.nextLine();

        if (input.equals("start") && start_available) { // waits for 'start' and then the game starts
            try {
                System.out.print("\t\t\t\tThe Game is starting\r");
                Thread.sleep(500);
                System.out.print("\t\t\t\tThe Game is starting .\r");
                Thread.sleep(500);
                System.out.print("\t\t\t\tThe Game is starting . .\r");
                Thread.sleep(500);
                System.out.print("\t\t\t\tThe Game is starting . . .\r");
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\t\t\t\tThe Game is starting\r");

            System.out.println("\t\t\t\tselect the amount of players (2 - 4)");
            start_available = false;
            amount_available = true;
        }

        else if ((input.startsWith("amount_") && amount_available)) { // input for the amount of players
            try {
                amountPlayers = Integer.parseInt(input.substring(7));
                if (!(amountPlayers >= 2 && amountPlayers <= 4)) {
                    System.out.println("\t\t\t\tenter a player count in between 2 and 4");
                    startingInput();
                }
            }
            catch (NumberFormatException e) { // catching exceptions
                System.out.println("\t\t\t\tnot a valid number");
                startingInput();
            }


            System.out.println("\t\t\t\tPlayers: " + amountPlayers); // amount is selected and enables the color choice
            amount_available = false;
            red_available = true;
            green_available = true;
            yellow_available = true;
            blue_available = true;
        }

        // choosing available colors
        else if (input.equals("red") && red_available) {
            currentColor = Color.RED;
            red_available = false;
        }
        else if (input.equals("green") && green_available) {
            currentColor = Color.GREEN;
            green_available = false;
        }
        else if (input.equals("yellow") && yellow_available) {
            currentColor = Color.YELLOW;
            yellow_available = false;
        }
        else if (input.equals("blue") && blue_available) {
            currentColor = Color.BLUE;
            blue_available = false;
        }

        // if u need the current commands
        else if (input.equals("help")) {
            help();
            startingInput(); // Recursive call
        }
        // if u need the rules
        else if (input.equals("rules")) {
            rules();
            startingInput(); // Recursive call
        }
        // if there is a typo or a wrong input
        else {
            wrongInput();
            startingInput(); // Recursive call
        }
    }
    // rules of the game
    private void rules() {
        System.out.println("\t\t\t\t--------------------------------Rules-------------------------------- \n" +
                "\t\t\t\t- You can roll the dice 3 times if you can´t move out of your base \n" +
                "\t\t\t\t  or if you can´t move with your game pieces.\n" +
                "\t\t\t\t- The game piece can be moved as far as the number on the dice.\n" +
                "\t\t\t\t- If you roll a 6 you can move out of your base with one game piece. \n" +
                "\t\t\t\t- As soon as the game piece is out of the base, it must be moved \n" +
                "\t\t\t\t  directly away from the starting point.\n" +
                "\t\t\t\t- If you roll a 6, you can roll again.\n" +
                "\t\t\t\t- Two game pieces cannot stand on one field.\n" +
                "\t\t\t\t- If you come to a field that is already occupied, the game piece is \n" +
                "\t\t\t\t  kicked out and his piece lands back in the base.\n" +
                "\t\t\t\t- Game pieces who are already in the house cannot be jumped over.\n" +
                "\t\t\t\t- You win when all the game pieces are in the house.\n" + "\n" +
                "\t\t\t\tNow it´s time to play. Have fun :)\n" +
                "\t\t\t\t---------------------------------------------------------------------\n");
    }
    // shows the possible commands for the current situation
    private void help() {
        System.out.println("\t\t\t\t-----------------Commands-----------------");
        if (start_available) {
            System.out.println("\t\t\t\t'start' - starts the game");
        }
        if (move_available) {
            System.out.println("\t\t\t\t'move_id' - move your chosen gamePiece by id (ex. move_1)");
        }
        if (roll_available) {
            System.out.println("\t\t\t\t'roll' - roll the dice and get a random number between 1 and 6");
        }
        if (amount_available) {
            System.out.println("\t\t\t\tamount_value - specifies the player amount");
        }
        if (red_available) {
            System.out.println("\t\t\t\t'red' - available");
        }
        if (green_available) {
            System.out.println("\t\t\t\t'green' - available");
        }
        if (yellow_available) {
            System.out.println("\t\t\t\t'yellow' - available");
        }
        if (blue_available) {
            System.out.println("\t\t\t\t'blue' - available");
        }
        System.out.println("\t\t\t\t'rules' - shows the rules");
    }

    // the input for the rolling phase
    public void inputRoll(){
        System.out.print("\t\t\t\t> ");
        this.input = s.nextLine();
        roll_available = true; // Now you can adress the roll commands
        if(input.equals("roll")) {
            roll_available = false; // it was a successful roll, now you don't need the roll command
        }
        else if (input.equals("help")) { // if the user asks for possible commands
            help();
            inputRoll();
        }
        else if(input.equals("rules")){ // if user wants the rules
            rules();
            inputRoll();
        }
        else{ // wrong input
            wrongInput();
            inputRoll();
        }
    }
    // the input for the moving phase
    public int inputMove(ArrayList<GamePiece> movableGamePieces) {
        this.move_available = true; // Now you can adress the move commands
        System.out.println("\n\t\t\t\t*--------------------------------------------*");
        System.out.println("\t\t\t\tThese figures are available for move:");
        for(GamePiece toPrint : movableGamePieces) // output of movable figures
            System.out.println("\t\t\t\t- Figure " + toPrint);

        System.out.println("\t\t\t\t*--------------------------------------------*");
        System.out.println();

        System.out.println("\t\t\t\tMove your characters by typing 'move_"+ TextColor.CYAN+"id"+ TextColor.END+"'");
        System.out.print("\t\t\t\t> ");
        this.input = s.nextLine();
        if (input.startsWith("move_")) { // selection of game piece
            try {
                int chosenGamePiece = Integer.parseInt(String.valueOf(input.substring(5))); // the chosen game piece

                if (!(chosenGamePiece >= 1 && chosenGamePiece <= 4))  { // if they mess up
                    System.out.println("\n\t\t\t\t"+ TextColor.RED+"This wasn't a viable choice"+ TextColor.END);
                    return inputMove(movableGamePieces);
                }
                else if( chosenGamePiece >= 1 && chosenGamePiece <= 4){
                    for(GamePiece toMove : movableGamePieces){
                        if(toMove.getId() == chosenGamePiece) { // successful choice
                            this.move_available = false;
                            return chosenGamePiece - 1;
                        }
                    }
                    System.out.println("\n\t\t\t\t"+ TextColor.RED+"This wasn't a viable choice"+ TextColor.END);
                    return inputMove(movableGamePieces);
                }
            } catch (NumberFormatException e) { // if they mess up
                System.out.println("\n\t\t\t\t"+ TextColor.RED+"This wasn't a viable choice"+ TextColor.END);
                return inputMove(movableGamePieces);
            }
        }
        else if (input.equals("help")) { // if the user asks for possible commands
            help();
            return inputMove(movableGamePieces);
        }
        else if(input.equals("rules")){ // if the user asks for rules
            rules();
            return inputMove(movableGamePieces);
        }
        else { // if the user messes up
            wrongInput();
            return inputMove(movableGamePieces);
        }
        return 0;
    }

    public void wrongInput() { // if a user did a wrong input
        System.out.println("\t\t\t\t"+ TextColor.RED+"Wrong input ["+input+"] Type 'help' to show the list of possible inputs" +
                " or type 'rules' to see the rules of the game."+ TextColor.END);
    }
}
