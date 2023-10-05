package game.utility.dice;

import java.util.Random;

//Simple class for creating a Dice with custom side count. Can be used to roll a random number between 1 and sideCount
public class Dice {

    private int sideCount;
    private Random rng;

    public Dice(int sides){
        sideCount = sides;
        rng = new Random();
    }

    //Returns how many sides the dice has
    public int getSideCount(){
        return sideCount;
    }

    //Rolls the dice and returns the result
    public int roll(){
        return rng.nextInt(1, sideCount + 1);
    }
}