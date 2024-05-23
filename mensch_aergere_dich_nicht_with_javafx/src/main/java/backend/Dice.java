package backend;

import java.util.*;
public class Dice {
    public static Random numberGenerator;
    private static int eyeCount; //
    public Dice(){
        this.numberGenerator = new Random();
    }

    public int allRollTheDice(int amountPlayers) { // Alle würfeln - Entscheidung wer anfängt
        return numberGenerator.nextInt(0, amountPlayers);
    }
    public int roll(){
        return eyeCount = numberGenerator.nextInt(1,7);
    }
    public static int getEyeCount(){
        return eyeCount;
    }
}