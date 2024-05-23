package backend;

public abstract class TextColor {
    // ANSII Farbcode

    // Beispiel  System.out.println("\033[1;31m" + "Dieser Text ist Rot" + "\u001B[0m");
    // oder System.out.println(RED + "Dieser Text ist Rot" + END);

    // Die END Konstante wird am Ende des Farbbereiches gesetzt

    static final String RED = "\033[1;31m";
    static final String GREEN = "\033[1;32m";
    static final String YELLOW = "\033[1;33m";
    static final String BLUE = "\033[1;34m";
    static final String PURPLE = "\033[1;35m";
    static final String CYAN = "\033[1;36m";

    static final String BLACK = "\033[1;30m";

    static final String RED_BG = "\033[1;41m";
    static final String GREEN_BG = "\033[1;42m";
    static final String YELLOW_BG = "\033[1;43m";
    static final String BLUE_BG = "\033[1;44m";

    static final String END = "\u001B[0m";
}
