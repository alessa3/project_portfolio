package backend;

public enum Color {
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    RED("Red");
    private final String name;
    private final char firstLetterOfColorName;   // for console frontend


    private static Color[] chosenColors;

    Color(String name){
        this.name = name;
        this.firstLetterOfColorName = name.charAt(0);    // first character of name
    }
    public static void setAmountPlayers(int amountPlayers){
        Color.chosenColors = new Color[amountPlayers];  // necessary for color selection in the 3rd scene
    }

    public static void setCurrentColor(int playerIndex,Color colorFromButtonId) { // for the color selection at the beginning
        Color.chosenColors[playerIndex] = colorFromButtonId;
    }

    public static Color getPlayerColor(int i) {
        return Color.chosenColors[i];
    }

    public char getFirstLetterOfColorName() {
        return firstLetterOfColorName;
    }

    @Override
    public String toString(){
        return this.name;
    }


}
