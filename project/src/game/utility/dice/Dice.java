package game.utility.dice;

import java.util.concurrent.ThreadLocalRandom;

import exception.dice.InvalidDiceSideCountException;

//Simple class for creating a Dice with custom side count. Can be used to roll a random number between 1 and sideCount
public class Dice {

    private int sideCount;

    public Dice(int sides) throws InvalidDiceSideCountException{
        setSides(sides);
    }

    public void setSides(int sides) throws InvalidDiceSideCountException{
        if(sides < 1)
            throw new InvalidDiceSideCountException();

        sideCount = sides;
    }

    //Returns how many sides the dice has
    public int getSideCount(){
        return sideCount;
    }

    //Rolls the dice and returns the result
    public int roll(){
        return ThreadLocalRandom.current().nextInt(1, sideCount + 1);
    }
}