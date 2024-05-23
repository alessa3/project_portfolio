package backend;



public class VisualBoard {
    private String textColor;
    private GamePiece[] gameBoard;
    char[][] map;
    public VisualBoard(GamePiece[] gameBoard){
        this.gameBoard = gameBoard;
        this.map = new char [][]
                {
                        //0   1   2   3   4   5   6   7   8   9   10  11  12  13  14
                        {'X','-','-','-','-','-','-','-','-','-','-','-','-','-','X'}, // 0
                        {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|'}, // 1
                        {'|',' ','o','o',' ',' ','o','o','o',' ',' ','o','o',' ','|'}, // 2
                        {'|',' ','o','o',' ',' ','o','g','o',' ',' ','o','o',' ','|'}, // 3
                        {'|',' ',' ',' ',' ',' ','o','g','o',' ',' ',' ',' ',' ','|'}, // 4
                        {'|',' ',' ',' ',' ',' ','o','g','o',' ',' ',' ',' ',' ','|'}, // 5
                        {'|',' ','o','o','o','o','o','g','o','o','o','o','o',' ','|'}, // 6
                        {'|',' ','o','y','y','y','y',' ','r','r','r','r','o',' ','|'}, // 7
                        {'|',' ','o','o','o','o','o','b','o','o','o','o','o',' ','|'}, // 8
                        {'|',' ',' ',' ',' ',' ','o','b','o',' ',' ',' ',' ',' ','|'}, // 9
                        {'|',' ',' ',' ',' ',' ','o','b','o',' ',' ',' ',' ',' ','|'}, // 10
                        {'|',' ','o','o',' ',' ','o','b','o',' ',' ','o','o',' ','|'}, // 11
                        {'|',' ','o','o',' ',' ','o','o','o',' ',' ','o','o',' ','|'}, // 12
                        {'|',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','|'}, // 13
                        {'X','-','-','-','-','-','-','-','-','-','-','-','-','-','X'}  // 14
                }; // representation ot the default map as a char array

    }
    // updates the position of the players and sets their icon
    public void updateMap(Player player, GamePiece gamePiece, int pos) {
        char icon;
        this.textColor = player.getTextColor();
        // if the game piece exists its icon is set to the initial of it's color
        if(gamePiece != null) {
            icon = gamePiece.getColor().getFirstLetterOfColorName();
        }
        // if it doesn't exist (case: it's moving and setting the old position to null) it's icon is set to the default icon
        else {
            // if the game piece moves on its home the icon of the previous field is set to the initial but in lowercase
            if (pos >= player.getHomeStart() && pos <=  player.getHomeStart() + 4) {
                icon = Character.toLowerCase(player.getColor().getFirstLetterOfColorName());
            }
            // if the game piece moves on the main field it's icon of the icon of the previous field is set to an 'o'
            else {
                icon = 'o';
            }
        }

        switch(pos) {
            // gameBoard ( 40 + 16 + 16 ) = 72
            /*---------------------------------------------------------*/
            // Main Fields
            case 0  -> map[12][6] = icon;  case 20 -> map[2][8]  = icon;
            case 1  -> map[11][6] = icon;  case 21 -> map[3][8]  = icon;
            case 2  -> map[10][6] = icon;  case 22 -> map[4][8]  = icon;
            case 3  -> map[9][6]  = icon;  case 23 -> map[5][8]  = icon;
            case 4  -> map[8][6]  = icon;  case 24 -> map[6][8]  = icon;
            case 5  -> map[8][5]  = icon;  case 25 -> map[6][9]  = icon;
            case 6  -> map[8][4]  = icon;  case 26 -> map[6][10] = icon;
            case 7  -> map[8][3]  = icon;  case 27 -> map[6][11] = icon;
            case 8  -> map[8][2]  = icon;  case 28 -> map[6][12] = icon;
            case 9  -> map[7][2]  = icon;  case 29 -> map[7][12] = icon;
            case 10 -> map[6][2]  = icon;  case 30 -> map[8][12] = icon;
            case 11 -> map[6][3]  = icon;  case 31 -> map[8][11] = icon;
            case 12 -> map[6][4]  = icon;  case 32 -> map[8][10] = icon;
            case 13 -> map[6][5]  = icon;  case 33 -> map[8][9]  = icon;
            case 14 -> map[6][6]  = icon;  case 34 -> map[8][8]  = icon;
            case 15 -> map[5][6]  = icon;  case 35 -> map[9][8]  = icon;
            case 16 -> map[4][6]  = icon;  case 36 -> map[10][8] = icon;
            case 17 -> map[3][6]  = icon;  case 37 -> map[11][8] = icon;
            case 18 -> map[2][6]  = icon;  case 38 -> map[12][8] = icon;
            case 19 -> map[2][7]  = icon;  case 39 -> map[12][7] = icon;
            /*---------------------------------------------------------*/
            // Red Base                     // Red House
            case 40 -> map[11][11] = icon;  case 56 -> map[7][11] = icon;
            case 41 -> map[11][12] = icon;  case 57 -> map[7][10] = icon;
            case 42 -> map[12][11] = icon;  case 58 -> map[7][9]  = icon;
            case 43 -> map[12][12] = icon;  case 59 -> map[7][8]  = icon;
            /*---------------------------------------------------------*/
            // Green Base                   // Green House
            case 44 -> map[2][11] = icon;   case 60 -> map[3][7] = icon;
            case 45 -> map[2][12] = icon;   case 61 -> map[4][7] = icon;
            case 46 -> map[3][11] = icon;   case 62 -> map[5][7] = icon;
            case 47 -> map[3][12] = icon;   case 63 -> map[6][7] = icon;
            /*---------------------------------------------------------*/
            // Yellow Base                  // Yellow House
            case 48 -> map[2][2] = icon;    case 64 -> map[7][3] = icon;
            case 49 -> map[2][3] = icon;    case 65 -> map[7][4] = icon;
            case 50 -> map[3][2] = icon;    case 66 -> map[7][5] = icon;
            case 51 -> map[3][3] = icon;    case 67 -> map[7][6] = icon;
            /*---------------------------------------------------------*/
            // Blue Base                    // Blue House
            case 52 -> map[11][2] = icon;   case 68 -> map[11][7] = icon;
            case 53 -> map[11][3] = icon;   case 69 -> map[10][7] = icon;
            case 54 -> map[12][2] = icon;   case 70 -> map[9][7]  = icon;
            case 55 -> map[12][3] = icon;   case 71 -> map[8][7]  = icon;
        }
    }

    // Gibt die Map inklusive Farben auf der Konsole aus
    private void fill(char[][] map, int i, int j) {
        String format = ""; // the background of each icon is default set to none

        // looks if there is a red game piece is on the red start position and colors it black
        if (i == 8 && j == 12)
            // red game piece on red background -> colors the game piece to black
            if (map[i][j] == 'R')
                format = TextColor.BLACK + TextColor.RED_BG;
            // different color on red background
            else
                format = TextColor.RED_BG;

        // looks if there is a green game piece is on the red start position and colors it black
        if (i == 2 && j == 8)
            // green game piece on green background -> colors the game piece to black
            if (map[i][j] == 'G')
                format = TextColor.BLACK + TextColor.GREEN_BG;
            // different color on green background
            else
                format = TextColor.GREEN_BG;

        // looks if there is a yellow game piece is on the red start position and colors it black
        if (i == 6 && j == 2)
            // yellow game piece on yellow background -> colors the game piece to black
            if (map[i][j] == 'Y')
                format = TextColor.BLACK + TextColor.YELLOW_BG;
            // different color on yellow background
            else
                format = TextColor.YELLOW_BG;

        // looks if there is a blue game piece is on the red start position and colors it black
        if (i == 12 && j == 6)
            // blue game piece on blue background -> colors the game piece to black
            if (map[i][j] == 'B')
                format = TextColor.BLACK + TextColor.BLUE_BG;
            // different color on blue background
            else
                format = TextColor.BLUE_BG;


        // Colors the Icons R, G, Y, B to their given color and if included also a background

        // if there is an R on the map it gets colored red
        if (map[i][j] == 'R') {
            System.out.print(TextColor.RED + format + 'R');
        }
        // if there is a G on the map it gets colored green
        else if (map[i][j] == 'G') {
            System.out.print(TextColor.GREEN + format + 'G');
        }
        // if there is a Y on the map it gets colored yellow
        else if (map[i][j] == 'Y') {
            System.out.print(TextColor.YELLOW + format + 'Y');
        }
        // if there is a B on the map it gets colored blue
        else if (map[i][j] == 'B') {
            System.out.print(TextColor.BLUE + format + 'B');
        }
        // every other icon gets no color
        else {
            System.out.print(format + map[i][j]);
        }
        System.out.print(TextColor.END + "  "); // ends the color stream and sets space between the icons
    }

    // outputs the map on the console with the use of a loop
    public void drawMap() {
        for(int i = 0; i < 15; i++) {
            System.out.print("\n\t\t\t\t");
            for(int j = 0; j < 15; j++) {
                fill(map,i,j);
            }
        }
    }
}
